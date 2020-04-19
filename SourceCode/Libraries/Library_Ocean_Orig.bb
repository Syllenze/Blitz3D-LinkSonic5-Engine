; waterlib, Inarie aka Mr.Keks, 2005
; mail@inarie.de
; www.inarie.de
; www.projectblitz.de

;First, Create a camera specially for the cube
Global CubeCamera

;Second, Create a texture for render the camera
Global CubeSize = 256 ; When bigger the size, bigger the quality, bigger the lag
Global CubeMapTex

;Now you need a mesh for receive the texture, somehow planes doesn't work well, I wish they could... Give it a try later and you will see!
Global WaterSize = 150
Global SizeMultiplyer = 200




Type wat ; water tile / water class
	Field bank ; bank that contains the water wave time
	Field width,depth ; width and depth of the mesh in quads
	Field mesh,sur,sur2
	Field brush,brush2
	Field cam,tex ; cubemappin cam and tex
	Field tex2, anim#
End Type

Type Water
	Field ent, surf
	Field size, v[10000 ^ 2], vh[10000 ^ 2]
	
	Field tex, cam
End Type

Type wat_surf ; a water circle ^^
	Field w.wat ; tile class
	Field size
	Field piv
End Type

Type wat_meshc ; a single mesh instance of the wat tile
	Field s.wat_surf
	Field mesh
End Type


Global wat_gamecam ; handle of the main cam
Global wat_whitecaps = 1 ; disable or enable whitecaps on high waves

; save all sin()-values in order to save some render time
Dim sin2#(255)
For i = 0 To 255
	sin2(i) = Sin(i*360/255)
Next

Function UpdateRefraction(cam);, WaterPlane, BumpTexture)
	
;ScaleTexture ProjectTex, 2, 2
	
	; render scene entities only (without refract duplicates)
	CameraClsMode cam,1,1
	ShowEntity SceneEntities
	HideEntity RefractEntities
	RenderWorld
	
	; copy to 2D projection texture 
	CopyRectStretch 0,0,GraphicsWidth(),GraphicsHeight(), 0,0,TextureWidth(ProjectTex),TextureHeight(ProjectTex), BackBuffer(), TextureBuffer(ProjectTex)	
	
	; render bump-entities only
	CameraClsMode cam,0,0
	HideEntity SceneEntities
	ShowEntity RefractEntities
	RenderWorld
	
	
	
End Function

Global TextureW
Global TextureH

Function Wat_UpdateCubemap(ReflectTexture,tCamera,Camera, WaterPlane, waterlevel,dif) ; this function isnt by me. dont know who wrote it
	
  ; render reflections to texture (hide water)
	
	HideEntity WaterPlane
	
	MirrorCamera Camera, WaterPlane           
	SetBuffer TextureBuffer(ReflectTexture)
	CameraViewport Camera, 0,0, TextureWidth(ReflectTexture), TextureHeight(ReflectTexture)
	ScaleEntity Camera,1,Float(GraphicsHeight())/Float(GraphicsWidth()),1     
	RenderWorld
	
	RestoreCamera Camera           
	
  ; render world to screen (back buffer)
  ; show water and set reflection texture to water-plane
	
	ShowEntity WaterPlane
	
	SetBuffer BackBuffer()
	CameraViewport Camera, 0,0, GraphicsWidth(), GraphicsHeight()
	ScaleEntity Camera,1,1,1         
	RenderWorld
	
End Function


Function CreateWater(size = 10, size_multiplicator# = 1)
	w.Water = New Water
	w\ent = CreateMesh()
	w\surf = CreateSurface(w\ent)
	w\size = size
	For x = 0 To w\size
		For y = 0 To w\size
			w\v[cnt] = AddVertex(w\surf, x, 0, y)
			cnt = cnt + 1
		Next
	Next
	For x = 0 To w\size - 1
		For y = 0 To w\size - 1
			v1 = x * (w\size + 1) + y
			v2 = v1 + 1
			v3 = (x + 1) * (w\size + 1) + y + 1
			v4 = v3 - 1
			AddTriangle w\surf, v1, v2, v3
			AddTriangle w\surf, v1, v3, v4
		Next
	Next
	PositionMesh w\ent, -size / 2.0, 0, -size / 2.0
	UpdateNormals w\ent
	EntityFX w\ent, 1+17
	ScaleEntity w\ent, size_multiplicator#, 1, size_multiplicator#
	EntityColor w\ent, 200, 200, 255
	
	
	EntityTexture w\ent, CubeMapTex;, 0, 1
;EntityTexture w\ent, TexBump, 0, 0
;EntityTexture w\ent, RefractTexture, 0, 0
	EntityAlpha w\ent,.35
	
	
	NewWater = w\ent
	Return Handle(w)
End Function

Function CreateTerrainWater(scale=1)
	NewWater = LoadMesh("Objects\Water.b3d", SceneEntities)
	ScaleMesh NewWater,scale,1,scale
	EntityFX NewWater,1
	EntityColor NewWater,220,230,240
	EntityAlpha NewWater,.8
	
	EntityTexture NewWater, CubeMapTex;, 0, 1
End Function

Function CreateRefractTextures(w,h)
	; ----
	If RefractTexture Then FreeTexture RefractTexture
	; ----
	RefractTexture = CreateTexture ( w, h, 1+16+32+256 + FE_RENDER + FE_ZRENDER)	; <<<<	Создадим текстуру для 3Д рендеринга
																	; create texture for render refractions			
	TextureBlend RefractTexture, FE_PROJECTSMOOTH							; <<<<	Новый бленд для наложения текстуры как проекции
																	; new blend for 2D project texture
	PositionTexture RefractTexture, 0.5, 0.5									; <<< Смещение = в центр 0.5
																	; set texture position to center of screen = 0.5
	ScaleTexture RefractTexture, 2, 2										; resize projection texture to full-screen
	; ----				
End Function


Function CreateWaterTextures()
	; ----
	; текстура для искажения отражения (типа вода)
	; create texture for distortion (bump)
	BumpTexture = LoadTexture ( "Textures\Water_Bump_Back.png")
	TextureBlend BumpTexture, FE_BUMP									; <<<< 	Новый бленд для бампа 
	;ScaleTexture BumpTexture,0.052,0.052
	
	FoamTexture = LoadTexture ( "Textures\foam.png", 1+2 )
	TextureBlend FoamTexture, 1
	ScaleTexture FoamTexture,0.052,0.052
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D