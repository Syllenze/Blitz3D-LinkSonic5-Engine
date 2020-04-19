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

Global Water

Global ReflectTexture


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

Function Wat_UpdateWaterReflection(ReflectTexture,Camera, WaterPlane) ;Thanks to FastExt - Replace FxManager_RenderWorldFast() in Stage.bb
	
		; render reflections to texture (hide water)		
	HideEntity WaterPlane
	ShowClipplane(WaterClipplane)
	
	MirrorCamera Camera, WaterPlane											
	SetBuffer TextureBuffer(ReflectTexture)
	CameraViewport Camera, 0,0, TextureWidth(ReflectTexture), TextureHeight(ReflectTexture)
	ScaleEntity Camera,1,Float(GraphicsHeight())/Float(GraphicsWidth()),1					
;	RenderWorld
	FxManager_RenderWorldFast()
	
        ; set the camera back to normal			
	RestoreCamera Camera											
	
		; show water and set reflection texture to water-plane
	HideClipplane(WaterClipplane)	
	ShowEntity WaterPlane
	
        ; render the shadows
	UpdateShadows(Camera)
	
		; render world to screen (back buffer)					
	SetBuffer BackBuffer()
	CameraViewport Camera, 0,0, GraphicsWidth(), GraphicsHeight()
	ScaleEntity Camera,1,1,1									
;	RenderWorld
	FxManager_RenderWorldFast()
	
End Function

Function Wat_UpdateRefraction(Camera, RefractTexture) ;Not Working - I'll let it here as an example only... use at your own risk
	
	SetBuffer TextureBuffer(RefractTexture)
	CameraViewport Camera, 0,0, TextureWidth(RefractTexture), TextureHeight(RefractTexture)
	ScaleEntity Camera,1,Float(GraphicsHeight())/Float(GraphicsWidth()),1
	RenderWorld
	
	SetBuffer BackBuffer()
	CameraViewport Camera, 0,0, GraphicsWidth(), GraphicsHeight()
	ScaleEntity Camera,1,1,1
	RenderWorld
	
End Function

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


;Function RemoveTrisUnderThePlane(mesh,planey#)
;	Local nmesh = CreateMesh()
;	Local vertex[2], nvertex[2]
;	
;	For s = 1 To CountSurfaces(mesh)
;		sur = GetSurface(mesh,s)
;		nsur = CreateSurface(nmesh,GetSurfaceBrush(sur))
;		For t = 0 To CountTriangles(sur)-1
;			temp = 0
;			For i = 0 To 2
;				vertex[i] = TriangleVertex(sur,t,i)
;				If VertexY(sur,vertex[i]) > planey Then temp = 1
;			Next
;			If temp = 1
;				For i = 0 To 2
;					nvertex[i] = AddVertex(nsur,VertexX(sur,vertex[i]),VertexY(sur,vertex[i]),VertexZ(sur,vertex[i]),VertexU(sur,vertex[i]),VertexV(sur,vertex[i]),VertexW(sur,vertex[i]))
;					VertexNormal nsur,nvertex[i],VertexNX(sur,vertex[i]),VertexNY(sur,vertex[i]),VertexNZ(sur,vertex[i])
;					VertexColor nsur,nvertex[i],VertexRed(sur,vertex[i]),VertexGreen(sur,vertex[i]),VertexBlue(sur,vertex[i]),VertexAlpha(sur,vertex[i])
;				Next
;				AddTriangle nsur,nvertex[0],nvertex[1],nvertex[2]
;			EndIf
;		Next
;	Next
	
;	Return nmesh
;EntityAlpha mesh, 0

;End Function


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
EntityFX w\ent, 17
ScaleEntity w\ent, size_multiplicator#, 1, size_multiplicator#
EntityColor w\ent, 200, 200, 255


EntityTexture w\ent, Tex;, 0, 1
;EntityTexture w\ent, TexBump, 0, 0
;EntityTexture w\ent, RefractTexture, 0, 0
EntityAlpha w\ent,1


;Water = w\ent
Return w\ent
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


Function Wat_CreateTextures(x=512, y=512) ;Put inside case "water"
	; ----
	ReflectionTexture = CreateTexture (x, y, 1+16+32+256 + FE_RENDER + FE_ZRENDER)	
	
	TextureBlend ReflectionTexture, FE_PROJECT										
	
	PositionTexture ReflectionTexture, 0.5, 0.5									
	
	ScaleTexture ReflectionTexture, 2, -2											
	; ----	
	
    Return ReflectionTexture ;Make sure to put Global Texture = Wat_CreateTextures(size, size)
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D