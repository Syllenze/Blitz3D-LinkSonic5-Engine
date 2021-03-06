Global ProShadowResourcesPath$="ProShadowsMedia\"


Type ProShadowsStructureType
	;global settings
	Field RenderMethod
	Field UseCubeMaps
	Field CastersPivot
	Field ShadowCamera
	Field TextureIndexCounterFext
	Field DotProductLayer
	Field DiffuseLayer
	
	;Sun Light
	Field Sun
	Field SunResolution
	Field SunRange#
	Field SunFadeTexture
	Field SunFadeSprite
	Field SunShadowMapTexture
	Field SunAmbientDynamicTexture
	Field SunAmbientStaticTexture
	Field SunDotProductTexture[6]
	Field SunMatrix.Matrix3D
	Field SunSmoothFade
	Field SunTextureIndex
	Field SunPt#
	Field SunYw#
	Field SunRl#
	Field SunCubePivot

	;Sun SunExtended Light
	Field SunExtend
	Field SunExtendResolution
	Field SunExtendRange#
	Field SunExtendFadeTexture
	Field SunExtendFadeSprite
	Field SunExtendShadowMapTexture
	Field SunExtendMatrix.Matrix3D
	Field SunExtendTextureIndex

End Type

ProShadowsStructure.ProShadowsStructureType=New ProShadowsStructureType

