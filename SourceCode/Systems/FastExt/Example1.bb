Include "fastext.bb"

Graphics3D 800,600,32,6
InitExt	

pxCreateWorld(1,"")
pxSetGravity(0,-58,0)


Global SunAngle# = 50 
Global ShadowRes = 512

Global SoftShadow

Global Cam = CreateCamera()
RotateEntity Cam,90,0,0
PositionEntity Cam,0,40,0
CameraClsColor Cam,255,255,255

AmbientLight 200,200,200

;Create shadow texture
Global ShadowTex = CreateTexture (ShadowRes, ShadowRes, 1+16+32+256 + FE_RENDER + FE_ZRENDER)	
TextureBlend ShadowTex, FE_PROJECT
PositionTexture ShadowTex, 0.5, 0.5									
ScaleTexture ShadowTex, 2, 2


Global plane = CreatePlane()
EntityTexture plane,ShadowTex,0,0
EntityPickMode plane,2
EntityColor plane,0,100,0

Global TestCnt=40

Global TestMesh = CreateCylinder(7,1)
ScaleEntity TestMesh,1.2,1.4,1.2
EntityColor TestMesh,100,100,100
HideEntity  TestMesh

For i=1 To TestCnt
	b=b+1:y=y+1
	If b > 5 c=c+5 :b=0:y=0
	Add_Phys(0,y*5.5,c)
Next


While Not KeyHit(1)

	If KeyDown(17) TranslateEntity Cam,0,0,.1
	If KeyDown(31) TranslateEntity Cam,0,0,-.1

	If KeyDown(32) TranslateEntity Cam,.1,0,0
	If KeyDown(30) TranslateEntity Cam,-.1,0,0

	If KeyHit(18) Add_Phys(EntityX(Cam),EntityY(Cam),EntityZ(Cam))

	If KeyDown(203) SunAngle#=SunAngle#+.1
	If KeyDown(205) SunAngle#=SunAngle#-.1
	
	If KeyHit(57) SoftShadow=1-SoftShadow

	Update_Shadows()
	Update_Phys()

	pxRenderPhysic(60,0)

	RenderWorld()

	Color 255,0,0
	Text 0,0, "Key left and right - Adjust the Sun angle " + SunAngle
	Text 0,15,"Key E - Add Cylinder" 
	Text 0,30,"Space - activate SoftShadows " + SoftShadow
	Text 0,45,"Fps: "+fps#(1000)

	Flip 0


Wend

Type Phys
	Field Mesh,Body
End Type

