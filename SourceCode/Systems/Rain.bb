; Singing in the rain
; Created by Mikkel Fredborg
; Use as you please!

Type Splashtex
	Field x#,y#
	Field sx#,sy#
	Field ox#,oy#
	Field c#
End Type

Type Splash3D
	Field sprite
	Field x#,y#,z#
	Field texture
	Field frame#,frames
	Field active
End Type

Type Rain3D
	Field sprite
	Field x#,y#,z#
	Field sx#,sy#,sz#
	Field active
End Type

Type Ring3D
	Field sprite
	Field x#,y#,z#
	Field texture
	Field frame#,frames
	Field active
End Type

Global s.Splashtex
Global s3d.Splash3D
Global r3d.Rain3D
Global t3d.Ring3D

; Colors
Global envr = 76
Global envg = 100
Global envb = 110

; Update the splashes
Function UpdateSplashes3D(cam,rainpercent)
	
	For s3d.Splash3D = Each Splash3D
		If s3d\active
			s3d\frame = s3d\frame + 0.75
			If s3d\frame < s3d\frames-1
				EntityTexture s3d\sprite,s3d\texture,s3d\frame
			Else
				s3d\active = False
				HideEntity s3d\sprite
			End If
		Else
			If Rand(100)<rainpercent
				If CameraPick(cam,Rand(GraphicsWidth()),Rand(GraphicsHeight()))
					If PickedNY()>0.25
						s3d\x = PickedX()
						s3d\y = PickedY()
						s3d\z = PickedZ()
						
						s3d\frame = 0
						s3d\active = True
						
						tmp# = Rnd(2,4)
						ScaleSprite	   s3d\sprite,Rnd(2,4)*PickedNY(),Rnd(2,4)*PickedNY()
						PositionEntity s3d\sprite,s3d\x,s3d\y,s3d\z
						ShowEntity	   s3d\sprite
						
						; add a wave sprite if it is on a flat area
						If PickedNY()>0.75
							SpawnRing3D(PickedX(),PickedY(),PickedZ(),PickedNX(),PickedNY(),PickedNZ())
						End If
					End If
				End If
			End If
		End If
	Next
	
End Function