Function ProShadows_GetNextTextureIndex()
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
ProShadowsStructure\TextureIndexCounterFext=ProShadowsStructure\TextureIndexCounterFext+1
Return ProShadowsStructure\TextureIndexCounterFext
End Function 
Function ProShadows_Init(RenderMethod=1, UseCubeMaps=1)
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
ProShadowsStructure\RenderMethod=RenderMethod
ProShadowsStructure\UseCubeMaps=UseCubeMaps
If GfxDriverCaps3D() <> 110 Then ProShadowsStructure\UseCubeMaps=0
;ProShadowsStructure\UseCubeMaps=0																										;COMMENT HERE
ProShadowsStructure\CastersPivot=CreatePivot()
HideEntity ProShadowsStructure\CastersPivot
ProShadowsStructure\ShadowCamera=CreateCamera ()
HideEntity ProShadowsStructure\ShadowCamera
ProShadowsStructure\TextureIndexCounterFext=-1
End Function
Function ProShadows_CreateSun(Quality=2, Range#=64.0, SmoothFade=1, ExtendQuality=-1, ExtendRange#=256.0,SunPt#=75,SunYw#=45,SunRl#=0)
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType

ProShadowsStructure\Sun=1
ProShadowsStructure\SunSmoothFade=SmoothFade
ProShadowsStructure\DotProductLayer=1
ProShadowsStructure\DiffuseLayer=3

;============================
;Sun shadow map routines
;============================
ProShadowsStructure\SunRange# = Range#
;set shadow map quality
Select Quality
	Case 0
		ProShadowsStructure\SunResolution=256
	Case 1
		ProShadowsStructure\SunResolution=512
	Case 2
		ProShadowsStructure\SunResolution=1024
	Case 3
		ProShadowsStructure\SunResolution=2048
	Default
		ProShadowsStructure\SunResolution=512
End Select
;create shadow map texture via rendermethod
Select ProShadowsStructure\RenderMethod
	Case 0		;Copy from BackBuffer()
		;Sun shadow map texture - limit texture size by graphics mode
		MaxGraphicsSize = GraphicsHeight()
		If GraphicsHeight()>GraphicsWidth() Then MaxGraphicsSize  = GraphicsWidth()
		While ProShadowsStructure\SunResolution>MaxGraphicsSize
			ProShadowsStructure\SunResolution= ProShadowsStructure\SunResolution Shr 1
		Wend
		ProShadowsStructure\SunShadowMapTexture = CreateTexture(ProShadowsStructure\SunResolution, ProShadowsStructure\SunResolution, 1+16+32+256 )
	Case 1		;Draw to TextureBuffer()
		;Sun shadow map texture - limit texture size by videocard capability
		If ProShadowsStructure\SunResolution>GfxDriverCapsEx\TextureMaxWidth Then ProShadowsStructure\SunResolution = GfxDriverCapsEx\TextureMaxWidth
		If ProShadowsStructure\SunResolution>GfxDriverCapsEx\TextureMaxHeight Then ProShadowsStructure\SunResolution = GfxDriverCapsEx\TextureMaxHeight
		ProShadowsStructure\SunShadowMapTexture = CreateTexture(ProShadowsStructure\SunResolution,ProShadowsStructure\SunResolution, 1+16+32+256+FE_RENDER+FE_ZRENDER )
End Select
;set blends for shadow texture
ScaleTexture(ProShadowsStructure\SunShadowMapTexture , 1.0, 1.0)
ShadowTexBlend = FETOP_PROJECT3D1 Or (D3DTOP_MODULATE Shl 8) Or D3DTOP_MODULATE
TextureBlend(ProShadowsStructure\SunShadowMapTexture, ShadowTexBlend)
ProShadowsStructure\SunTextureIndex=ProShadows_GetNextTextureIndex()
TextureIndex(ProShadowsStructure\SunShadowMapTexture, ProShadowsStructure\SunTextureIndex)

;============================
;Second sun shadow map routines 
;============================
ProShadowsStructure\SunExtendRange# = ExtendRange#
;set shadow map quality
Select ExtendQuality
	Case 0
		ProShadowsStructure\SunExtendResolution=256
		ProShadowsStructure\SunExtend=1
	Case 1
		ProShadowsStructure\SunExtendResolution=512
		ProShadowsStructure\SunExtend=1
	Case 2
		ProShadowsStructure\SunExtendResolution=1024
		ProShadowsStructure\SunExtend=1
	Case 3
		ProShadowsStructure\SunExtendResolution=2048
		ProShadowsStructure\SunExtend=1
	Default
		ProShadowsStructure\SunExtendResolution=0
		ProShadowsStructure\SunExtend=0
End Select

If GfxDriverCapsEx\TextureMaxStages<5 Then ProShadowsStructure\SunExtend=0

If ProShadowsStructure\SunExtend=1

	ProShadowsStructure\DotProductLayer=2
	ProShadowsStructure\DiffuseLayer=4

	;create shadow map texture via rendermethod
	Select ProShadowsStructure\RenderMethod
		Case 0		;Copy from BackBuffer()
			MaxGraphicsSize = GraphicsHeight()
			If GraphicsHeight()>GraphicsWidth() Then MaxGraphicsSize  = GraphicsWidth()
			While ProShadowsStructure\SunExtendResolution>MaxGraphicsSize
				ProShadowsStructure\SunExtendResolution= ProShadowsStructure\SunExtendResolution Shr 1
			Wend
			ProShadowsStructure\SunExtendShadowMapTexture = CreateTexture(ProShadowsStructure\SunExtendResolution, ProShadowsStructure\SunExtendResolution, 1+16+32+256 )
		Case 1		;Draw to TextureBuffer()
			If ProShadowsStructure\SunExtendResolution>GfxDriverCapsEx\TextureMaxWidth Then ProShadowsStructure\SunExtendResolution = GfxDriverCapsEx\TextureMaxWidth
			If ProShadowsStructure\SunExtendResolution>GfxDriverCapsEx\TextureMaxHeight Then ProShadowsStructure\SunExtendResolution = GfxDriverCapsEx\TextureMaxHeight
			ProShadowsStructure\SunExtendShadowMapTexture = CreateTexture(ProShadowsStructure\SunExtendResolution,ProShadowsStructure\SunExtendResolution, 1+16+32+256+FE_RENDER+FE_ZRENDER )
	End Select
	;set blends for shadow textures
	ScaleTexture(ProShadowsStructure\SunExtendShadowMapTexture , 1.0, 1.0)
	ShadowTexBlend = FETOP_PROJECT3D1 Or (D3DTOP_ADDSIGNED Shl 8) Or D3DTOP_MODULATE
	TextureBlend(ProShadowsStructure\SunExtendShadowMapTexture, ShadowTexBlend)
	ProShadowsStructure\SunExtendTextureIndex=ProShadows_GetNextTextureIndex()
	TextureIndex(ProShadowsStructure\SunExtendShadowMapTexture, ProShadowsStructure\SunExtendTextureIndex)
EndIf

;============================
;Rotate sunlight and build project matrices
;============================
ProShadowsStructure\SunMatrix=New Matrix3d
If ProShadowsStructure\SunExtend=1 Then 
	ProShadowsStructure\SunExtendMatrix=New Matrix3d
EndIf 
ProShadows_RotateSunLight(SunPt#, SunYw#, SunRl#)

;============================
;Load overlay textures  and build overlay mesh
;============================
;make shadow sprite
If ProShadowsStructure\SunSmoothFade
	;load textures
	ProShadowsStructure\SunFadeTexture=LoadTexture("Textures\Overlay_Far.png",1+2+16+32)
	ProShadowsStructure\SunFadeSprite=CreateMesh(ProShadowsStructure\ShadowCamera)
	If ProShadowsStructure\SunExtend=1 Then
		SpriteSize#=ProShadowsStructure\SunExtendRange
	Else 
		SpriteSize#=ProShadowsStructure\SunRange
	EndIf
	;make fade sprite 
	surf=CreateSurface(ProShadowsStructure\SunFadeSprite)
	v0=AddVertex(surf,-SpriteSize,SpriteSize,4,0,0)
	v1=AddVertex(surf,SpriteSize,SpriteSize,4,1,0)
	v2=AddVertex(surf,SpriteSize,-SpriteSize,4,1,1)
	v3=AddVertex(surf,-SpriteSize,-SpriteSize,4,0,1)
	AddTriangle surf,v0,v1,v2
	AddTriangle surf,v0,v2,v3
	EntityFX ProShadowsStructure\SunFadeSprite,1+32
	EntityTexture ProShadowsStructure\SunFadeSprite, ProShadowsStructure\SunFadeTexture, 0, 0
	HideEntity ProShadowsStructure\SunFadeSprite
	;make fade sprite for extended light
	If ProShadowsStructure\SunExtend=1 Then
		ProShadowsStructure\SunExtendFadeTexture=LoadTexture("Textures\Overlay_Near.png",1+2+16+32)
		ProShadowsStructure\SunExtendFadeSprite=CreateMesh(ProShadowsStructure\ShadowCamera)
		SpriteSize#=ProShadowsStructure\SunRange
		surf=CreateSurface(ProShadowsStructure\SunExtendFadeSprite)
		v0=AddVertex(surf,-SpriteSize,SpriteSize,4,0,0)
		v1=AddVertex(surf,SpriteSize,SpriteSize,4,1,0)
		v2=AddVertex(surf,SpriteSize,-SpriteSize,4,1,1)
		v3=AddVertex(surf,-SpriteSize,-SpriteSize,4,0,1)
		AddTriangle surf,v0,v1,v2
		AddTriangle surf,v0,v2,v3
		EntityFX ProShadowsStructure\SunExtendFadeSprite,1+32
		EntityTexture ProShadowsStructure\SunExtendFadeSprite, ProShadowsStructure\SunExtendFadeTexture, 0, 0
		HideEntity ProShadowsStructure\SunExtendFadeSprite
	EndIf
EndIf

;ProShadowResourcesPath$+

;============================
;DotProduct textures
;============================
If ProShadowsStructure\UseCubeMaps=1
	;set cubemap rotation matrix pivot
	ProShadowsStructure\SunCubePivot=CreatePivot(ProShadowsStructure\CastersPivot)
	ProShadowsStructure\SunDotProductTexture[0]=CreateTextureCube(255,128,128,4,2,ProShadowsStructure\SunCubePivot)
	ProShadowsStructure\SunDotProductTexture[1]=CreateTextureCube(0,128,128,4,2,ProShadowsStructure\SunCubePivot)
	ProShadowsStructure\SunDotProductTexture[2]=CreateTextureCube(128,255,128,4,2,ProShadowsStructure\SunCubePivot)
	ProShadowsStructure\SunDotProductTexture[3]=CreateTextureCube(128,0,128,4,2,ProShadowsStructure\SunCubePivot)
	ProShadowsStructure\SunDotProductTexture[4]=CreateTextureCube(128,128,255,4,2,ProShadowsStructure\SunCubePivot)
	ProShadowsStructure\SunDotProductTexture[5]=CreateTextureCube(128,128,0,4,2,ProShadowsStructure\SunCubePivot)
	
Else 
	ProShadowsStructure\SunDotProductTexture[0]=CreateTextureFilled(255,128,128,4)
	ProShadowsStructure\SunDotProductTexture[1]=CreateTextureFilled(0,128,128,4)
	ProShadowsStructure\SunDotProductTexture[2]=CreateTextureFilled(128,255,128,4)
	ProShadowsStructure\SunDotProductTexture[3]=CreateTextureFilled(128,0,128,4)
	ProShadowsStructure\SunDotProductTexture[4]=CreateTextureFilled(128,128,255,4)
	ProShadowsStructure\SunDotProductTexture[5]=CreateTextureFilled(128,128,0,4)
EndIf


;============================
;Make Add Textures
;light up from dark(0,0,0)  to Amb (96,96,96) after DotProducts
;============================
;simple ambient level
ProShadowsStructure\SunAmbientStaticTexture=CreateTextureFilled(96,96,96,3)
ProShadowsStructure\SunAmbientDynamicTexture=LoadTexture("Textures\AddTex.png",1+16+32)
ScaleTexture(ProShadowsStructure\SunAmbientDynamicTexture , 1.0, 1.0)
AddAmbientTextureBlend = FETOP_PROJECT3D1 Or (D3DTOP_ADD Shl 8) Or D3DTOP_MODULATE	
TextureBlend(ProShadowsStructure\SunAmbientDynamicTexture, AddAmbientTextureBlend)
If ProShadowsStructure\SunExtend=1
	TextureIndex(ProShadowsStructure\SunAmbientDynamicTexture, ProShadowsStructure\SunExtendTextureIndex)
Else 
	TextureIndex(ProShadowsStructure\SunAmbientDynamicTexture, ProShadowsStructure\SunTextureIndex)
EndIf 




End Function
Function ProShadows_Update(GameCamera)
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
OldBuffer=GraphicsBuffer()
;update sun
If ProShadowsStructure\Sun

	RotateEntity ProShadowsStructure\ShadowCamera,ProShadowsStructure\SunPt,ProShadowsStructure\SunYw,ProShadowsStructure\SunRl
	PositionEntity ProShadowsStructure\ShadowCamera,0,0,0
	TFormPoint EntityX(GameCamera),EntityY(GameCamera),EntityZ(GameCamera),0,ProShadowsStructure\ShadowCamera
	grid#= (2.0 * 64.0) / Float(ProShadowsStructure\SunResolution)			;zoom
	grid#= (2.0 * ProShadowsStructure\SunRange#) / Float(ProShadowsStructure\SunResolution)			;zoom
	
	xlong=TFormedX() / grid
	ylong=TFormedY() / grid
	zlong=TFormedZ() / grid
	MoveEntity ProShadowsStructure\ShadowCamera,xlong*grid,ylong*grid,-64.0*ProShadowsStructure\SunRange#


	ShowEntity ProShadowsStructure\CastersPivot
	ShowEntity ProShadowsStructure\ShadowCamera


	If ProShadowsStructure\UseCubeMaps=1
		RotateEntity ProShadowsStructure\SunCubePivot,ProShadowsStructure\SunPt#, ProShadowsStructure\SunYw#, ProShadowsStructure\SunRl#
	EndIf 
	
	If ProShadowsStructure\RenderMethod=1
		SetBuffer TextureBuffer(ProShadowsStructure\SunShadowMapTexture)
	Else
		SetBuffer BackBuffer()
	EndIf 

	CameraViewport(ProShadowsStructure\ShadowCamera,0,0,ProShadowsStructure\SunResolution,ProShadowsStructure\SunResolution)
	CameraProjMode ProShadowsStructure\ShadowCamera,2
	CameraZoom ProShadowsStructure\ShadowCamera,(1/ProShadowsStructure\SunRange#)		;size of area
	CameraClsColor ProShadowsStructure\ShadowCamera,255,128,128
	CameraRange ProShadowsStructure\ShadowCamera,1,ProShadowsStructure\SunRange#*64.0*1.5

	If ProShadowsStructure\SunSmoothFade
		ShowEntity ProShadowsStructure\SunFadeSprite
	EndIf 

	RenderEntity(ProShadowsStructure\CastersPivot, ProShadowsStructure\ShadowCamera,1)


	If ProShadowsStructure\SunSmoothFade
		HideEntity ProShadowsStructure\SunFadeSprite
	Else
		Color 255,128,128
		Rect 0,0,ProShadowsStructure\SunResolution,ProShadowsStructure\SunResolution,0
	EndIf

	If ProShadowsStructure\SunExtend
		Color 128,128,128
		Rect 0,0,ProShadowsStructure\SunResolution,ProShadowsStructure\SunResolution,0
	EndIf 


	If ProShadowsStructure\RenderMethod=0
		CopyRect 0,0,ProShadowsStructure\SunResolution,ProShadowsStructure\SunResolution,0,0,BackBuffer(),TextureBuffer(ProShadowsStructure\SunShadowMapTexture)
	EndIf
	
	HideEntity ProShadowsStructure\CastersPivot
	HideEntity ProShadowsStructure\ShadowCamera
	
	TFormPoint 0,0,0,0,ProShadowsStructure\ShadowCamera
		ProShadowsStructure\SunMatrix\m41 = TFormedX()*64.0			;real zooom
		ProShadowsStructure\SunMatrix\m42 = TFormedY()*64.0
		ProShadowsStructure\SunMatrix\m43 = 0
	SetProjMatrix ProShadowsStructure\SunTextureIndex, ProShadowsStructure\SunMatrix

	;==================================
	; Render Sun Extend!
	;==================================
	If ProShadowsStructure\SunExtend

		PositionEntity ProShadowsStructure\ShadowCamera,0,0,0
		TFormPoint EntityX(GameCamera),EntityY(GameCamera),EntityZ(GameCamera),0,ProShadowsStructure\ShadowCamera
		grid#= (2.0 * 64.0) / Float(ProShadowsStructure\SunExtendResolution)			;zoom
		grid#= (2.0 * ProShadowsStructure\SunExtendRange#) / Float(ProShadowsStructure\SunExtendResolution)			;zoom
		
		xlong=TFormedX() / grid
		ylong=TFormedY() / grid
		zlong=TFormedZ() / grid
		MoveEntity ProShadowsStructure\ShadowCamera,xlong*grid,ylong*grid,-64.0*ProShadowsStructure\SunExtendRange#
	
	
		ShowEntity ProShadowsStructure\CastersPivot
		ShowEntity ProShadowsStructure\ShadowCamera
	
		
;		If ProShadowsStructure\UseCubeMaps=1
;			RotateEntity ProShadowsStructure\SunCubePivot,ProShadowsStructure\SunPt#, ProShadowsStructure\SunYw#, ProShadowsStructure\SunRl#
;		EndIf 
		
		If ProShadowsStructure\RenderMethod=1
			SetBuffer TextureBuffer(ProShadowsStructure\SunExtendShadowMapTexture)
		Else
			SetBuffer BackBuffer()
		EndIf 
	
		CameraViewport(ProShadowsStructure\ShadowCamera,0,0,ProShadowsStructure\SunExtendResolution,ProShadowsStructure\SunExtendResolution)
		CameraProjMode ProShadowsStructure\ShadowCamera,2
		CameraZoom ProShadowsStructure\ShadowCamera,(1/ProShadowsStructure\SunExtendRange#)		;size of area
		CameraClsColor ProShadowsStructure\ShadowCamera,255,128,128
		CameraRange ProShadowsStructure\ShadowCamera,1,ProShadowsStructure\SunExtendRange#*64.0*1.5
	
		If ProShadowsStructure\SunSmoothFade
			ShowEntity ProShadowsStructure\SunFadeSprite
			ShowEntity ProShadowsStructure\SunExtendFadeSprite
		EndIf 
	
		RenderEntity(ProShadowsStructure\CastersPivot, ProShadowsStructure\ShadowCamera,1)
	
	
		If ProShadowsStructure\SunSmoothFade
			HideEntity ProShadowsStructure\SunFadeSprite
			HideEntity ProShadowsStructure\SunExtendFadeSprite
		Else
			Color 255,128,128
			Rect 0,0,ProShadowsStructure\SunExtendResolution,ProShadowsStructure\SunExtendResolution,0
		EndIf 
	
	
	
		If ProShadowsStructure\RenderMethod=0
			CopyRect 0,0,ProShadowsStructure\SunExtendResolution,ProShadowsStructure\SunExtendResolution,0,0,BackBuffer(),TextureBuffer(ProShadowsStructure\SunExtendShadowMapTexture)
		EndIf
		
		HideEntity ProShadowsStructure\CastersPivot
		HideEntity ProShadowsStructure\ShadowCamera
		
		TFormPoint 0,0,0,0,ProShadowsStructure\ShadowCamera
			ProShadowsStructure\SunExtendMatrix\m41 = TFormedX()*64.0			;real zooom
			ProShadowsStructure\SunExtendMatrix\m42 = TFormedY()*64.0
			ProShadowsStructure\SunExtendMatrix\m43 = 0
		SetProjMatrix ProShadowsStructure\SunExtendTextureIndex, ProShadowsStructure\SunExtendMatrix

	EndIf 


EndIf 

;==========================================================================================
;SPOT
;==========================================================================================

For ProShadowsSpotLight.ProShadowsSpotLightStructure=Each ProShadowsSpotLightStructure

	RotateEntity ProShadowsStructure\ShadowCamera,ProShadowsSpotLight\pt,ProShadowsSpotLight\yw,ProShadowsSpotLight\rl
	PositionEntity ProShadowsStructure\ShadowCamera,ProShadowsSpotLight\x,ProShadowsSpotLight\y,ProShadowsSpotLight\z

	ShowEntity ProShadowsStructure\CastersPivot
	ShowEntity ProShadowsStructure\ShadowCamera

	If ProShadowsStructure\UseCubeMaps=1
		RotateEntity ProShadowsSpotLight\Pivot,ProShadowsSpotLight\pt,ProShadowsSpotLight\yw,ProShadowsSpotLight\rl
		ShowEntity ProShadowsSpotLight\Pivot
	EndIf 
	
	If ProShadowsStructure\RenderMethod=1
		SetBuffer TextureBuffer(ProShadowsSpotLight\ShadowMapTexture)
	Else
		SetBuffer BackBuffer()
	EndIf 

	CameraViewport(ProShadowsStructure\ShadowCamera,0,0,ProShadowsSpotLight\Resolution,ProShadowsSpotLight\Resolution)
	CameraProjMode ProShadowsStructure\ShadowCamera,1
	CameraZoom ProShadowsStructure\ShadowCamera,(ProShadowsSpotLight\Zoom#)		;size of area
	CameraClsColor ProShadowsStructure\ShadowCamera,128,128,128
	CameraRange ProShadowsStructure\ShadowCamera,0.5,ProShadowsSpotLight\Range#
	CameraFogColor  ProShadowsStructure\ShadowCamera,128,128,128
	CameraFogRange ProShadowsStructure\ShadowCamera,1,ProShadowsSpotLight\Range#
	;CameraFogMode ProShadowsStructure\ShadowCamera,1

	ShowEntity ProShadowsSpotLight\FadeMesh
	ShowEntity ProShadowsStructure\ShadowCamera
	


	RenderEntity(ProShadowsStructure\CastersPivot, ProShadowsStructure\ShadowCamera,1)

;	If ProShadowsStructure\UseCubeMaps=1
;		HideEntity ProShadowsSpotLight\Pivot
;	EndIf 
;
	CameraFogMode ProShadowsStructure\ShadowCamera,0
	HideEntity ProShadowsSpotLight\FadeMesh


	If ProShadowsStructure\RenderMethod=0
		CopyRect 0,0,ProShadowsSpotLight\Resolution,ProShadowsSpotLight\Resolution,0,0,BackBuffer(),TextureBuffer(ProShadowsSpotLight\ShadowMapTexture)
	EndIf
	
	HideEntity ProShadowsStructure\CastersPivot
	HideEntity ProShadowsStructure\ShadowCamera
	
	SetViewMatrixCurrent(ProShadowsSpotLight\ShadowMapTextureIndex)
	CopyProjMatrix(ProShadowsSpotLight\ShadowMapMatrix)
	SetProjMatrix(ProShadowsSpotLight\ShadowMapTextureIndex, ProShadowsSpotLight\ShadowMapMatrix)

	
	;move back
	
	;fit texture to the sizes
	;MMSM_ScaleMatrix\m11=MMSM_Glb\Zoom
	;MMSM_ScaleMatrix\m22=MMSM_Glb\Zoom
	;SM_MatrixMultiply%(MMSM_ShadowMapMatrix,MMSM_ResultMatrix,MMSM_ScaleMatrix)
	;fix projected texture position
	;MMSM_ShadowMapMatrix\m41=MMSM_ShadowMapMatrix\m41+ShadowDepth*MMSM_Glb\Zoom*0.5
	;MMSM_ShadowMapMatrix\m42=MMSM_ShadowMapMatrix\m42-ShadowDepth*MMSM_Glb\Zoom*0.5

	
	;MatrixMultiply(ProShadowsSpotLight\GradientMatrix,ProShadowsSpotLight\ShadowMapMatrix,ProShadowsSpotLight\RotationMatrix)
	MatrixMultiply(ProShadowsSpotLight\GradientMatrix,ProShadowsSpotLight\RotationMatrix,ProShadowsSpotLight\ShadowMapMatrix)
	MatrixMultiply(ProShadowsSpotLight\ResultGradientMatrix,ProShadowsSpotLight\GradientMatrix,ProShadowsSpotLight\ScaleMatrix)

	;SM_MatrixMultiply%(MMSM_ShadowMapMatrix,MMSM_ResultMatrix,MMSM_ScaleMatrix)
	SetViewMatrixCurrent(ProShadowsSpotLight\GradientTextureIndex)
	
	ProShadowsSpotLight\ResultGradientMatrix\m43=ProShadowsSpotLight\ResultGradientMatrix\m43 + ProShadowsSpotLight\Range*ProShadowsSpotLight\Zoom*50.0
	SetProjMatrix ProShadowsSpotLight\GradientTextureIndex, ProShadowsSpotLight\ResultGradientMatrix

;
;
;	SM_MatrixMultiply%(MMSM_ResultMatrix,MMSM_ShadowMapMatrix,MMSM_RotateMatrix)
;	;move back
;	MMSM_ResultMatrix\m43=MMSM_ResultMatrix\m43+ExceptedShadowMapSize*0.5 + MMSM_Glb\Zoom*ShadowDepth*0.5
;	;fit texture to the sizes
;	MMSM_ScaleMatrix\m11=MMSM_Glb\Zoom
;	MMSM_ScaleMatrix\m22=MMSM_Glb\Zoom
;	SM_MatrixMultiply%(MMSM_ShadowMapMatrix,MMSM_ResultMatrix,MMSM_ScaleMatrix)
;
;
	


Next 






SetBuffer OldBuffer




	
End Function
Function ProShadows_DotProductLayer()
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
Return ProShadowsStructure\DotProductLayer
End Function
Function ProShadows_DiffuseLayer()
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
Return ProShadowsStructure\DiffuseLayer
End Function 
Function ProShadows_ShowCasters()
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
ShowEntity ProShadowsStructure\CastersPivot
End Function
Function ProShadows_HideCasters()
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
HideEntity ProShadowsStructure\CastersPivot
End Function 

Function ProShadows_PrepareAlphaTextureForCasters(Texture)
SetBuffer TextureBuffer(Texture)
LockBuffer(TextureBuffer(Texture))
	width=TextureWidth(Texture)-1
	height=TextureHeight(Texture)-1
	For x=0 To width
	For y=0 To height
		Pixel=ReadPixelFast(x,y)
		WritePixelFast(x,y, Pixel Or $00FFFFFF)
	Next
	Next 
UnlockBuffer(TextureBuffer(Texture))
SetBuffer BackBuffer()
End Function 

Function ProShadows_PrepareMaskedTextureForRecievers(Texture, ColorPixel=$00000000)
OldBuffer=GraphicsBuffer ()
SetBuffer TextureBuffer(Texture)
LockBuffer(TextureBuffer(Texture))
	width=TextureWidth(Texture)-1
	height=TextureHeight(Texture)-1
	For x=0 To width
	For y=0 To height
		Pixel=ReadPixelFast(x,y)
		If Pixel Shr 24 = 0 Then  WritePixelFast(x,y, ColorPixel)
			 
	Next
	Next 
UnlockBuffer(TextureBuffer(Texture))
SetBuffer OldBuffer
TextureBlendCustom(Texture, D3DTOP_MODULATE%, 2)
End Function 

Function ProShadows_AddCaster(CasterEntity,Group=0,AlphaTexture=0)
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType

EntityParent CasterEntity,ProShadowsStructure\CastersPivot

Select group
	Case 1
		Brush=CreateBrush(0,128,128)
	Case 2
		Brush=CreateBrush(128,255,128)
	Case 3 
		Brush=CreateBrush(128,0,128)
	Case 4
		Brush=CreateBrush(128,128,255)
	Case 5 
		Brush=CreateBrush(128,128,0)
	Default 
		Brush=CreateBrush(255,128,128)
End Select 
	
BrushFX Brush,1+32*(AlphaTexture>0)
If AlphaTexture Then BrushTexture Brush,AlphaTexture,0,0


If EntityClass(CasterEntity)="Mesh"
	For i=1 To CountSurfaces(CasterEntity)
		PaintSurface(GetSurface(CasterEntity,i),brush)
	Next
Else
	PaintEntity  CasterEntity, Brush
EndIf 


Return CasterEntity
End Function 
Function ProShadows_PaintRecieverSun(RecieverEntity,Group=0, ForceAlpha=0,StaticAmbient=0)
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType

ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType


Brush=CreateBrush(255,255,255)
BrushFX Brush,1+32*(ForceAlpha>0)

If ProShadowsStructure\SunExtend

	BrushTexture Brush, ProShadowsStructure\SunShadowMapTexture,0,0
	BrushTexture Brush, ProShadowsStructure\SunExtendShadowMapTexture,0,1
	BrushTexture Brush, ProShadowsStructure\SunDotProductTexture[Group],0,2
	If StaticAmbient=1
		BrushTexture Brush, ProShadowsStructure\SunAmbientStaticTexture,0,3
	Else
		BrushTexture Brush, ProShadowsStructure\SunAmbientDynamicTexture,0,3
	EndIf

Else

	BrushTexture Brush, ProShadowsStructure\SunShadowMapTexture,0,0
	BrushTexture Brush, ProShadowsStructure\SunDotProductTexture[Group],0,1
	If StaticAmbient=1
		BrushTexture Brush, ProShadowsStructure\SunAmbientStaticTexture,0,2
	Else
		BrushTexture Brush, ProShadowsStructure\SunAmbientDynamicTexture,0,2
	EndIf

EndIf 

If EntityClass(RecieverEntity)="Mesh"
	For i=1 To CountSurfaces(RecieverEntity)
		PaintSurface(GetSurface(RecieverEntity,i),brush)
	Next
Else
	PaintEntity  RecieverEntity, Brush
EndIf 

End Function 



Function ProShadows_RotateSunLight(SunPt#, SunYw#, SunRl#)

ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
;if sun exists
If ProShadowsStructure\Sun=1
	;store angles
	ProShadowsStructure\SunPt=SunPt
	ProShadowsStructure\SunYw=SunYw
	ProShadowsStructure\SunRl=SunRl
	;Read sun matrix 
	CameraProjMode ProShadowsStructure\ShadowCamera,1
	CameraZoom(ProShadowsStructure\ShadowCamera,64.0)
	CameraViewport(ProShadowsStructure\ShadowCamera,0,0,32,32)
	CameraRange ProShadowsStructure\ShadowCamera,1,ProShadowsStructure\SunRange#*64.0*1.5
	PositionEntity ProShadowsStructure\ShadowCamera,0,0,0
	RotateEntity(ProShadowsStructure\ShadowCamera, SunPt#, SunYw#, SunRl#)
	MoveEntity ProShadowsStructure\ShadowCamera,0,0,-64.0*ProShadowsStructure\SunRange#
	ShowEntity ProShadowsStructure\ShadowCamera
	ShowEntity ProShadowsStructure\CastersPivot
		RenderEntity(ProShadowsStructure\CastersPivot, ProShadowsStructure\ShadowCamera,1)
		SetViewMatrixCurrent(ProShadowsStructure\SunTextureIndex)
		CopyProjMatrix(ProShadowsStructure\SunMatrix)
		SetProjMatrix(ProShadowsStructure\SunTextureIndex, ProShadowsStructure\SunMatrix)
	HideEntity ProShadowsStructure\ShadowCamera
	HideEntity ProShadowsStructure\CastersPivot
	
	;Read sun extend matrix
	If ProShadowsStructure\SunExtend=1
		CameraProjMode ProShadowsStructure\ShadowCamera,1
		CameraZoom(ProShadowsStructure\ShadowCamera,64.0)
		CameraViewport(ProShadowsStructure\ShadowCamera,0,0,32,32)
		PositionEntity ProShadowsStructure\ShadowCamera,0,0,0
		RotateEntity(ProShadowsStructure\ShadowCamera, SunPt#, SunYw#, SunRl#)
		MoveEntity ProShadowsStructure\ShadowCamera,0,0,-64.0*ProShadowsStructure\SunExtendRange#
		ShowEntity ProShadowsStructure\ShadowCamera
		ShowEntity ProShadowsStructure\CastersPivot
			RenderEntity(ProShadowsStructure\CastersPivot, ProShadowsStructure\ShadowCamera,1)
			SetViewMatrixCurrent(ProShadowsStructure\SunExtendTextureIndex)
			CopyProjMatrix(ProShadowsStructure\SunExtendMatrix)
			SetProjMatrix(ProShadowsStructure\SunExtendTextureIndex, ProShadowsStructure\SunExtendMatrix)
		HideEntity ProShadowsStructure\ShadowCamera
		HideEntity ProShadowsStructure\CastersPivot
	EndIf 

EndIf 
End Function


;Perfect scheme

Type ProShadowsSpotLightStructure
	Field x#,y#,z#
	Field pt#,yw#,rl#
	Field Pivot
	Field Resolution

	Field FadeTexture, FadeMesh
	Field Range#, Zoom#
	Field ShadowMapTexture
	Field ShadowMapTextureIndex
	Field GradientTexture
	Field GradientTextureIndex

	Field ShadowMapMatrix.Matrix3d
	Field GradientMatrix.Matrix3d
	Field RotationMatrix.Matrix3d
	Field ScaleMatrix.Matrix3d
	Field ResultGradientMatrix.Matrix3d

	Field DotProductTexture[6]
	Field TEMPAMBIENTTEXTURE
	
End Type 


Function ProShadows_CreateSpotLight(Quality=1, Range#=10.0, Zoom#=1)
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType
ProShadowsStructure\DotProductLayer=1
ProShadowsStructure\DiffuseLayer=3

ProShadowsSpotLight.ProShadowsSpotLightStructure=New ProShadowsSpotLightStructure
ProShadowsSpotLight\TEMPAMBIENTTEXTURE=CreateTextureFilled(96,96,96,3)

;============================
;Sun shadow map routines
;============================
ProShadowsSpotLight\Range# = Range#
ProShadowsSpotLight\Zoom# = Zoom#
;set shadow map quality
Select Quality
	Case 0
		ProShadowsSpotLight\Resolution=256
	Case 1
		ProShadowsSpotLight\Resolution=512
	Case 2
		ProShadowsSpotLight\Resolution=1024
	Case 3
		ProShadowsSpotLight\Resolution=2048
	Default
		ProShadowsSpotLight\Resolution=512
End Select
;create shadow map texture via rendermethod
Select ProShadowsStructure\RenderMethod
	Case 0		;Copy from BackBuffer()
		;Sun shadow map texture - limit texture size by graphics mode
		MaxGraphicsSize = GraphicsHeight()
		If GraphicsHeight()>GraphicsWidth() Then MaxGraphicsSize  = GraphicsWidth()
		While ProShadowsSpotLight\Resolution>MaxGraphicsSize
			ProShadowsSpotLight\Resolution= ProShadowsSpotLight\Resolution Shr 1
		Wend
		ProShadowsSpotLight\ShadowMapTexture = CreateTexture(ProShadowsSpotLight\Resolution, ProShadowsSpotLight\Resolution, 1+16+32+256 )
	Case 1		;Draw to TextureBuffer()
		;Sun shadow map texture - limit texture size by videocard capability
		If ProShadowsSpotLight\Resolution>GfxDriverCapsEx\TextureMaxWidth Then ProShadowsSpotLight\Resolution = GfxDriverCapsEx\TextureMaxWidth
		If ProShadowsSpotLight\Resolution>GfxDriverCapsEx\TextureMaxHeight Then ProShadowsSpotLight\Resolution = GfxDriverCapsEx\TextureMaxHeight
		ProShadowsSpotLight\ShadowMapTexture = CreateTexture(ProShadowsSpotLight\Resolution, ProShadowsSpotLight\Resolution, 1+16+32+256+FE_RENDER+FE_ZRENDER )
End Select
;set blends for shadow texture
ScaleTexture(ProShadowsSpotLight\ShadowMapTexture , 1.0, 1.0)
ShadowTexBlend = FETOP_PROJECT3D1 Or (D3DTOP_MODULATE Shl 8) Or D3DTOP_MODULATE
TextureBlend(ProShadowsSpotLight\ShadowMapTexture, ShadowTexBlend)
ProShadowsSpotLight\ShadowMapTextureIndex=ProShadows_GetNextTextureIndex()
TextureIndex(ProShadowsSpotLight\ShadowMapTexture, ProShadowsSpotLight\ShadowMapTextureIndex)

;============================
; build project matrices
;============================
ProShadowsSpotLight\ShadowMapMatrix=New Matrix3d
ProShadowsSpotLight\GradientMatrix=New Matrix3d
ProShadowsSpotLight\ResultGradientMatrix= New Matrix3d
ProShadowsSpotLight\RotationMatrix=GenerateMatrixFromEuler.Matrix3D(270,0,0)
ProShadowsSpotLight\ScaleMatrix=GenerateScaleMatrix.Matrix3D(100,100,1)


;============================
;Load overlay textures  and build overlay mesh
;============================
	;load textures
	ProShadowsSpotLight\FadeTexture=LoadTexture("Textures\Overlay_SpotLight.png",1+2+16+32)
	ProShadowsSpotLight\FadeMesh=CreateMesh(ProShadowsStructure\ShadowCamera)
	;make fade sprite
	SpriteSize#=(1.0 / Zoom#)*1.02
	surf=CreateSurface(ProShadowsSpotLight\FadeMesh)
	v0=AddVertex(surf,-SpriteSize,SpriteSize,1.0,0,0)
	v1=AddVertex(surf,SpriteSize,SpriteSize,1.0,1,0)
	v2=AddVertex(surf,SpriteSize,-SpriteSize,1.0,1,1)
	v3=AddVertex(surf,-SpriteSize,-SpriteSize,1.0,0,1)
	AddTriangle surf,v0,v1,v2
	AddTriangle surf,v0,v2,v3
	EntityFX ProShadowsSpotLight\FadeMesh,1+32
	EntityTexture ProShadowsSpotLight\FadeMesh, ProShadowsSpotLight\FadeTexture, 0, 0
	HideEntity ProShadowsSpotLight\FadeMesh


;============================
;DotProduct textures
;============================
If ProShadowsStructure\UseCubeMaps=1
	;set cubemap rotation matrix pivot
	ProShadowsSpotLight\Pivot=CreatePivot()
	ProShadowsSpotLight\DotProductTexture[0]=CreateTextureCube(255,128,128,4,2,ProShadowsSpotLight\Pivot)
	ProShadowsSpotLight\DotProductTexture[1]=CreateTextureCube(0,128,128,4,2,ProShadowsSpotLight\Pivot)
	ProShadowsSpotLight\DotProductTexture[2]=CreateTextureCube(128,255,128,4,2,ProShadowsSpotLight\Pivot)
	ProShadowsSpotLight\DotProductTexture[3]=CreateTextureCube(128,0,128,4,2,ProShadowsSpotLight\Pivot)
	ProShadowsSpotLight\DotProductTexture[4]=CreateTextureCube(128,128,255,4,2,ProShadowsSpotLight\Pivot)
	ProShadowsSpotLight\DotProductTexture[5]=CreateTextureCube(128,128,0,4,2,ProShadowsSpotLight\Pivot)
	
Else 
	ProShadowsSpotLight\DotProductTexture[0]=CreateTextureFilled(255,128,128,4)
	ProShadowsSpotLight\DotProductTexture[1]=CreateTextureFilled(0,128,128,4)
	ProShadowsSpotLight\DotProductTexture[2]=CreateTextureFilled(128,255,128,4)
	ProShadowsSpotLight\DotProductTexture[3]=CreateTextureFilled(128,0,128,4)
	ProShadowsSpotLight\DotProductTexture[4]=CreateTextureFilled(128,128,255,4)
	ProShadowsSpotLight\DotProductTexture[5]=CreateTextureFilled(128,128,0,4)
EndIf







;============================
;Make Add Textures
;light up from dark(0,0,0)  to Amb (96,96,96) after DotProducts
;============================

;simple ambient level
ProShadowsSpotLight\GradientTexture=LoadTexture("Textures\gradiento.tga",1+2+32)
;ProShadowsSpotLight\GradientTexture=LoadTexture(ProShadowResourcesPath$+"hellknight_bump.jpg",1+2+32)

ScaleTexture(ProShadowsSpotLight\GradientTexture , 1.0, 1.0)
GradientTextureBlend = FETOP_PROJECT3D1 Or (D3DTOP_BLENDTEXTUREALPHAPM Shl 8) Or D3DTOP_BLENDTEXTUREALPHAPM	
TextureBlend(ProShadowsSpotLight\GradientTexture, GradientTextureBlend)
ProShadowsSpotLight\GradientTextureIndex=ProShadows_GetNextTextureIndex()
TextureIndex(ProShadowsSpotLight\GradientTexture, ProShadowsSpotLight\GradientTextureIndex)


End Function 
Function ProShadows_PaintRecieverSpot(RecieverEntity,Group=0, ForceAlpha=0,StaticAmbient=0)
ProShadowsStructure.ProShadowsStructureType=First ProShadowsStructureType

ProShadowsSpotLight.ProShadowsSpotLightStructure=First ProShadowsSpotLightStructure
Brush=CreateBrush(255,255,255)
BrushFX Brush,1+32*(ForceAlpha>0)

BrushTexture Brush, ProShadowsSpotLight\ShadowMapTexture,0,0
BrushTexture Brush, ProShadowsSpotLight\DotProductTexture[Group],0,1
BrushTexture Brush, ProShadowsSpotLight\GradientTexture,0,2

;BrushTexture Brush, ProShadowsSpotLight\TEMPAMBIENTTEXTURE,0,2
If EntityClass(RecieverEntity)="Mesh"
	For i=1 To CountSurfaces(RecieverEntity)
		PaintSurface(GetSurface(RecieverEntity,i),brush)
	Next
Else
	PaintEntity  RecieverEntity, Brush
EndIf 

End Function 


Function ProShadows_SpotSet(x#,y#,z#,pt#,yw#,rl#)
ProShadowsSpotLight.ProShadowsSpotLightStructure=First ProShadowsSpotLightStructure
ProShadowsSpotLight\x=x
ProShadowsSpotLight\y=y
ProShadowsSpotLight\z=z
ProShadowsSpotLight\pt=pt
ProShadowsSpotLight\yw=yw
ProShadowsSpotLight\rl=rl
End Function 
Function CreateTextureFilled(rc,gc,bc,blend)
tex=CreateTexture(2,2)
OldBuffer=GraphicsBuffer()
SetBuffer TextureBuffer(tex)
	Color rc,gc,bc
	Rect 0,0,2,2,1
SetBuffer OldBuffer
TextureBlend tex,blend
Return tex
End Function
Function CreateTextureCube(Rc, Gc, Bc, Blend, CubeMode, Pivot=0)
CubeTexture=CreateTexture(32,32,128+1)

Color rc,gc,bc
Rect 0,0,16,32,1
Color 128,128,128
Rect 16,0,16,32,1
SetCubeFace (CubeTexture, 0)
CopyRect 0,0,32,32,0,0,0,TextureBuffer (CubeTexture)

Color rc,gc,bc
Rect 16,0,16,32,1
Color 128,128,128
Rect 0,0,16,32,1
SetCubeFace (CubeTexture, 2)
CopyRect 0,0,32,32,0,0,0,TextureBuffer (CubeTexture)

Color rc,gc,bc
Rect 0,0,32,32,1
SetCubeFace (CubeTexture, 3)
CopyRect 0,0,32,32,0,0,0,TextureBuffer (CubeTexture)

Color 128,128,128
Rect 0,0,32,32,1
SetCubeFace (CubeTexture, 1)
CopyRect 0,0,32,32,0,0,0,TextureBuffer (CubeTexture)

Color rc,gc,bc
Rect 0,0,32,16,1
Color 128,128,128
Rect 0,16,32,16,1
SetCubeFace (CubeTexture, 4)
CopyRect 0,0,32,32,0,0,0,TextureBuffer (CubeTexture)

Color rc,gc,bc
Rect 0,16,32,16,1
Color 128,128,128
Rect 0,0,32,16,1
SetCubeFace (CubeTexture, 5)
CopyRect 0,0,32,32,0,0,0,TextureBuffer (CubeTexture)

TextureBlend CubeTexture, blend
SetCubeMode CubeTexture,cubemode
If Pivot<>0 Then SetCubeAlign(CubeTexture, pivot)
Return CubeTexture
End Function 




Function GenerateMatrixFromEuler.Matrix3D(Pt#,Yw#,Rl#)
;http://www.j3d.org/matrix_faq/matrfaq_latest.html
;Q36. How do I generate a rotation matrix from Euler angles?
RotMatrix.Matrix3D = New Matrix3D
A# = Cos(Pt#)
B# = Sin(Pt#)
C# = Cos(Yw#)
D# = Sin(Yw#)
E# = Cos(Rl#)
F# = Sin(Rl#)
AD# = A * D
BD# = B * D


RotMatrix\m11# = C * E
RotMatrix\m12# = -C * F
RotMatrix\m13# = D
RotMatrix\m14# = 0

RotMatrix\m21# = BD * E + A * F
RotMatrix\m22# = (-BD * F + A * E )
RotMatrix\m23# = -B * C
RotMatrix\m24# = 0

RotMatrix\m31# = AD * E + B * F
RotMatrix\m32# = AD * F + B * E
RotMatrix\m33# = A * C 
RotMatrix\m34# = 0

RotMatrix\m41# = 0
RotMatrix\m42# = 0
RotMatrix\m43# = 0
RotMatrix\m44# = 1

Return RotMatrix.Matrix3D
End Function
Function GenerateScaleMatrix.Matrix3D(xs#,ys#,zs#)
;http://www.j3d.org/matrix_faq/matrfaq_latest.html
;Q42. What is a scaling matrix?
ScaleMatrix.Matrix3D = New Matrix3D

ScaleMatrix\m11# = xs
ScaleMatrix\m12# = 0
ScaleMatrix\m13# = 0
ScaleMatrix\m14# = 0

ScaleMatrix\m21# = 0
ScaleMatrix\m22# = ys
ScaleMatrix\m23# = 0
ScaleMatrix\m24# = 0

ScaleMatrix\m31# = 0
ScaleMatrix\m32# = 0
ScaleMatrix\m33# = zs
ScaleMatrix\m34# = 0

ScaleMatrix\m41# = 0
ScaleMatrix\m42# = 0
ScaleMatrix\m43# = 0
ScaleMatrix\m44# = 1

Return ScaleMatrix.Matrix3D
End Function


