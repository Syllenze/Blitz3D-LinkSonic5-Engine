; Example of use FastExt library
; (c) 2006-2010 created by MixailV aka Monster^Sage [monster-sage@mail.ru]  http://www.fastlibs.com



Include "FastExt.bb"	; <<<<    Include FastExt.bb file

Graphics3D 800,600,0,2

InitExt					; <<<< 	����������� ������������ ����� Graphics3D
						;               Initialize library after Graphics3D function


; �������� 3� ��� ��� �������
; Create world objects
	Global Camera, Light, Sphere, Cylinder, Help : CreateScene


AlphaTexture = LoadTexture("media\radial.png",1+2)


; ���������� ��� ��������� �������
; Variables for adjusting the effect
	Global BlurCenterX# = 0.5
	Global BlurCenterY# = 0.5
	Global BlurSpinAngle# = 5
	Global BlurAlpha# = 1.0
	Global BlurPasses = 4
	Global BlurQuality = 1
		; quality 0-1 with small graphic bugs (on NVidia videocards), but maximum fast
		; quality 2-3 without bugs, but middle speed
	Global BlurColorRed = 255
	Global BlurColorGreen = 255
	Global BlurColorBlue = 255
	Global BlurAlphaTexture = 0


; ������� ����
; Main loop
While Not KeyDown(1)
	;MouseLook Camera
	AnimateScene
	RenderWorld

	
	; ���������� ����, ������ ���������
	; Read input keys and change effect variables
		If KeyHit(59) Then Help=1-Help
		
		BlurCenterX = Float(MouseX())/Float(GraphicsWidth())
		BlurCenterY =Float( MouseY())/Float(GraphicsHeight())
		
		If KeyHit(2)>0 And BlurSpinAngle<90 Then BlurSpinAngle = BlurSpinAngle + 1
		If KeyHit(3)>0 And BlurSpinAngle>-90 Then BlurSpinAngle = BlurSpinAngle - 1
		If KeyHit(4)>0 And BlurAlpha<1 Then BlurAlpha = BlurAlpha + 0.1
		If KeyHit(5)>0 And BlurAlpha>0 Then BlurAlpha = BlurAlpha - 0.1
		If KeyHit(6)>0 And BlurPasses<16 Then BlurPasses = BlurPasses + 1
		If KeyHit(7)>0 And BlurPasses>1 Then BlurPasses = BlurPasses - 1
		If KeyHit(8)>0 Then
			BlurQuality = (1 + BlurQuality) And 3
		EndIf
		If KeyHit(9) Then
			BlurColorRed = Rand(0,255)
			BlurColorGreen = Rand(0,255)
			BlurColorBlue = Rand(0,255)
		EndIf
		If KeyHit(10) Then BlurAlphaTexture = AlphaTexture - BlurAlphaTexture
	
	
	; Customize effect
	CustomPostprocessBlurSpin BlurCenterX, BlurCenterY, BlurSpinAngle, BlurAlpha, BlurPasses, BlurQuality, BlurColorRed, BlurColorGreen, BlurColorBlue, BlurAlphaTexture
	
	
	RenderPostprocess FE_BlurSpin		; <<<< 	�������� ����-������� ������� ����� ������� RenderWorld
									;               Render postprocess effects after RenderWorld function
	
	DrawHelper
	Flip 0
Wend










; ������ ��������������� �������
; auxiliary functions

