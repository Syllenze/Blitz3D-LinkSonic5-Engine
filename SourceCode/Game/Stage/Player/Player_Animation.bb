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
;   METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

Type tAnim
	Field mode
	Field speed#
	Field sequence
	Field transition#
End Type

	; =========================================================================================================
	; Player_Animate
	; =========================================================================================================
	Function Player_Animate(p.tPlayer, d.tDeltaTime)
		; Change animation depending on action
		
		If p\DiveTimer > 400 And p\Motion\Ground = False And p\Action <> ACTION_STOMPPART2 Then
			
			p\Animation\Animation = 39
			
		Else
			
		Select p\Action
			Case ACTION_COMMON
				SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
				
				
						; Declarate acceleration and speed vectors and setup.
				Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
				Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
				SpeedCompensation.tVector	= Vector(0, 0, 0)
				Speed_Length#				= Vector_Length#(Speed)
		;		
		;		If (Not p\Action = ACTION_LAND) Then
		;			p\LandStopped = 0
		;			p\LandMoving = 0
		;		EndIf
;				
				If (p\LandMoving = 1) Or (p\LandStopped = 1) Then p\Action = ACTION_LAND
				If (p\Grinding = 0) Then
				If ((p\Motion\Ground = True)) Then ;And (p\LandMoving = 0) And (p\LandStopped = 0)) Then
					
					If (Goal = 0) Then
						
						If (p\Animation\Idle = 0) Then p\Animation\Animation = 0
						If (p\Animation\Idle = 1) Then p\Animation\Animation = 22
						If (p\Animation\Idle = 2) Then p\Animation\Animation = 23
						
					Else
						p\Animation\Animation = 26
					EndIf
				EndIf
						If p\Flags\Skidding = True Then
							
							p\Animation\Animation = 25
						Else
							
		;-------------------------------------------------------------------------
							
		;-------------------------------------------------------------------------
					; SONIC Animation Controller		
							If (p\Character = CHARACTER_SONIC) Then
								
								
								
								If (SpeedLength# > 0.0) Then
									
									p\Animation\Idle = 0
						
					;	If (Game\Stage\Properties\Hub = 0)
						
									If (Input\Hold\Right Or Input\Hold\Left Or Input\Hold\Down Or Input\Hold\Up) Then
										If (p\Animation\ToJog = 1) Then
											p\Animation\Animation = 30 ;Jog Start
										Else
											p\Animation\Animation = 14
										EndIf
									Else
										If (p\Animation\ToJog = 1) Then
											p\Animation\Animation = 30 ;Jog Start
										Else
							p\Animation\Animation = 1 ;Walk
						EndIf
					EndIf
				;	Else
				;		p\Animation\Animation = 14
				;	EndIf
						
					EndIf
					
					If (SpeedLength# > 2.40) Then p\Animation\Animation = 13 ;Jog
					
					If (SpeedLength# > 4.20) Then
						If p\BB_InMove = 0 Then
							p\Animation\Animation = 19 ;Run 1
						Else
							p\Animation\Animation = 2
						EndIf
					EndIf
						
					If (SpeedLength# > 5.75) Then p\Animation\Animation = 2 ;Run 2
					
				EndIf
		;-------------------------------------------------------------------------
			
								; TAILS Animation Controller		
			If (p\Character = CHARACTER_TAILS) Then
				If (SpeedLength# > 0.0) Then
					
					;	If (Game\Stage\Properties\Hub = 0)
					
					If (Input\Hold\Right Or Input\Hold\Left Or Input\Hold\Down Or Input\Hold\Up) Then
						p\Animation\Animation = 14 ;Jog Start
					Else
						p\Animation\Animation = 1 ;Walk
					EndIf
				;	Else
				;		p\Animation\Animation = 14
				;	EndIf
					
				EndIf
				
				If (SpeedLength# > 2.40) Then p\Animation\Animation = 13 ;Jog
				
				If (SpeedLength# > 3.40) Then p\Animation\Animation = 19 ;Run 1
				
				If (SpeedLength# > 4.75) Then p\Animation\Animation = 2 ;Run 2
				
			EndIf
		;-------------------------------------------------------------------------
		
							; KNUCKLES Animation Controller		
		If (p\Character = CHARACTER_KNUCKLES) Then
			If (SpeedLength# > 0.0) Then
				
					;	If (Game\Stage\Properties\Hub = 0)
				
				If (Input\Hold\Right Or Input\Hold\Left Or Input\Hold\Down Or Input\Hold\Up) Then
					p\Animation\Animation = 14 ;Jog Start
				Else
					p\Animation\Animation = 1 ;Walk
				EndIf
				;	Else
				;		p\Animation\Animation = 14
				;	EndIf
				
			EndIf
		
		
		
			If (SpeedLength# > 2.40) Then p\Animation\Animation = 13 ;Jog
			
			If (SpeedLength# > 3.20) Then p\Animation\Animation = 19 ;Run 1
			
			If (SpeedLength# > 4.75) Then p\Animation\Animation = 2 ;Run 2
			
		EndIf
		
		;-------------------------------------------------------------------------
	
		;-------------------------------------------------------------------------
		
		
		
	EndIf
	
Else
	p\Animation\Animation = 33
EndIf

	
	
	
Case ACTION_JUMP
					
				If (p\Motion\Speed\y# <= -1) Then
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) > 3) Then
						p\Animation\Animation = 12
					EndIf
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) <= 3) Then
						p\Animation\Animation = 4
					EndIf
				EndIf
				
				
			Case ACTION_JUMPPART2
				
				If (p\Motion\Speed\y# > -1) Then p\Animation\Animation = 3 : p\JumpImageShow = 0
				
				If (p\Motion\Speed\y# <= -1) Then
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) > 3) Then
						p\Animation\Animation = 12
						p\JumpImageShow = 0
					EndIf
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) <= 3) Then
						p\Animation\Animation = 4
						p\JumpImageShow = 0
					EndIf
				EndIf
				
				
			Case ACTION_CROUCH
				p\Animation\Animation = 1
			Case ACTION_SPINDASH
				p\Animation\Animation = 38
			Case ACTION_FALL
				
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) > 3) Then
						p\Animation\Animation = 12
					EndIf
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) <= 3) Then
						p\Animation\Animation = 4
					EndIf
				
			Case ACTION_JUMPDASH
				p\Animation\Animation = 36
			Case ACTION_SPRING
				p\Animation\Animation = 5
			Case ACTION_SPRINGFALL
				
				If (p\Motion\Speed\y# > -2.5) Then
					p\Animation\Animation = 40
				EndIf
				
				If (p\Motion\Speed\y# =< -2.5) Then
				
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) > 1) Then
					p\Animation\Animation = 12
				EndIf
				If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) <= 1) Then
					p\Animation\Animation = 4
				EndIf
			EndIf
			
			If (p\Motion\Speed\y# < -4.5) Then p\Animation\Animation = 39
				
			Case ACTION_STOMP
				p\Animation\Animation = 35
			Case ACTION_HOMING
				p\Animation\Animation = 37
			Case ACTION_HOMEJUMP
				p\JumpImageShow = 0
			;	If (p\Motion\Speed\y# >= 0.7) Then p\Animation\Animation = 32
			;	If (p\Motion\Speed\y# < 0.7) And (p\Motion\Speed\y# > -0.7) Then p\Animation\Animation = 21 : p\JumpImageShow = 0
			;	If (p\Motion\Speed\y# <= -0.8) Then
				If (p\Motion\Speed\y# <= -1.2) Then
					
					p\JumpImageShow = 0
					
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) > 3) Then
						p\Animation\Animation = 12
					EndIf
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) <= 3) Then
						p\Animation\Animation = 4
					EndIf
				EndIf
				
				
			Case ACTION_ROLL
				p\Animation\Animation = 3
			Case ACTION_KICK
				p\Animation\Animation = 7
			Case ACTION_RINGDASH
				p\Animation\Animation = 10
			Case ACTION_LAND
				p\Animation\Animation = 6
			Case ACTION_SLIDE
				p\Animation\Animation = 8
			Case ACTION_STOMPPART2
				p\Animation\Animation = 9
			Case ACTION_HURT
				p\Animation\Animation = 15
			Case ACTION_RAMP
				p\Animation\Animation = 18
			Case ACTION_LANDHARD
				p\Animation\Animation = 20
			Case ACTION_BOUNCE
				p\Animation\Animation = 3
				If (p\Motion\Speed\y# > -0.8) Then p\Animation\Animation = 35 : p\JumpImageShow = 1
				If (p\Motion\Speed\y# <= -0.8) Then
					
					p\JumpImageShow = 0
					
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) > 3) Then
						p\Animation\Animation = 12
					EndIf
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) <= 3) Then
						p\Animation\Animation = 4
					EndIf
				EndIf
				
				
			Case ACTION_DESTSPRING
				
			;	If (p\Motion\GroundSpeed# > p\Motion\Speed\y#) Then
					p\Animation\Animation = 18
			;	Else
			;		If (p\Motion\Speed\y# > 0) Then p\Animation\Animation = 5
			;		If (p\Motion\Speed\y# < 0) Then p\Animation\Animation = 4
			;	EndIf
				
		;		If (p\Motion\Speed\y# > -0.8) Then p\Animation\Animation = 5
		;		If (p\Motion\Speed\y# <= 1.2 And p\Motion\Speed\y# >= -1.2) Then p\Animation\Animation = 18
		;		If (p\Motion\Speed\y# < -1.2) Then p\Animation\Animation = 4
				Case ACTION_SKIDDING
					p\Animation\Animation = 3
					
				Case ACTION_DIVE
					If (p\Motion\Speed\y# < -2.7) Then
						p\Animation\Animation = 17
				Else
					p\Animation\Animation = 16
				EndIf
			Case ACTION_SPINDASHNEW
				p\Animation\Animation = 3
				
			Case ACTION_GLIDE
				p\Animation\Animation = 28
			Case ACTION_FLY
				p\Animation\Animation = 28
			Case ACTION_WALLHIT
				p\Animation\Animation = 29
			Case ACTION_CLIMB
				p\Animation\Animation = 30
			Case ACTION_DOUBLEJUMP
				If (p\Motion\Speed\y# > -1) Then p\Animation\Animation = 3 : p\JumpImageShow = 0
				
				If (p\Motion\Speed\y# <= -1) Then
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) > 3) Then
						p\Animation\Animation = 12
						p\JumpImageShow = 0
					EndIf
					If (Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) <= 3) Then
						p\Animation\Animation = 4
						p\JumpImageShow = 0
					EndIf
				EndIf
			Case ACTION_DIVEINTOROLL
				p\Animation\Animation = 17
		End Select
		
	EndIf
			
			
			; Update normals
			RecursiveUpdateNormals(p\Objects\Mesh)
			RecursiveUpdateNormals(p\Objects\Shadow)
			
		; If the animation changed, animate new
		If (p\Animation\Animation<>p\Animation\PreviousAnimation) Then
			Select p\Animation\Animation
				Case 0
					RecursiveAnimate(p\Objects\Mesh, 1, 0.5, 1, 8)
				Case 1
					RecursiveAnimate(p\Objects\Mesh, 1, 0.9, 2, 0)
				;	p\Animation\Time# = 0.0
				Case 2
					RecursiveAnimate(p\Objects\Mesh, 1, 2.2, 3, 3)
				;	p\Animation\Time# = 0.0
				;	RecursiveSetAnimTime(p\Objects\Mesh, 0, 3)
				Case 3
					RecursiveAnimate(p\Objects\Mesh, 1, 1.3, 4, 4)
				Case 4
					RecursiveAnimate(p\Objects\Mesh, 1, 0.8, 5, 8)
				Case 5
					RecursiveAnimate(p\Objects\Mesh, 1, 0.7, 6, 0)
				Case 6
					RecursiveAnimate(p\Objects\Mesh, 3, 0.8, 7, 0)
				;	p\Animation\Time# = 0.0
				Case 7
					RecursiveAnimate(p\Objects\Mesh, 3, 0.4, 8, 0)
				Case 8
					RecursiveAnimate(p\Objects\Mesh, 1, 0.6, 9, 0)
				Case 9
					RecursiveAnimate(p\Objects\Mesh, 1, 0.6, 10, 0)
				Case 10
					RecursiveAnimate(p\Objects\Mesh, 1, 0.6, 11, 0)
				Case 11
					RecursiveAnimate(p\Objects\Mesh, 1, -1.3, 4, 8)
				Case 12
					RecursiveAnimate(p\Objects\Mesh, 1, 0.5, 12, 8)
				Case 13
					RecursiveAnimate(p\Objects\Mesh, 1, 1.0, 13, 4)
				;	p\Animation\Time# = 0.0
				Case 14
					RecursiveAnimate(p\Objects\Mesh, 1, 0.9, 13, 0)
					p\Animation\Time# = 0.0
				Case 15
					RecursiveAnimate(p\Objects\Mesh, 1, 0.9, 14, 0)
				Case 16
					RecursiveAnimate(p\Objects\Mesh, 1, 0.9, 15, 11)
				Case 17
					RecursiveAnimate(p\Objects\Mesh, 1, 0.9, 16, 15)
				Case 18
					RecursiveAnimate(p\Objects\Mesh, 1, 0.9, 17, 0)
				Case 19
					RecursiveAnimate(p\Objects\Mesh, 1, 0.9, 18, 0)
				Case 20
					RecursiveAnimate(p\Objects\Mesh, 3, 0.8, 19, 0)
				Case 21
					RecursiveAnimate(p\Objects\Mesh, 1, 0.85, 20, 7)
				Case 22
					RecursiveAnimate(p\Objects\Mesh, 3, 0.5, 21, 3)
					PlayRandomIdleSound()
				Case 23
					RecursiveAnimate(p\Objects\Mesh, 3, 0.5, 22, 3)
					PlayRandomIdleSound()
				Case 24
					RecursiveAnimate(p\Objects\Mesh, 3, 0.5, 23, 4)
				Case 25
					RecursiveAnimate(p\Objects\Mesh, 1, 1.5, 24, 4)
				Case 26
					RecursiveAnimate(p\Objects\Mesh, 1, 0.5, 25, 6)
				Case 27
					RecursiveAnimate(p\Objects\Mesh, 1, 2.3, 4, 4)
				Case 28
					RecursiveAnimate(p\Objects\Mesh, 1, 1.6, 27, 12)
				Case 29
					RecursiveAnimate(p\Objects\Mesh, 3, 0.6, 28, 3)
				Case 30
					RecursiveAnimate(p\Objects\Mesh, 3, 0.6, 29, 7)
				Case 31
					RecursiveAnimate(p\Objects\Mesh, 3, 0.55, 30, 5)
					p\Animation\Time# = 0.0
					
				Case 32
					RecursiveAnimate(p\Objects\Mesh, 3, 1.0, 31, 5)
				Case 33
					RecursiveAnimate(p\Objects\Mesh, 1, 1.0, 32, 5)
				Case 34
					RecursiveAnimate(p\Objects\Mesh, 1, 0.8, 33, 9)
				Case 35
					RecursiveAnimate(p\Objects\Mesh, 1, 1.7, 34, 9)
				Case 36
					RecursiveAnimate(p\Objects\Mesh, 1, 1.7, 35, 0)
				Case 37
					RecursiveAnimate(p\Objects\Mesh, 1, 1.7, 36, 0)
				Case 38
					RecursiveAnimate(p\Objects\Mesh, 1, 1.7, 37, 0)
				Case 39
					RecursiveAnimate(p\Objects\Mesh, 1, 1.7, 38, 14)
			;	Case 40
			;		RecursiveAnimate(p\Objects\Mesh, 1, 0.75, 39, 14)
				Case 40
					RecursiveAnimate(p\Objects\Mesh, 3, 0.5, 39, 6)
				Case 41
					RecursiveAnimate(p\Objects\Mesh, 3, 1.0, 40, 5)
				Case 42
					RecursiveAnimate(p\Objects\Mesh, 3, 1.0, 41, 5)
			End Select
			
			p\Animation\PreviousAnimation = p\Animation\Animation
		End If

		; Depending on animation, change animation speed
		Select p\Animation\Animation
			Case 1
				;Walk
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.1)+0.6)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 2)
				;Full Sprint 2
			Case 2
			;	p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.34)+0.0)*d\Delta
			;	RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 3)
				
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.11)+1.5)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 3)
				;Jog 2
			Case 13
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.23)+0.4)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 13)
				;Jog 1
			Case 14
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.28)+0.4)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 13)
				;Ramp
			Case 18
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.24)+0.4)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 17)
				;Full Sprint 1
			Case 19
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.19)+0.49)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 18)
				;Skidding
			Case 25
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.24)+0.58)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 24)
				;Fall
	;		Case 4
	;			p\Animation\Time# = p\Animation\Time#+((Abs(p\Motion\Speed\y#)*0.3)+0.6)*d\Delta
	;			RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 5)
				
				;Roll
			Case 3
				p\Animation\Time# = p\Animation\Time#+((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.1)+1.2)*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh, p\Animation\Time#, 4)
		End Select
		
		; Update loaded sounds
		
		For i = 1 To SoundArrayEnd
		;	For q.tSound = Each tSound
		;	If Sounds1[1]\Sound <> 0 Then
			If SoundsLoaded = 1 Then
		;	If SoundArrayEnd > 0 Then
			If (p\Frame > Sounds1[i]\Frame-2) And (p\Frame < Sounds1[i]\Frame+3) And (p\Animation\Animation = Sounds1[i]\Anim) And (ChannelPlaying(Sounds1[i]\Channel)=False) Then Sounds1[i]\Channel = PlaySnd(Sounds1[i]\Sound, p\Objects\Entity)
		EndIf				
	;Next
	Next
		
	; If not idle, then use Common Animation
	;	If ((Not p\Animation\Animation = 21) And (Not p\Animation\Animation = 22)) Then p\Animation\Idle = 0
		
		
		

		; On spinning animation, show ball
	;	If (p\Animation\Animation = 3) Then
	;		If ((MilliSecs() Mod 20) < 10) Then : ShowEntity(p\Objects\Mesh_JumpBall)
	;		Else : HideEntity(p\Objects\Mesh_JumpBall) : End If
	;	Else
	;		HideEntity(p\Objects\Mesh_JumpBall)
	;	End If
		
		; Update normals
		RecursiveUpdateNormals(p\Objects\Mesh)
End Function

; Random Idle Animation

Function RandomIdle(p.tPlayer, d.tDeltaTime)
	IdleAnim = Rand(1,2)
	Select IdleAnim
		Case 1 : p\Animation\Animation = 21 : p\Animation\Idle = 1
		Case 2 : p\Animation\Animation = 22 : p\Animation\Idle = 1
	End Select
	
	Return
End Function

Function RandomTrick()
	Anim = Rand(1,3)
	For p.tPlayer = Each tPlayer
	Select Anim
		Case 1 : p\Animation\Animation = 32
		Case 2 : p\Animation\Animation = 41
		Case 3 : p\Animation\Animation = 42
	End Select
Next
	
	Return
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D