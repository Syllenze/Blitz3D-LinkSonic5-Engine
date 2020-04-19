; ------------------------------------------------------------------------
; BlitzSonic Engine -- Classic Sonic the Hedgehog engine for Blitz 3D
; version 0.1, February 7th, 2008
;
; Copyright (C) 2008 - BlitzSonic Team.
; ------------------------------------------------------------------------
;
; This software is provided 'as-is', without any express or implied
; warranty.  In no event will the authors be held liable for any damages
; arising from the use of this software.
; 
; Permission is granted to anyone to use this software for any purpose
; (except for commercial applications) and to alter it and redistribute
; it freely subject to the following restrictions:
;
; 1. The origin of this software must not be misrepresented; you must not
;    claim that you wrote the original software. If you use this software
;    in a product, an acknowledgment in the product itself as a splash
;    screen is required.
; 2. Altered source versions must be plainly marked as such, and must not be
;    misrepresented as being the original software.
; 3. This notice may not be removed or altered from any source distribution.
;
; All characters and materials in relation to the Sonic the Hedgehog game series
; are copyrights/trademarks of SEGA of Japan (SEGA Co., LTD). This product
; has been developed without permission of SEGA, therefore it's prohibited
; to sell/make profit of it.
;
; The BlitzSonic Team:
; - Héctor "Damizean" (elgigantedeyeso at gmail dot com)
; - Mark "Coré" (mabc_bh at yahoo dot com dot br)
; - Streak Thunderstorm
; - Mista ED
;

;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;	Project Title : Sonic the Hedgehog                                                                         ;
; ============================================================================================================ ;
;	Author :                                                                                                   ;
;	Email :                                                                                                    ;
;	Version: 0.1                                                                                               ;
;	Date: --/--/2008                                                                                           ;
;                                                                                                              ;
;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;                                                                                                              ;
;   Changelog:  -(Damizean)------------------------------->                                                    ;
;               17/01/2008 - Code reorganization.                                                              ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO:                                                                                                      ;
;                                                                                                              ;
;==============================================================================================================;

Type tBrush
	Field brush
	Field texture