Function Add_Phys(x#,y#,z#)

	Asd.Phys = New Phys
	
	Asd\Mesh = CopyEntity(TestMesh)
	Asd\Body = pxBodyCreateCylinder(1.2,1.4,6,5)
	pxBodySetPosition Asd\Body, x, y, z

End Function


Function Update_Phys()

	For Asd.Phys = Each Phys
		pxBodySetEntity (Asd\Mesh, Asd\Body)
	Next

End Function


Function Update_Shadows()

	Local Px#, Pz#, Py#, NewX#
	
	Px = EntityX(Cam,1)
	Pz = EntityZ(Cam,1)
	Py = EntityY(Cam,1)
	HideEntity plane

	PositionEntity Cam,0,32,0

	RotateEntity Cam,90,0,0
	TurnEntity Cam,0,SunAngle,0
	
        NewX# = 32 / Tan( 90-SunAngle )
	PositionEntity Cam,Px+NewX,32,Pz
	CameraZoom Cam, 1 / Py
	CameraProjMode Cam,2
	ScaleEntity Cam,Cos(SunAngle),1,1

	SetBuffer TextureBuffer(ShadowTex)

	RenderWorld()

	PositionEntity Cam,Px,Py,Pz
	RotateEntity Cam,90,0,0
	ScaleEntity Cam,1,1,1
	CameraZoom Cam,1
	CameraProjMode Cam,1

	ShowEntity plane


	SetBuffer BackBuffer()


	If SoftShadow = True
		HideEntity Cam
		BlurTexture(ShadowTex,2,2)
		TextureBlend ShadowTex, FE_PROJECT
		ScaleTexture ShadowTex,2,2
		ShowEntity Cam
	EndIf


End Function




Function BlurTexture(Texture, Blur_Quality, Blur_Radius#)

	; This is used for temporary storage of the meshes used for soft shadow blurring.
	Local BlurMesh[16*4]
	Local Loop
	Local Blur_Cam

	Local BLUR_CAM_X# = 65536.0
	Local BLUR_CAM_Y# = 65536.0
	Local BLUR_CAM_Z# = 0.0

	; If blurring is enabled...
	If Blur_Quality > 0

		Blur_Cam = CreateCamera()


		; Set the camera viewport to the same size as the texture.		
		CameraViewport Blur_Cam, 0, 0, TextureWidth(Texture), TextureHeight(Texture)

		; Set the camera so it clears the color buffer before rendering the texture.
		CameraClsColor Blur_Cam, 0, 0, 0
		CameraClsMode  Blur_Cam, True, True						

		; Set the camera's range to be very small so as to reduce the possiblity of extra objects making it into the scene.
		CameraRange Blur_Cam, 0.1, 50
	
		; Set the camera to zoom in on the object to reduce perspective error from the object being too close to the camera.
		CameraZoom Blur_Cam, 16.0

		; Aim camera straight down.	
		RotateEntity Blur_Cam, 90, 0, 0, True
		
		; Position the blur camera far from other entities in the world.
		PositionEntity Blur_Cam, BLUR_CAM_X#, BLUR_CAM_Y#, BLUR_CAM_Z#
		
		; Create the sprites to use for blurring the shadow maps.
		For Loop = 0 To (Blur_Quality*4)-1
			BlurMesh[Loop] = CreateSprite()
		Next
		
		; Set the texture blend mode to multiply.
		TextureBlend Texture, 2
												
		; Scale the texture down because we scale the sprites up so they fill a larger area of the
		; screen.  (Otherwise the edges of the texture are darker than the middle because they don't
		; get covered.
		ScaleTexture    Texture, 0.5, 0.5
		PositionTexture Texture, 0.5, 0.5
						
		; Blur texture by blitting semi-transparent copies of it on top of it.
		BlurRadius# = Blur_Radius# * (1.0 / 256.0)
		BlurAngleStep# = 360.0 / Float(Blur_Quality*4)

		; Normally we would just divide 255 by the number of passes so that adding all the passes
		; together would not exceed 256.  However, if we did that, then we could not have a number of
		; passes which does not divide 256 evenly, or else the error would result in the white part of
		; the image being slightly less than white.  So we round partial values up to ensure that
		; white will always be white, even if it ends up being a little whiter than white as a result
		; when all the colors are added, since going higher than white just clamps to white.
		BlurShade = Ceil(255.0 / Float(Blur_Quality*4))
		
		; Place each of the blur objects around a circle of radius blur_radius.
		For Loop = 0 To (Blur_Quality*4)-1
		
			EntityTexture BlurMesh[Loop], Texture
			EntityFX BlurMesh[Loop], 1+8
			EntityAlpha BlurMesh[Loop], 1.0 / Float(Loop+1)
			ScaleSprite BlurMesh[Loop], 2, 2
																						
			BlurAngle# = BlurAngleStep# * Float(Loop) + 180.0*(Loop Mod 2)
							
			Xoff# = BlurRadius# * Cos(BlurAngle#)
			Yoff# = BlurRadius# * Sin(BlurAngle#)

			PositionEntity BlurMesh[Loop], BLUR_CAM_X# + Xoff#, BLUR_CAM_Y# - 16.0, BLUR_CAM_Z# + Yoff#, True
					
		Next
					
		; Render the new texture.
		RenderWorld
				
		; Copy the new texture from the screen buffer to the texture buffer.		
		CopyRect 0, 0, TextureWidth(Texture), TextureHeight(Texture), 0, 0, BackBuffer(), TextureBuffer(Texture)
					
		; Free the blur entities.
		For Loop = 0 To (Blur_Quality*4)-1
			FreeEntity BlurMesh[Loop]
		Next

		; Free the blur camera.
		FreeEntity Blur_Cam
		
	EndIf

End Function

Global fpsindex#, fpstime#, fpsfold_millisecs#, fpsfps#

Function fps#(time=1000)
	fpsindex=fpsindex+1
	fpstime=fpstime+MilliSecs()-fpsfold_millisecs
	If fpstime=>time
		fpsfps=fpsindex
		fpstime=0
		fpsindex=0
	EndIf
	fpsfold_millisecs=MilliSecs()
	Return fpsfps
End Function