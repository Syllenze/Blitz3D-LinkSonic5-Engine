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
		Field MoveEntity1
		Field MoveEntity2
		
		Field JumpTimer
	End Type


	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Objects
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Objects2
		Field Entity2
	;	Field Camera2.tCamera2
		Field Shadow2
		Field Mesh2
		Field Mesh2a
		Field Mesh2b
		
		Field Mesh_Balance
		Field Mesh_Head
		Field Mesh_Arms
		Field Mesh_Torso
		Field Mesh_Legs
		Field Mesh_Shield
		Field Mesh_JumpBall
		Field Mesh_Spindash
		
		Field FollowBone
		
		Field Pointer
		Field Pointera
		Field Pointerb
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
	Global Mesh_TagSonic1
	Global Mesh_TagSonic2

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Creation/Destruction and management ----
	; =========================================================================================================
	; Player_Create
	; =========================================================================================================
Function Player_Create2.tPlayer2(Character=0, ch$="tails")
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
			Case 1
				
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
			;	If (FXEnabled = 1) Then CreateShadowCaster p\Objects\Mesh2
				;ScaleEntity(p\Objects\Mesh, GAME_SCALE#, GAME_SCALE#, GAME_SCALE#)

			;	p\Objects\Mesh_Balance	= FindChild(p\Objects\Mesh2, "Bone01")
			;	p\Objects\Mesh_Head 	= FindChild(p\Objects\Mesh2, "Head")
			;	p\Objects\Mesh_Arms 	= FindChild(p\Objects\Mesh2, "Arms")
			;	p\Objects\Mesh_Torso 	= FindChild(p\Objects\Mesh2, "Torso")
			;	p\Objects\Mesh_Legs 	= FindChild(p\Objects\Mesh2, "Legs")
				
				p\Objects\Pointer = CreatePivot()
				
				p\MoveEntity1 = 0
				p\MoveEntity2 = 0

			;	p\Objects\Mesh_Spindash = CopyEntity(Mesh_Sonic_Spindash, Game\Stage\Root)
			;	p\Objects\Mesh_JumpBall = CopyEntity(Mesh_Sonic_JumpBall, p\Objects\Mesh2)
			;	ScaleEntity(p\Objects\Mesh_Spindash, GAME_SCALE#, GAME_SCALE#, GAME_SCALE#)
			;	HideEntity p\Objects\Mesh_Spindash

			Case 2
				
				Mesh_TagSonic1							= LoadAnimMesh("Characters/" + ch$ + "/Sonic.b3d")
				
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	0,		120)	; Idle
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	121,	150)	; Walking
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	151,	180)	; Running 2
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	181,	196)	; Spinning
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	197,	216)	; Falling
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	217,	235)	; Spring
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	236,	260)	; Land
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	261,	272)	; Spin Kick
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	273,	292)	; Slide
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	293,	300)	; StompPart2
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	301,	320)	; RingDash
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	321,	340)	; FallSpeed
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	341,	370)	; Jogging
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	371,	400)	; Hurt
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	401,	420)	; Dive 1
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	421,	430)	; Dive 2
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	431,	450)	; Ramp
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	451,	480)	; Running 1
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	481,	540)	; Land Hard
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	541,	556)	; Trick Loop
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	557,	626)	; Idle 1
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	627,	696)	; Idle 2
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	697,	720)	; Hop
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	721,	740)	; Skidding
				RecursiveExtractAnimSeq(Mesh_TagSonic1,	741,	860)	; Win Idle
				HideEntity(Mesh_TagSonic1)
				
				p\Objects\Mesh2 			= CopyEntity(Mesh_TagSonic1, Game\Stage\Root)
			;	If (FXEnabled = 1) Then CreateShadowCaster p\Objects\Mesh2
				p\Objects\Pointer = CreatePivot()
				p\Objects\FollowBone 	= FindChild(p\Objects\Mesh2, "Bone")
				p\MoveEntity1 = 1
				p\MoveEntity2 = 0
			Case 3
				
				
				Mesh_TagSonic2							= LoadAnimMesh("Characters/" + ch$ + "/Sonic.b3d")
				
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	0,		120)	; Idle
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	121,	150)	; Walking
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	151,	180)	; Running 2
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	181,	196)	; Spinning
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	197,	216)	; Falling
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	217,	235)	; Spring
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	236,	260)	; Land
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	261,	272)	; Spin Kick
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	273,	292)	; Slide
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	293,	300)	; StompPart2
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	301,	320)	; RingDash
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	321,	340)	; FallSpeed
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	341,	370)	; Jogging
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	371,	400)	; Hurt
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	401,	420)	; Dive 1
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	421,	430)	; Dive 2
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	431,	450)	; Ramp
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	451,	480)	; Running 1
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	481,	540)	; Land Hard
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	541,	556)	; Trick Loop
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	557,	626)	; Idle 1
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	627,	696)	; Idle 2
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	697,	720)	; Hop
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	721,	740)	; Skidding
				RecursiveExtractAnimSeq(Mesh_TagSonic2,	741,	860)	; Win Idle
				HideEntity(Mesh_TagSonic2)
				
				p\Objects\Mesh2 			= CopyEntity(Mesh_TagSonic2, Game\Stage\Root)
			;	If (FXEnabled = 1) Then CreateShadowCaster p\Objects\Mesh2
				p\Objects\Pointer = CreatePivot()
				p\Objects\FollowBone 	= FindChild(p\Objects\Mesh2, "Bone")
				p\MoveEntity1 = 0
				p\MoveEntity2 = 1
				
			;	p\Objects\Mesh2 			= CopyEntity(Mesh_TagSonicb, Game\Stage\Root)
				;ScaleEntity(p\Objects\Mesh, GAME_SCALE#, GAME_SCALE#, GAME_SCALE#)
				
			;	p\Objects\Pointer = CreatePivot()
				
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
	
;	If Mesh_TagSonic <> 0 Then FreeEntity Mesh_TagSonic
;	If Mesh_TagSonic1 <> 0 Then FreeEntity Mesh_TagSonic1
;	If Mesh_TagSonic2 <> 0 Then FreeEntity Mesh_TagSonic2
	
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
	
	If p\Objects\FollowBone <> 0 And p\MoveEntity1 = 1 Then MoveEntity p\Objects\FollowBone,2,0,5
	If p\Objects\FollowBone <> 0 And p\MoveEntity2 = 1 Then MoveEntity p\Objects\FollowBone,-2,0,10
	
		; FOLLOW CODE
	For p1.tPlayer = Each tPlayer
		If (p\Objects\Pointer<>0) Then
			TurnEntity(p\Objects\Pointer,10,180,0) : TFormPoint(10,180,0,p\Objects\Pointer,p1\Objects\Mesh) : p\Animation\Direction# = EntityYaw#(p\Objects\Pointer)
			PositionEntity(p\Objects\Pointer, EntityX(p\Objects\Entity2), EntityY(p\Objects\Entity2), EntityZ(p\Objects\Entity2))
			PointEntity(p\Objects\Pointer, p1\Objects\Entity)
		EndIf
		
;		If (p\Objects\Pointera<>0) Then
;			TurnEntity(p\Objects\Pointera,10,180,0) : TFormPoint(10,180,0,p\Objects\Pointera,p1\Objects\Mesh) : p\Animation\Direction# = EntityYaw#(p\Objects\Pointera)
;			PositionEntity(p\Objects\Pointera, EntityX(p\Objects\Entity2), EntityY(p\Objects\Entity2), EntityZ(p\Objects\Entity2))
;			PointEntity(p\Objects\Pointera, p1\Objects\Entity)
;		EndIf
			
;		If (p\Objects\Pointerb<>0) Then
;			TurnEntity(p\Objects\Pointerb,10,180,0) : TFormPoint(10,180,0,p\Objects\Pointerb,p1\Objects\Mesh) : p\Animation\Direction# = EntityYaw#(p\Objects\Pointerb)
;			PositionEntity(p\Objects\Pointerb, EntityX(p\Objects\Entity2), EntityY(p\Objects\Entity2), EntityZ(p\Objects\Entity2))
;			PointEntity(p\Objects\Pointerb, p1\Objects\Entity)
;		EndIf
			
			
			If (((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) < 7) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) < 7))) Then
			
		;	If (p\Motion\Speed\x# < 0.07) And (p\Motion\Speed\z# < 0.07) Then
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\z# = 0
		;	ElseIf (p\Motion\Speed\x# > 0.07) And (p\Motion\Speed\z# > 0.07) Then
				p\Motion\Speed\x# = p\Motion\Speed\x# * 0.81
				p\Motion\Speed\z# = p\Motion\Speed\z# * 0.81
		;	EndIf
			
		EndIf
				
			; If the tagger is close behind, set a speed
			If (((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) >= 7) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) >= 7))) Then; And ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) < 50) And (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) < 50)) Then
		;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*(p1\Motion\GroundSpeed#*0.05)	;0.06
		;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*(p1\Motion\GroundSpeed#*0.05)
			
				p\Motion\Speed\x# = Sin(p\Animation\Direction#)*(p1\Motion\GroundSpeed#*0.9)	;0.06
				p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*(p1\Motion\GroundSpeed#*0.9)
			EndIf
			
			If (((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) > 11) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) > 11))) Then; And ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) < 50) And (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) < 50)) Then
				
				If (p1\Motion\GroundSpeed# > 0.9) Then
					p\Motion\Speed\x# = Sin(p\Animation\Direction#)*(p1\Motion\GroundSpeed#*1)
					p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*(p1\Motion\GroundSpeed#*1)
				EndIf
			EndIf
		
			If (((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) > 15) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) > 15))) Then; And ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) < 50) And (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) < 50)) Then
			;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)
			;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)
				If (p1\Motion\GroundSpeed# > 0.9) Then
					p\Motion\Speed\x# = Sin(p\Animation\Direction#)*((p1\Motion\GroundSpeed#*1)+0.5)
					p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*((p1\Motion\GroundSpeed#*1)+0.5)
				EndIf
				
			;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*(p1\Motion\GroundSpeed#*1.2)
			;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*(p1\Motion\GroundSpeed#*1.2)
		EndIf
		
		If (((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) > 30) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) > 30))) Then; And ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) < 50) And (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) < 50)) Then
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*3
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*3
		;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.23
		;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.23
		EndIf
			
			; If the tagger falls behind, speed up
	;		If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity)) >= 50) Or (Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) >= 50)) Then; And ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) < 90) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity))) < 90) Then; And (Abs(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) < 30) Then
	;			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.18	;0.05
	;			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.18
	;		EndIf
			
				; If the tagger is falling far behind, don't spaz
	;		If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) >= 90) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity))) >= 90) Then
	;			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.19	;0.05
	;			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*EntityDistance(p\Objects\Entity2, p1\Objects\Entity)*0.19
	;		EndIf
			
			; If he is close, stop the tagger
			;If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) < 10) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity))) < 10) Then
					;	p\Motion\Speed\x# = CountToValue(p\Motion\Speed\x#, 0, 8, d\Delta#)
					;	p\Motion\Speed\z# = CountToValue(p\Motion\Speed\z#, 0, 8, d\Delta#)
			;	p\Motion\Speed\x# = 0
			;	p\Motion\Speed\z# = 0
		;	SpeedMult = 0
			;EndIf
			
					
			; If he is close, stop the tagger
		;			If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) < 10) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity))) < 10) Then
		;			;	p\Motion\Speed\x# = CountToValue(p\Motion\Speed\x#, 0, 8, d\Delta#)
		;			;	p\Motion\Speed\z# = CountToValue(p\Motion\Speed\z#, 0, 8, d\Delta#)
		;				p\Motion\Speed\x# = 0
		;				p\Motion\Speed\z# = 0
			;	SpeedMult = 0
		;	EndIf
			
			; If the tagger fails to reach the player if he's stopped for a while
		If ((Abs(EntityX(p\Objects\Entity2) - EntityX(p1\Objects\Entity))) > 30) Or ((Abs(EntityZ(p\Objects\Entity2) - EntityZ(p1\Objects\Entity)) > 30)) And (p1\Motion\Groundspeed# < 1.5) And (p\Motion\Ground = True) Then; And ((Abs(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity))) > 10) And (p1\Motion\Groundspeed# < 0.5) Then
			p\JumpTimer = p\JumpTimer + GlobalTimer
		Else
			p\JumpTimer = 0
		EndIf
			
			; Try jumping
		If p\JumpTimer > 300 Then
			p\Action2 = ACTION_JUMP
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2
			p\Motion\Speed\y# = 1.3
			Player_ConvertGroundToAir2(p)
			If (p\Motion\Ground = True) And (ChannelPlaying(Channel_Jump)=False) Then Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity2, 1)
			p\Motion\Ground = False
		;	p\JumpTimer = 0
		EndIf
				
			; If the player is this far above the tagger, the tagger will jump
		If (-(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) > 20) And p1\Motion\Ground = False Then
				p\Action2 = ACTION_JUMP
				p\Motion\Speed\y# = 1.4
				Player_ConvertGroundToAir2(p)
				If (p\Motion\Ground = True) And (ChannelPlaying(Channel_Jump)=False) Then Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity2, 1)
				p\Motion\Ground = False
			EndIf
			
			If (-(EntityY(p\Objects\Entity2) - EntityY(p1\Objects\Entity)) > 40) And p1\Motion\Ground = True Then
				p\Action2 = ACTION_JUMP
				p\Motion\Speed\y# = 1.9
				Player_ConvertGroundToAir2(p)
				If (p\Motion\Ground = True) And (ChannelPlaying(Channel_Jump)=False) Then Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity2, 1)
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