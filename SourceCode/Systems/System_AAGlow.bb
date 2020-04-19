; Anti Alias/Glow code writen by Nate The Great

; createaamesh function arguments:
;
; Mesh
; Camera
; Number of layers
; Distance between layers
; How translucent it should be (negative numbers make it more opaque)
; Scale with distance True/False
; Red
; Green
; Blue

Global HideAAFlag = False

Function UpdateHiddenAAMeshes()
	For aam.tAAMesh = Each tAAMesh
		If hideaaflag = True Then
			hideaa(aam.tAAMesh)
		Else
			showaa(aam.tAAMesh)
		EndIf
	Next
	
	If KeyHit(57) Then HideAAFlag = 1-HideAAFlag
	
	updateaameshes(cam)
End Function


Type tAAMesh
	Field entity
	Field ccount	;copy count, number of times to render the mesh
	Field gdist#		;gap- distance between meshes
	Field SDist		;Starting distance from the camera.
	Field cam
	Field meshes[10]
	Field hide
	Field scale
	Field supScale#
End Type

Function CreateAAMesh.tAAMesh(entity,cam,ccount,gdist#,alphadiv#,scale,r=255,g=255,b=255, sdist#=1, supScale#=1.0, blendmode=1, fx=0)
	Local a.tAAMesh = New tAAMesh
	a\entity = entity
	a\ccount = ccount
	a\gdist = gdist
	a\sdist = sdist
	a\scale = scale
	a\supScale = supScale
	a\cam = cam
	If sdist = 0 Then
		a\sdist = EntityDistance(cam,a\entity)
	EndIf
	
	For i = 1 To ccount
		a\meshes[i] = CopyMesh(entity)
		EntityAlpha a\meshes[i],((1.0/(ccount+1))*(ccount+1-i))/alphadiv
		ScaleEntity(a\meshes[i],i*gdist+1 * supScale#,i*gdist+1 * supScale#,i*gdist+1 * supScale#)
		PositionEntity(a\meshes[i],EntityX(entity),EntityY(entity),EntityZ(entity))
		TurnEntity(a\meshes[i],EntityPitch(entity),EntityYaw(entity),EntityRoll(entity))
		EntityColor a\meshes[i],r,g,b
		EntityBlend a\meshes[i],blendmode
		EntityFX a\meshes[i],fx
		EntityParent(a\meshes[i],entity)
	Next
	
	Return a.tAAMesh
End Function


Function hideAA(a.tAAMesh)
	a\hide = True
End Function
Function showAA(a.tAAMesh)
	a\hide = False
End Function


Function UpdateAAMeshes(cam)
	For a.tAAMesh = Each tAAMesh
		If a\cam = cam And a\hide = False Then
			For i = 1 To a\ccount
			If a\meshes[i] Then
				ShowEntity a\meshes[i]
				If a\scale = True Then
					Local dist# = EntityDistance(a\entity,cam)
					Local scl# = (i*a\gdist*(dist#/a\sdist#))+1.0
					ScaleEntity a\meshes[i],scl*a\supScale,scl*a\supScale,scl*a\supScale
				EndIf
			EndIf
			Next
		Else
			For i = 1 To a\ccount
				HideEntity a\meshes[i]
			Next
		EndIf
	Next
End Function

Function DeleteAAMesh(a.tAAMesh)
	For i=0 To 9
		If (a\meshes[i]<>0) Then FreeEntity a\meshes[i] : a\meshes[i]=0
	Next
	FreeEntity a\entity
	Delete a
End Function