; Spawn a water ring
Function SpawnRing3D(x#,y#,z#,nx#,ny#,nz#)
	For t3d.Ring3D = Each Ring3D
		If t3d\active = False
			t3d\x = x#
			t3d\y = y#+0.05
			t3d\z = z#
			
			t3d\frame = 0
			t3d\active = True
			
			tmp# = Rnd(4,7)
			ScaleSprite    t3d\sprite,tmp*ny,tmp*ny
			
			PositionEntity t3d\sprite,t3d\x,t3d\y,t3d\z
			ShowEntity	   t3d\sprite
			AlignToVector  t3d\sprite,nx,ny,nz,0
			
			Exit
		End If
	Next
End Function

; Update the water rings
Function UpdateRings3D()
	
	For t3d.Ring3D = Each Ring3D
		If t3d\active
			t3d\frame = t3d\frame + 0.5
			If t3d\frame < t3d\frames-1
				EntityTexture t3d\sprite,t3d\texture,t3d\frame
			Else
				t3d\active = False
				HideEntity t3d\sprite
			End If
		End If
	Next
	
End Function

; Update the rain sprites
Function UpdateRain3D(cam,maxdist#)
	
	For r3d.Rain3D = Each Rain3D
		If r3d\active
			r3d\x = r3d\x + r3d\sx	
			r3d\y = r3d\y + r3d\sy
			r3d\z = r3d\z + r3d\sz
			
			PositionEntity r3d\sprite,r3d\x,r3d\y,r3d\z	
			
			If EntityY(r3d\sprite)<-20
				r3d\active = False
				HideEntity r3d\sprite
			End If
		Else
			d#  = Rnd(5,maxdist)
			d2# = Rnd(-50,50)
			
			r3d\x = EntityX(cam,True) - (Sin(EntityYaw(cam,True)+d2)*d)
			r3d\y = EntityY(cam,True) + d
			r3d\z = EntityZ(cam,True) + (Cos(EntityYaw(cam,True)+d2)*d)
			
			r3d\sx = Rnd(-0.1,0.1)
			r3d\sy = Rnd(-RainSpeed,-RainSpeed*0.75)
			r3d\sz = Rnd(-0.1,0.1)
			
			r3d\active = True
			ShowEntity r3d\sprite
		End If	
	Next
	
End Function

; Create some sprites for the splashes
Function CreateSplashes3D(amount,tex,texframes)
	
	For i = 0 To amount-1
		s3d.Splash3D = New Splash3D
		s3d\sprite   = CreateSprite()
		s3d\texture  = tex
		s3d\frames	 = texframes
		s3d\frame	 = Rand(s3d\frames)
		
		s3d\x		 = Rnd(-3.0,3.0)
		s3d\y		 = Rnd(-3.0,3.0)
		s3d\z		 = Rnd(-3.0,3.0)
		
		s3d\active	 = False
		
		EntityTexture s3d\sprite,s3d\texture,s3d\frame
		EntityFX	  s3d\sprite,1+8
		EntityBlend   s3d\sprite,3
		
		EntityColor   s3d\sprite,envr,envg,envb
		EntityAlpha   s3d\sprite,0.5
		
		PositionEntity s3d\sprite,s3d\x,s3d\y,s3d\z
		ScaleSprite	   s3d\sprite,3,3
		HandleSprite   s3d\sprite,0,-0.1
		HideEntity	   s3d\sprite
	Next
	
End Function

; Create some sprites for the water rings
Function CreateRings3D(amount,tex,texframes)
	
	For i = 0 To amount-1
		t3d.Ring3D   = New Ring3D
		t3d\sprite   = CreateSprite()
		t3d\texture  = tex
		t3d\frames	 = texframes
		t3d\frame	 = Rand(t3d\frames)
		
		t3d\x		 = Rnd(-3.0,3.0)
		t3d\y		 = Rnd(-3.0,3.0)
		t3d\z		 = Rnd(-3.0,3.0)
		
		t3d\active	 = False
		
		EntityTexture t3d\sprite,t3d\texture,t3d\frame
		EntityFX	  t3d\sprite,1+8+16
		EntityBlend   t3d\sprite,3
		
		EntityColor   t3d\sprite,envr,envg,envb
		EntityAlpha   t3d\sprite,0.5
		
		PositionEntity t3d\sprite,t3d\x,t3d\y,t3d\z
		ScaleSprite	   t3d\sprite,5,5
		HideEntity	   t3d\sprite
		
		SpriteViewMode t3d\sprite,2
	Next
	
End Function

; Create some rain sprites
Function CreateRain3D(amount,tex)
	
	For i = 0 To amount-1
		r3d.Rain3D = New Rain3D
		r3d\sprite = CreateSprite()
		
		r3d\x	   = 0.0
		r3d\y	   = -20.0
		r3d\z	   = 0.0
		
		r3d\sx	   = 0.0
		r3d\sy	   = -Rnd(-RainSpeed,-RainSpeed*0.75)
		r3d\sz	   = 0.0
		
		EntityTexture r3d\sprite,tex
		EntityFX 	  r3d\sprite,1+8
		EntityBlend	  r3d\sprite,3
		
		EntityColor   r3d\sprite,envr,envg,envb
		EntityAlpha   r3d\sprite,0.25
		
		ScaleSprite   r3d\sprite,5,5
		
		SpriteViewMode r3d\sprite,4
	Next
	
End Function

; Create a simple texture for the rain sprites
Function CreateRainTexture(drops,size)
	
	rtex = CreateTexture(size,size,0)
	gbuff = GraphicsBuffer()
	
	SetBuffer TextureBuffer(rtex)
	
	For i=0 To drops-1
		l  = Rand(5,40)
		l2 = l/2
		x  = Rand(TextureWidth(rtex))
		y  = Rand(TextureHeight(rtex))
		
		For j=0 To l-1
			grey = (1.0-Abs(Float(j-l2)/Float(l2)))*255
			Color grey,grey,grey
			y2 = (y+j) Mod (TextureHeight(rtex)-1)
			Plot x,y2
		Next
	Next
	
	SetBuffer gbuff
	
	Return rtex
	
End Function

; Create an animated texture for the water rings
Function CreateRingTexture(drops,size,frames)
	
	Local sizeratio#  = Float(size)/128.0
	Local frameratio# = Float(frames)/100.0
	Local framestep   = 100/frames
	Local nextframe   = 0
	Local texframe	  = 0
	Local size2#	  = size/2
	Local draw		  = False
	
	For i=0 To drops-1
		s.Splashtex = New Splashtex
		tmp1# = Rnd(-2.0*sizeratio,2.0*sizeratio)
		tmp2# = Rnd(0,360)
		s\x    = size2 + (Cos(tmp2)*tmp1)
		s\y    = size2 + (Sin(tmp2)*tmp1)
		s\sx   = Rnd(0.0          , 2.0*sizeratio)
		s\sy   = Rnd(1.0*sizeratio, 2.5*sizeratio)
		s\c	   = Rnd(0.1,1.0)
		
		s\ox   = s\x
		s\oy   = s\y
	Next
	
	rtex = CreateTexture(size,size,0,frames)
	gbuff = GraphicsBuffer()
	
	SetBuffer TextureBuffer(rtex)
	
	For f=0 To 99
		
		If f = nextframe 
			SetBuffer TextureBuffer(rtex,texframe)
			nextframe = nextframe + framestep
			texframe  = texframe + 1
			draw = True
		Else
			draw = False
		End If
		
		For s.Splashtex = Each Splashtex
			s\sx = s\sx + s\sy
			
			s\sy = s\sy * 0.99 
			
			If s\sx>size2 Then s\sx = size2
			
			If draw
				mul# = (Sqr((s\sx - size2)*(s\sx - size2))/size2)
				If mul<0 Then mul = 0
				
				grey = (Float(99-f) / 99.0) * 255 * mul * s\c
				
				Color grey,grey,grey
				Oval s\x-(s\sx/2),s\y-(s\sx/2),s\sx,s\sx,False
			End If
		Next
	Next
	
	For s.Splashtex = Each Splashtex
		Delete s
	Next
	
	SetBuffer gbuff
	
	Return rtex
	
End Function

; Create an animated texture for the water splashes
Function CreateSplashTexture(drops,size,frames)
	
	Local sizeratio#  = Float(size)/128.0
	Local frameratio# = Float(frames)/100.0
	Local framestep   = 100/frames
	Local nextframe   = 0
	Local texframe	  = 0
	Local size2#	  = size/2
	Local draw		  = False
	
	For i=0 To drops-1
		s.Splashtex = New Splashtex
		tmp1# = Rnd(-16.0*sizeratio,16.0*sizeratio)
		tmp2# = Rnd(0,360)
		s\x    = size2 + (Cos(tmp2)*tmp1)
		s\y    = size2 + (Sin(tmp2)*tmp1)
		s\sx   = Rnd(-1.0*sizeratio, 1.0*sizeratio)
		s\sy   = Rnd(-1.2*sizeratio,-0.5*sizeratio)
		s\c	   = Rnd(0.1,1.0)
		
		s\ox   = s\x
		s\oy   = s\y
	Next
	
	stex = CreateTexture(size,size,0,frames)
	
	gbuff = GraphicsBuffer()
	
	For f=0 To 99
		
		If f = nextframe 
			SetBuffer TextureBuffer(stex,texframe)
			nextframe = nextframe + framestep
			texframe  = texframe + 1
			draw = True
		Else
			draw = False
		End If
		
		For s.Splashtex = Each Splashtex
			s\x = s\x + s\sx
			s\y = s\y + s\sy
			
			s\sy = s\sy + (0.02 * sizeratio)
			s\sx = s\sx * 0.99 
			
			If s\x<0      Then s\x = 0
			If s\x>size-1 Then s\x = size-1
			If s\y<0      Then s\y = 0
			If s\y>size-1 Then s\y = size-1			
			
			If draw
				mul# = 1.0 - (Sqr( (s\x - size2)*(s\x - size2) + (s\y - size2)*(s\y - size2))/size2)
				If mul<0 Then mul = 0
				
				grey = (Float(99-f) / 99.0) * 255 * mul * s\c
				
				Color grey,grey,grey
				Line s\x,s\y,s\ox,s\oy
				
				s\ox = s\x
				s\oy = s\y
			End If
		Next
	Next
	
	For s.Splashtex = Each Splashtex
		Delete s
	Next
	
	SetBuffer gbuff
	
	Return stex
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D