; "Sprite Control.bb" - Include file

; Main code by Syntax Error

; Modified by Barliesque
; - Re-wrote CopyImage3D() to create an independant copy of a sprite
; - Modified texture application technique in various functions to allow
;       sprite copies to use different frames of the same animated texture
; - Fixed an error in ImageToSprite(), so that parent parameter defaults correctly
;
;
; Thanks go to:
; -------------
; SkidRacer        - Pixel-Perfect sprites (pixies.bb)
; Rob Cummings     - Information on using a 2nd sprite camera (now defunct)
; FredBorg         - HandleImage3D() and CopyImage3D()
; EricZann         - Idea for ModifyText3D
; Ghislain Schoofs - Suggestion for optional 'Parent' flag to give more flexibility
; BlitzSupport     - MaskImage3D - modified LoadMaskedTexture
; Bot Builder      - MidHandle3D - modified Mid3DHandle from code archives

;---------------------------------------------------------------------------------

Global spritepivot,spritecamera

;---------------------------------------------------------------------------------

; Create a complete sprite display with camera/pivot
Function SpriteGraphics3D(gw,gh,d=0,m=0,pivotdist#=1.0)

	Graphics3D gw,gh,d,m
	SetBuffer BackBuffer()
	spritecamera=CreateCamera()
	spritepivot=CreateSpritePivot(spritecamera,pivotdist)

End Function

;---------------------------------------------------------------------------------

; Free up all entites, brushes, textures, and close the 3d display
Function EndGraphics3D()

	ClearWorld
	EndGraphics

End Function

;---------------------------------------------------------------------------------

; Create a single 'sprite pivot' and scale to screen resolution
Function CreateSpritePivot(parentcam=0,dist#=1.0)

	Local gw=GraphicsWidth()
	Local gh=GraphicsHeight()
	spritepivot=CreatePivot(parentcam)
	Local aspect#=Float(gh)/Float(gw)
	Local scale#=2.0/gw
	PositionEntity spritepivot,-1,aspect,dist
	ScaleEntity spritepivot,scale,scale,scale
	NameEntity spritepivot,"SpriteControl Pivot"
	Return spritepivot

End Function

;---------------------------------------------------------------------------------

; Use this command to zoom the main camera and sprite pivot
; NOTE: Only positive values if 1 and greater can be used
Function CameraZoom3D(cam,z#)

	If z<1.0 z=1.0
	If EntityClass$(cam)="Camera" CameraZoom cam,z
	Local numchilds=CountChildren(cam)
	If numchilds>0
		For c=1 To numchilds
			Local child=GetChild(cam,c)
			If EntityName$(child)="SpriteControl Pivot"
				PositionEntity child,EntityX(child),EntityY(child),z+0.001
				Exit
			EndIf
		Next
	EndIf

End Function

;---------------------------------------------------------------------------------

; Create a blank quad sprite
Function CreateImage3D(w=1,h=1,par=-1,u2#=1.0,v2#=1.0)

	If par=-1 par=spritepivot
	Local sprite=CreateMesh(par)
	Local s=CreateSurface(sprite)
	AddVertex s,0,0,0 ,0,0
	AddVertex s,2,0,0 , u2,0
	AddVertex s,0,-2,0 ,0,v2
	AddVertex s,2,-2,0 , u2,v2
	AddTriangle s,0,1,2
	AddTriangle s,3,2,1
	ScaleEntity sprite,Float(w)/2,Float(h)/2,1
	EntityFX sprite,1+16+32
	EntityOrder sprite,-100
	PositionEntity sprite,-10000,-10000,0
	Return sprite

End Function

;---------------------------------------------------------------------------------

; Load an image into a texture and attach it to a quad sprite
Function LoadImage3D(file$,texflags=4,par=-1)

	If par=-1 par=spritepivot
	Local tmpimage=LoadImage(file$)
	sprite=ImageToSprite(tmpimage,texflags)
	FreeImage tmpimg
	Return sprite

End Function

;---------------------------------------------------------------------------------

; Load and create a quad sprite with animation frames
Function LoadAnimImage3D(file$,framesX=1,framesY=1,texflags=4,par=-1)

	If par=-1 par=spritepivot
	If frames<2 Then frames=2
	Local tmpimage=LoadImage(file$)
	Local iw=ImageWidth(tmpimage)
	Local ih=ImageHeight(tmpimage)
	Local tw=2 Shl (Len(Int(Bin(iw-1)))-1)
	Local th=2 Shl (Len(Int(Bin(ih-1)))-1)
	FreeImage tmpimg
	sprite=ImageToSprite(tmpimage,texflags,framesX,framesY)
	Local ename$="W"+Float(tw)/(Float(iw)/Float(framesX))
	ename$=ename$+"H"+Float(th)/(Float(ih)/Float(framesY))
	ename$=ename$+"X"+Str$(framesX)
	ename$=ename$+"Y"+Str$(framesY)+"E"
	NameEntity sprite,ename$
	Return sprite

End Function

;---------------------------------------------------------------------------------

; Grab an area of the current drawing buffer into a quad sprite
Function GrabImage3D(x,y,iw,ih,par=-1)

	If par=-1 par=spritepivot
	Local tmpimage=CreateImage(iw,ih)
	GrabImage tmpimage,x,y
	sprite=ImageToSprite(tmpimage)
	FreeImage tmpimage
	Return sprite

End Function

;---------------------------------------------------------------------------------

; Duplicate an existing quad sprite
Function CopyImage3D(sprite,Alpha#=1.0,Blend=1)

	Local i,x,y
	Local vx#,vy#,vz#,u#,v#
	Local Surf = GetSurface(sprite,1)
   Local par = GetParent(sprite)
	Local NewSprite = CreateMesh(par)
	Local NewSurf = CreateSurface(NewSprite)

	i=0
   for y=0 to -2 step -2
      for x=0 to 2 step 2
			u  = VertexU(Surf,i)
			v  = VertexV(Surf,i)
			AddVertex NewSurf, x,y,0, u,v
	   next
	next
	AddTriangle NewSurf,0,1,2
	AddTriangle NewSurf,3,2,1

	positionentity NewSprite, EntityX(sprite),EntityY(sprite),EntityZ(sprite)
	ResizeImage3D NewSprite, ImageWidth3D(sprite),ImageHeight3D(sprite)
	
	EntityFX    NewSprite, 17
	EntityOrder NewSprite, -100
	EntityBlend NewSprite, Blend
	EntityAlpha NewSprite, Alpha
	NameEntity  NewSprite, EntityName(sprite)
	entitytexture NewSprite, GetSpriteTexture(sprite)
	Return NewSprite

End Function

;---------------------------------------------------------------------------------

; Create a quad sprite from a string of text
Function Text3D(xpos,ypos,txt$,flags=5,par=-1)

	If par=-1 par=spritepivot
	If t$="" Then t$="?"
	Local gbuffer=GraphicsBuffer()
	Local tmpimage=CreateImage(StringWidth(txt$),FontHeight())
	SetBuffer ImageBuffer(tmpimage)
	Cls :	Text 0,0,txt$ :	SetBuffer gbuffer
	textsprite=ImageToSprite(tmpimage,flags)
	FreeImage tmpimage
	DrawImage3D textsprite,xpos,ypos
	Return textsprite

End Function

;---------------------------------------------------------------------------------

; Modify existing text in quad sprite
Function ModifyText3D(sprite,xpos,ypos,t$,flags=5)

	If t$="" Then t$="?"
	Local par=GetParent(sprite)
	FreeImage3D sprite
	Local newstextsprite=Text3D(xpos,ypos,t$,flags,par)
	Return newsprite
	
End Function

;---------------------------------------------------------------------------------

; Position quad sprite at 2D screen coordinates
Function DrawImage3D(sprite,x,y,frame=-99999,z#=0)

	PositionEntity sprite,x+0.5,-y+0.5,z
	If frame<>-99999
		Local en$=EntityName(sprite)
		Local fw#=Float(Mid$(en$,Instr(en$,"W")+1,Instr(en$,"H")-Instr(en$,"W")))
		Local fh#=Float(Mid$(en$,Instr(en$,"H")+1,Instr(en$,"X")-Instr(en$,"H")))
		Local framesH=Int(Mid$(en$,Instr(en$,"X")+1,Instr(en$,"Y")-Instr(en$,"X")))
		Local framesV=Int(Mid$(en$,Instr(en$,"Y")+1,Instr(en$,"E")-Instr(en$,"Y")))
		Local numframes=framesH*framesV
		If frame>numframes frame=(frame Mod numframes)
		If frame<1 frame=numframes-(Abs(frame) Mod numframes)
		frame=frame-1

		local frameX = frame mod framesH
		local frameY = int(frame/framesH)

		Local u1#=float(frameX)/float(framesH)
		Local v1#=float(frameY)/float(framesV)
	   Local u2#=u1 + (1.0/FramesH)
	   Local v2#=v1 + (1.0/FramesV)

		local s=GetSurface(sprite,1)
		VertexTexCoords s,0,u1,v1	
		VertexTexCoords s,1,u2,v1	
		VertexTexCoords s,2,u1,v2	
		VertexTexCoords s,3,u2,v2	
	EndIf
		
End Function

;---------------------------------------------------------------------------------

; Scale a quad sprite to equivalent 2d pixel width/height values
Function ResizeImage3D(sprite,w,h)

	ScaleEntity sprite,Float(w)/2,Float(h)/2,1

End Function

;---------------------------------------------------------------------------------

; Flip the quad sprite horizontally or vertically
Function FlipImage3D(sprite,flipX=1,flipY=0)

	Local s=GetSurface(sprite,1)
	Local vx#,vy#,vz#
	If flipX
		vx#=VertexX(s,1) : vy#=VertexY(s,1) : vz#=VertexZ(s,1)
		VertexCoords s,1,VertexX(s,0),VertexY(s,0),VertexZ(s,0)
		VertexCoords s,0,vx,vy,vz
		vx#=VertexX(s,3) : vy#=VertexY(s,3) : vz#=VertexZ(s,3)
		VertexCoords s,3,VertexX(s,2),VertexY(s,2),VertexZ(s,2)
		VertexCoords s,2,vx,vy,vz
	EndIf
	If flipY
		vx#=VertexX(s,2) : vy#=VertexY(s,2) : vz#=VertexZ(s,2)
		VertexCoords s,2,VertexX(s,0),VertexY(s,0),VertexZ(s,0)
		VertexCoords s,0,vx,vy,vz
		vx#=VertexX(s,3) : vy#=VertexY(s,3) : vz#=VertexZ(s,3)
		VertexCoords s,3,VertexX(s,1),VertexY(s,1),VertexZ(s,1)
		VertexCoords s,1,vx,vy,vz
	EndIf

End Function

;---------------------------------------------------------------------------------

; Rotate a quad sprite by ang# angle
;       0 = normal
;      90 = clockwise by 90 degrees
;     180 = upside down
;     270 = anti-clockwise by 90 degrees
Function RotateImage3D(sprite,angle#)

	RotateEntity sprite,0,0,-angle

End Function

;---------------------------------------------------------------------------------

; Set the handle/axis of a quad sprite (courtesy of FredBorg)
;   handleX ->   -1 is left   0 is centre   1 is right
;   handleY ->   -1 is top    0 is centre   1 is bottom
Function HandleImage3D(sprite,handleX#,handleY#)

	MidHandle3D sprite
	Local s=GetSurface(sprite,1)
	VertexCoords s,0,VertexX(s,0)-handleX,VertexY(s,0)+handleY,VertexZ(s,0)
	VertexCoords s,1,VertexX(s,1)-handleX,VertexY(s,1)+handleY,VertexZ(s,1)
	VertexCoords s,2,VertexX(s,2)-handleX,VertexY(s,2)+handleY,VertexZ(s,2)
	VertexCoords s,3,VertexX(s,3)-handleX,VertexY(s,3)+handleY,VertexZ(s,3)

End Function

;---------------------------------------------------------------------------------

; Move the quad sprites handle/axis to the center (courtesy of bot builder)
Function MidHandle3D(sprite)

	Local ux#=-100000 , uy#=-100000 , uz#=-100000
	Local lx#=100000 , ly#=100000 , lz#=100000
	Local s=GetSurface(sprite,1)
	For v=0 To 3
		vx#=VertexX#(s,v) : vy#=VertexY#(s,v) : vz#=VertexZ#(s,v)
		If vx#<lx# Then lx#=vx#
		If vx#>ux# Then ux#=vx#
		If vy#<ly# Then ly#=vy#
		If vy#>uy# Then uy#=vy#
		If vz#<lz# Then lz#=vz#
		If vz#>uz# Then uz#=vz#
	Next
	ax#=(ux#+lx#)/2 : ay#=(uy#+ly#)/2 : az#=(uz#+lz#)/2
	PositionMesh sprite,-ax#,-ay#,-az#

End Function

;---------------------------------------------------------------------------------

; Return the X handle/axis position (-1 = Left  0 = Centre  +1 = Right)
Function ImageXHandle3D#(sprite)

	Return -VertexX(GetSurface(sprite,1),1)+1

End Function

;---------------------------------------------------------------------------------

; Return the Y handle/axis position (-1= Top  0 = Centre  +1 = Bottom)
Function ImageYHandle3D#(sprite)

	Return VertexY(GetSurface(sprite,1),0)-1

End Function

;---------------------------------------------------------------------------------

; Return width of a quad sprite in screen pixel dimensions
Function ImageWidth3D(sprite)

	Local x#=GetMatElement(sprite,0,0)
	Local y#=GetMatElement(sprite,0,1)
	Local z#=GetMatElement(sprite,0,2)
	Return Sqr(x*x+y*y+z*z) * GraphicsWidth()

End Function

;---------------------------------------------------------------------------------

; Return height of a quad sprite in screen pixel dimensions
Function ImageHeight3D(sprite)

	Local x#=GetMatElement(sprite,1,0)
	Local y#=GetMatElement(sprite,1,1)
	Local z#=GetMatElement(sprite,1,2)
	Return Sqr(x*x+y*y+z*z) * GraphicsWidth()

End Function

;---------------------------------------------------------------------------------

; Apply a color mask to the sprites texture (thanks to BlitzSupport)
Function MaskImage3D(sprite,r,g,b,numframes=1)
	Local temp=CreateTexture(1,1,flags)
	WritePixel 0,0,((r Shl 16)+(g Shl 8)+b) And $00FFFFFF,TextureBuffer(temp)
	Local trgb=ReadPixel(0,0,TextureBuffer(temp))
	r=trgb Shr 16 And $ff : g=trgb Shr 8 And $ff : b=trgb And $ff
	FreeTexture temp
	Local gb=GraphicsBuffer()
	Local tex=GetSpriteTexture(sprite)
	For frm=1 To numframes
		SetBuffer TextureBuffer(tex,frm-1)
		LockBuffer GraphicsBuffer()
		For x=0 To TextureWidth(tex)-1
			For y=0 To TextureHeight(tex)- 1
				Local rgb=ReadPixelFast(x,y) And $00FFFFFF
				If rgb=((r Shl 16)+(g Shl 8)+b)
					WritePixelFast x,y,$00000000
				Else
					WritePixelFast x,y,rgb Or $FF000000
				EndIf
			Next
		Next
		UnlockBuffer GraphicsBuffer()
	Next
	SetBuffer gb
End Function

;---------------------------------------------------------------------------------

; Return TRUE if two sprite images are overlapping
Function ImagesOverlap3D(sprite1,sprite2,ovl1=0,ovl2=0)

	Local iw1=ImageWidth3D(sprite1) , ih1=ImageHeight3D(sprite1)
	Local iw2=ImageWidth3D(sprite2) , ih2=ImageHeight3D(sprite2)
	Local fX=1,fY=1 , s=GetSurface(sprite1,1)
	If VertexX(s,0)>VertexX(s,1) fX=-1
	If VertexY(s,0)<VertexY(s,2) fY=3
	Local ix1#=Abs(EntityX(sprite1))-((ImageXHandle3D(sprite1)+fX)/2)*iw1+0.5
	Local iy1#=Abs(EntityY(sprite1))-((ImageYHandle3D(sprite1)+fY)/2)*ih1+0.5
	fX=1 : fY=1 :	s=GetSurface(sprite2,1)
	If VertexX(s,0)>VertexX(s,1) fX=-1
	If VertexY(s,0)<VertexY(s,2) fY=3
	Local ix2#=Abs(EntityX(sprite2))-((ImageXHandle3D(sprite2)+fX)/2)*iw2+0.5
	Local iy2#=Abs(EntityY(sprite2))-((ImageYHandle3D(sprite2)+fY)/2)*ih2+0.5
	Return RectsOverlap(ix1+ovl1,iy1+ovl1,iw1-ovl1,ih1-ovl1 , ix2+ovl2,iy2+ovl2,iw2-ovl2,ih2-ovl2)
	
End Function

;---------------------------------------------------------------------------------

; Convert an existing 2D image to quad sprite
Function ImageToSprite(img,texflags=5,numframesX=1,numframesY=1,par=-1)

	If par=-1 then par=spritepivot
	Local iw=ImageWidth(img) , ih=ImageHeight(img)
	Local tw=2 Shl (Len(Int(Bin(iw-1)))-1)
	Local th=2 Shl (Len(Int(Bin(ih-1)))-1)
	Local tex=CreateTexture(tw,th,texflags)
	Local ib=ImageBuffer(img) : LockBuffer ib
	Local tb=TextureBuffer(tex) : LockBuffer tb
	Local x,y
	For x=0 To iw-1
		For y=0 To ih-1
			rgbc=ReadPixelFast(x,y,ib) And $00ffffff
			If rgbc=((r Shl 16)+(g Shl 8)+b)
				WritePixelFast x,y,($00000000),tb
			Else
				WritePixelFast x,y,(rgbc Or $ff000000),tb
			EndIf
		Next
	Next
	UnlockBuffer ib : UnlockBuffer tb
	
	Local sprite=CreateImage3D(iw,ih,par,1.0/float(numframesX),1.0/float(numframesY))
	EntityTexture sprite,tex
	EntityFX sprite,16+1
	EntityOrder sprite,-100
	ScaleEntity sprite,Float(iw/numframesX)/2.0,Float(ih/numframesY)/2.0,1.0
	Return sprite
	
End Function

;---------------------------------------------------------------------------------

; Flush out an existing quad sprite and it's texture
Function FreeImage3D(sprite)

	Local brush=GetEntityBrush(sprite)
	Local tex=GetBrushTexture(brush)
	If brush<>0 FreeBrush brush
	If tex<>0 FreeTexture tex
	If sprite<>0 FreeEntity sprite

End Function

;---------------------------------------------------------------------------------

; Return the texture handle of a quad sprite
Function GetSpriteTexture(sprite)

	Local brush=GetEntityBrush(sprite)
	Local tex=GetBrushTexture(brush)
	FreeBrush brush
	Return tex

End Function



