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

	; =========================================================================================================
	; Player_Animate
	; =========================================================================================================
	Function Player_Animate2(p.tPlayer2, d.tDeltaTime)
		; Change animation depending on action
		Select p\Action2
		
		Case ACTION_COMMON
				; Get the speed of the player so we can adjust the animation speed
				SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) 
				If (p\Motion\Ground = True) Then 
				
					p\Animation\Animation = 0
					
					; Change to running animations based on speed
					If (SpeedLength# > 0.0)  Then p\Animation\Animation = 12					
					If (SpeedLength# > 3.4)  Then p\Animation\Animation = 17
			;		If (SpeedLength# > 1.3)  Then p\Animation\Animation = 4
			;		If (SpeedLength# > 1.8)  Then p\Animation\Animation = 5
			;		If (SpeedLength# > 2.30) Then p\Animation\Animation = 6
			;		If (SpeedLength# > 4.0)  Then p\Animation\Animation = 7 ; Boost animation, only at high speeds.
					
					; Skidding?
					If p\Flags\Skidding = True Then
						;If (SpeedLength# <= 1.3) Then p\Animation\Animation = 13
						;If (SpeedLength# > 1.3) Then p\Animation\Animation = 14
						p\Animation\Animation = 14
					End If			
					Else
					If (p\Motion\Speed\y > 0.5)  Then p\Animation\Animation = 3		
					If (p\Motion\Speed\y < 0.5)  Then p\Animation\Animation = 4
				End If

			Case ACTION_JUMP
				If (p\Motion\Speed\y > 0.1)  Then p\Animation\Animation = 3
				If (p\Motion\Speed\y < 0.1)  Then p\Animation\Animation = 4
			Case ACTION_CROUCH
				p\Animation\Animation = 12
			Case ACTION_SPINDASH
				p\Animation\Animation = 3
		End Select

		; If the animation changed, animate new
		If (p\Animation\Animation<>p\Animation\PreviousAnimation) Then
			Select p\Animation\Animation
				Case 0
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 1, 8)
				Case 1
					RecursiveAnimate(p\Objects\Mesh2, 1, 1, 2, 8)
				Case 2
					RecursiveAnimate(p\Objects\Mesh2, 1, 1, 3, 9)
					;p\Animation\Time# = 0.0
				Case 3
					RecursiveAnimate(p\Objects\Mesh2, 1, 2, 4, 8)
				Case 4
				    RecursiveAnimate(p\Objects\Mesh2, 1, 1.3, 5, 8)
				Case 5
				    RecursiveAnimate(p\Objects\Mesh2, 1, 3, 6, 8)
				Case 6
				    RecursiveAnimate(p\Objects\Mesh2, 1, 3.5+SpeedLength#, 7, 8)
				Case 7
			     	RecursiveAnimate(p\Objects\Mesh2, 1, 3, 8, 8)
				Case 8
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.6, 9, 8)
                Case 9
                    RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 10, 8)
                Case 10
                    RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 11, 8)
                Case 11
                    RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 12, 8)
				Case 12
					RecursiveAnimate(p\Objects\Mesh2, 1, 1, 13, 0)
				Case 13
					RecursiveAnimate(p\Objects\Mesh2, 1, 2, 14, 8)
				Case 14
					RecursiveAnimate(p\Objects\Mesh2, 1, 2, 15, 8)
				Case 15
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.8, 16, 8)
				Case 17
					RecursiveAnimate(p\Objects\Mesh2, 1, 1.4, 18, 8)
					p\Animation\Time# = 0.0
				Case 18
                    RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 19, 8)
					p\Animation\Time# = 0.0
				Case 19 ; Pick up
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 20, 8)
				Case 20 ; Hold-idle
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 21, 8)
				Case 21 ; Hold-walk
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 22, 8)
				Case 22 ; Put down
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 23, 8)

				Case 23 ; Pet
					RecursiveAnimate(p\Objects\Mesh2, 1, 0.5, 24, 8)
			End Select
			
			p\Animation\PreviousAnimation = p\Animation\Animation
		End If

		; Depending on animation, change animation speed
		Select p\Animation\Animation
			Case 1
				p\Animation\Time# = p\Animation\Time#+Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)*0.53*d\Delta
				RecursiveSetAnimTime(p\Objects\Mesh2, p\Animation\Time#, 2)
		End Select


		
		; Update normals
		RecursiveUpdateNormals(p\Objects\Mesh2)
	End Function
;~IDEal Editor Parameters:
;~C#Blitz3D