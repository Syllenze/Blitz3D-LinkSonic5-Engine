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

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	STRUCTURES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; tVector
	; ---------------------------------------------------------------------------------------------------------
	Type tVector
		Field x#
		Field y#
		Field z#
	End Type

	; ---------------------------------------------------------------------------------------------------------
	; tPlane
	; ---------------------------------------------------------------------------------------------------------
	Type tPlane
		Field a#
		Field b#
		Field c#
		Field d#
	End Type
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Const TWOPI# 		= Pi*2.0
	Const HALFPI# 		= Pi*0.5
	Const QUARTERPI#   	= Pi*0.25
	Const EPSILON#     	= 0.000001
	Const OMEGA#       	= 10000000.0

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Vector management methods ----
	; ---------------------------------------------------------------------------------------------------------
	; Vector
	; ---------------------------------------------------------------------------------------------------------
	Function Vector.tVector(x#, y#, z#)
		v.tVector = New tVector
			v\x# = x#
			v\y# = y#
			v\z# = z#
		Return v
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_Copy
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Copy.tVector(v1.tVector)
		v.tVector = New tVector
			v\x# = v1\x#
			v\y# = v1\y#
			v\z# = v1\z#
		Return v
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Vector_Set
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Set(v.tVector, x#, y#, z#)
		v\x# = x#
		v\y# = y#
		v\z# = z#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_SetFromVector
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_SetFromVector(v1.tVector, v2.tVector)
		v1\x# = v2\x#
		v1\y# = v2\y#
		v1\z# = v2\z#
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Vector_Add
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Add(v1.tVector, v2.tVector)
		v1\x# = v1\x#+v2\x#
		v1\y# = v1\y#+v2\y#
		v1\z# = v1\z#+v2\z#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_Substract
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Substract(v1.tVector, v2.tVector)
		v1\x# = v1\x#-v2\x#
		v1\y# = v1\y#-v2\y#
		v1\z# = v1\z#-v2\z#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_Multiply
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Multiply(v1.tVector, v2.tVector)
		v1\x# = v1\x#*v2\x#
		v1\y# = v1\y#*v2\y#
		v1\z# = v1\z#*v2\z#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_MultiplyByScalar
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_MultiplyByScalar(v1.tVector, Scalar#)
		v1\x# = v1\x#*Scalar#
		v1\y# = v1\y#*Scalar#
		v1\z# = v1\z#*Scalar#
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Vector_Divide
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Divide(v1.tVector, v2.tVector)
		v1\x# = v1\x#/v2\x#
		v1\y# = v1\y#/v2\y#
		v1\z# = v1\z#/v2\z#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_DivideByScalar
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_DivideByScalar(v.tVector, Scalar#)
		v\x# = v\x#/Scalar#
		v\y# = v\y#/Scalar#
		v\z# = v\z#/Scalar#
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Vector_Length
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Length#(v.tVector)
		Return Sqr(v\x#*v\x#+v\y#*v\y#+v\z#*v\z#)
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Vector_Normalize
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Normalize(v.tVector)
		Length# = Vector_Length#(v)
		If (Length# <> 0.0) Length# = 1.0/Length#

		v\x# = v\x# * Length
		v\y# = v\y# * Length
		v\z# = v\z# * Length
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Vector_CrossProduct
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_CrossProduct.tVector(v1.tVector, v2.tVector, vout.tVector)
		x# = v1\y*v2\z-v1\z*v2\y
		y# = v1\z*v2\x-v1\x*v2\z
		z# = v1\x*v2\y-v1\y*v2\x
		vout\x# = x# : vout\y# = y# : vout\z# = z#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_DotProduct
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_DotProduct#(v1.tVector, v2.tVector)
		Return v1\x#*v2\x#+v1\y#*v2\y#+v1\z#*v2\z#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_DotProductNormalized
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_DotProductNormalized#(v1o.tVector, v2o.tVector)
		v1.tVector = Vector_Copy(v1o) : Vector_Normalize(v1)
		v2.tVector = Vector_Copy(v2o) : Vector_Normalize(v2)
		
		Result# = Vector_DotProduct(v1, v2)
		Delete v1 : Delete v2 : Return Result#
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Vector_LinearInterpolation
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_LinearInterpolation(v1.tVector, v2.tVector, t#)
		t# = Min#(Max#(t#, 0.0), 1.0)
		v1\x# = v1\x# + (v2\x#-v1\x#)*t#
		v1\y# = v1\y# + (v2\y#-v1\y#)*t#
		v1\z# = v1\z# + (v2\z#-v1\z#)*t#
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_Yaw
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Yaw(v.tVector)
		Return ATan2( v\x#, v\z# )
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_Pitch
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_Pitch(v.tVector)
		Return -ATan2( v\y#, Sqr#(v\x#*v\x#+v\z#*v\z#))
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Vector_AlignTo
	; ---------------------------------------------------------------------------------------------------------
	Function Vector_AlignTo(Entity, v.tVector, Axis, Rate#=1.0)
	
		Pitch# = (EntityPitch(Entity))
		Yaw#   = (EntityYaw(Entity))
		Roll#  = (EntityRoll(Entity))

		Select Axis
			Case 1
			
				DestYaw#   = (ATan2(v\z#,v\x#))
				DestRoll#  = (ATan2(v\y#,v\x#))
			
				Yaw#   = Yaw#+(DestYaw#-Yaw#)*Rate#
				Roll#  = Roll#+(DestRoll#-Roll#)*Rate#

			Case 2
				
				DestPitch# = (ATan2(v\z#,v\y#))
				DestRoll#  = (-ATan2(v\x#,v\y#))
			
				Pitch# = Pitch#+(DestPitch#-Pitch#)*Rate#
				Roll#  = Roll#+(DestRoll#-Roll#)*Rate#

			Case 3
			
				DestPitch# = (-ATan2(v\y#,v\z#))
				DestYaw#   = (-ATan2(v\x#,v\z#))
	
				Pitch# = Pitch#+(DestPitch#-Pitch#)*Rate#
				Yaw#   = Yaw#+(DestYaw#-Yaw#)*Rate#

		End Select
		
		RotateEntity(Entity, Pitch#, Yaw#, Roll#)
	End Function

	
;~IDEal Editor Parameters:
;~C#Blitz3D