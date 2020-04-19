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
;               16/01/2008 - Code reorganization.                                                              ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO: - Implement structures for actions and object interaction                                            ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
; 	STRUCTURES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\

	; ---------------------------------------------------------------------------------------------------------
	; tPlayer
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer2
		; Player objects and entities
		Field Objects.tPlayer_Objects2

		; Player motion values
		Field Motion.tPlayer_Motion2

		; Other values
		Field Character2
		Field Action2
		Field Flags.tPlayer_Flags2
		Field Animation.tPlayer_Animation2
		Field SpindashCharge2
	End Type


	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Objects
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Objects2
		Field Entity2
	;	Field Camera2.tCamera2
		Field Shadow2
		Field Mesh2
		Field Mesh_Balance
		Field Mesh_Head
		Field Mesh_Arms
		Field Mesh_Torso
		Field Mesh_Legs
		Field Mesh_Shield
		Field Mesh_JumpBall
		Field Mesh_Spindash
		
		Field Pointer
	End Type 
	
	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Motion
	; ---------------------------------------------------------------------------------------------------------
	; Contains information of player object's motion, such as speed and
	; other values.
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Motion2
		Field Speed.tVector
		Field Align.tVector
		Field Ground
	End Type

	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Flags
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Flags2
		Field AllowCommonInput
		Field AllowXZMovement
		Field AllowYMovement
		Field AllowSkidding
		Field Hurt
		Field Shield
		Field Invincibility
		Field SpeedSneakers
		Field Skidding
	End Type

	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Animation
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Animation2
		Field Animation
		Field PreviousAnimation
		Field Time#
		Field Direction#
		Field Tilt#
		Field Align.tVector
	End Type 
	
	Global Mesh_TagSonic

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Creation/Destruction and management ----
	; =========================================================================================================
	; Player_Create
	; =========================================================================================================
