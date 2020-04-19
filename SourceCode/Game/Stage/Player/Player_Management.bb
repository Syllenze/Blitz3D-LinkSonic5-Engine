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

	Const PLAYER_MODE_MOUSELOOK		= 0
	Const PLAYER_MODE_ANALOG		= 1
	Const PLAYER_MODE_SRB			= 2
	Const PLAYER_MODE_MARIO64		= 3
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
	
	
	
	
	; ---- Player management ----
	; =========================================================================================================
	; Player_Handle
	; =========================================================================================================
	Function Player_Handle(p.tPlayer, d.tDeltaTime)
		; Depending on current mode, the pressed direction uses a different method. Mouselook and analog are
		; quite similar in this aspect.
		Select Gameplay_Control_Mode
			Case PLAYER_MODE_MOUSELOOK
				MotionDirection# = p\Objects\Camera\Rotation\y#-Input\Movement_Direction#
				MotionPressure#  = Input\Movement_Pressure#
			Case PLAYER_MODE_ANALOG
				MotionDirection# = p\Objects\Camera\Rotation\y#-Input\Movement_Direction#
				MotionPressure#  = Input\Movement_Pressure#
			Case PLAYER_MODE_SRB
				MotionDirection# = p\Objects\Camera\Rotation\y#-90*Input\Movement_AnalogY#
				MotionPressure#  = Input\Movement_Pressure#*Abs(Input\Movement_AnalogY#)
			Case PLAYER_MODE_MARIO64
				MotionDirection# = p\Objects\Camera\Rotation\y#-Input\Movement_Direction#
				MotionPressure#  = Input\Movement_Pressure#
		End Select
		
		; Declarate acceleration and speed vectors and setup.
		Accelerationp.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
		Speedp.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
		SpeedCompensation.tVector	= Vector(0, 0, 0)
		Speed_Length#				= Vector_Length#(Speedp)
		
		; Disable skidding flag
		p\Flags\Skidding = False
		
		

		; If there exists acceleration, handle the acceleration and change to new
		; direction, preserving the momentum in the needed cases.
		If (Vector_Length#(Accelerationp)) Then

			; Calculate delta cos and sin
			DeltaCos# = Cos#(p\Animation\Direction#-90)
			DeltaSin# = Sin#(p\Animation\Direction#-90)
			
			; Change Player's direction. Depending on current motion orientation and speed, this
			; direction change would be done instantly or smoothly. This rotation isn't done entirely
			; based on delta, because it would appear as if the character automatically rotates when
			; at low FPS xD
			
			; Default 1.0001
			
	;		If (Speed_Length# < 0.1) Then
	;			p\Animation\Direction# = ATan2(((Acceleration\x#+DeltaCos#*3)/4)*1.0001,-(Acceleration\z#+DeltaSin#*3)/4)
	;		Else
	;			p\Animation\Direction# = ATan2(((Acceleration\x#+DeltaCos#*5)/6)*1.0001,-(Acceleration\z#+DeltaSin#*5)/6)
	;		End If
			
			
				
				; FR Code
			
					; Rolling code setup
			Game\Gameplay\Rot = (Sqr#(EntityPitch(p\Objects\Entity)^2+EntityRoll(p\Objects\Entity)^2))
			
		;	DirectionRate# = ConstantRate# / DeltaTime#
			
		;	TURNING_SHARPNESS# = ANTI_SLIDING_FACTOR# / (Game\DeltaTime\Delta# / 2)
			
			
			
				If (Speed_Length# < 0.1) Then
					p\Animation\Direction# = ATan2(((Accelerationp\x#+DeltaCos#*TURNING_SHARPNESS#)/(TURNING_SHARPNESS#+1))*1.0001,-(Accelerationp\z#+DeltaSin#*TURNING_SHARPNESS#)/(TURNING_SHARPNESS#+1))
				Else
					p\Animation\Direction# = ATan2(((Accelerationp\x#+DeltaCos#*(TURNING_SHARPNESS#+1))/(TURNING_SHARPNESS#+2))*1.0001,-(Accelerationp\z#+DeltaSin#*(TURNING_SHARPNESS#+1))/(TURNING_SHARPNESS#+2))
				End If
				
			; Depending on the dot product between current direction and new motion direction
				DotProduct# = Vector_DotProductNormalized#(Accelerationp, speedp)
				
				If (DotProduct# < MOTION_DEVIATION_FACTOR#) Then
					
				; If there's an opposite change of motion direction, completely 
					If (p\Motion\Ground = True) Then
						Vector_MultiplyByScalar(Accelerationp, 1.2)
						
					;Then check to make sure the player isn't rolling, and set the skidding flag to true
						If (Speed_Length# > (DEVIATION_HIGH_FACTOR#) And p\Action <> ACTION_ROLL And p\Grinding = 0) Then
							p\Flags\Skidding = True
							p\Animation\DirectionLocked# = p\Animation\Direction# : p\Animation\Direction# = p\Motion\Direction1# ;PointEntity(p\Objects\Mesh, p\Objects\Entity) : ; : TurnEntity(p\Objects\Mesh,10,180,0) : TFormPoint(10,180,0,p\Objects\Entity,p\Objects\Mesh) : ;p\Animation\Direction# = EntityYaw#(p\Objects\Mesh); : p\Action = ACTION_SKIDDING
							If (ChannelPlaying(Channel_Skidding)=False) Then Channel_Skidding = playsnd(Sound_Skidding, p\Objects\Entity, 1); : ChannelVolume(Channel_Skidding,Game\SoundVol)
							SetEmitter(p\Objects\TempPiv, p\Objects\Template)
							SetEmitter(p\Objects\TempPiv, p\Particles\Rock)
							;SetEmitter(p\Objects\Mesh, Game\ParticleGroup1\Tmp3)
					;	Else
					;		If p\Action = ACTION_SKIDDING Then p\Action = ACTION_COMMON
						End If
					End If
					
					Player_SubstractTowardsZero(speedp, DEVIATION_SUBTRACT#*d\Delta#)
					
				Else If (DotProduct# < (DEVIATION_HIGH_FACTOR# + 0.1)) Then
					
				;	If (p\Action = ACTION_SKIDDING) Then p\Action = ACTION_COMMON
					
				; If there's a harsh change in motion direction, decrease
				; greatly the motion in current direction and increase acceleration
				; on the new.
					If (p\Motion\Ground = True) Then
						SpeedCompensation\x# = (Speedp\x#*HIGH_DEVIATION_COMPENSATION#+DeltaCos#*Speed_Length#)/(HIGH_DEVIATION_COMPENSATION#+1)*0.96 ;33
						SpeedCompensation\z# = (Speedp\z#*HIGH_DEVIATION_COMPENSATION#+DeltaSin#*Speed_Length#)/(HIGH_DEVIATION_COMPENSATION#+1)*0.96
						
						Vector_LinearInterpolation(speedp, SpeedCompensation, d\Delta#)
					Else
						Player_SubstractTowardsZero(speedp, DEVIATION_SUBTRACT#*d\Delta#)
					EndIf
					
					Vector_MultiplyByScalar(Accelerationp, ANTI_SLIDING_FACTOR#)
					
				Else If (DotProduct# < DEVIATION_MILD_FACTOR#) Then
					
				; If there's a mild change in direction, slighty decrease
				; the motion in current direction.
					If (p\Motion\Ground = True) Then
						SpeedCompensation\x# = (Speedp\x#*MILD_DEVIATION_COMPENSATION#+DeltaCos#*speed_Length#)/(MILD_DEVIATION_COMPENSATION#+1)
						SpeedCompensation\z# = (speedp\z#*MILD_DEVIATION_COMPENSATION#+DeltaSin#*Speed_Length#)/(MILD_DEVIATION_COMPENSATION#+1)
					Else
						If (Vector_Length#(p\Motion\Speed) < COMMON_XZABSOLUTESPEED#) Then
							SpeedCompensation\x# = (Speedp\x#*(MILD_DEVIATION_COMPENSATION#+2.2)+DeltaCos#*Speed_Length#)/(MILD_DEVIATION_COMPENSATION#+3.1)*0.98
							SpeedCompensation\z# = (Speedp\z#*(MILD_DEVIATION_COMPENSATION#+2.2)+DeltaSin#*Speed_Length#)/(MILD_DEVIATION_COMPENSATION#+3.1)*0.98
						End If				
					EndIf
					
					Vector_MultiplyByScalar(Accelerationp, (ANTI_SLIDING_FACTOR# * 0.5))
					Vector_LinearInterpolation(Speedp, SpeedCompensation, d\Delta#)
					
				Else If (DotProduct# < DEVIATION_LOW_FACTOR#) Then
					
				; If there's a low change in direction, slighty decrease
				; the motion in current direction.
					If (p\Motion\Ground = True) Then
						SpeedCompensation\x# = (Speedp\x#*LOW_DEVIATION_COMPENSATION#+DeltaCos#*Speed_Length#)/(LOW_DEVIATION_COMPENSATION#+1)
						SpeedCompensation\z# = (Speedp\z#*LOW_DEVIATION_COMPENSATION#+DeltaSin#*Speed_Length#)/(LOW_DEVIATION_COMPENSATION#+1)
					Else
						If (Vector_Length#(p\Motion\Speed) < COMMON_XZABSOLUTESPEED#) Then
							SpeedCompensation\x# = (Speedp\x#*(LOW_DEVIATION_COMPENSATION#+3.3)+DeltaCos#*Speed_Length#)/(LOW_DEVIATION_COMPENSATION#+4.2)*0.98
							SpeedCompensation\z# = (Speedp\z#*(LOW_DEVIATION_COMPENSATION#+3.3)+DeltaSin#*Speed_Length#)/(LOW_DEVIATION_COMPENSATION#+4.2)*0.98
						End If
					EndIf
					
					Vector_MultiplyByScalar(Accelerationp, (ANTI_SLIDING_FACTOR# * 0.5))
					Vector_LinearInterpolation(Speedp, SpeedCompensation, d\Delta#)
					; End Of FR Code
				
			End If

			If (Speed_Length# <= COMMON_XZTOPSPEED#) Then
				Vector_MultiplyByScalar(Accelerationp, COMMON_XZACCELERATION#*d\Delta#)
				Vector_Add(Speedp, Accelerationp)
			End If
		End If

		; Set back the ground speed
		p\Motion\Speed\x# = Speedp\x# : p\Motion\Speed\z# = Speedp\z#
		Delete Accelerationp : Delete Speedp : Delete SpeedCompensation


		; If the character's on the ground, apply deceleration based on the current slope, and
		; check if he has not enough speed to go further.
		If (p\Motion\Ground=True) Then Player_HandleAngleAcceleration(p, d)

		
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
		If (p\Motion\Ground = False) And (p\Flags\Platform=0) Then
			p\Motion\Speed\y# = Max#(p\Motion\Speed\y#-(COMMON_YACCELERATION#*d\Delta), COMMON_YTOPSPEED#)
		Else
			p\Motion\Speed\y# = 0
		End If 
		
		
	;	If (KeyDown(KEY_Q)) Then
	;		Stage_Action_Tilt(0.7,40,90,0,0)
	;		obj.tObject = Object_SpewRing_Create(EntityX(p\Objects\Entity),EntityY(p\Objects\Entity)+10,EntityZ(p\Objects\Entity))
	;	EndIf
		
;		If (KeyDown(KEY_R)) Then
;			Stage_Action_Tilt(0.7,40,90,0,0)
;			obj.tObject = Object_Monitor_Create(EntityX(p\Objects\Entity),EntityY(p\Objects\Entity)+10,EntityZ(p\Objects\Entity))
;			ModInput(1)
;		EndIf
		
		Local PSonic
		
	;	If (KeyHit(KEY_F5)) Then p2.tPlayer2 = Player_Create2(CHARACTER_SONIC, "Tails")
		
	;	If (KeyDown(KEY_SHIFT_LEFT)) Then
	;		If (KeyHit(KEY_F10)) Then p\Character = CHARACTER_SONIC
	;		If (KeyHit(KEY_F11)) Then p\Character = CHARACTER_TAILS
	;		If (KeyHit(KEY_F12)) Then p\Character = CHARACTER_KNUCKLES
		;	If (KeyHit(KEY_T)) Then Stage_Action_Tilt(0.7,40,90,0,0)
	;	EndIf
		
	;	If (KeyDown(KEY_CTRL_LEFT)) Then
	;		If (KeyHit(KEY_1)) Then obj.tObject = Object_SpewRing_Create(EntityX(p\Objects\Entity),EntityY(p\Objects\Entity)+10,EntityZ(p\Objects\Entity)+10)
	;		If (KeyHit(KEY_2)) Then obj.tObject = Object_Monitor_Create(EntityX(p\Objects\Entity),EntityY(p\Objects\Entity),EntityZ(p\Objects\Entity))
	;		If (KeyHit(KEY_3)) Then obj.tObject = Object_Check_Create(EntityX(p\Objects\Entity),EntityY(p\Objects\Entity),EntityZ(p\Objects\Entity))
	;	If (KeyHit(KEY_4)) Then obj.tObject = Object_Missile_Create(EntityX(p\Objects\Entity)+50,EntityY(p\Objects\Entity),EntityZ(p\Objects\Entity))
	;	If (KeyHit(KEY_5)) Then obj.tObject = Object_Player2_Create(EntityX(p\Objects\Entity),EntityY(p\Objects\Entity),EntityZ(p\Objects\Entity))
	;	If (KeyHit(KEY_6)) Then obj.tObject = Object_Pickup_Create(EntityX(p\Objects\Entity),EntityY(p\Objects\Entity),EntityZ(p\Objects\Entity))
	;	EndIf
		
		; Manage platform
		If (p\Flags\Platform) Then
			p\Action=ACTION_COMMON
			p\Motion\Ground 	= True
			;dx# = EntityX#(p\Objects\Entity) - EntityX#(p\Flags\Platform)
			;dy# = EntityY#(p\Objects\Entity) - EntityY#(p\Flags\Platform)
			;dz# = EntityZ#(p\Objects\Entity) - EntityZ#(p\Flags\Platform)
			;PositionEntity p\Objects\Entity, EntityX#(p\Objects\Entity)+dx#, EntityY#(p\Objects\Entity)+dx#, EntityZ#(p\Objects\Entity)+dx#
		End If
			
	;	If (KeyHit(KEY_F3)) Then PlaySnd(Sound_DashVox1, p\Objects\Entity, 1) : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/DashVox1.wav",0,0,BASS_SAMPLE_LOOP)
	;	If (KeyHit(KEY_F4)) Then spd = spd + 0.1 : d\IdealInterval = 1/(spd/Float(FPS))
		
		
		
End Function


	; =========================================================================================================
	; Player_HandleAngleAcceleration
	; =========================================================================================================
	Function Player_HandleAngleAcceleration(p.tPlayer, d.tDeltaTime)
		; Decelerate and check for falling
		If (Abs(p\Motion\Align\y#) <= 0.7) Then 
			p\Motion\Speed\x# = p\Motion\Speed\x#+p\Motion\Align\x#^2*0.06*Sgn(p\Motion\Align\x#)*d\Delta
			p\Motion\Speed\z# = p\Motion\Speed\z#+p\Motion\Align\z#^2*0.06*Sgn(p\Motion\Align\z#)*d\Delta
		End If
		
		; ORIGINAL
	;	If (Abs(p\Motion\Align\y#) <= 0.7) Then 
	;		p\Motion\Speed\x# = p\Motion\Speed\x#+p\Motion\Align\x#^2*0.04*Sgn(p\Motion\Align\x#)*d\Delta
	;		p\Motion\Speed\z# = p\Motion\Speed\z#+p\Motion\Align\z#^2*0.04*Sgn(p\Motion\Align\z#)*d\Delta
	;	End If

		; Check if player falls
		If (p\Motion\Align\y# <= 0.1 And Vector_Length#(p\Motion\Speed)*10<((-Dot#)+2.0))
			Player_ConvertGroundToAir(p)
			p\Motion\Align\x# 	= Game\Stage\GravityAlignment\x#
			p\Motion\Align\y# 	= Game\Stage\GravityAlignment\y#
			p\Motion\Align\z# 	= Game\Stage\GravityAlignment\z#
			p\Motion\Ground 	= False 
		End If
	End Function


	; =========================================================================================================
	; Player_Action_Common
	; =========================================================================================================
Function Player_Action_Common(p.tPlayer, d.tDeltaTime)
	
	Local HoldingB
	Local BTimer
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
	p\BounceHeight = 0.7
	
	p\WillLandHard = 0
	
	If (p\Animation\Idle = 0) Then
		If (p\Frame > 117) Then p\Frame = 0 : p\Animation\Idle = Rand(1,2)
	EndIf
	If (p\Animation\Idle <> 0) Then
		If (p\Frame > 67) Then p\Frame = 0 : p\Animation\Idle = 0
	EndIf
	
	;If (SonicSpeed# > 0.6) Then
					; Align Head
	;	If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) And (p\Flags\HeadTrackEnabled = 1) Then
	;		
	;		p\HeadTrackTimer# = MilliSecs() + 300
	;		
	;		PointEntity(p\Objects\Mesh_HeadPoint, p\Objects\Entity)
	;		TurnEntity(p\Objects\Mesh_HeadPoint,30,180,0)
	;		TFormPoint(30,180,0,p\Objects\Entity, p\Objects\Mesh_HeadPoint)
	;	EndIf
	;EndIf
	
	If ((Input\Pressed\Up = True) Or (Input\Pressed\Down = True) Or (Input\Pressed\Left = True) Or (Input\Pressed\Right = True)) And (p\Motion\GroundSpeed# < 0.1) Then
		p\Animation\ToJog = 1
	ElseIf (p\Frame > 8 And p\Animation\ToJog = 1) Then
		p\Animation\ToJog = 0
;		p\Animation\ToJog = 0
	EndIf
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then
		
			p\Action = ACTION_JUMP
			p\Motion\Speed\x# = p\Motion\Speed\x#*0.95
			p\Motion\Speed\y# = JUMP_STRENGHT#
			p\Motion\Speed\z# = p\Motion\Speed\z#*0.95
			Player_ConvertGroundToAir(p)
			p\Motion\Ground = False
			
			If (p\Motion\GroundSpeed > 2.5) Then
				p\Animation\Animation = 31
			Else
				p\Animation\Animation = 24
			EndIf
			
			PositionEntity(p\Objects\PPivot, EntityX#(p\Objects\Entity), EntityY#(p\Objects\Entity), EntityZ#(p\Objects\Entity))
			
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
			p\Motion\Align\x# = 0.0
			p\Motion\Align\y# = 1.0
			p\Motion\Align\z# = 0.0
			
			PlayRandomJumpSound()


			Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity, 1)
		End If
		
	;	If (Input\Pressed\ActionB) Then
	;		HoldingB = 1
	;		BTimer = MilliSecs() + 500
		
		If (p\Grinding = 0) Then
		
		If ((p\ActionBTimer >= 300) And (p\ActionBTimer > 0) And (SonicSpeed# < 2.4)) Then
			Channel_SpinDash=PlaySound(Sound_SpinDash)
			p\Action = ACTION_SPINDASHNEW
			p\ActionBTimer = 0
			
		EndIf
		
		If (Input\Hold\ActionB=True) Then
			p\ActionBTimer = p\ActionBTimer + GlobalTimer
			
	Else
		If ((Input\Hold\ActionB=False) And (p\ActionBTimer < 300) And (p\ActionBTimer > 0.5) And (SonicSpeed# < 2.4)) Then; And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1.8) Then ;And Game\Gameplay\RingE > 0) Then
			p\Action = ACTION_KICK
			p\ActionBTimer = 0
			Channel_SpinKick = PlaySnd(Sound_SpinKick, p\Objects\Entity, 1)
			PlayRandomKickSound()
		EndIf
		
		
		p\ActionBTimer = 0
	EndIf
	
	
		If (Input\Pressed\ActionB) And (SonicSpeed# >= 2.4) And (p\BB_CanDo=0) Then
			Player_ActionB_Fast(p, d)
		EndIf
		
	EndIf
		
;		If (Input\Pressed\ActionB) And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1.8) Then ;And Game\Gameplay\RingE > 0) Then
;			p\Action = ACTION_SPINDASH
;			p\Charge1=1
;			p\SDTimer = MilliSecs() + 800
;			p\SpindashCharge = 3
;			Channel_SpinDash = PlaySound(Sound_SpinDash)
;		EndIf
	
	If (p\Motion\Ground=True) Then p\FallTimerStarted = 0
	
	If ((p\FallTimer < MilliSecs()) And (Not Input\Pressed\ActionA) And (p\Motion\Ground=False)) Then p\Action = ACTION_FALL
	
	If (Not Input\Pressed\ActionA) And (p\Motion\Ground=False) And (p\FallTimerStarted = 0) Then
		p\FallTimerStarted = 1
		p\FallTimer = MilliSecs() + 300
	;		p\Action = ACTION_FALL
	End If
End Function

	; =========================================================================================================
	; Player_Action_Skidding
	; =========================================================================================================
Function Player_Action_Skidding(p.tPlayer, d.tDeltaTime)
	
	Local HoldingB
	Local BTimer
;	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
;	Speedp.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
;	SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
;	Speed_Length#				= Vector_Length#(Speed)
	
;	DotProduct# = Vector_DotProductNormalized#(Accelerationp, speedp)
	
;	SetEmitter(p\Objects\Mesh, Game\ParticleGroup1\Tmp1)
	
	p\BounceHeight = 0.7
	
	p\WillLandHard = 0
	
;	p\Animation\Direction# = p\Animation\DirectionLocked#
	
;	p\Animation\Direction# = EntityY#(p\Objects\Mesh)
	
;	If (DotProduct# < (DEVIATION_HIGH_FACTOR# + 0.1)) Then p\Action = ACTION_COMMON
	
	;If ((Input\Pressed\Up = False) And (Input\Pressed\Down = False) And (Input\Pressed\Left = False) And (Input\Pressed\Right = False)) Then
	
;	If () Then p\Action = ACTION_COMMON
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then			
		p\Action = ACTION_JUMP
		p\Motion\Speed\x# = p\Motion\Speed\x#*0.9
		p\Motion\Speed\y# = JUMP_STRENGHT#
		p\Motion\Speed\z# = p\Motion\Speed\z#*0.9
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
		If (p\Motion\GroundSpeed > 2.5) Then
			p\Animation\Animation = 31
		Else
			p\Animation\Animation = 24
		EndIf
		
		PositionEntity(p\Objects\PPivot, EntityX#(p\Objects\Entity), EntityY#(p\Objects\Entity), EntityZ#(p\Objects\Entity))
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
		PlayRandomJumpSound()
		
		
		Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity, 1)
	End If
	
	;	If (Input\Pressed\ActionB) Then
	;		HoldingB = 1
	;		BTimer = MilliSecs() + 500
	
	
	If ((p\ActionBTimer >= 300) And (p\ActionBTimer > 0) And (SonicSpeed# < 2.4)) Then
		Channel_SpinDash=PlaySound(Sound_SpinDash)
		p\Action = ACTION_SPINDASHNEW
		p\ActionBTimer = 0
		
	EndIf
	
	If (Input\Hold\ActionB=True) Then
		p\ActionBTimer = p\ActionBTimer + GlobalTimer
		
	Else
		If ((Input\Hold\ActionB=False) And (p\ActionBTimer < 300) And (p\ActionBTimer > 0.5) And (SonicSpeed# < 2.4)) Then; And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1.8) Then ;And Game\Gameplay\RingE > 0) Then
			p\Action = ACTION_KICK
			p\ActionBTimer = 0
			Channel_SpinKick = PlaySnd(Sound_SpinKick, p\Objects\Entity, 1)
			PlayRandomKickSound()
		EndIf
	EndIf
		
		If (Input\Pressed\ActionB) And (SonicSpeed# >= 2.4) And (p\BB_CanDo=0) Then
			Player_ActionB_Fast(p, d)
		EndIf
	
;		If (Input\Pressed\ActionB) And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1.8) Then ;And Game\Gameplay\RingE > 0) Then
;			p\Action = ACTION_SPINDASH
;			p\Charge1=1
;			p\SDTimer = MilliSecs() + 800
;			p\SpindashCharge = 3
;			Channel_SpinDash = PlaySound(Sound_SpinDash)
;		EndIf
	
	If (p\Motion\Ground=True) Then p\FallTimerStarted = 0
	
	If ((p\FallTimer < MilliSecs()) And (Not Input\Pressed\ActionA) And (p\Motion\Ground=False)) Then p\Action = ACTION_FALL
	
	If (Not Input\Pressed\ActionA) And (p\Motion\Ground=False) And (p\FallTimerStarted = 0) Then
		p\FallTimerStarted = 1
		p\FallTimer = MilliSecs() + 300
	;		p\Action = ACTION_FALL
	End If
End Function

	; =========================================================================================================
	; Player_Action_Land
	; =========================================================================================================
Function Player_Action_Land(p.tPlayer, d.tDeltaTime)
	
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
	p\WillLandHard = 0
	
	If (p\LandMoving = 1) Then;And (p\LandStopped = 0) Then ;And p\Action = ACTION_LAND) Then
;		p\Action = ACTION_LAND
		If (p\Frame > 1) Then
			p\LandMoving = 0
			p\Action = ACTION_COMMON
		EndIf
	EndIf
	
	If (p\LandStopped = 1) Then; And (p\LandMoving = 0) Then ;And p\Action = ACTION_LAND) Then
;		p\Action = ACTION_LAND
		If (p\Frame > 22) Then
			p\LandStopped = 0
			p\Action = ACTION_COMMON
		EndIf
	EndIf
	
	If (SonicSpeed# > 0.2 And p\LandStopped = 1) Then
		p\Action = ACTION_COMMON
		p\LandStopped = 0
	EndIf
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then			
		p\Action = ACTION_JUMP
		p\Motion\Speed\x# = p\Motion\Speed\x#*0.9
		p\Motion\Speed\y# = JUMP_STRENGHT#
		p\Motion\Speed\z# = p\Motion\Speed\z#*0.9
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
		If (p\Motion\GroundSpeed > 2.5) Then
			p\Animation\Animation = 31
		Else
			p\Animation\Animation = 24
		EndIf
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
		PlayRandomJumpSound()
		
		
		Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity, 1)
	End If
	
	If ((p\ActionBTimer >= 300) And (p\ActionBTimer > 0) And (SonicSpeed# < 2.4)) Then
		Channel_SpinDash=PlaySound(Sound_SpinDash)
		p\Action = ACTION_SPINDASHNEW
		p\ActionBTimer = 0
		
	EndIf
	
	If (Input\Hold\ActionB=True) Then
		p\ActionBTimer = p\ActionBTimer + GlobalTimer
		
	Else
		If ((Input\Hold\ActionB=False) And (p\ActionBTimer < 300) And (p\ActionBTimer > 0.5) And (SonicSpeed# < 2.4)) Then; And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1.8) Then ;And Game\Gameplay\RingE > 0) Then
			p\Action = ACTION_KICK
			p\ActionBTimer = 0
			Channel_SpinKick = PlaySnd(Sound_SpinKick, p\Objects\Entity, 1)
			PlayRandomKickSound()
		EndIf
	EndIf
		
		If (Input\Pressed\ActionB) And (SonicSpeed# >= 2.4) And (p\BB_CanDo=0) Then
			Player_ActionB_Fast(p, d)
		EndIf
	
;		If (Input\Pressed\ActionB) And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1.8) Then ;And Game\Gameplay\RingE > 0) Then
;			p\Action = ACTION_SPINDASH
;			p\Charge1=1
;			p\SDTimer = MilliSecs() + 800
;			p\SpindashCharge = 3
;			Channel_SpinDash = PlaySound(Sound_SpinDash)
;		EndIf
	If (p\Motion\Ground=True) Then p\FallTimerStarted = 0
	
	If ((p\FallTimer < MilliSecs()) And (Not Input\Pressed\ActionA) And (p\Motion\Ground=False)) Then p\Action = ACTION_FALL
	
	If (Not Input\Pressed\ActionA) And (p\Motion\Ground=False) And (p\FallTimerStarted = 0) Then
		p\FallTimerStarted = 1
		p\FallTimer = MilliSecs() + 300
	;		p\Action = ACTION_FALL
	End If
End Function

	; =========================================================================================================
	; Player_Action_LandHard
	; =========================================================================================================
Function Player_Action_LandHard(p.tPlayer, d.tDeltaTime)
	
	SpeedLength#		= Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	Speed.tVector		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SonicSpeed#			= Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
	p\WillLandHard = 0
	
	If (p\LandMoving = 1) Then;And (p\LandStopped = 0) Then ;And p\Action = ACTION_LAND) Then
	;	p\Action = ACTION_LANDHARD
		If (p\Frame > 1) Then
			p\LandMoving = 0
			p\Action = ACTION_COMMON
		EndIf
	EndIf
	
		If (p\LandStopped = 1) Then; And (p\LandMoving = 0) Then ;And p\Action = ACTION_LAND) Then
	;	p\Action = ACTION_LANDHARD
		If (p\Frame > 58) Then
			p\LandStopped = 0
			p\Action = ACTION_COMMON
		EndIf
	EndIf
	
	If (SonicSpeed# > 0.2 And p\LandStopped = 1) Then
		p\Action = ACTION_COMMON
		p\LandStopped = 0
	EndIf
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then			
		p\Action = ACTION_JUMP
		p\Motion\Speed\x# = p\Motion\Speed\x#*0.9
		p\Motion\Speed\y# = JUMP_STRENGHT#
		p\Motion\Speed\z# = p\Motion\Speed\z#*0.9
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
		If (p\Motion\GroundSpeed > 2.5) Then
			p\Animation\Animation = 31
		Else
			p\Animation\Animation = 24
		EndIf
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
		PlayRandomJumpSound()
		
		
		Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity, 1)
	End If
	
	If ((p\ActionBTimer >= 300) And (p\ActionBTimer > 0) And (SonicSpeed# < 2.4)) Then
		Channel_SpinDash=PlaySound(Sound_SpinDash)
		p\Action = ACTION_SPINDASHNEW
		p\ActionBTimer = 0
		
	EndIf
	
	If (Input\Hold\ActionB=True) Then
		p\ActionBTimer = p\ActionBTimer + GlobalTimer
		
	Else
		If ((Input\Hold\ActionB=False) And (p\ActionBTimer < 300) And (p\ActionBTimer > 0.5) And (SonicSpeed# < 2.4)) Then; And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1.8) Then ;And Game\Gameplay\RingE > 0) Then
			p\Action = ACTION_KICK
			p\ActionBTimer = 0
			Channel_SpinKick = PlaySnd(Sound_SpinKick, p\Objects\Entity, 1)
			PlayRandomKickSound()
		EndIf
	EndIf
	
	If (Input\Pressed\ActionB) And (SonicSpeed# >= 2.4) And (p\BB_CanDo=0) Then
		Player_ActionB_Fast(p, d)
	EndIf
	
;		If (Input\Pressed\ActionB) And ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1.8) Then ;And Game\Gameplay\RingE > 0) Then
;			p\Action = ACTION_SPINDASH
;			p\Charge1=1
;			p\SDTimer = MilliSecs() + 800
;			p\SpindashCharge = 3
;			Channel_SpinDash = PlaySound(Sound_SpinDash)
;		EndIf
	If (p\Motion\Ground=True) Then p\FallTimerStarted = 0
	
	If ((p\FallTimer < MilliSecs()) And (Not Input\Pressed\ActionA) And (p\Motion\Ground=False)) Then p\Action = ACTION_FALL
	
	If (Not Input\Pressed\ActionA) And (p\Motion\Ground=False) And (p\FallTimerStarted = 0) Then
		p\FallTimerStarted = 1
		p\FallTimer = MilliSecs() + 300
	;		p\Action = ACTION_FALL
	End If
End Function

	; =========================================================================================================
	; Player_Action_WallHit
	; =========================================================================================================
Function Player_Action_WallHit(p.tPlayer, d.tDeltaTime)
	
		; Land
	If (p\Frame > 36) Then p\Action = ACTION_COMMON
End Function

	; =========================================================================================================================
	; AIR ACTIONS
	; =========================================================================================================================


	; =========================================================================================================
	; Player_Action_Jump
	; =========================================================================================================
Function Player_Action_Jump(p.tPlayer, d.tDeltaTime)
	
		; Variable jump
		If (Input\Hold\ActionA = False And p\Motion\Speed\y# > JUMP_STRENGHT_VARIABLE#) Then
			p\Motion\Speed\y# = JUMP_STRENGHT_VARIABLE#
		End If
		
		p\Frame = RecursiveAnimTime(p\Objects\Mesh)
		
		If (p\DiveTimer < 1700) Then
		If (Input\Pressed\ActionA) Then
			Player_ActionA_Air(p, d)
		EndIf
	EndIf
		
		If ((p\Frame > 7 And Input\Hold\ActionA = True) And (Not Input\Pressed\ActionA)) Then
			p\Action = ACTION_JUMPPART2
			
			Channel_Spin = PlaySnd(Sound_Spin, p\Objects\Entity)
		EndIf
		
		If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
			Player_ActionB_Air(p, d)
		EndIf

		; Land
		If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_DoubleJump
	; =========================================================================================================
Function Player_Action_DoubleJump(p.tPlayer, d.tDeltaTime)
	
		; Variable jump
	;If (Input\Hold\ActionA = False And p\Motion\Speed\y# > JUMP_STRENGHT_VARIABLE#) Then
	;	p\Motion\Speed\y# = JUMP_STRENGHT_VARIABLE#
	;End If
	
	p\Frame = RecursiveAnimTime(p\Objects\Mesh)
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_JumpPart2
	; =========================================================================================================
Function Player_Action_JumpPart2(p.tPlayer, d.tDeltaTime)
	
		; Variable jump
	If (Input\Hold\ActionA = False And p\Motion\Speed\y# > JUMP_STRENGHT_VARIABLE#) Then
		p\Motion\Speed\y# = JUMP_STRENGHT_VARIABLE#
	End If
	
	p\Frame = RecursiveAnimTime(p\Objects\Mesh)
	If (p\DiveTimer < 1700) Then
	If (Input\Pressed\ActionA) Then
		Player_ActionA_Air(p, d)
	EndIf
EndIf
	
If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
	Player_ActionB_Air(p, d)
	EndIf
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_DiveIntoRoll
	; =========================================================================================================
Function Player_Action_DiveIntoRoll(p.tPlayer, d.tDeltaTime)
	
	p\Frame = RecursiveAnimTime(p\Objects\Mesh)
	If (p\DiveTimer < 1700) Then
		If (Input\Pressed\ActionA) Then
			p\Action = ACTION_SPRINGFALL
		EndIf
	EndIf
	
;	If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
;		Player_ActionB_Air(p, d)
;	EndIf
	
		; Land
	If (p\Motion\Ground = True) Then p\Action = ACTION_ROLL
End Function

	; =========================================================================================================
	; Player_Action_Bounce
	; =========================================================================================================
Function Player_Action_Bounce(p.tPlayer, d.tDeltaTime)
	
	p\Frame = RecursiveAnimTime(p\Objects\Mesh)
	
	If (p\DiveTimer < 1700) Then
	If (Input\Pressed\ActionA) Then
		Player_ActionA_Air(p, d)
	EndIf
EndIf
	
If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
	Player_ActionB_Air(p, d)
	EndIf
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function
	
	; =========================================================================================================
	; Player_Action_Stomp
	; =========================================================================================================
	Function Player_Action_Stomp(p.tPlayer, d.tDeltaTime)

		p\Motion\Speed\y# = -2.9
		
		If (Not Input\Hold\ActionB) Then
			p\Action = ACTION_STOMPPART2
		;	CreateLS5Trail()
		EndIf
		
		If (Input\Pressed\ActionA) Then
			If p\Character = CHARACTER_KNUCKLES Then Player_ActionA_Air(p, d)
		EndIf

		; Land
		If (p\Motion\Ground = True) Then
			p\Action = ACTION_BOUNCE
			
			SetEmitter(p\Objects\TempPiv, p\Particles\Land)
			
			StopChannel(Channel_Bounce)
			Channel_Bounce = PlaySnd(Sound_Bounce, p\Objects\Entity, 1)
			
			p\BounceHeight = p\BounceHeight + 0.5
			
			p\Motion\Speed\x# = p\Motion\Speed\x#*0.25
			p\Motion\Speed\z# = p\Motion\Speed\z#*0.25
			p\Motion\Speed\y# = p\BounceHeight#
			
			Player_ConvertGroundToAir(p)
			p\Motion\Ground = False
			
		EndIf
End Function

	; =========================================================================================================
	; Player_Action_StompPart2
	; =========================================================================================================
Function Player_Action_StompPart2(p.tPlayer, d.tDeltaTime)
	
	; Declarate acceleration and speed vectors and setup.
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
	p\Motion\Speed\y# = -4.8
;	p\HomingEffectAlpha# = 0
;	p\HomingEffectAlphaNeg# = 40
	
	If (Input\Pressed\ActionA) Then
		If p\Character = CHARACTER_KNUCKLES Then Player_ActionA_Air(p, d)
	EndIf
	
;	If (SpeedLength# > 0.1) Then
;		PointEntity(p\Objects\Mesh, p\Objects\Entity)
;		TurnEntity(p\Objects\Mesh,80,180,0)
;		TFormPoint(80,180,0,p\Objects\Entity, p\Objects\Mesh)
;		p\Animation\Direction# = EntityYaw#(p\Objects\Mesh) ;Turns player in direction pointed
;	EndIf
	
;	PointEntity(p\Objects\Mesh, p\Objects\Entity) : TurnEntity(p\Objects\Mesh,100,180,0) : TFormPoint(100,180,0,p\Objects\Entity,p\Objects\Mesh)
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_HomeJump
	; =========================================================================================================
Function Player_Action_HomeJump(p.tPlayer, d.tDeltaTime)
	
	If p\Motion\GroundSpeed < 3 Then
		PlayerPitch# = -p\Motion\GroundSpeed * 10
	Else
		PlayerPitch# = -30
	EndIf
	
	RotateEntity p\Objects\Mesh, PlayerPitch#,p\Animation\Direction#, 0
	
	If (p\DiveTimer < 1700) Then
	If (Input\Pressed\ActionA) Then
		Player_ActionA_Air(p, d)
	EndIf
EndIf
	
If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
	Player_ActionB_Air(p, d)
	EndIf
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function
	
	; =========================================================================================================
	; Player_Action_JumpDash
	; =========================================================================================================
	Function Player_Action_JumpDash(p.tPlayer, d.tDeltaTime)
;		If (p\AnimTimer1 > 15) Then
;			p\Action = ACTION_FALL
	;	RecursiveAnimate(p\Objects\AnimTimer1, 3, 0.5, 2, 0)
;		EndIf
		
	;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*4.6
	;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*4.6
	;	p\Motion\Speed\y# = -0.3
		
	;	p\Animation\Direction# = EntityYaw#(p\Objects\Mesh) ;Turns player in direction pointed
		p\Animation\Direction# = p\Motion\Direction1# ;Turns player in direction pointed
		
		p\HomingEffectAlpha# = 100
		p\HomingEffectAlphaNeg# = 0
		
		If (Input\Pressed\ActionA) Then
			p\Action = ACTION_FALL
	;		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2.6
	;		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2.6
		EndIf
		
	;	p\HomingTimer = RecursiveAnimTime(p\Objects\AnimTimer1)
		
		p\HomingTimer = p\HomingTimer + (game\deltaTime\timeCurrentFrame-Game\DeltaTime\TimePreviousFrame)
		
		If p\HomingTimer > 250 Then
			p\Action = FALL
	;		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2.6
	;		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2.6
		EndIf
		
		p\HomingEffectAlpha# = 0
		p\HomingEffectAlphaNeg# = 40
		
	;	MoveEntity(p\Objects\Mesh_Legs,1,1,5)

		; Land
		If (p\Motion\Ground = True) Then
			Land(p,d); : RecursiveAnimate(p\Objects\AnimTimer1, 3, 0.5, 2, 0)
	;		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2.6
	;		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2.6
		EndIf
	End Function
	
	; =========================================================================================================
	; Player_Action_Homing
	; =========================================================================================================
Function Player_Action_Homing(p.tPlayer, d.tDeltaTime)
	
	p\HomingEffectAlpha# = 0
	p\HomingEffectAlphaNeg# = 40
	
	diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
	
	p\HomingTimer = p\HomingTimer + d\Delta#
	
				; Align the LightDevice
	If (p\Objects\Enemy<>0) Then PointEntity p\Objects\LightDevice, p\Objects\Enemy
	;AlignToVector(p\Objects\LightDevice, p\rx#, p\ry#, p\rz#, 1)
	p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
	
;	If (p\Objects\Enemy<>0) Then
;		rx#=EntityX#(p\Objects\Enemy)
;		rz#=EntityZ#(p\Objects\Enemy)
;		
;		dx#=rx#-EntityX#(p\Objects\Entity)
;		dz#=rz#-EntityZ#(p\Objects\Entity)
;		
;		diff# = Abs(p\Animation\Direction# - (ATan2(dz#, dx#)+90))
;		
;				; Align the LightDevice
;		PointEntity p\Objects\LightDevice, p\Objects\Enemy
;		p\Animation\Direction# = ATan2(dz#, dx#)+90
;	EndIf
		
	;If (p\FValues[0]<55) Then
	If p\IValues[0]=False Then p\IValues[0]=True
			p\Motion\Ground=False
			TFormVector 0, 0, 1, p\Objects\LightDevice, p\Objects\Entity
			If p\BB_InMove = 0 Then
			p\Motion\Speed\x = TFormedX() * COMMON_LIGHTDASH_SPEED#
			p\Motion\Speed\y = TFormedY() * COMMON_LIGHTDASH_SPEED#
			p\Motion\Speed\z = TFormedZ() * COMMON_LIGHTDASH_SPEED#
		Else
			p\Motion\Speed\x = TFormedX() * 4
			p\Motion\Speed\y = TFormedY() * 4
			p\Motion\Speed\z = TFormedZ() * 4
		EndIf
	;	End If
			
			If (Input\Pressed\ActionB) Then
				p\Action = ACTION_FALL
			;	Channel_JumpDash = PlaySnd(Sound_JumpDash, p\Objects\Entity, 1)
	;	PlayRandomDashSound()
			EndIf
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_Hurt
	; =========================================================================================================
Function Player_Action_Hurt(p.tPlayer, d.tDeltaTime)
	
		; Land
	If (p\Motion\Ground = True) Then
		
		If (p\LoseRings = 1) Then
			TriDRingLoss(p, d)
		;	p\LoseRings = 0
		EndIf
		
		Land(p,d)
		
	EndIf
	
	If (p\LoseLife = 1) Then
		If (p\HurtTimer < MilliSecs()) Or (p\Motion\Ground = True) Then
			BackToCheckpoint(p)
			p\HurtTimer = MilliSecs()
		EndIf
	EndIf
		
	;	If (p\LoseLife = 1) Then
	;		TriDRingLoss(p, d)
	;	EndIf
End Function


	; =========================================================================================================
	; Player_Action_RingDash
	; =========================================================================================================
Function Player_Action_RingDash(p.tPlayer, d.tDeltaTime)
	
;	For o.tObject = Each tObject
		;-------------------------------------------------------------
		; Return to ACTION_COMMON if there are no more rings in range
		;-------------------------------------------------------------
;	If (p\FValues[0]>30) Then
;		p\Action = ACTION_COMMON
;		p\IValues[0]=False
;	End If
	
			;-----------------------------------
			; Point the player at the next ring
			;-----------------------------------
			; Point the LightDevice
	
;	If (p\Objects\Ring<>0) Then
				; Maths for player alignment
;		p\rx#=EntityX#(p\Objects\Ring)
;		p\rz#=EntityZ#(p\Objects\Ring)
		
;		p\dx#=p\rx#-EntityX#(p\Objects\Entity)
;		p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
		
;		diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
		
				; Align the LightDevice
;		PointEntity p\Objects\LightDevice, p\Objects\Ring
;		If p\IValues[0]=True Then
;			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
				; Turn the camera to match Sonic's direction
;			For c.tCamera = Each tCamera
			;	c\TargetRotation\y# = p\Animation\Direction# - 180
			;	c\TargetRotation\y# = RotateTowardsAngle#(c\TargetRotation\y#, c\Target\Animation\Direction#+180, 4*d\Delta)
;			Next
;		EndIf
;	EndIf
	
	diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
	
;	PositionEntity (p\Objects\LightDevice, p\EnemyX#, p\EnemyY#, p\EnemyZ#)
	
					; Align the LightDevice
	If (p\Objects\Enemy<>0) Then PointEntity p\Objects\LightDevice, p\Objects\Enemy
;	AlignToVector(p\Objects\LightDevice, p\rx#, p\ry#, p\rz#, 1)
	p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
	
				;-------------------------------
				;-------------------------------				; Move player to next ring
	
;	If (p\FValues[0]<30) Then
;		If p\IValues[0]=False Then p\IValues[0]=True
;		p\Motion\Ground=False
;		TFormVector 0, 0, 1, p\Objects\LightDevice, p\Objects\Entity
;		p\Motion\Speed\x = TFormedX() * COMMON_LIGHTDASH_SPEED#
;		p\Motion\Speed\y = TFormedY() * COMMON_LIGHTDASH_SPEED#
;		p\Motion\Speed\z = TFormedZ() * COMMON_LIGHTDASH_SPEED#
;	End If
	
	If p\IValues[0]=False Then p\IValues[0]=True
	p\Motion\Ground=False
	TFormVector 0, 0, 1, p\Objects\LightDevice, p\Objects\Entity
	p\Motion\Speed\x = TFormedX() * COMMON_LIGHTDASH_SPEED#
	p\Motion\Speed\y = TFormedY() * COMMON_LIGHTDASH_SPEED#
	p\Motion\Speed\z = TFormedZ() * COMMON_LIGHTDASH_SPEED#
	
	
		; If player presses jump or homing again, cancel light dash
	If (Input\Pressed\ActionA = True) Then
		p\Action = ACTION_COMMON
		p\IValues[0]=False
	End If
	
;	If (p\RingDashTimer < MilliSecs()) Then
;		p\Action = ACTION_FALL
;		AlignToVector(p\Objects\Entity, p\Animation\Align\x#, p\Animation\Align\y#, p\Animation\Align\z#, 2)
;	;	p\Motion\Speed\y# = 0
;	EndIf
;	
;		; Land
;	If (p\Motion\Ground = True) Then Land(p,d)
	
;Next
	
End Function
	
	; =========================================================================================================
	; Player_Action_Spring
	; =========================================================================================================
Function Player_Action_Spring(p.tPlayer, d.tDeltaTime)
	
; Declarate acceleration and speed vectors and setup.
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
	;Local Timer
	p\SpringTimer# = p\SpringTimer# + d\Delta#
	
	;Point player to the spring
	;AlignToVector p\Objects\PointsToEntity, EntityX#(p\Objects\Enemy),EntityY#(p\Objects\Enemy),EntityZ#(p\Objects\Enemy),2
	;PointEntity p\Objects\Mesh, p\Objects\Enemy
	;p\Animation\Direction# = EntityYaw#(p\Objects\PointsToEntity)
	
	;p\Animation\Direction# = EntityYaw#(p\Objects\Box)
	
	If ((p\SpringTimer# > 35) Or (p\Motion\Speed\y# < 0.5)) Then
		
	;	If EntityPitch#(p\Objects\Mesh) > -90 Then
		
	;	p\Animation\Direction# = EntityYaw#(p\Objects\Mesh) ;Turns player in direction pointed
	;	Else
		p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
	;	EndIf
		
		p\Action = ACTION_SPRINGFALL
	EndIf
		
	;AlignToVector (p\Objects\Mesh, EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity),EntityZ#(p\Objects\Entity), 2, 1)
	;PointEntity(p\Objects\Mesh, p\Objects\Entity)
	;RotateEntity(p\Objects\Mesh, TFormedX#, TFormedY#, TFormedZ#)
	
	If (p\DiveTimer < 1700) Then
		If (Input\Pressed\ActionA) Then
			Player_ActionA_Air(p, d)
		EndIf
	EndIf
		
	If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
		Player_ActionB_Air(p, d)
		EndIf
		
			; Align Body In Flight Direction
		;If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0.0) Then
		If (SpeedLength# > 0.3) Then
				
			;	p\HeadTrackTimer# = MilliSecs() + 300
				
			;	p\Objects\HeadOptX# = CountToValue#(p\Objects\HeadPitch#, p\Objects\HeadPointPitch#, 3, d\Delta#)
				
			PointEntity(p\Objects\Mesh, p\Objects\Entity)
			TurnEntity(p\Objects\Mesh,-70,180,0)
			
		;	If p\Motion\GroundSpeed < 3 Then
		;		PlayerPitch# = -p\Motion\GroundSpeed * 10
		;	Else
		;		PlayerPitch# = -30
		;	EndIf
				
		;	RotateEntity p\Objects\Mesh, PlayerPitch#,p\Animation\Direction#, 0
		;	TFormPoint(-70,180,0,p\Objects\Entity, p\Objects\Mesh)
			
			
			;If (EntityPitch#(p\Objects\Mesh) < -90) Then p\Animation\Direction# = -EntityYaw#(p\Objects\Mesh)
			
			
		EndIf
		
	;	Player_EaseMeshRot(p.tPlayer, p\Objects\Entity, 1)
			
		; Land
		If (p\Motion\Ground = True) Then
	;		If EntityPitch#(p\Objects\Mesh) > -90 Then
	;			p\Animation\Direction# = EntityYaw#(p\Objects\Mesh) ;Turns player in direction pointed
	;		Else
			p\Animation\Direction# = EntityYaw#(p\Objects\Mesh) : TurnEntity(p\Objects\Mesh,-70,180,0)
	;		EndIf
			Land(p,d)
		EndIf
			
End Function

	; =========================================================================================================
	; Player_Action_DestSpring
	; =========================================================================================================
Function Player_Action_DestSpring(p.tPlayer, d.tDeltaTime)
;	If (p\Motion\Speed\y# < 0.5) Then
;		p\Action = ACTION_SPRINGFALL
;	EndIf
	
	PointEntity p\Objects\LightDevice, p\Objects\Enemy
	
	diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
	
	;Rotate camera to face flight direction
	For c.tCamera = Each tCamera
;	c\TargetRotation\y# = p\Animation\Direction# - 180
;	c\TargetRotation\y# = RotateTowardsAngle#(c\TargetRotation\y#, c\Target\Animation\Direction#+180, 9*d\Delta)
Next
	
;	If (p\HomingTimer < MilliSecs()) Then
;				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
;		p\Action = ACTION_FALL
;	EndIf
	
;	p\Animation\Direction# = ATan2(p\DestZ#, p\DestX#)+90
	
	p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
	
	If p\IValues[0]=False Then p\IValues[0]=True
	p\Motion\Ground=False
	TFormVector 0, 0, 1, p\Objects\LightDevice, p\Objects\Entity
	
	p\Motion\Speed\x = TFormedX() * p\LaunchSpeed#
	p\Motion\Speed\y = TFormedY() * p\LaunchSpeed#
	p\Motion\Speed\z = TFormedZ() * p\LaunchSpeed#
	
	;AlignToVector(p\Objects\Mesh, p\dx#, p\DestY#, p\dz#, 2)
	
	;	AlignToVector (p\Objects\Mesh, EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity),EntityZ#(p\Objects\Entity), 2, 1)
	
	PointEntity(p\Objects\Mesh, p\Objects\Enemy)
	TurnEntity(p\Objects\Mesh,0,180,0)
	TFormPoint(0,180,0,p\Objects\Enemy,p\Objects\Mesh)
;	RotateEntity(p\Objects\Mesh, TFormedX#, TFormedY#, TFormedZ#)
;	AlignToVector(p\Objects\Mesh, p\rx#, p\ry#, p\rz#, 1)
	
	If (Abs(EntityX(p\Objects\Entity) - p\DestX#) < 5.5) And (Abs(EntityY(p\Objects\Entity) - p\DestY#) < 5.5) And Abs(EntityZ(p\Objects\Entity) - p\DestZ# < 5.5) Then
		p\Action = ACTION_SPRINGFALL
		p\WillLandHard = 1
		
		If (p\StopsAtDest = 1) Then
		p\Motion\Speed\x = 0
		p\Motion\Speed\y = 0
		p\Motion\Speed\z = 0
	EndIf
	EndIf
	
		; Land
;	If (p\Motion\Ground = True) Then Land(p,d)
	
End Function
	
	; =========================================================================================================
	; Player_Action_SpringFall
	; =========================================================================================================
	Function Player_Action_SpringFall(p.tPlayer, d.tDeltaTime)
		
		If (p\DiveTimer < 1700) Then
		If (Input\Pressed\ActionA) Then
			Player_ActionA_Air(p, d)
		EndIf
	EndIf
	
	If p\Motion\GroundSpeed < 3 Then
		PlayerPitch# = -p\Motion\GroundSpeed * 10
	Else
		PlayerPitch# = -30
	EndIf
	
	RotateEntity p\Objects\Mesh, PlayerPitch#,p\Animation\Direction#, 0
		
	If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
		Player_ActionB_Air(p, d)
		EndIf
		
		; Land
		If (p\Motion\Ground = True) Then Land(p,d)
	End Function
	
	; =========================================================================================================
	; Player_Action_Fall
	; =========================================================================================================
Function Player_Action_Fall(p.tPlayer, d.tDeltaTime)
	
;	If (Input\Pressed\ActionB) And (p\BB_CanDo=0) Then
;		Player_ActionB_Air(p, d)
;	EndIf
	
	If (Input\Pressed\ActionA) Then Player_ActionA_Fall(p, d)

		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_Dive
	; =========================================================================================================
Function Player_Action_Dive(p.tPlayer, d.tDeltaTime)
	
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
							; Declarate acceleration and speed vectors and setup.
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
	;If (SpeedLength# > 0.3) Then
	;	PointEntity(p\Objects\Mesh, p\Objects\Entity)
	;	TurnEntity(p\Objects\Mesh,75,180,0)
	;	TFormPoint(75,180,0,p\Objects\Entity, p\Objects\Mesh)
	;	p\Animation\Direction# = EntityYaw#(p\Objects\Mesh) ;Turns player in direction pointed
	;EndIf
	
	If (Input\Pressed\ActionA) Then Player_ActionA_Fall(p, d)
	
	If (Input\Hold\ActionB) And (p\BB_CanDo=0) Then
	;	p\Action = ACTION_STOMP
	;	Channel_JumpDash = PlaySnd(Sound_JumpDash, p\Objects\Entity, 1)
	;	PlayRandomDashSound()
		
		COMMON_YTOPSPEED# = -4.5
		
		If p\Motion\GroundSpeed < 3 Then
			PlayerPitch# = p\Motion\GroundSpeed * 10
		Else
			PlayerPitch# = 30
		EndIf
		
		RotateEntity p\Objects\Mesh, PlayerPitch#,p\Animation\Direction#, 0
		
	Else
		COMMON_YTOPSPEED# = -2.6
		
		If p\Motion\GroundSpeed < 3 Then
			PlayerPitch# = -p\Motion\GroundSpeed * 10
		Else
			PlayerPitch# = -30
		EndIf
		
		RotateEntity p\Objects\Mesh, PlayerPitch#,p\Animation\Direction#, 0
		
	EndIf
	
;	If (SonicSpeed# < 0.1) Then
;		PointEntity(p\Objects\Mesh, p\Objects\Entity) : TurnEntity(p\Objects\Mesh,100,180,0) : TFormPoint(100,180,0,p\Objects\Entity,p\Objects\Mesh)
;	EndIf
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_Ramp
	; =========================================================================================================
Function Player_Action_Ramp(p.tPlayer, d.tDeltaTime)
	
	If (Input\Pressed\ActionA) Then Player_ActionA_Fall(p, d)
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function
	
	; =========================================================================================================================
	; GROUND ACTIONS
	; =========================================================================================================================
	; =========================================================================================================
	; Player_Action_Crouch
	; =========================================================================================================
	Function Player_Action_Crouch(p.tPlayer, d.tDeltaTime)
		p\Motion\Speed\x# = 0
		p\Motion\Speed\z# = 0		
		If (Input\Pressed\ActionA) Then
			p\Action = ACTION_SPINDASH
			p\SpindashCharge = 1.7
			PlaySound(Sound_SpinDash)
		;	Animate p\Objects\Mesh_Spindash, 1,  0.8
;			Animate FindChild(p\Objects\Mesh_Spindash, "Cone01"), 1, 0.8
		EndIf
		
		; Land
		If (p\Motion\Ground = True) Then Land(p,d)
	End Function 


	; =========================================================================================================
	; Player_Action_Spindash
	; =========================================================================================================
Function Player_Action_Spindash(p.tPlayer, d.tDeltaTime)
	
	If (ChannelPlaying(Channel_SpinDashLoop)=False) Then
		Channel_SpinDashLoop = PlaySound(Sound_SpinDashLoop)
	EndIf
	
	;Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	
	If (p\SpindashCharge > 6.0) Then
		p\SpindashCharge = 6.0
	EndIf
	
;	EntityRadius(p\Objects\Entity, 2.20)
	
		;p\Motion\Speed\x# = 0
		;p\Motion\Speed\z# = 0	
	
	;Player_SubstractTowardsZero(Speed, 0.6*d\Delta#)	
	
	p\Motion\Speed\x# = 0
	p\Motion\Speed\z# = 0
	
	If ((p\SDTimer < MilliSecs()) And p\Charge1=1) Then
		p\SpindashCharge = 5
		p\SDTimer = MilliSecs() + 300
		p\Charge2=1
	EndIf
	If ((p\SDTimer < MilliSecs()) And p\Charge2=1) Then
		p\SpindashCharge = 7
		p\SDTimer = MilliSecs() + 350
		p\Charge3=1
	EndIf
	If ((p\SDTimer < MilliSecs()) And p\Charge3=1) Then
		p\SpindashCharge = 8.5
		p\SDTimer = MilliSecs() + 400
	;		If (p\SpindashCharge > 4.0) Then p\SpindashCharge = 4.0
	;		
	;		PlaySound(Sound_SpinDash)
	;	Else
	;		p\SpindashCharge = p\SpindashCharge - 0.1
	;		If (p\SpindashCharge < 1.7) Then p\SpindashCharge = 2.6
	EndIf
	
	;	HideEntity p\Objects\Mesh
	;	ShowEntity p\Objects\Mesh_Spindash
	;	RotateEntity(p\Objects\Mesh_Spindash, 0, 0, 0)
	;	PositionEntity p\Objects\Mesh_Spindash, EntityX(p\Objects\Mesh), EntityY(p\Objects\Mesh), EntityZ(p\Objects\Mesh)
	;	RotateEntity(p\Objects\Mesh_Spindash, 0, p\Animation\Direction#-90, 0)
	;	AlignToVector(p\Objects\Mesh_Spindash, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2)
;		UpdateNormals FindChild(p\Objects\Mesh_Spindash, "Cone01")
	
	If (Not Input\Hold\ActionB) Then
		p\Charge1=0
		p\Charge2=0
		p\Charge3=0
		p\Action = ACTION_ROLL
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*p\SpindashCharge
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*p\SpindashCharge	
		PlaySound(Sound_SpindashRelease)
		;	ShowEntity p\Objects\Mesh
		;	HideEntity p\Objects\Mesh_Spindash
	EndIf		
End Function

	; =========================================================================================================
	; Player_Action_SpindashNew
	; =========================================================================================================
Function Player_Action_SpindashNew(p.tPlayer, d.tDeltaTime)
	
	p\SpindashCharge = p\SpindashCharge + GlobalTimer
	
	If (Game\Stage\Properties\Hub = 0) Then
		Select p\Character
			Case CHARACTER_SONIC
				If (p\SpindashCharge# < 650) Then p\SDCharge# = (p\SpinDashCharge#/100)+1 : Else p\SDCharge# = 6.5
			Case CHARACTER_TAILS
				If (p\SpindashCharge# < 300) Then p\SDCharge# = (p\SpinDashCharge#/100)+1 : Else p\SDCharge# = 3
			Case CHARACTER_KNUCKLES
				If (p\SpindashCharge# < 300) Then p\SDCharge# = (p\SpinDashCharge#/100)+1 : Else p\SDCharge# = 3
		End Select
	EndIf
	If (Game\Stage\Properties\Hub = 1) Then
		p\SDCharge# = 2
	EndIf
		
	p\Motion\Speed\x# = 0
	p\Motion\Speed\z# = 0
	
	SetEmitter(p\Objects\TempPiv, p\Objects\Template, False)
	SetEmitter(p\Objects\TempPiv, p\Particles\Rock, False)
	
	If (Input\Hold\ActionB = False) Then
		StopChannel(Channel_SpinDash)
		PlaySound(Sound_SpindashRelease)
		p\ActionBTimer = 0
		p\Action = ACTION_ROLL
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*(p\SDCharge#)
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*(p\SDCharge#)
	EndIf
	
End Function

	; =========================================================================================================
	; Player_Action_Roll
	; =========================================================================================================
Function Player_Action_Roll(p.tPlayer, d.tDeltaTime)
	
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	
	Player_SubstractTowardsZero(Speed, 0.1*d\Delta#)
	
	;	If (SpeedLength# > 4.8) Then
	;	CustomPostprocessBlurMotion (.55)
	;	EndIf
	;	If (SpeedLength# < 4.8) Then
	;	CustomPostprocessBlurMotion (.35)
	;	EndIf
	StopChannel(Channel_Boost)
	;HideEntity(Mesh_Sonic_BoostFX)
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then			
		p\Action = ACTION_JUMP
		PlayRandomJumpSound()
		Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity, 1)
		p\Motion\Speed\x# = p\Motion\Speed\x#*0.99
		p\Motion\Speed\y# = 1.6
		p\Motion\Speed\z# = p\Motion\Speed\z#*0.99
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
		If (p\Motion\GroundSpeed > 2.5) Then
			p\Animation\Animation = 31
		Else
			p\Animation\Animation = 24
		EndIf
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
	End If
	
	If (Input\Pressed\ActionB) Then ;And Game\Gameplay\RingE > 0) Then
		p\Action = ACTION_COMMON
	EndIf
	
	If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) < 1.5) Then
		p\Action = ACTION_COMMON
	EndIf
	
;	If (p\Motion\Ground = False) Then
;		p\Action = ACTION_HOMEJUMP
;	EndIf
	
End Function

	; =========================================================================================================
	; Player_Action_Kick
	; =========================================================================================================
Function Player_Action_Kick(p.tPlayer, d.tDeltaTime)
	
	
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	
	Player_SubstractTowardsZero(Speed, 0.1*d\Delta#)
	
	;	If (SpeedLength# > 4.8) Then
	;	CustomPostprocessBlurMotion (.55)
	;	EndIf
	;	If (SpeedLength# < 4.8) Then
	;	CustomPostprocessBlurMotion (.35)
	;	EndIf
	StopChannel(Channel_Boost)
	;HideEntity(Mesh_Sonic_BoostFX)
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then
		RandomTrick()
		p\Action = ACTION_HOMEJUMP
	;	PlayRandomJumpSound()
		Channel_SpinKick = PlaySnd(Sound_SpinKick, p\Objects\Entity, 1)
		p\Motion\Speed\x# = p\Motion\Speed\x#*0.99
		p\Motion\Speed\y# = 1.6
		p\Motion\Speed\z# = p\Motion\Speed\z#*0.99
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
	End If
	
	If ((p\FallTimer < MilliSecs()) And (Not Input\Pressed\ActionA) And (p\Motion\Ground=False)) Then p\Action = ACTION_FALL
	
	If (Not Input\Pressed\ActionA) And (p\Motion\Ground=False) And (p\FallTimerStarted = 0) Then
		p\FallTimerStarted = 1
		p\FallTimer = MilliSecs() + 300
	;		p\Action = ACTION_FALL
	End If
	
	If (p\Frame > 9) Then
		p\Action = ACTION_COMMON
	EndIf
	
End Function

	; =========================================================================================================
	; Player_Action_Slide
	; =========================================================================================================
Function Player_Action_Slide(p.tPlayer, d.tDeltaTime)
	
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
	Player_SubstractTowardsZero(Speed, 0.4*d\Delta#)
	
	StopChannel(Channel_Boost)
	;HideEntity(Mesh_Sonic_BoostFX)
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
	SetEmitter(p\Objects\TempPiv, p\Objects\Template)
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then
		RandomTrick()
		p\Action = ACTION_HOMEJUMP
	;	PlayRandomJumpSound()
		Channel_SpinKick = PlaySnd(Sound_SpinKick, p\Objects\Entity, 1)
		p\Motion\Speed\x# = p\Motion\Speed\x#*0.99
		p\Motion\Speed\y# = 1.5
		p\Motion\Speed\z# = p\Motion\Speed\z#*0.99
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
	End If
	
;	If (p\Motion\Ground = False) And (Not Input\Pressed\ActionA) Then
;		p\Action = ACTION_FALL
;	EndIf
	
	If (p\Motion\Ground=True) Then p\FallTimerStarted = 0
	
	If ((p\FallTimer < MilliSecs()) And (Not Input\Pressed\ActionA) And (p\Motion\Ground=False)) Then p\Action = ACTION_FALL
	
	If (Not Input\Pressed\ActionA) And (p\Motion\Ground=False) And (p\FallTimerStarted = 0) Then
		p\FallTimerStarted = 1
		p\FallTimer = MilliSecs() + 300
	;		p\Action = ACTION_FALL
	End If
	
	If (SonicSpeed# < 0.4 Or Input\Pressed\ActionB) Then
		p\Action = ACTION_COMMON
	EndIf
	
End Function

	; =========================================================================================================
	; Player_Action_Ledge
	; =========================================================================================================
Function Player_Action_Ledge(p.tPlayer, d.tDeltaTime)
	
	SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	
	p\Motion\Speed\x# = 0
	p\Motion\Speed\y# = 0
	p\Motion\Speed\z# = 0
	
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
		; Jump when pressed and if on ground
	If (Input\Pressed\ActionA) Then			
		p\Action = ACTION_JUMP
		PlayRandomJumpSound()
		Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity, 1)
		p\Motion\Speed\x# = p\Motion\Speed\x#*0.99
		p\Motion\Speed\y# = 1.6
		p\Motion\Speed\z# = p\Motion\Speed\z#*0.99
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
		If (p\Motion\GroundSpeed > 2.5) Then
			p\Animation\Animation = 31
		Else
			p\Animation\Animation = 24
		EndIf
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
	End If
	
	If (Input\Pressed\ActionB) Then ;And Game\Gameplay\RingE > 0) Then
		p\Action = ACTION_FALL
	EndIf
	
;	If (p\Motion\Ground = False) Then
;		p\Action = ACTION_HOMEJUMP
;	EndIf
	
End Function


	; =========================================================================================================================
	; MISC
	; =========================================================================================================================
	
	; =========================================================================================================
	; Player_SubstractTowardsZero
	; =========================================================================================================
	Function Player_SubstractTowardsZero(v.tVector, Delta#)
		; Clamp delta
		Delta# = Min#(Max#(Delta#, 0.0), 1.0)

		; Calculate substract value
		SubX# = v\x#*Delta#
		SubY# = v\y#*Delta#
		SubZ# = v\z#*Delta#

		; Substract to each axys
		If (v\x# > 0) Then : v\x# = Max#(v\x#-SubX#, 0) : Else  : v\x# = Min#(v\x#-SubX#, 0) : End If
		If (v\y# > 0) Then : v\y# = Max#(v\y#-SubY#, 0) : Else  : v\y# = Min#(v\y#-SubY#, 0) : End If
		If (v\z# > 0) Then : v\z# = Max#(v\z#-SubZ#, 0) : Else  : v\z# = Min#(v\z#-SubZ#, 0) : End If
End Function 

	; =========================================================================================================
	; Player_SubstractTowardsValue
	; =========================================================================================================
Function Player_SubstractTowardsValue(v#, Delta#, Value#)
	
		; Clamp delta
	Delta# = Min#(Max#(Delta#, 0), 1)
	
	; Calculate substract value
	SubX# = v#*Delta#
	
		; Substract to each axys0
;If (v\x# > Value#) Then : v\x# = Max#(v\x#-SubX#, 0) : Else  : v\x# = Min#(v\x#-SubX#, 0) : End If
;If (v\x# < Value#) Then : v\x# = Max#(v\x#-SubX#, 0) : Else  : v\x# = Min#(v\x#-SubX#, 0) : End If
	If (v# > Value#) Then : v# = Max#(v#-SubX#, 0) : ElseIf (v# < Value#) : v# = Max#(v#-SubX#, 0) : EndIf
;If (v# < Value#) Then : v# = Max#(v#-SubX#, 0) : Else : v# = Max#(v#-SubX#, 0) : End If
End Function
	


Function Land(p.tPlayer, d.tDeltaTime)
	
	p\ActionBTimer = 0
	
;	p\HomingTimer = 0
	COMMON_GLIDEDESCENTIONRATE#	= -0.4
	p\FallTimerStarted = 0
	SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
	SetEmitter(p\Objects\TempPiv, p\Particles\Land)
	
	If (p\WillLandHard = 0) Then
		If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)>=0.9) Then
		p\LandMoving = 1
		p\LandStopped = 0
		p\Action = ACTION_LAND
	EndIf
EndIf
	
If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)<0.9) Then
		p\LandMoving = 0
		p\LandStopped = 1
		p\Action = ACTION_LAND
	EndIf
	
	If (p\WillLandHard = 1) Then
		If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)>=0.9) Then
		p\LandMoving = 1
		p\LandStopped = 0
		p\Action = ACTION_LANDHARD
		SetEmitter(p\Objects\TempPiv, p\Objects\Template)
	EndIf
	
	If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)<0.9) Then
		p\LandMoving = 0
		p\LandStopped = 1
		p\Action = ACTION_LANDHARD
	EndIf
EndIf

If (ChannelPlaying (Channel_Landing) = False) Then
	If (p\LoseLife = 0) Then
		Channel_Landing = PlaySnd (Sound_Landing, p\Objects\Entity)
	EndIf
EndIf
	
If (SonicSpeed# < 0.9 And Input\Hold\Up = False And Input\Hold\Down = False And Input\Hold\Left = False And Input\Hold\Right = False)
	p\Motion\Speed\x# = 0
	p\Motion\Speed\y# = 0
	p\Motion\Speed\z# = 0
EndIf

p\BounceHeight = 0.7
p\WillLandHard = 0
	p\Animation\Time# = 0.0
	RecursiveSetAnimTime(p\Objects\Mesh, 0, 7)
End Function

Function ModInput(Mode)
	
	Select Mode
		Case 1
			INPUT_BUTTON_UP				= 999
			INPUT_BUTTON_DOWN			= 999
			INPUT_BUTTON_LEFT			= 999
			INPUT_BUTTON_RIGHT			= 999
			INPUT_BUTTON_ACTION_A		= 999
			INPUT_BUTTON_ACTION_B		= 999
			INPUT_BUTTON_ACTION_C		= 999
			INPUT_BUTTON_INTERACT		= 999
			INPUT_BUTTON_HOVER			= 999
			INPUT_BUTTON_COPYPOS		= 999
			INPUT_BUTTON_COPYROT		= 999
			INPUT_BUTTON_COPYDEST		= 999
			INPUT_BUTTON_WALK			= 999
			INPUT_BUTTON_CAMERA_UP		= 999
			INPUT_BUTTON_CAMERA_DOWN	= 999
			INPUT_BUTTON_CAMERA_LEFT	= 999
			INPUT_BUTTON_CAMERA_RIGHT	= 999
			INPUT_BUTTON_CAMERA_ZIN		= 999
			INPUT_BUTTON_CAMERA_ZOUT	= 999
		Case 2
			INPUT_BUTTON_UP				= 0
			INPUT_BUTTON_DOWN			= 1
			INPUT_BUTTON_LEFT			= 2
			INPUT_BUTTON_RIGHT			= 3
			INPUT_BUTTON_ACTION_A		= 4
			INPUT_BUTTON_ACTION_B		= 5
			INPUT_BUTTON_ACTION_C		= 6
			INPUT_BUTTON_INTERACT		= 7
			INPUT_BUTTON_HOVER			= 8
			INPUT_BUTTON_COPYPOS		= 9
			INPUT_BUTTON_COPYROT		= 10
			INPUT_BUTTON_COPYDEST		= 11
			INPUT_BUTTON_WALK			= 12
			INPUT_BUTTON_CAMERA_UP		= 13
			INPUT_BUTTON_CAMERA_DOWN	= 14
			INPUT_BUTTON_CAMERA_LEFT	= 15
			INPUT_BUTTON_CAMERA_RIGHT	= 16
			INPUT_BUTTON_CAMERA_ZIN		= 17
			INPUT_BUTTON_CAMERA_ZOUT	= 18
		Case 3
			INPUT_BUTTON_UP				= 999
			INPUT_BUTTON_DOWN			= 999
			INPUT_BUTTON_LEFT			= 999
			INPUT_BUTTON_RIGHT			= 999
			INPUT_BUTTON_ACTION_A		= 999
			INPUT_BUTTON_ACTION_B		= 999
			INPUT_BUTTON_ACTION_C		= 999
			INPUT_BUTTON_INTERACT		= 7
			INPUT_BUTTON_HOVER			= 999
			INPUT_BUTTON_COPYPOS		= 999
			INPUT_BUTTON_COPYROT		= 999
			INPUT_BUTTON_COPYDEST		= 999
			INPUT_BUTTON_WALK			= 12
			INPUT_BUTTON_CAMERA_UP		= 13
			INPUT_BUTTON_CAMERA_DOWN	= 14
			INPUT_BUTTON_CAMERA_LEFT	= 15
			INPUT_BUTTON_CAMERA_RIGHT	= 16
			INPUT_BUTTON_CAMERA_ZIN		= 17
			INPUT_BUTTON_CAMERA_ZOUT	= 18
		Case 4
			INPUT_BUTTON_UP				= 999
			INPUT_BUTTON_DOWN			= 999
			INPUT_BUTTON_LEFT			= 999
			INPUT_BUTTON_RIGHT			= 999
			INPUT_BUTTON_ACTION_A		= 4
			INPUT_BUTTON_ACTION_B		= 5
			INPUT_BUTTON_ACTION_C		= 6
			INPUT_BUTTON_INTERACT		= 7
			INPUT_BUTTON_HOVER			= 8
			INPUT_BUTTON_COPYPOS		= 9
			INPUT_BUTTON_COPYROT		= 10
			INPUT_BUTTON_COPYDEST		= 11
			INPUT_BUTTON_WALK			= 12
			INPUT_BUTTON_CAMERA_UP		= 13
			INPUT_BUTTON_CAMERA_DOWN	= 14
			INPUT_BUTTON_CAMERA_LEFT	= 15
			INPUT_BUTTON_CAMERA_RIGHT	= 16
			INPUT_BUTTON_CAMERA_ZIN		= 17
			INPUT_BUTTON_CAMERA_ZOUT	= 18
	End Select
	Return
End Function

Function TriDRingLoss(p.tPlayer, d.tDeltaTime)
	
	RingSpew(p, p\RingLossCount, 0)
		
		PlaySnd(Sound_Loss, p\Objects\Entity)
		Objects_Update(d.tDeltaTime)
		
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D