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
;               17/01/2008 - Finally managed to fully convert management code to Delta Time. Works great :)    ;
;                                                                                                              ;
;               -(Damizean)------------------------------->                                                    ;
;               16/01/2008 - Code reorganization.                                                              ;
;                          - Rewrote some chunks of code to adapt to changes on the Maths library. The code    ;
;                            is now overall better readable.                                                   ;
;                          - Rewrote some chunks of code to work in a Delta Time environment. Not very good    ;
;                            results, but it's a start.                                                        ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO: - Implement basic movements, interaction with Rings, Monitors and Springs.                           ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Const PLAYER_MODE_MOUSELOOK_2	= 0
	Const PLAYER_MODE_ANALOG_2		= 1
	Const PLAYER_MODE_SRB_2			= 2
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Player management ----
	; =========================================================================================================
	; Player_Handle
	; =========================================================================================================
	Function Player_Handle2(p.tPlayer2, d.tDeltaTime)
		; Depending on current mode, the pressed direction uses a different method. Mouselook and analog are
		; quite similar in this aspect.
        		Select Gameplay_Control_Mode
			Case PLAYER_MODE_MOUSELOOK_2
		;		MotionDirection# = p\Objects\Camera2\Rotation2\y#-Input2\Movement_Direction#
				MotionPressure#  = Input2\Movement_Pressure#
			Case PLAYER_MODE_ANALOG_2
		;		MotionDirection# = p\Objects\Camera2\Rotation2\y#-Input2\Movement_Direction#
				MotionPressure#  = Input2\Movement_Pressure#
			Case PLAYER_MODE_SRB_2
		;		MotionDirection# = p\Objects\Camera2\Rotation2\y#-90*Input2\Movement_AnalogY#
				MotionPressure#  = Input2\Movement_Pressure#*Abs(Input2\Movement_AnalogY#)
		End Select
		
		; Declarate acceleration and speed vectors and setup.
		Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
		Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
		SpeedCompensation.tVector	= Vector(0, 0, 0)
		Speed_Length#				= Vector_Length#(Speed)
		
		; Disable skidding flag
		p\Flags\Skidding = False

		; If there exists acceleration, handle the acceleration and change to new
		; direction, preserving the momentum in the needed cases.
		If (Vector_Length#(Acceleration)) Then

			; Calculate delta cos and sin
			DeltaCos# = Cos#(p\Animation\Direction#-90)
			DeltaSin# = Sin#(p\Animation\Direction#-90)
			
			; Change Player's direction. Depending on current motion orientation and speed, this
			; direction change would be done instantly or smoothly. This rotation isn't done entirely
			; based on delta, because it would appear as if the character automatically rotates when
			; at low FPS xD
			If (Speed_Length# < 0.1) Then
				p\Animation\Direction# = ATan2(((Acceleration\x#+DeltaCos#*3)/4)*1.0001,-(Acceleration\z#+DeltaSin#*3)/4)
			Else
				p\Animation\Direction# = ATan2(((Acceleration\x#+DeltaCos#*5)/6)*1.0001,-(Acceleration\z#+DeltaSin#*5)/6)
			End If
			
			; Depending on the dot product between current direction and new motion direction
			DotProduct# = Vector_DotProductNormalized#(Acceleration, Speed)
			If (DotProduct# < 0.0) Then
					
				; If there's an opposite change of motion direction, completely 
				If (p\Motion\Ground = True) Then
					Vector_MultiplyByScalar(Acceleration, 1.2)

					If (Speed_Length#>0.4) Then
						p\Flags\Skidding = True
						If (ChannelPlaying(Channel_Skidding)=False) Then Channel_Skidding = PlaySound(Sound_Skidding)
					End If
				End If

				Player_SubstractTowardsZero(Speed, 0.06*d\Delta#)
				
			Else If (DotProduct# < 0.4) Then
			
				; If there's a harsh change in motion direction, decrease
				; greatly the motion in current direction and increase acceleration
				; on the new.
				If (p\Motion\Ground = True) Then
					SpeedCompensation\x# = (Speed\x#*33+DeltaCos#*Speed_Length#)/34*0.96
					SpeedCompensation\z# = (Speed\z#*33+DeltaSin#*Speed_Length#)/34*0.96
					
					Vector_LinearInterpolation(Speed, SpeedCompensation, d\Delta#)
				Else
					Player_SubstractTowardsZero(Speed, 0.02*d\Delta#)
				EndIf
				
				Vector_MultiplyByScalar(Acceleration, 1.2)
				
			Else If (DotProduct# < 0.95) Then

				; If there's a mild change in direction, slighty decresae
				; the motion in current direction.
				If (p\Motion\Ground = True) Then
					SpeedCompensation\x# = (Speed\x#*19+DeltaCos#*Speed_Length#)/20
					SpeedCompensation\z# = (Speed\z#*19+DeltaSin#*Speed_Length#)/20
				Else
					SpeedCompensation\x# = (Speed\x#*21+DeltaCos#*Speed_Length#)/22*0.98
					SpeedCompensation\z# = (Speed\z#*21+DeltaSin#*Speed_Length#)/22*0.98					
				EndIf
				
				Vector_LinearInterpolation(Speed, SpeedCompensation, d\Delta#)
				
			End If

			If (Speed_Length# <= COMMON_XZTOPSPEED#) Then
				Vector_MultiplyByScalar(Acceleration, COMMON_XZACCELERATION#*d\Delta#)
				Vector_Add(Speed, Acceleration)
			End If
		End If

		; Set back the ground speed
		p\Motion\Speed\x# = Speed\x# : p\Motion\Speed\z# = Speed\z#
		Delete Acceleration : Delete Speed : Delete SpeedCompensation


		; If the character's on the ground, apply deceleration based on the current slope, and
		; check if he has not enough speed to go further.
		If (p\Motion\Ground=True) Then Player_HandleAngleAcceleration2(p, d)

		
		; However, decelerate if no acceleration exists.
		If (Speed_Length#>0.0) Then
			If (p\Motion\Speed\x#>0.0) Then
				p\Motion\Speed\x# = Max#(p\Motion\Speed\x#-(p\Motion\Speed\x#/Speed_Length#)*COMMON_XZDECELERATION#*d\Delta#, 0.0)
			Else
				p\Motion\Speed\x# = Min#(p\Motion\Speed\x#-(p\Motion\Speed\x#/Speed_Length#)*COMMON_XZDECELERATION#*d\Delta#, 0.0)
			End If
			If (p\Motion\Speed\z#>0.0) Then
				p\Motion\Speed\z# = Max#(p\Motion\Speed\z#-(p\Motion\Speed\z#/Speed_Length#)*COMMON_XZDECELERATION#*d\Delta#, 0.0)
			Else
				p\Motion\Speed\z# = Min#(p\Motion\Speed\z#-(p\Motion\Speed\z#/Speed_Length#)*COMMON_XZDECELERATION#*d\Delta#, 0.0)
			End If 
		End If
		

		; Manage Y speeds
		If (p\Motion\Ground = False) Then
			p\Motion\Speed\y# = Max(p\Motion\Speed\y#-(COMMON_YACCELERATION#*d\Delta), COMMON_YTOPSPEED#)
		Else
			p\Motion\Speed\y# = 0
		End If 
	End Function


	; =========================================================================================================
	; Player_HandleAngleAcceleration
	; =========================================================================================================
	Function Player_HandleAngleAcceleration2(p.tPlayer2, d.tDeltaTime)
		; Decelerate and check for falling
		If (Abs(p\Motion\Align\y#) <= 0.7) Then 
			p\Motion\Speed\x# = p\Motion\Speed\x#+p\Motion\Align\x#^2*0.04*Sgn(p\Motion\Align\x#)*d\Delta
			p\Motion\Speed\z# = p\Motion\Speed\z#+p\Motion\Align\z#^2*0.04*Sgn(p\Motion\Align\z#)*d\Delta
		End If

		; Check if player falls
		If (p\Motion\Align\y# <= 0.1 And Vector_Length#(p\Motion\Speed)*10<((-Dot#)+2.0))
			Player_ConvertGroundToAir2(p)
			p\Motion\Align\x# 	= Game\Stage\GravityAlignment\x#
			p\Motion\Align\y# 	= Game\Stage\GravityAlignment\y#
			p\Motion\Align\z# 	= Game\Stage\GravityAlignment\z#
			p\Motion\Ground 	= False 
		End If
	End Function


	; =========================================================================================================
	; Player_Action_Common
	; =========================================================================================================
	Function Player_Action_Common2(p.tPlayer2, d.tDeltaTime)
		; Jump when pressed and if on ground
		If (Input2\Pressed\ActionA And p\Motion\Ground=True) Then			
			p\Action2 = ACTION_JUMP
			p\Motion\Speed\x# = p\Motion\Speed\x#*0.7
			p\Motion\Speed\y# = JUMP_STRENGHT#
			p\Motion\Speed\z# = p\Motion\Speed\z#*0.7
			Player_ConvertGroundToAir2(p)
			p\Motion\Ground = False
			
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
			p\Motion\Align\x# = 0.0
			p\Motion\Align\y# = 1.0
			p\Motion\Align\z# = 0.0


			PlaySound(Sound_Jump)
		End If

		If (Input2\Pressed\ActionB And p\Motion\Ground=True) Then
			p\Action2 = ACTION_CROUCH
		End If
	End Function


	; =========================================================================================================
	; Player_Action_Jump
	; =========================================================================================================
	Function Player_Action_Jump2(p.tPlayer2, d.tDeltaTime)
		; Variable jump
	;	If (Input2\Hold\ActionA = False And p\Motion\Speed\y# > JUMP_STRENGHT_VARIABLE#) Then
	;		p\Motion\Speed\y# = JUMP_STRENGHT_VARIABLE#
	;	End If

		; Land
		If (p\Motion\Ground = True) Then p\Action2 = ACTION_COMMON
	End Function


	; =========================================================================================================
	; Player_Action_Crouch
	; =========================================================================================================
	Function Player_Action_Crouch2(p.tPlayer2, d.tDeltaTime)
		p\Motion\Speed\x# = 0
		p\Motion\Speed\z# = 0		
		If (Input2\Pressed\ActionA) Then
			p\Action2 = ACTION_SPINDASH
			p\SpindashCharge2 = 1.7
			PlaySound(Sound_SpinDash)
			Animate p\Objects\Mesh_Spindash, 1,  0.8
;			Animate FindChild(p\Objects\Mesh_Spindash, "Cone01"), 1, 0.8
		EndIf
		
		If (Not Input2\Hold\ActionB) Then p\Action2 = ACTION_COMMON
	End Function 


	; =========================================================================================================
	; Player_Action_Spindash
	; =========================================================================================================
	Function Player_Action_Spindash2(p.tPlayer2, d.tDeltaTime)
		p\Motion\Speed\x# = 0
		p\Motion\Speed\z# = 0		

		If (Input2\Pressed\ActionA) Then
			p\SpindashCharge2 = p\SpindashCharge2 + 0.6
			If (p\SpindashCharge2 > 7.0) Then p\SpindashCharge2 = 7.0
			
			PlaySound(Sound_SpinDash)
		Else
			p\SpindashCharge2 = p\SpindashCharge2 - 0.1
			If (p\SpindashCharge2 < 1.7) Then p\SpindashCharge2 = 2.6
		EndIf
		
		HideEntity p\Objects\Mesh2
		ShowEntity p\Objects\Mesh_Spindash
		RotateEntity(p\Objects\Mesh_Spindash, 0, 0, 0)
		PositionEntity p\Objects\Mesh_Spindash, EntityX(p\Objects\Mesh2), EntityY(p\Objects\Mesh2), EntityZ(p\Objects\Mesh2)
		RotateEntity(p\Objects\Mesh_Spindash, 0, p\Animation\Direction#-90, 0)
		AlignToVector(p\Objects\Mesh_Spindash, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2)
;		UpdateNormals FindChild(p\Objects\Mesh_Spindash, "Cone01")

		If (Not Input2\Hold\ActionB) Then
			;p\Action = ACTION_COMMON
			Player_Action_Roll2(p,d)
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*p\SpindashCharge2
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*p\SpindashCharge2	
			PlaySound(Sound_SpindashRelease)
			ShowEntity p\Objects\Mesh2
			HideEntity p\Objects\Mesh_Spindash
		EndIf		
	End Function

; =========================================================================================================
	; Player_Action_Roll
	; =========================================================================================================
	Function Player_Action_Roll2(p.tPlayer2, d.tDeltaTime)
	DirectionKey1 = KeyDown(200)
		If ((Sqr#(p\Motion\Speed\x#^2) < 1) And (Sqr#(p\Motion\Speed\z#^2) < 1)) Then p\Action2 = ACTION_COMMON And (DirectionKey1 = 0)
		If (p\Motion\Speed\y# > 0 And p\Motion\Ground = True And p\Action2 = ACTION_ROLL) Then
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*-.1
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*-.1
		EndIf 
		
		If (p\Motion\Speed\y# < 0 And p\Motion\Ground = True And p\Action2 = ACTION_ROLL) Then
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2
		EndIf 

		If (p\Action2 = ACTION_ROLL And p\Motion\Ground = False) Then p\Action2 = ACTION_COMMON


		If (Input2\Pressed\ActionA And p\Motion\Ground=True) Then
			p\Action2 = ACTION_JUMP		
			p\Motion\Speed\x# = p\Motion\Speed\x#*0.7
			p\Motion\Speed\y# = JUMP_STRENGHT#
			p\Motion\Speed\z# = p\Motion\Speed\z#*0.7
			Player_ConvertGroundToAir2(p)
			p\Motion\Ground = False
					
		; When jumping, we need to set the align vector instantly, or else the 
		; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
		PlaySound Sound_Jump
		End If
	
		If (p\Action2 = ACTION_JUMP And p\Motion\Ground = True) Then p\Action2 = ACTION_COMMON

			
	End Function

;~IDEal Editor Parameters:
;~C#Blitz3D