Function Player_Create2.tPlayer2(Character=0, ch$)
		; Create new player object
        p.tPlayer2 	= New tPlayer2

		; Create objects
		p\Objects 	= New tPlayer_Objects2
		p\Motion  	= New tPlayer_Motion2
		p\Flags		= New tPlayer_Flags2
		p\Animation	= New tPlayer_Animation2
		
		p\Motion\Speed    = Vector(0, 0, 0)
		p\Motion\Align 	  = Vector(0, 1, 0)
		p\Animation\Align = Vector(0, 1, 0)
		
		p\Objects\Entity2  = CreatePivot(Game\Stage\Root)
		
		p\Flags\AllowCommonInput = True
		p\Flags\AllowXZMovement  = True
		p\Flags\AllowYMovement   = True
		
		
		; Setup character values
		p\Character2 = Character
		Select p\Character2
			Case CHARACTER_SONIC
				; Load mesh
				
				Mesh_TagSonic							= LoadAnimMesh("Characters/" + ch$ + "/Sonic.b3d")
				
				RecursiveExtractAnimSeq(Mesh_TagSonic,	0,		120)	; Idle
				RecursiveExtractAnimSeq(Mesh_TagSonic,	121,	150)	; Walking
				RecursiveExtractAnimSeq(Mesh_TagSonic,	151,	180)	; Running 2
				RecursiveExtractAnimSeq(Mesh_TagSonic,	181,	196)	; Spinning
				RecursiveExtractAnimSeq(Mesh_TagSonic,	197,	216)	; Falling
				RecursiveExtractAnimSeq(Mesh_TagSonic,	217,	235)	; Spring
				RecursiveExtractAnimSeq(Mesh_TagSonic,	236,	260)	; Land
				RecursiveExtractAnimSeq(Mesh_TagSonic,	261,	272)	; Spin Kick
				RecursiveExtractAnimSeq(Mesh_TagSonic,	273,	292)	; Slide
				RecursiveExtractAnimSeq(Mesh_TagSonic,	293,	300)	; StompPart2
				RecursiveExtractAnimSeq(Mesh_TagSonic,	301,	320)	; RingDash
				RecursiveExtractAnimSeq(Mesh_TagSonic,	321,	340)	; FallSpeed
				RecursiveExtractAnimSeq(Mesh_TagSonic,	341,	370)	; Jogging
				RecursiveExtractAnimSeq(Mesh_TagSonic,	371,	400)	; Hurt
				RecursiveExtractAnimSeq(Mesh_TagSonic,	401,	420)	; Dive 1
				RecursiveExtractAnimSeq(Mesh_TagSonic,	421,	430)	; Dive 2
				RecursiveExtractAnimSeq(Mesh_TagSonic,	431,	450)	; Ramp
				RecursiveExtractAnimSeq(Mesh_TagSonic,	451,	480)	; Running 1
				RecursiveExtractAnimSeq(Mesh_TagSonic,	481,	540)	; Land Hard
				RecursiveExtractAnimSeq(Mesh_TagSonic,	541,	556)	; Trick Loop
				RecursiveExtractAnimSeq(Mesh_TagSonic,	557,	626)	; Idle 1
				RecursiveExtractAnimSeq(Mesh_TagSonic,	627,	696)	; Idle 2
				RecursiveExtractAnimSeq(Mesh_TagSonic,	697,	720)	; Hop
				RecursiveExtractAnimSeq(Mesh_TagSonic,	721,	740)	; Skidding
				RecursiveExtractAnimSeq(Mesh_TagSonic,	741,	860)	; Win Idle
				HideEntity(Mesh_TagSonic)
				
				p\Objects\Mesh2 			= CopyEntity(Mesh_TagSonic, Game\Stage\Root)
				;ScaleEntity(p\Objects\Mesh, GAME_SCALE#, GAME_SCALE#, GAME_SCALE#)

				p\Objects\Mesh_Balance	= FindChild(p\Objects\Mesh2, "Bone01")
				p\Objects\Mesh_Head 	= FindChild(p\Objects\Mesh2, "Head")
				p\Objects\Mesh_Arms 	= FindChild(p\Objects\Mesh2, "Arms")
				p\Objects\Mesh_Torso 	= FindChild(p\Objects\Mesh2, "Torso")
				p\Objects\Mesh_Legs 	= FindChild(p\Objects\Mesh2, "Legs")
				
				p\Objects\Pointer = CreatePivot()

			;	p\Objects\Mesh_Spindash = CopyEntity(Mesh_Sonic_Spindash, Game\Stage\Root)
			;	p\Objects\Mesh_JumpBall = CopyEntity(Mesh_Sonic_JumpBall, p\Objects\Mesh2)
			;	ScaleEntity(p\Objects\Mesh_Spindash, GAME_SCALE#, GAME_SCALE#, GAME_SCALE#)
			;	HideEntity p\Objects\Mesh_Spindash

			Case CHARACTER_TAILS
			Case CHARACTER_KNUCKLES
		End Select

		; Create shadow quad
		;p\Objects\Shadow = CreateQuad(4, 4, 2, 2, Game\Stage\Root)
		;EntityTexture(p\Objects\Shadow, Textures_Shadow)
		
		; Setup pivot collision
		EntityType(p\Objects\Entity2, COLLISION_PLAYER)
		EntityRadius(p\Objects\Entity2, 2.20)

		; Done
		Return p
	End Function


	; =========================================================================================================
	; Player_Destroy
	; =========================================================================================================
	Function Player_Destroy2(p.tPlayer2)
		FreeEntity(p\Objects\Entity2)
		FreeEntity(p\Objects\Mesh2)
		
		Delete p\Motion\Speed
		Delete p\Motion\Align
		Delete p\Animation\Align
		Delete p\Objects
		Delete p\Motion
		Delete p\Animation
		Delete p\Flags
	End Function


	; =========================================================================================================
	; Player_Update
	; =========================================================================================================
Function Player_Update2(p.tPlayer2, d.tDeltaTime)
		; Perform player's movement
	Player_Motion2(p, d)
	
	Local SpeedMult
	Local ReachTimer
	
		
		; FOLLOW CODE
		For p1.tPlayer = Each tPlayer
			TurnEntity(p\Objects\Pointer,10,180,0) : TFormPoint(10,180,0,p\Objects\Pointer,p1\Objects\Mesh) : p\Animation\Direction# = EntityYaw#(p\Objects\Pointer)
			PositionEntity(p\Objects\Pointer, EntityX(p\Objects\Entity2), EntityY(p\Objects\Entity2), EntityZ(p\Objects\Entity2))
			PointEntity(p\Objects\Pointer, p1\Objects\Entity)
			
			
			; If the tagger is close behind, set a speed
			If (((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) >= 10) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) >= 10))) Then; And ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) < 50) And (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) < 50)) Then
			;	If (Abs(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) < 50) And (Abs(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) < 50) And p1\Motion\Ground = True Then
				p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.055	;0.05
				p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.055
			;	SpeedMult = SpeedMult + GlobalTimer
			EndIf
		;EndIf
			
		;	If (EntityDistance(p\Objects\Entity2, p1\Objects\Entity) > 10) And (EntityDistance(p\Objects\Entity2, p1\Objects\Entity) < 50) And (Abs(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) > 10) And p1\Motion\Ground = False Then
	;			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.06	;0.05
	;			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.06
			;	SpeedMult = SpeedMult + GlobalTimer
		;	EndIf
			
			; If the tagger falls behind, speed up
			If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) >= 50) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) >= 50)) Then; And ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) < 90) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity))) < 90) Then; And (Abs(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) < 30) Then
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.12	;0.05
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.12
		EndIf
				
				; If the tagger is falling far behind, don't spaz
		If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) >= 90) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity))) >= 90) Then
					p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.07	;0.05
					p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.07
					EndIf
					
			; If he is close, stop the tagger
					If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) < 10) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity))) < 10) Then
					;	p\Motion\Speed\x# = CountToValue(p\Motion\Speed\x#, 0, 8, d\Delta#)
					;	p\Motion\Speed\z# = CountToValue(p\Motion\Speed\z#, 0, 8, d\Delta#)
						p\Motion\Speed\x# = 0
						p\Motion\Speed\z# = 0
			;	SpeedMult = 0
			EndIf
			
			; If the tagger fails to reach the player if he's stopped for a while
			If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) > 20) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) > 20)) And ((Abs(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity))) > 10) And (p1\Motion\Groundspeed# < 0.5) Then
				ReachTimer = ReachTimer + GlobalTimer
			EndIf
			
			; Try jumping
			If ReachTimer > 500 Then
				p\Action2 = ACTION_JUMP
				p\Motion\Speed\y# = 0.7
				Player_ConvertGroundToAir2(p)
				p\Motion\Ground = False
				ReachTimer = 0
			EndIf
				
			; If the player is this far above the tagger, the tagger will jump
			If (-(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) > 30) Then
				p\Action2 = ACTION_JUMP
				p\Motion\Speed\y# = 1.8
				Player_ConvertGroundToAir2(p)
				p\Motion\Ground = False
			EndIf
			
			;If the tagger is lost, spawn back to the player
			If EntityDistance(p\Objects\Entity2, p1\Objects\Entity) > 370 Then 
				
				p\Action2 = ACTION_COMMON
				p\Motion\Speed\x# = 0
				p\Motion\Speed\y# = 0
				p\Motion\Speed\z# = 0
				EntityType(p\Objects\Entity2, 0)
				PositionEntity p\Objects\Entity2, EntityX(p1\Objects\Entity), EntityY(p1\Objects\Entity)+5, EntityZ(p1\Objects\Entity)+1
				PositionEntity p\Objects\Mesh2, EntityX(p1\Objects\Entity), EntityY(p1\Objects\Entity)+5, EntityZ(p1\Objects\Entity)+1
				EntityType(p\Objects\Entity2, COLLISION_PLAYER)
				
			EndIf
			
		Next
		
		; Handle actions
		Player_Handle2(p, d)
		Select p\Action2
			Case ACTION_COMMON
				Player_Action_Common2(p, d)
			Case ACTION_JUMP
				Player_Action_Jump2(p, d)
			Case ACTION_CROUCH
				Player_Action_Crouch2(p, d)
			Case ACTION_SPINDASH
				Player_Action_Spindash2(p, d)
		End Select

		; Animate
		Player_Animate2(p, d)
	End Function
;~IDEal Editor Parameters:
;~C#Blitz3D