Function MouseLook ( ent, mov# = 0.3 )
	mxspd#=MouseXSpeed()*0.25
	myspd#=MouseYSpeed()*0.25	
	campitch#=EntityPitch(ent)+myspd#
	If campitch#<-65 Then campitch#=-65
	If campitch#>65 Then campitch#=65
	RotateEntity ent,campitch#,EntityYaw(ent)-mxspd#,EntityRoll(ent)
	If KeyDown(17) MoveEntity ent,0,0,mov
	If KeyDown(31) MoveEntity ent,0,0,-mov
	If KeyDown(32) MoveEntity ent,mov,0,0
	If KeyDown(30) MoveEntity ent,-mov,0,0
	MoveMouse GraphicsWidth()/2,GraphicsHeight()/2
End Function

Function Musor()
	For i=1 To 50
		cub=CreateCube()
		EntityColor cub,Rand(128,255),Rand(128,255),Rand(128,255)
		;EntityAlpha cub,Rnd(0.3,1.0)
		PositionEntity cub,Rnd(-10,10),Rnd(-10,10),Rnd(5,15)
		ScaleEntity cub,Rnd(0.3,0.5),Rnd(0.3,0.5),Rnd(0.3,0.5)
		;TurnEntity cub,Rnd(0,90),Rnd(0,90),Rnd(0,90)
		;EntityFX cub,32
	Next
End Function


Global FPSCount = 0
Global FPSCountTemp = 0
Global FPSTime = 0
Function FPS()
	If (MilliSecs()-FPSTime)>=1000 Then
		FPSTime = MilliSecs()
		FPSCount = FPSCountTemp
		FPSCountTemp = 0
	EndIf
	FPSCountTemp = FPSCountTemp + 1
	Return FPSCount
End Function


Function LoadSkyBox( file$ )
	m=CreateMesh()
	;front face
	b=LoadBrush( file$+"_FR.jpg",49 )
	s=CreateSurface( m,b )
	AddVertex s,-1,+1,-1,0,0:AddVertex s,+1,+1,-1,1,0
	AddVertex s,+1,-1,-1,1,1:AddVertex s,-1,-1,-1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3:
	FreeBrush b
	;right face
	b=LoadBrush( file$+"_LF.jpg",49 )
	s=CreateSurface( m,b )
	AddVertex s,+1,+1,-1,0,0:AddVertex s,+1,+1,+1,1,0
	AddVertex s,+1,-1,+1,1,1:AddVertex s,+1,-1,-1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;back face
	b=LoadBrush( file$+"_BK.jpg",49 )
	s=CreateSurface( m,b )
	AddVertex s,+1,+1,+1,0,0:AddVertex s,-1,+1,+1,1,0
	AddVertex s,-1,-1,+1,1,1:AddVertex s,+1,-1,+1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;left face
	b=LoadBrush( file$+"_RT.jpg",49 )
	s=CreateSurface( m,b )
	AddVertex s,-1,+1,+1,0,0:AddVertex s,-1,+1,-1,1,0
	AddVertex s,-1,-1,-1,1,1:AddVertex s,-1,-1,+1,0,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;top face
	b=LoadBrush( file$+"_UP.jpg",49 )
	s=CreateSurface( m,b )
	AddVertex s,-1,+1,+1,0,1:AddVertex s,+1,+1,+1,0,0
	AddVertex s,+1,+1,-1,1,0:AddVertex s,-1,+1,-1,1,1
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	;bottom face	
	b=LoadBrush( file$+"_DN.jpg",49 )
	s=CreateSurface( m,b )
	AddVertex s,-1,-1,-1,1,0:AddVertex s,+1,-1,-1,1,1
	AddVertex s,+1,-1,+1,0,1:AddVertex s,-1,-1,+1,0,0
	AddTriangle s,0,1,2:AddTriangle s,0,2,3
	FreeBrush b
	ScaleMesh m,100,100,100
	FlipMesh m
	EntityFX m,1
	Return m
End Function

Function Reflect(Entity,Camera,RTexture)
	Local fxCamera = CreateCamera()
	HideEntity entity
	WaterMapSize=TextureWidth(RTexture)
	CameraProjMode fxCamera,1
	CameraProjMode Camera,0
	CameraViewport fxCamera,0,0,WaterMapSize,WaterMapSize
	PositionEntity fxCamera,EntityX(Entity,1), EntityY(Entity,1) ,EntityZ(Entity,1),1; EntityY(Entity)
	; do left view
	SetCubeFace RTexture,0
	RotateEntity fxCamera,0,90,0
	RenderWorld
	CopyRect 0,0,WaterMapSize,WaterMapSize,0,0,BackBuffer(),TextureBuffer(RTexture)
	; do forward view
	SetCubeFace RTexture,1
	RotateEntity fxCamera,0,0,0
	RenderWorld
	CopyRect 0,0,WaterMapSize,WaterMapSize,0,0,BackBuffer(),TextureBuffer(RTexture)
	; do right view	
	SetCubeFace RTexture,2
	RotateEntity fxCamera,0,-90,0
	RenderWorld
	CopyRect 0,0,WaterMapSize,WaterMapSize,0,0,BackBuffer(),TextureBuffer(RTexture)
	; do backward view
	SetCubeFace RTexture,3
	RotateEntity fxCamera,0,180,0
	RenderWorld
	CopyRect 0,0,WaterMapSize,WaterMapSize,0,0,BackBuffer(),TextureBuffer(RTexture)
	; do up view
	SetCubeFace RTexture,4
	RotateEntity fxCamera,-90,0,0
	RenderWorld
	CopyRect 0,0,WaterMapSize,WaterMapSize,0,0,BackBuffer(),TextureBuffer(RTexture)		
	SetCubeFace RTexture,5
	RotateEntity fxCamera,90,0,0
	RenderWorld
	CopyRect 0,0,WaterMapSize,WaterMapSize,0,0,BackBuffer(),TextureBuffer(RTexture)	
	db=db+1
	CameraProjMode fxCamera,0
	CameraProjMode Camera,1
	ShowEntity entity
End Function




Function CreateScene()
	SetFont LoadFont("Tahoma",13)
	AmbientLight 50,50,70
	
	Camera=CreateCamera()
	ScaleEntity Camera,1,1,1
	CameraRange Camera,1,10000
	
	
	PositionEntity Camera,0,40,95
	RotateEntity Camera,1,180,0
	
	Sky = LoadSkyBox ( "media\sky" )
	ScaleEntity Sky, 10, 10, 10
	EntityOrder sky,1
	
	Light=CreateLight(2)
	
	Center=CreatePivot()
	
	PositionEntity Light,50,80,0
	
	ReflectTex = CreateTexture(128,128,  128+256)
	
	tex1=LoadTexture("media\wall.jpg")
	ScaleTexture tex1,.3,.3
	tex2=LoadTexture("media\wood.jpg")
	
	modelR=LoadMesh( "media\teapot.x" )
	PositionEntity modelR,0,35,0
	ScaleEntity modelR, 15, 15, 15
	EntityTexture modelR, ReflectTex
	EntityShininess modelR,1
	
	Sphere=CreateSphere(16)
	PositionEntity Sphere,40,40, 0
	ScaleEntity Sphere,10,10,10
	EntityTexture Sphere, ReflectTex
	EntityShininess Sphere,1
	
	Cylinder=CreateCylinder(12,1)
	PositionEntity Cylinder,30,10,20
	ScaleEntity Cylinder,5,15,5
	RotateEntity Cylinder,91,0,0
	EntityTexture Cylinder,tex2
	
	
	
	cube2=LoadMesh("media\cube.3DS"); create receiver
	ScaleEntity cube2,180,2,180
	EntityTexture cube2,tex1
	
	cube3=CreateCylinder(16)
	ScaleMesh cube3,3,15,3
	sp = CreateSphere(12)
	ScaleMesh sp ,5,5,5
	PositionMesh sp,0,20,0
	AddMesh sp, cube3
	FreeEntity sp
	
	
	PositionEntity cube3,60,10,60
	EntityTexture cube3,tex2
		For i=1 To 3
			cuben = CopyEntity( cube3 )
			PositionEntity cuben,60,10,60 - (30*i)
		Next
		For i=1 To 3
			cuben = CopyEntity( cube3 )
			PositionEntity cuben,-60,10,60 - (30*i)
		Next
		For i=1 To 3
			cuben = CopyEntity( cube3 )
			PositionEntity cuben,60 - (30*i),10,60 
		Next
		For i=1 To 3
			cuben = CopyEntity( cube3 )
			PositionEntity cuben,60 - (30*i),10,-60
		Next
	
	cube4=CopyEntity( cube3 )
	PositionEntity cube4,-60,10,60
	
	cube5=CopyEntity( cube3 )
	PositionEntity cube5,60,10,-60
	
	cube6=CopyEntity( cube3 )
	PositionEntity cube6,-60,10,-60
	
	Reflect( ModelR,Camera,ReflectTex)
	MoveMouse GraphicsWidth()/2,GraphicsHeight()/2
End Function

Function AnimateScene()
	TurnEntity Light,0,.5,0
 	MoveEntity Light,.0,.0,.5
 	TurnEntity Sphere,0,.3,0
 	MoveEntity Sphere,.0,.0,.2
 	TurnEntity Cylinder,0,0,1
End Function

Function DrawHelper()
	Color 0,0,128
	If Help=0
		Text 10,10,FPS()+" fps"
		Text 10,30,"F1 - help"
	Else
		Text 10,10,FPS()+" fps"
		Text 10,30,"F1 - hide help"
		Text 10,50,"W,S,A,D keys for move"
		Text 10,70,"Blur position: "+Str(BlurCenterX)+" x "+Str(BlurCenterY)
		Text 10,90,"keys 1/2 - increase/decrease Blur spin angle: "+Str(BlurSpinAngle)
		Text 10,110,"keys 3/4 - increase/decrease Blur alpha value: "+Str(BlurAlpha)
		Text 10,130,"key 5/6 - increase/decrease Blur passes: "+Str(BlurPasses)
		Text 10,150,"key 7 - change Blur quality: "+Str(BlurQuality)
		Text 10,170,"key 8 - change Blur color (random)"
		Text 10,190,"key 9 - enable/disable Blur alpha-texture: $"+Hex(BlurAlphaTexture)
	EndIf
End Function