End Type

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; Min
	; ---------------------------------------------------------------------------------------------------------
	Function Min#(a#, b#)
		If (a#<b#) Then Return a#
		Return b#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Max
	; ---------------------------------------------------------------------------------------------------------
	Function Max#(a#, b#)
		If (a#>b#) Then Return a#
		Return b#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Clamp
	; ---------------------------------------------------------------------------------------------------------
	Function Clamp#(v#, m#, mx#)
		Return Min#(Max#(v#, m#), mx#)
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; WrapAngle
	; ---------------------------------------------------------------------------------------------------------
	Function WrapAngle#(a#)
		While(a#<0.0)
			a# = a#+360.0
		Wend 
		While(a#>=360.0)
			a# = a#-360.0
		Wend
		Return a#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RotateTowardsAngle
	; ---------------------------------------------------------------------------------------------------------
	Function RotateTowardsAngle#(o#, d#, s#)
    	OCos# = Cos(o#)
    	OSin# = Sin(o#)
    	DCos# = Cos(d#)
    	DSin# = Sin(d#)
    
    	Z#		= OCos*DSin-OSin*DCos
		Dot#	= OCos*DCos+OSin*DSin
		a#		= ACos(Dot#)
    	If (a#>s#) Then 
        	If (Z# >= 0) Then Return WrapAngle(Min#(o#+s#, d#))
	        Return WrapAngle#(Max#(o#-s#, d#))
		End If
    	Return WrapAngle#(o#)
End Function

	; ---------------------------------------------------------------------------------------------------------
	; RotateTowardsAngle1
	; ---------------------------------------------------------------------------------------------------------
Function RotateTowardsAngle1#(o#, d#, s#)
	OCos# = Cos(o#)
	OSin# = Sin(o#)
	DCos# = Cos(d#)
	DSin# = Sin(d#)
    
	Z#		= OCos*DSin-OSin*DCos
	Dot#	= OCos*DCos+OSin*DSin
	a#		= ACos(Dot#)
	If (a#>s#) Then 
		If (Z# >= 0) Then Return WrapAngle(Min#(o#+s#, d#))
		Return WrapAngle#(Max#(o#-s#, d#))
End If
Return WrapAngle#(o#)
End Function

	; ---------------------------------------------------------------------------------------------------------
	; CountToValue
	; ---------------------------------------------------------------------------------------------------------
Function CountToValue#(a#, b#, r#, d#)
	For p.tPlayer = Each tPlayer
		
	;	ba# = Abs(b#)
		
		If (a#<b#) Then
			o# = a# + (d#*(r#))
	EndIf
	
	If (a#>b#) Then
		o# = a# - (d#*(r#))
	EndIf
	
	If ((a# < (b#+2)) And (a# > (b#-2))) Then
		o# = b#
	EndIf
	
Next
	
Return o#
	
End Function

	; ---------------------------------------------------------------------------------------------------------
	; AngleDifference
	; ---------------------------------------------------------------------------------------------------------
	Function AngleDifference#(a#, b#)
		If (a#<b#) Then Return Min(Abs(a#-b#), Abs((a#+360.0)-b#))
		Return Min(Abs(b#-a#), Abs((b#+360.0)-a#))		
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveEntityType
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveEntityType(e, t)
		EntityType(e, t)
		For i=1 To CountChildren(e)
			RecursiveEntityType(GetChild(e, i), t)
		Next 
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecurisveEntityPickMode
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveEntityPickMode(entity, pick_geometry, obscurer=True)
		EntityPickMode(entity, pick_geometry, obscurer)
		For i=1 To CountChildren(entity)
			RecursiveEntityPickMode(GetChild(entity, i), pick_geometry, obscurer)
		Next
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveAnimate
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveAnimate(entity, mode=1, speed#=1.0, sequence=0, transition#=0.0)
		If (AnimLength(entity)<>-1) Then Animate(entity, mode, speed#, sequence, transition#)
		For i=1 To CountChildren(entity)	
			RecursiveAnimate(GetChild(entity, i), mode, speed#, sequence, transition#)
		Next
End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveRotate
	; ---------------------------------------------------------------------------------------------------------
Function RecursiveRotate(entity, x#, y#, z#)
	RotateEntity(entity, x#, y#, z#)
	For i=1 To CountChildren(entity)	
		RecursiveRotate(GetChild(entity, i), x#, y#, z#)
	Next
End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveExtractAnimSeq
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveExtractAnimSeq(entity, first_frame, last_frame, anim_seq=0)
		If (AnimLength(entity)<>-1) Then ExtractAnimSeq(entity, first_frame, last_frame, anim_seq)
		For i=1 To CountChildren(entity)
			RecursiveExtractAnimSeq(GetChild(entity, i), first_frame, last_frame, anim_seq)
		Next
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveSetAnimTime
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveSetAnimTime(entity, time#, anim_seq=0)
		If (AnimLength(entity)<>-1) Then SetAnimTime(entity, time#, anim_seq)
		For i=1 To CountChildren(entity)
			RecursiveSetAnimTime(GetChild(entity, i), time#, anim_seq)
		Next
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveUpdateNormals
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveUpdateNormals(entity)
		If (EntityClass$(entity)="Mesh") Then UpdateNormals(entity)
		For i=1 To CountChildren(entity)
			RecursiveUpdateNormals(GetChild(entity, i))
		Next
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveEntityAutoFade
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveEntityAutoFade(entity, near#, far#)
		If (EntityClass$(entity)="Mesh") Then EntityAutoFade(entity, near#, far#)
		For i=1 To CountChildren(entity)
			RecursiveEntityAutoFade(GetChild(entity, i), near#, far#)
		Next
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveEntityFX
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveEntityFx(entity, fx)
		If (EntityClass$(entity)="Mesh") Then EntityFX(entity, fx)
		For i=1 To CountChildren(entity)
			RecursiveEntityFx(GetChild(entity, i), fx)
		Next
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveEntityOrder
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveEntityOrder(entity, order)
		If (EntityClass$(entity)="Mesh") Then EntityOrder(entity, order)
		For i=1 To CountChildren(entity)
			RecursiveEntityOrder(GetChild(entity, i), order)
		Next
End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveEntityPaint
	; ---------------------------------------------------------------------------------------------------------
Function RecursiveEntityPaint(entity, paint$="", a#=1, flag=16, blend=1)
	If (EntityClass$(entity)="Mesh") Then
		Brush.tBrush=New tBrush
		Brush\brush=CreateBrush(255,255,255)
		If (paint$<>"") Then
			Brush\texture=LoadTexture(paint$,4)
			BrushTexture(Brush\brush,Brush\texture)
		End If
		BrushBlend(Brush\brush,blend)
		BrushAlpha(Brush\brush,a#)
		PaintMesh(entity,Brush\brush)
		EntityFX(entity,flag)
	End If
	For i=1 To CountChildren(entity)
		RecursiveEntityPaint(GetChild(entity, i), paint$, a#, flag, blend)
	Next
	Return
End Function

	; ---------------------------------------------------------------------------------------------------------
	; RecursiveEntityBlend
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveEntityBlend(entity, blend)
		If (EntityClass$(entity)="Mesh") Then EntityBlend(entity, blend)
		For i=1 To CountChildren(entity)
			RecursiveEntityBlend(GetChild(entity, i), blend)
		Next
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; RecursiveEntityColor
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveEntityColor(entity, r, g, b)
		If (EntityClass$(entity)="Mesh") Then EntityColor(entity, r, g, b)
		For i=1 To CountChildren(entity)
			RecursiveEntityColor(GetChild(entity, i), r, g, b)
		Next
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Interpolate
	; ---------------------------------------------------------------------------------------------------------
	Function Interpolate#(a#, b#, t#)
		Return a#+(b#-a#)*Clamp(t#, 0, 1)
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; InterpolateAngle
	; ---------------------------------------------------------------------------------------------------------
	Function InterpolateAngle#(a#, b#, t#)
		Sign = 1
    	If (Abs(a#-b#) < 180) Then Sign = -1

		t# = Clamp#(t#, 0, 1)
		
        Return ATan2(-(Sin(a#)+(Sin(b#)-Sin(a#)))*t#*Sign, Cos(a#)+(Cos(b#)-Cos(a#))*t#*Sign)
	End Function	

	; ---------------------------------------------------------------------------------------------------------
	; ZeroPadding
	; ---------------------------------------------------------------------------------------------------------
	Function ZeroPadding$(s$, n)
		l = n-Len(s$)
		While(l>0)
			s$ = "0"+s$
			l = l-1
		Wend
		Return s$
End Function

	; ---------------------------------------------------------------------------------------------------------
	; CreateQuad
	; ---------------------------------------------------------------------------------------------------------
	Function CreateQuad(SizeX#, SizeZ#, OffsetX#, OffsetZ#, Parent=0)
		Quad    = CreateMesh(Parent)
		Surface = CreateSurface(Quad)
		
		AddVertex(Surface, -OffsetX#, 			0, -OffsetZ#, 			0, 1)
		AddVertex(Surface, -OffsetX#+SizeX#, 	0, -OffsetZ#, 			1, 1)
		AddVertex(Surface, -OffsetX#,  			0, -OffsetZ#+SizeZ#, 	0, 0)
		AddVertex(Surface, -OffsetX#+SizeX#,	0, -OffsetZ#+SizeZ#, 	1, 0)
		
		AddTriangle(Surface, 0, 2, 1)
		AddTriangle(Surface, 2, 3, 1)

		Return Quad
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; CalculateBoundingBox
	; ---------------------------------------------------------------------------------------------------------
	Function CalculateBoundingBox(Entity)
		MinX# = OMEGA#
		MinY# = OMEGA#
		MinZ# = OMEGA#
		MaxX# = EPSILON#
		MaxY# = EPSILON#
		MaxZ# = EPSILON#
		
		For i = 1 To CountSurfaces(Entity)
			Surface = GetSurface(Entity, i)
			For j = 0 To CountVertices(Surface)-1
				MinX# = Min#(VertexX#(Surface, j), MinX#)
				MinY# = Min#(VertexY#(Surface, j), MinY#)
				MinZ# = Min#(VertexZ#(Surface, j), MinZ#)

				MaxX# = Max#(VertexX#(Surface, j), MaxX#)
				MaxY# = Max#(VertexY#(Surface, j), MaxY#)
				MaxZ# = Max#(VertexZ#(Surface, j), MaxZ#)
			Next
		Next

		EntityBox(Entity, MinX#, MinY#, MinZ#, (MaxX#-MinX#)+1, (MaxY#-MinY#)+1, (MaxZ#-MinZ#)+1)
		DebugLog(MinX#+" "+MinY#+" "+MinZ#+" "+MaxX#+" "+MaxY#+" "+MaxZ#)
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; RecursiveAnimTime
	; ---------------------------------------------------------------------------------------------------------
	Function RecursiveAnimTime(entity, Tween#=1)
		If (AnimLength(entity)<>-1) Then Return Int(AnimTime(entity))
		For i=1 To CountChildren(entity)
				Local t
			t = Int(RecursiveAnimTime(GetChild(entity, i),Tween))
				If (t<> -1) Then Return t
		Next
		Return -1
	End Function
	
Function WriteClipboardText(txt$)
	Local cb_TEXT=1
	If txt$="" Then Return 
	If api_OpenClipboard(0)
		api_EmptyClipboard
	;	api_SetClipboardText cb_TEXT,txt$
		CopyTextToClipboard txt$
		api_CloseClipboard
	EndIf
End Function


Const recursive_resize=1024
Global recursive_bank=CreateBank(recursive_resize),recursive_size=recursive_resize
Global recursive_entity,recursive_parent,recursive_id,recursive_start,recursive_total,recursive_offset

Function findchildentity(entity,name$)
	name$=Lower$(name$)
	recursive_parent=entity
	recursive_start=1
	recursive_offset=0
	.recursive_label
	recursive_total=CountChildren(recursive_parent)
	For recursive_id=recursive_start To recursive_total
		recursive_entity=GetChild(recursive_parent,recursive_id)
		If name$=Lower$(EntityName$(recursive_entity))
			Return recursive_entity
		Else
			If recursive_offset+8 > recursive_size-1
				ResizeBank(recursive_bank,recursive_size+recursive_resize)
				recursive_size=recursive_size+recursive_resize
			End If
			PokeInt(recursive_bank,recursive_offset,recursive_id+1)
			PokeInt(recursive_bank,recursive_offset+4,recursive_parent)
			recursive_offset=recursive_offset+8
			recursive_start=1
			recursive_parent=recursive_entity
			Goto recursive_label
		End If
	Next
	If recursive_offset=0
		Return 0
	Else
		recursive_start=PeekInt(recursive_bank,recursive_offset-8)
		recursive_parent=PeekInt(recursive_bank,recursive_offset-4)
		recursive_offset=recursive_offset-8
		Goto recursive_label
	End If
End Function


;~IDEal Editor Parameters:
;~C#Blitz3D