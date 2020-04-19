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
;   Changelog:  -(Mark)----------------------------------->                                                    ;
;               25/01/2008 - Original Version                                                                  ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO:                                                                                                      ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Const OBJTYPE_NULL		= 0
	Const OBJTYPE_RING		= 1
	Const OBJTYPE_SPRING	= 2
	Const OBJTYPE_MONITOR	= 3
	Const OBJTYPE_BUMPER	= 4
	Const OBJTYPE_GOAL		= 5
	Const OBJTYPE_GOALINVIS	= 6
	Const OBJTYPE_CAMLOCK	= 7
	Const OBJTYPE_SPRING2	= 8
	Const OBJTYPE_SPRING3	= 9
	Const OBJTYPE_SPRING4	= 10
	Const OBJTYPE_HOMINGNODE = 11
	Const OBJTYPE_BOMBER	= 12
	Const OBJTYPE_HOOP		= 13
	Const OBJTYPE_TRICKHOOP	= 14
	Const OBJTYPE_FLYENEMY	= 15
	Const OBJTYPE_ENEMY		= 16
	Const OBJTYPE_DASHPAD	= 17
	Const OBJTYPE_CHECK		= 18
	Const OBJTYPE_SPEWRING	= 19
	Const OBJTYPE_NPC		= 20
	Const OBJTYPE_DASHPADNEW = 21
	Const OBJTYPE_RINGLOST = 22
	Const OBJTYPE_DASHRAMP = 23
	Const OBJTYPE_AMBSOUND = 24
	Const OBJTYPE_BALLOON = 25
	Const OBJTYPE_PICKUP = 26
	Const OBJTYPE_DESTSPRING = 27
	Const OBJTYPE_HINT	 	= 28
	Const OBJTYPE_HINTINVIS = 29
	Const OBJTYPE_GRAVITY	 = 30
	Const OBJTYPE_MUSIC		 = 31
	Const OBJTYPE_TERMINAL	 = 32
	Const OBJTYPE_PLATFORM	 = 33
	Const OBJTYPE_MISSILE	 = 34
	Const OBJTYPE_PLAYER2    = 35
	

	Const OBJECT_VIEWDISTANCE_MIN#	= 420
	Const OBJECT_VIEWDISTANCE_MAX#	= 800 ;550
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   STRUCTURES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Type tObject
		Field ObjType
		
		Field Position.tVector
		Field Dest.tVector
		Field Rotation.tVector
		Field Speed.tvector
		
		Field IValues[16]
		Field FValues#[16]
		
		Field Action
		
		Field Mode
		Field State
		Field GoalDestination$
		Field Radius#
		Field Ends
		Field Gotten
		Field Timer
		Field pwr#
		Field cam
		Field Speech$
		Field Sound
		Field Animated
		Field RingTime
		Field Power#
		Field TurnTimer
		Field Where
		Field EffectMesh
		Field Loops
		Field Attacks
		
		Field Homing
		Field Stops
		Field WallJump
		Field LaunchSpeed#
		
		Field StartX#
		Field StartY#
		Field StartZ#
		Field UsePos
		
		Field Model$
		Field Pivot
		Field Entity
		Field Light
		Field SndVolume#
		Field Channel
		Field SoundNum
		Field Target
		Field Hint
		
		Field TransTime#
		Field Duration#
		Field TiltX#
		Field TiltY#
		Field TiltZ#
		
		Field Music
		Field HeadFocus
		Field Buttons
		Field cType
		Field cState
		Field Homer
		Field Pointer
		
		; Menu
		Field Menu_Win
		
		Field Menu_B1
		Field Menu_B2
		Field Menu_B3
		Field Menu_B4
		Field Menu_B5
		Field Menu_B6
		Field Menu_B7
		Field Menu_B8
		Field Menu_B9
		Field Menu_B10
		
		Field Stage1$
		Field Stage2$
		Field Stage3$
		Field Stage4$
		Field Stage5$
		Field Stage6$
		Field Stage7$
		Field Stage8$
		Field Stage9$
		Field Stage10$
		
		Field Menu_StageInput
		
		Field Menu_Exit
		
		; Online
		Field Entity1
		Field Number
		
	End Type

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   RINGS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

; =========================================================================================================
; Object_Ring_Parse
; =========================================================================================================
	.RingParse
Function Object_Ring_Parse(Node)
	
	; Create some local variables.
	positionX# = 0
	positionY# = 0
	positionZ# = 0
	
	; Setup position, rotation and scale.
	ScenePosition = xmlNodeFind("position", Node)
	If (ScenePosition<>0) Then
		positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
		positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
		positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
	End If
	
	; Execute the create object function.
	obj.tObject = Object_Ring_Create(positionX#, positionY#, positionZ#)
	
End Function
	
	; =========================================================================================================
	; Object_Ring_Create
	; =========================================================================================================
	; Creates a New Ring Object

	Function Object_Ring_Create.tObject(x#, y#, z#)
	
		o.tObject = New tObject
		
		o\ObjType = OBJTYPE_RING
		
		o\Position = New tVector	
		o\Position\x# = x#
		o\Position\y# = y#
		o\Position\z# = z#
		
		o\Homer = CreatePivot()
		o\Gotten = 0
		
	;	o\EffectMesh = LoadMesh("Objects\Rings\Ring.b3d")
		
		o\Entity = LoadMesh("Objects\Rings\Ring.b3d")
		If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then
			o\IValues[0] = CreateQuad(3, 1.1, 1.5, 0.5)
			EntityTexture(o\IValues[0], Textures_Shadow)
			PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True)

			o\FValues[0] = PickedNX#()
			o\FValues[1] = PickedNY#()
			o\FValues[2] = PickedNZ#()
			
			MoveEntity(o\IValues[0], 0, 0.2, 0)
			ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1))
			EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1))
		End If
		
		EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, OBJECT_VIEWDISTANCE_MAX#)
		ScaleEntity o\Entity, 0.6, 0.6, 0.6
		TranslateEntity o\Entity, x#, y#, z#	
		TranslateEntity o\Homer, x#, y#, z#
		
	;	TranslateEntity o\EffectMesh, x#, y#, z#
	;	ScaleEntity o\EffectMesh, 0.6, 0.6, 0.6
		
	;	HideEntity o\EffectMesh
		
		Return o
		
	End Function
	
	; =========================================================================================================
	; Object_Ring_Update
	; =========================================================================================================
	; Updates the ring object
	
Function Object_Ring_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = p\EnemyY# - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = o\Position\y# - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	o\Position\x# = EntityX(o\Entity)
	o\Position\y# = EntityY(o\Entity)
	o\Position\z# = EntityZ(o\Entity)
	
	If o\State = 0 Then
		RotateEntity o\Entity, 0, Float#(MilliSecs())*0.2, 0
		If (o\IValues[0] <> 0) Then
			RotateEntity o\IValues[0], 0, Float#(MilliSecs())*0.2, 0
			AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
		End If
	Else
		
		If (o\ObjType=OBJTYPE_RING And o\Gotten = 0 And p\HasRingTarget = 0) Then
			
						; !! need code to check if ring is in front or behind Sonic
				;	If o\Entity <> 0 Then
			p\Objects\Ring = o\Entity
			
				;	If (p\Action = ACTION_RINGDASH) Then
				;		p\HasRingTarget = 1
				;	Else
				;		p\HasRingTarget = 0
				;	EndIf
			
			p\FValues[0]=edNearest#
		Else
			p\FValues[0]=100
		End If
		
		
		; The ring will fly upwards
		
	;	MoveEntity o\Entity, 0, 0.5*d\Delta#, 0
	;	RotateEntity o\Entity, 0, Float#(MilliSecs())*1.1, 0
	;	If (o\IValues[0] <> 0) Then
	;		RotateEntity o\IValues[0], 0, Float#(MilliSecs())*0.8, 0
	;		AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
	;	End If
		
		; The ring will move towards Sonic
		
	;	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) > 0.2) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) > 0.2) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) > 0.2)
			
			PointEntity(o\Entity, p\Objects\Entity)
			MoveEntity(o\Entity,0,0,(EntityDistance(o\Entity, p\Objects\Entity)*0.24)*Game\DeltaTime\Delta#)
		;	MoveEntity(o\Entity,0,0,(p\Motion\GroundSpeed#)*Game\DeltaTime\Delta#)
		
	;	EndIf
		
	;	RotateEntity o\Entity, 0, Float#(MilliSecs())*1.1, 0
	;	If (o\IValues[0] <> 0) Then
	;		RotateEntity o\IValues[0], 0, Float#(MilliSecs())*0.8, 0
	;AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
;End If
		
	EndIf
	
	If o\Timer > 400 And o\State = 1 Then
			; Delete the Ring
		p\Objects\Ring = 0
		
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If o\State = 1 Then
		o\Timer = o\Timer + GlobalTimer
		EntityAlpha(o\Entity, 1-(o\Timer*0.003))
	;	HideEntity o\Entity
	;	ShowEntity o\EffectMesh
	Else
	;	HideEntity o\EffectMesh
	;	ShowEntity o\Entity
	EndIf
		
		; Player collided with ring
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 5.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 5.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 5.5) And p\Action <> ACTION_RINGDASH And o\State = 0 Then
		
		p\HasRingTarget = 0
		o\Gotten = 1
		p\Objects\Ring = 0
		
		; Bling!			
		Channel_Ring = playsnd(Sound_Ring, o\Entity, 1)
		
		
		
		; Add to ring counter
		Game\Gameplay\Rings = Game\Gameplay\Rings + 1
		Game\Gameplay\Score = Game\Gameplay\Score + 10	
		p\RingDashTimer = MilliSecs() + 100
		
		o\State = 1
		o\Timer = 0
		
	;	FreeEntity o\Entity
		
	;	If (o\IValues[0]) Then FreeEntity(o\IValues[0])
	;	Delete o\Position
	;	Delete o
		
	;	FreeEntity o\Homer
		
	;	MoveEntity o\Homer, 0, 500, 0
			
		
	EndIf
	
			; Player collided with ring
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 6.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 6.5) And p\Action = ACTION_RINGDASH And o\State = 0 Then
		
			; Add to ring counter
		Game\Gameplay\Rings = Game\Gameplay\Rings + 1
		Game\Gameplay\Score = Game\Gameplay\Score + 10
		
			; Bling!
	;	If (ChannelPlaying(Channel_JumpDash)=False) Then Channel_JumpDash = PlaySound (Sound_JumpDash)
		
		p\HasRingTarget = 0
		o\Gotten = 1
		p\Objects\Ring = 0
		
		StopChannel(Channel_Ring)
		Channel_Ring = PlaySound (Sound_Ring)
		
			; Delete the Ring
		FreeEntity o\Entity
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
		
		
	EndIf	
		
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 15.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 15) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 15) And p\Action = ACTION_RINGDASH And o\State = 0 And p\HasRingTarget = 0 Then
		p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : p\Action = ACTION_RINGDASH : p\HomingTimer = MilliSecs() + 1000 : p\Motion\Speed\y# = 0 : p\HasRingTarget = 1
		
;		p\Action = ACTION_RINGDASH
	;		p\Motion\Ground = False
		;	PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;	p\RingDashTimer = MilliSecs() + 400 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x#
		;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*3.6
		;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*3.6
		;	p\Motion\Speed\y# = 0.2
			
			; Add to ring counter
		;	Game\Gameplay\Rings = Game\Gameplay\Rings + 1
		;	Game\Gameplay\Score = Game\Gameplay\Score + 10
			
			; Bling!
		;	If (ChannelPlaying(Channel_JumpDash)=False) Then Channel_JumpDash = PlaySound (Sound_JumpDash)
			
		;	StopChannel(Channel_Ring)
		;	Channel_Ring = PlaySound (Sound_Ring)
			
			; Delete the Ring
		;	FreeEntity o\Entity
		;	If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		;	Delete o\Position
		;	Delete o
		;	Return
			
;			If (p\RingDashTimer < MilliSecs()) Then
;				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
;				p\Action = ACTION_FALL
;			p\Objects\Ring = o\Entity
;			p\FValues[0]=edNearest#
;		Else
;			p\FValues[0]=100
			
			
	EndIf
			
End Function

	; =========================================================================================================
	; Object_RingLost_Create
	; =========================================================================================================
	; Creates a New Ring Object

Function Object_RingLost_Create.tObject(x#, y#, z#)
	
	o.tObject = New tObject
	
	o\Position = New tVector
	
	o\Entity = LoadMesh("Objects\Rings\Ring_LOD.b3d")
	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	o\RingTime = MilliSecs() + 5000
	
	o\ObjType = OBJTYPE_RINGLOST
	
	If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then
		o\IValues[0] = CreateQuad(3, 1.1, 1.5, 0.5)
		EntityTexture(o\IValues[0], Textures_Shadow)
		PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True)
		
		o\FValues[0] = PickedNX#()
		o\FValues[1] = PickedNY#()
		o\FValues[2] = PickedNZ#()
		
		MoveEntity(o\IValues[0], 0, 0.2, 0)
		ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1))
		EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1))
	End If
	
	ScaleEntity o\Entity, 0.6, 0.6, 0.6
	TranslateEntity o\Entity, x#, y#, z#
	
	EntityType(o\Entity, COLLISION_SPEWRING)
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_RingLost_Update
	; =========================================================================================================
	; Updates the ring object

Function Object_RingLost_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	MoveEntity(o\Entity,0,-0.8,0)
	
	o\Position\x# = EntityX(o\Entity)
	o\Position\y# = EntityY(o\Entity)
	o\Position\z# = EntityZ(o\Entity)
	
	RotateEntity o\Entity, 0, Float#(MilliSecs())*0.2, 0
	If (o\IValues[0] <> 0) Then
		RotateEntity o\IValues[0], 0, Float#(MilliSecs())*0.2, 0
		AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
	End If
	
	p\Objects\Ring = o\Entity
	
		; Player collided with ring
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 3.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 3.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 3.5) Then
		
			; Add to ring counter
;		Game\Gameplay\RingDash = MilliSecs() + 200
;		Game\Gameplay\Rings = Game\Gameplay\Rings + 1
		
			; Bling!			
;		StopChannel(Channel_Ring)
;		Health% = Health% + 15
;		Channel_Ring=PlaySnd(Sound_Ring, o\Entity)
;		ringd = CreateTemplate()
;		SetTemplateEmitterBlend(ringd, 1)
;		SetTemplateInterval(ringd, 1)
;		SetTemplateEmitterLifeTime(ringd, 3)
;		SetTemplateParticleLifeTime(ringd, 75, 85)
;		SetTemplateTexture(ringd, "Textures\RingP.png", 2, 1)
;		SetTemplateOffset(ringd, 0, 0, 0, 0, 0, 0)
;		SetTemplateVelocity(ringd, -.04, .04, .1, .2, -.04, .04)
;		SetTemplateAlphaVel(ringd, True)
;		SetTemplateSize(ringd, 3, 3, .9, .9)
	;SetTemplateSizeVel(home, 1, 1)
;		SetTemplateMaxParticles(ringd,3)
;		SetEmitter(p\Objects\Mesh,ringd)
		
			; Delete the Ring
;		FreeEntity o\Entity : FreeShadowCaster o\Entity
;		FreeEmitter ringd
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If (o\RingTime < MilliSecs()) Then
		
			; Delete the Ring
;		FreeEntity o\Entity : FreeShadowCaster o\Entity
;		FreeEmitter ringd
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
	EndIf
	
End Function



	; =========================================================================================================
	; Object_SpewRing_Create
	; =========================================================================================================
	; Creates a New SpewRing Object

Function Object_SpewRing_Create.tObject(x#, y#, z#, direction#=-1)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_SPEWRING
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Speed	= New tVector
	o\Speed\x# = Rnd#(-0.1,0.1)
	o\Speed\y# = Rnd#(0.8,1.5)
	o\Speed\z# = Rnd#(-0.1,0.1)
	
	o\Pivot = CreatePivot()
	PositionEntity(o\Pivot, x#, y#, z#)
		;For p.tPlayer = Each tPlayer
	RotateEntity(o\Pivot, 0, Rnd#(0.0,359.9), 0)
		;Next
	
	o\Speed	= New tVector
	If direction#=-1 Then
		RotateEntity(o\Pivot, 0, Rnd#(0.0,359.9), 0)
		o\Speed\x# = Rnd#(-0.1,0.1)
		o\Speed\y# = Rnd#(0.8,1.5)
		o\Speed\z# = Rnd#(-0.1,0.1)
	Else
		RotateEntity(o\Pivot, 0, direction#, 0)
		o\Speed\x# = 0.0
		o\Speed\y# = 1.0
		o\Speed\z# = 0.1
	End If
	
	o\Entity = LoadMesh("Objects\Rings\Ring_LOD.b3d", o\Pivot)
	If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then
		o\IValues[0] = CreateQuad(3, 1.1, 1.5, 0.5)
		EntityTexture(o\IValues[0], Textures_Shadow)
		PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True)
		
		o\FValues[0] = PickedNX#()
		o\FValues[1] = PickedNY#()
		o\FValues[2] = PickedNZ#()
		
		MoveEntity(o\IValues[0], 0, 0.2, 0)
		ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1))
		EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1))
	End If
	
	EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, OBJECT_VIEWDISTANCE_MAX#)
	ScaleEntity o\Entity, 2, 2, 2
	EntityType(o\Pivot, COLLISION_SPEWRING)
	EntityRadius(o\Pivot, 2.0)
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_SpewRing_Update
	; =========================================================================================================
	; Updates the ring object

Function Object_SpewRing_Update(o.tObject, p.tPlayer, d.tDeltaTime)
;	For c.tCamera = Each tCamera
;		UpdateLOD(o\LOD, c\Entity)
;	Next
	
	o\Timer = o\Timer+(d\TimeCurrentFrame-d\TimePreviousFrame)
	
	o\Position\x# = EntityX#(o\Pivot)
	o\Position\y# = EntityY#(o\Pivot, True)
	o\Position\z# = EntityZ#(o\Pivot)
	
	If ( (o\Position\y#-2)<EntityY#(o\IValues[0], True)) Then
		If (-o\Speed\y#>0.2) Then o\Speed\y# = -(o\Speed\y#+0.2) : PlayRandomRingBounceSound() : Else o\Speed\y#=0
		If (o\Speed\z#>0.0) Then o\Speed\z#=(o\Speed\z#-0.01)
	End If
	
	If ( (o\Position\y#-2)>EntityY#(o\IValues[0])) Then o\Speed\y# = (o\Speed\y#-0.06*d\Delta)
	MoveEntity(o\Pivot, o\Speed\x#*d\Delta, o\Speed\y#*d\Delta, o\Speed\z*d\Delta)
	
	; Rotate the ring, and the shadow if there exists one
	RotateEntity o\Entity, 0, Float#(MilliSecs())*0.2, 0
	If (o\IValues[0] <> 0) Then
		If (LinePick(o\Position\x#, o\Position\y#-2, o\Position\z#, 0, -OMEGA#, 0)<>0) Then
			RotateEntity o\IValues[0], 0, EntityYaw#(o\Entity), 0
			AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
			PositionEntity o\IValues[0], o\Position\x#, PickedY#()+.2, o\Position\z#, True
		Else
			RotateEntity o\IValues[0], 0, EntityYaw#(o\Entity), 0
			AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
			PositionEntity o\IValues[0], o\Position\x#, EntityY#(o\IValues[0]), o\Position\z#, True
		End If
	End If
	
	; Player collided with ring
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 3.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 3.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 3.5) Then
		
		; Add to ring counter
		Game\Gameplay\Rings = Game\Gameplay\Rings + 1					
		
		; Bling!
	;	RingPan=-RingPan
	;	SoundPan(Sounds[SOUND_RING]\Sound, RingPan)
		Channel_Ring = playsnd(Sound_Ring, o\Entity, 1)
		
		; Delete the Ring
		p\Objects\Ring = 0
		;FreeEntity o\LOD\Pivot
		;Delete o\LOD
		FreeEntity o\Entity
		FreeEntity o\Pivot
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If o\Timer > 8000 Then
		; Delete the Ring
		p\Objects\Ring = 0
	;	FreeEntity o\LOD\Pivot
	;	Delete o\LOD
		FreeEntity o\Entity
		;FreeEntity o\TestEntity
		FreeEntity o\Pivot
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
	End If
	
End Function

Function RingSpew(p.tPlayer, count, spewtype=0)
	; Position it
	x#=EntityX#(p\Objects\Entity)
	y#=EntityY#(p\Objects\Entity)
	z#=EntityZ#(p\Objects\Entity)
	
	; Play RingLoss sound
	;SoundPlay(SOUND_RINGLOSS)
	
	; Drop no more than 30 rings
	If count > 30 Then count = 30
	
	Select spewtype
		; Circle
		Case 0
			For i=1 To count
				d# = 360/count*i
				obj.tObject = Object_SpewRing_Create(x#, y#+10, z#, d#)
			Next
			
		; Random
		Case 1
			For i=1 To count
				obj.tObject = Object_SpewRing_Create(x#+Rnd#(-10.0,10.0), y#, z#+Rnd#(-10.0,10.0))
			Next
	End Select
End Function
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   SPRINGS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
	
	; =========================================================================================================
	; Object_Spring_Create
	; =========================================================================================================
	; Creates a New Spring Object	
	
Function Object_Spring_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, power#=3)
	
		o.tObject = New tObject
		
		o\ObjType = OBJTYPE_SPRING
		
		o\Position = New tVector	
		o\Position\x# = x#
		o\Position\y# = y#
		o\Position\z# = z#
		
		If power# >= 4 Then o\Entity = LoadMesh("Objects\Springs2\Spring.b3d")
		
		If power# >= 2.2 And power# < 4 Then o\Entity = LoadMesh("Objects\Springs\Spring.b3d")
		
		If power# > 1.8 And power# < 2.2 Then o\Entity = LoadMesh("Objects\Springs3\Spring.b3d")
		
		If power# <= 1.8 Then o\Entity = LoadMesh("Objects\Springs4\Spring.b3d")
		
		o\Power# = power#

		o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
		EntityBlend o\IValues[0], 3
		EntityFX o\IValues[0], 1+16
		EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
		EntityAlpha o\IValues[0], 0.0
		TranslateEntity o\IValues[0], 0, 1, 0
		o\FValues[0] = 0

		ScaleEntity o\Entity, 1.5, 1.5, 1.5
		TranslateEntity o\Entity, x#, y#, z#
		TurnEntity o\Entity, rx#, ry#, rz#

		
		Return o
		
	End Function
	

	; =========================================================================================================
	; Object_Spring_Update
	; =========================================================================================================
	; Updates the spring object	
	
	Function Object_Spring_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
		ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
		ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
		ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
		vx# = o\Position\x# - EntityX( p\Objects\Entity )
		vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
		vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;		p\EnemyX# = o\Position\x#
		;		p\EnemyY# = o\Position\y#
		;		p\EnemyZ# = o\Position\z#
				p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 2000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : ;CreateLS5Trail()
			;	e1=CreatePivot(p\Objects\PPivot)
				;CreateLS5Trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
				
		EndIf
			If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
				If (p\HomingTimer < MilliSecs()) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
					p\Action = ACTION_FALL
				EndIf
		;		AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
		;		AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
				
				If (p\EnemyPicked = 0) Then
					
					p\EnemyPicked = 1
				p\Homing = 1
				p\Objects\Home = 1
				
				p\Objects\Enemy = o\Entity
				
				p\rx#=EntityX#(p\Objects\Enemy)
				p\ry#=EntityY#(p\Objects\Enemy)
				p\rz#=EntityZ#(p\Objects\Enemy)
				
				p\dx#=p\rx#-EntityX#(p\Objects\Entity)
				p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
				
				diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
				
				; Align the LightDevice
				PointEntity p\Objects\LightDevice, p\Objects\Enemy
				p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
				
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
			EndIf
		EndIf
		
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40
		
			If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 5.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 5.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 5.5) And o\State = 0 Then

				o\State = 1
				o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
				Player_Align(p)
				TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
				p\Motion\Ground = False
				p\Motion\Speed\x = TFormedX()*o\Power#
				p\Motion\Speed\y = TFormedY()*o\Power#
				p\Motion\Speed\z = TFormedZ()*o\Power#
				p\Action = ACTION_SPRING
				
				; Transform the position vector to World space
				TFormVector 0, 1, 0, o\Entity, 0					
				PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
				
				; Sproing!
				Channel_Spring = playsnd(Sound_Spring, o\Entity, 1)
				
				p\Objects\Enemy = o\Entity
				
				; Apply motion blur
			;	PostEffect_Create_MotionBlur(0.85)
			
			EndIf
		
		If o\State = 1 Then

			o\FValues[0] = o\FValues[0] + 0.05*d\Delta
			ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
			TurnEntity o\IValues[0], 0, 5*d\Delta, 0
			EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
			
			ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
			
			If o\FValues[0] >= 1.0 Then
				ScaleEntity o\IValues[0], 1, 1, 1
				o\FValues[0] = 0
				o\State = 0
				ScaleEntity o\Entity, 1.5, 1.5, 1.5
			EndIf
								
		EndIf
		
		
End Function

	; =========================================================================================================
	; Object_DestSpring_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_DestSpring_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, dx#, dy#, dz#, stops=0, speed#=3, walljump=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_DESTSPRING
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Dest = New tVector	
	o\Dest\x# = dx#
	o\Dest\y# = dy#
	o\Dest\z# = dz#
	
	o\Stops = stops
	o\LaunchSpeed# = speed#
	o\WallJump = walljump
	
	o\Entity = LoadMesh("Objects\Springs\Spring.b3d")
	o\Target = CreatePivot()
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, 1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TranslateEntity o\Target, dx#, dy#, dz#
	;TurnEntity o\Entity, 0, 90, 0
	;TFormPoint(0,0,90,o\Entity,o\Target)
	;PointEntity o\Entity, o\Target
	;AlignToVector(o\Entity, dx#, dy#, dz#, 2)
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_DestSpring_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_DestSpring_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	If o\WallJump = 1 Then HideEntity(o\Entity)
	
	PointEntity(o\Entity, o\Target)
	TFormPoint(90,0,0, o\Entity, o\Target)
	TurnEntity(o\Entity,90,0,0)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	If o\WallJump = 0 Then
	
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;		p\EnemyX# = o\Position\x#
		;		p\EnemyY# = o\Position\y#
		;		p\EnemyZ# = o\Position\z#
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 5000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : ;CreateLS5Trail()
			;	e1=CreatePivot(p\Objects\PPivot)
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	
;	For c.tCamera = Each tCamera
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;		AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
		;		AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
		
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			PointEntity p\Objects\LightDevice, o\Entity
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
	EndIf
;Next
EndIf
	
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40

If o\Stops = 0 Then o\Radius = 9.5 : Else o\Radius = 6.5

If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius#) And o\State = 0 Then
		
		o\State = 1
		o\FValues[0] = 0.0
		
				; Transform the Speed vector to the Player space
	;	Player_Align(p)
	;	TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
		p\Motion\Ground = False
	;	p\Motion\Speed\x = TFormedX()*3
	;	p\Motion\Speed\y = TFormedY()*3
	;	p\Motion\Speed\z = TFormedZ()*3
		p\Action = ACTION_DESTSPRING
		AlignToVector(p\Objects\Mesh, p\rx#, p\ry#, p\rz#, 2)
		
	;	p\HomingTimer = MilliSecs() + 50000
		
		p\EnemyPicked = 1
		p\Homing = 1
		p\Objects\Home = 1
		
		p\Objects\Enemy = o\Target
		
		PointEntity p\Objects\LightDevice, o\Target
		diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
		
		p\StopsAtDest = o\Stops
		
		p\rx#=EntityX#(p\Objects\Enemy)
		p\ry#=EntityY#(p\Objects\Enemy)
		p\rz#=EntityZ#(p\Objects\Enemy)
		
		p\dx#=p\rx#-EntityX#(p\Objects\Entity)
		p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
		
	;	p\DestX# = o\Dest\x#
	;	p\DestY# = o\Dest\y#
	;	p\DestZ# = o\Dest\z#
		
		p\DestX# = EntityX#(o\Target)
		p\DestY# = EntityY#(o\Target)
		p\DestZ# = EntityZ#(o\Target)
		
		p\LaunchSpeed = o\LaunchSpeed#
		
		
				; Transform the position vector to World space
		TFormVector 0, 1, 0, o\Entity, 0					
		PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
		
				; Sproing!
		If (o\WallJump = 0) Then Channel_Spring = playsnd(Sound_Spring, o\Entity, 1) : Else Channel_Spring = playsnd(Sound_Jump, o\Entity, 1)
		
				; Apply motion blur
	;	PostEffect_Create_MotionBlur(0.85)
		
	EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_Spring2_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Spring2_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_SPRING2
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadMesh("Objects\Springs2\Spring.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, 1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_Spring2_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Spring2_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	;SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			;	p\EnemyX# = o\Position\x#
			;	p\EnemyY# = o\Position\y#
			;	p\EnemyZ# = o\Position\z#
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 2000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : ;CreateLS5Trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
		
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 5.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 5.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 5.5) And o\State = 0 Then
		
		o\State = 1
		o\FValues[0] = 0.0
		
				; Transform the Speed vector to the Player space
		AlignToVector(p\Objects\Entity, p\Animation\Align\x#, p\Animation\Align\y#, p\Animation\Align\z#, 2)
		TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
		p\Action = ACTION_SPRING
		p\Motion\Ground = False
		RotateEntity(p\Objects\Mesh,x#,y#,z#)
		p\Motion\Speed\x = TFormedX()*4.2
		p\Motion\Speed\y = TFormedY()*4.2
		p\Motion\Speed\z = TFormedZ()*4.2
		
				; Transform the position vector to World space
		TFormVector 0, 1, 0, o\Entity, 0					
		PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
		
				; Sproing!
		Channel_Spring = playsnd(Sound_Spring, o\Entity, 1)
		p\Action = ACTION_SPRING
				; Apply motion blur
				;PostEffect_Create_MotionBlur(0.85)
		
	EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_Spring3_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Spring3_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_SPRING3
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadMesh("Objects\Springs3\Spring.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, 1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_Spring3_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Spring3_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;		p\EnemyX# = o\Position\x#
		;		p\EnemyY# = o\Position\y#
		;		p\EnemyZ# = o\Position\z#
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 2000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : ;CreateLS5Trail()
			;	e1=CreatePivot(p\Objects\PPivot)
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
	EndIf
	
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 5.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 5.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 5.5) And o\State = 0 Then
			
			o\State = 1
			o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
			Player_Align(p)
			TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
			p\Motion\Ground = False
			p\Motion\Speed\x = TFormedX()*2.2
			p\Motion\Speed\y = TFormedY()*2.2
			p\Motion\Speed\z = TFormedZ()*2.2
			p\Action = ACTION_SPRING
			
				; Transform the position vector to World space
			TFormVector 0, 1, 0, o\Entity, 0					
			PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
			
				; Sproing!
			Channel_Spring = playsnd(Sound_Spring, o\Entity, 1)
			
				; Apply motion blur
		;	PostEffect_Create_MotionBlur(0.85)
			
		EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_Spring4_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Spring4_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_SPRING4
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadMesh("Objects\Springs4\Spring.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, 1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_Spring4_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Spring4_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;		p\EnemyX# = o\Position\x#
		;		p\EnemyY# = o\Position\y#
		;		p\EnemyZ# = o\Position\z#
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 2000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : ;CreateLS5Trail()
			;	e1=CreatePivot(p\Objects\PPivot)
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
	EndIf
	
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 5.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 5.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 5.5) And o\State = 0 Then
			
			o\State = 1
			o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
			Player_Align(p)
			TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
			p\Motion\Ground = False
			p\Motion\Speed\x = TFormedX()*1.8
			p\Motion\Speed\y = TFormedY()*1.8
			p\Motion\Speed\z = TFormedZ()*1.8
			p\Action = ACTION_SPRING
			
				; Transform the position vector to World space
			TFormVector 0, 1, 0, o\Entity, 0					
			PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
			
				; Sproing!
			Channel_Spring = playsnd(Sound_Spring, o\Entity, 1)
			
				; Apply motion blur
		;	PostEffect_Create_MotionBlur(0.85)
			
		EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_HomingNode_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_HomingNode_Create.tObject(x#, y#, z#, radius#)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_HOMINGNODE
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Radius# = radius#
	
	o\Entity = CreatePivot()
	
;	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
;	EntityBlend o\IValues[0], 3
;	EntityFX o\IValues[0], 1+16
;	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
;	EntityAlpha o\IValues[0], 0.0
;	TranslateEntity o\IValues[0], 0, 1, 0
;	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_HomingNode_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_HomingNode_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = p\EnemyY# - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius# And p\Action = ACTION_JUMPDASH) Then ; And EntityVisible(p\Objects\Entity, o\Entity)=True
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;		p\EnemyX# = o\Position\x#
		;		p\EnemyY# = o\Position\y#
		;		p\EnemyZ# = o\Position\z#
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 2000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : ;CreateLS5Trail()
			;	e1=CreatePivot(p\Objects\PPivot)
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius# + 5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius# + 5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius# + 5 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
		
	;	MoveEntity(p\Objects\Entity,0,1.4*Game\DeltaTime\Delta#,0)
		
	;	p\Motion\Speed\x# = 0
	;	p\Motion\Speed\y# = 0
	;	p\Motion\Speed\z# = 0
		
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
	EndIf
	
		
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40
		
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 2.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 2.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 2.5) And p\Action = ACTION_HOMING Then
		
	;	p\Animation\Direction# = ATan2(dz#, dx#)+90
		
			o\State = 1
			o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
			Player_Align(p)
			TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
			p\Motion\Ground = False
			p\Action = ACTION_FALL
			p\Motion\Speed\x# = 0
			p\Motion\Speed\y# = 0
			p\Motion\Speed\z# = 0
			
				; Transform the position vector to World space
			TFormVector 0, 1, 0, o\Entity, 0					
			PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
			
		EndIf
	
	
End Function
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   MONITORS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
	
	; =========================================================================================================
	; Object_Monitor_Create
	; =========================================================================================================
	; Creates a New Monitor Object	
	
	Function Object_Monitor_Create.tObject(x#, y#, z#)
	
		o.tObject = New tObject
		
		o\ObjType = OBJTYPE_MONITOR
		
		o\Position = New tVector	
		o\Position\x# = x#
		o\Position\y# = y#
		o\Position\z# = z#
		
		o\Entity = LoadMesh("Objects\Monitor\Monitor.b3d")
		;EntityType o\Entity, 2
		ScaleEntity o\Entity, 1.1, 1.1, 1.1
		TranslateEntity o\Entity, x#, y#, z#	
		
		Return o
		
	End Function
	

	; =========================================================================================================
	; Object_Monitor_Update
	; =========================================================================================================
	; Updates the Monitor object	
	
Function Object_Monitor_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True And o\State = 0) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		;		p\EnemyX# = o\Position\x#
		;		p\EnemyY# = o\Position\y#
		;		p\EnemyZ# = o\Position\z#
		;PositionEntity HomingTarget,o\Position\x#,o\Position\y#,o\Position\z#
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 2000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x# : ;CreateLS5Trail()
			;	e1=CreatePivot(p\Objects\PPivot)
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
		;	PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
		EndIf
			
	;---------------------
		
		
		If o\State = 1 Then
			o\Timer = o\Timer + GlobalTimer
			EntityAlpha(o\Entity, 1-(o\Timer*0.006))
			ScaleEntity o\Entity, 1.1+(o\Timer*0.006), 1.1+(o\Timer*0.006), 1.1+(o\Timer*0.006)
		EndIf
		
		If o\Timer > 200 And o\State = 1 Then
			
		;	If (o\IValues[0]) Then FreeEntity(o\IValues[0])
			FreeEntity o\Entity
			Delete o\Position
			Delete o
			Return
		EndIf
		
		If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False) And (p\Action = ACTION_HOMING) And o\State = 0) Then
			
		;	p\Animation\Direction# = ATan2(dz#, dx#)+90
			
			DestroyObject(o, p, 1, 0)
			
			Channel_TV = PlaySnd(Sound_TV, o\Entity, 1)
			
		;	FreeEntity o\Entity
			
			o\State = 1
			o\Timer = 0
			
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
			
		;	Delete o\Position
		;	Delete o
		;	Return
		EndIf
		
		If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False) And o\State = 0) Then
				
			DestroyObject(o, p, 1, 0)
			
			Channel_TV = PlaySnd(Sound_TV, o\Entity, 1)
			
			;	FreeEntity o\Entity
				
				o\State = 1
				o\Timer = 0
				
			;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
			;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
				
							; Add to ring counter
				Game\Gameplay\Rings = Game\Gameplay\Rings + 10
				Game\Gameplay\Score = Game\Gameplay\Score + 10	
				
			;	Delete o\Position
			;	Delete o
			;	Return
			EndIf
			
			If (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5 And (p\Attacking = 1) And (p\Motion\Ground = True) And o\State = 0) Then
				
				DestroyObject(o, p, 2, 0)
				
				Channel_TV = PlaySnd(Sound_TV, o\Entity, 1)
				
			;	FreeEntity o\Entity
				
				o\State = 1
				o\Timer = 0
				
							; Add to ring counter
				Game\Gameplay\Rings = Game\Gameplay\Rings + 10
				Game\Gameplay\Score = Game\Gameplay\Score + 10	
				
			;	Delete o\Position
			;	Delete o
			;	Return
			EndIf
	;---------------------
		
	End Function	
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   BUMPERS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; =========================================================================================================
	; Object_Bumper_Create
	; =========================================================================================================
	; Creates a New Bumper Object

	Function Object_Bumper_Create.tObject(x#, y#, z#)
	
		o.tObject = New tObject
		
		o\ObjType = OBJTYPE_BUMPER
		
		o\Position = New tVector	
		o\Position\x# = x#
		o\Position\y# = y#
		o\Position\z# = z#
		
		o\Entity = LoadMesh("Objects\Bumper\Bumper.b3d")
		ScaleEntity o\Entity, 0.2, 0.2, 0.2
		TranslateEntity o\Entity, x#, y#, z#	
		
		Return o
		
	End Function
	
	; =========================================================================================================
	; Object_Bumper_Update
	; =========================================================================================================
	; Updates the Bumperobject
	
	Function Object_Bumper_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
		TurnEntity o\Entity, 0, 1.2*d\Delta, 0
		
		; Player collided with bumper
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 3.7) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 3.7) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 3.7) And ((o\State = 0) Or (o\FValues[0] > 100)) Then
		
			o\State = 1
			o\FValues[0] = 40.0
		
			Game\Gameplay\Score = Game\Gameplay\Score + 10						

			; Bump!
			Channel_Bumper = playsnd(Sound_Bumper, o\Entity, 1)
			
			x# = o\Position\x# - EntityX(p\Objects\Entity)
			y# = o\Position\y# - EntityY(p\Objects\Entity)
			z# = o\Position\z# - EntityZ(p\Objects\Entity)						
			
			l# = Sqr(x^2 + y^2 + z^2)*1.5
						
			TFormVector x/l, y/l, z/l, 0, p\Objects\Entity
			p\Action = ACTION_COMMON
			p\Motion\Ground = False
			p\Motion\Speed\x = p\Motion\Speed\x-TFormedX()*3
			p\Motion\Speed\y = p\Motion\Speed\y-TFormedY()*3
			p\Motion\Speed\z = p\Motion\Speed\z-TFormedZ()*3
			
			; Apply motion blur
		;	PostEffect_Create_MotionBlur(0.85)			
		
		EndIf
		
		If o\State = 1 Then
			o\FValues[0] = o\FValues[0] + 18*d\Delta

			; Wooble effect
			;ScaleEntity o\Entity, 0.2-0.2*Cos(o\FValues[0])/(o\FValues[0]*0.01), 0.2-0.2*Sin(o\FValues[0])/(o\FValues[0]*0.01), 0.2-0.2*Sin(o\FValues[0])/(o\FValues[0]*0.01)
			ScaleEntity o\Entity, 0.2-0.1*Cos(o\FValues[0])/(o\FValues[0]*0.01), 0.2-0.1*Cos(o\FValues[0])/(o\FValues[0]*0.01), 0.2-0.1*Cos(o\FValues[0])/(o\FValues[0]*0.01)


			If o\FValues[0] >= 4500 Then
				o\State = 0
				o\FValues[0] = 0.0
			EndIf

		EndIf
		
	End Function
	
	; =========================================================================================================
	; Object_Goal_Create
	; =========================================================================================================
	; Creates a New Ring Object

	; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/ 
	; GOAL 
	; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/ 

	; Creates a New Goal Object 

Function Object_Goal_Create.tObject(x#, y#, z#, goaldestination$, radius#=11, ends, sx#, sy#, sz#) 
	Select Game\State 
	Case GAME_STATE_END : Game_Startup_Update() 
	End Select 

	o.tObject = New tObject 

	o\ObjType = OBJTYPE_GOAL 

	o\Position = New tVector 
	o\Position\x# = x# 
	o\Position\y# = y# 
	o\Position\z# = z# 
	o\GoalDestination$ = goaldestination$
	o\Ends = ends
	o\Radius#	= radius#

	o\Entity = LoadMesh("Objects\Goal\Goal.b3d") 
	;o\Light = CreateLight(2)
	
	;LightColor o\Light, 215, 190, 0
	;LightRange o\Light, 100

;	If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then 
;	o\IValues[0] = CreateQuad(9, 3.3, 4.5, 4.5) 
;	EntityTexture(o\IValues[0], Textures_Shadow) 
;	PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True) 

;	o\FValues[0] = PickedNX#() 
;	o\FValues[1] = PickedNY#() 
;	o\FValues[2] = PickedNZ#() 

;	MoveEntity(o\IValues[0], 0, 4.0, 0) 
;	ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1)) 
;	EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1)) 
;	End If 

	EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, OBJECT_VIEWDISTANCE_MAX#) 
	ScaleEntity o\Entity, 3.0, 3.0, 3.0 
	TranslateEntity o\Entity, x#, y#, z# 
	;TranslateEntity o\Light, x#, y#, z#
	
	Return o 

	End Function
	
	; =========================================================================================================
	; Object_Goal_Update
	; =========================================================================================================
	; Updates the Goal object 

	Function Object_Goal_Update(o.tObject, p.tPlayer, d.tDeltaTime)
		
		RotateEntity o\Entity, 0, Float#(MilliSecs())*0.2, 0
		
		If Online = 1 Then EntityAlpha o\Entity, 0.3
		
		If Online = 0 Then
		
		If (Not (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius#)) And (ChannelPlaying(Channel_GoalIdle)=False) Then
	Channel_GoalIdle = playsnd(Sound_GoalIdle, o\Entity, 1)
EndIf



;	If (o\IValues[0] <> 0) Then 
;	RotateEntity o\IValues[0], 0, Float#(MilliSecs())*0.2, 0 
;	AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2) 

	;End If 

	; GOAL DELETES
If o\State = 1 Then
	o\Timer = o\Timer + GlobalTimer
	EntityAlpha(o\Entity, 1-(o\Timer*0.002))
	ScaleEntity o\Entity, 1.1-(o\Timer*0.002), 1.1+(o\Timer*0.002), 1.1-(o\Timer*0.002)
EndIf

If o\Timer > 600 And o\State = 1 Then
	
FreeEntity o\Entity 
Delete o\Position
Delete o
Return

EndIf

	; ------------

	; Player collided with Goal 
	
If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 25.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 25) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 25.5) And o\State = 0 Then
		
		Channel_Goal = playsnd(Sound_Goal, o\Entity, 1)
		Channel_End = PlaySound (Sound_End)
		
		Game\EndTimer = MilliSecs() + 7000
		
		If (o\StartX# <> 0) Then
			Game\Stage\StartPos = 1
			Game\Stage\StartPosDel = 0
			Game\Stage\StartX# = o\StartX#
			Game\Stage\StartY# = o\StartY#
			Game\Stage\StartZ# = o\StartZ#
		ElseIf (o\StartX# = 0) Then
			Game\Stage\StartPos = 0
			Game\Stage\StartPosDel = 1
			
		;	d\IdealInterval		= 1/(1000/Float(30))
			
			Game\Stage\Ends = o\Ends
			Game\Stage\GoalDestination = o\GoalDestination
			
			
			StopChannel(Channel_RingIdle)
			
			StopChannel Game\MusicChn : Game\Stage\Properties\Music = LoadSound ("Sounds\Null.wav") : Game\Stage\Properties\StartMusic = LoadSound ("Sounds\Null.wav")
			
			p\Motion\Speed\X# = 0
			p\Motion\Speed\Z# = 0
			
;	Game\Stage\StartX# = 0
;	Game\Stage\StartY# = 0
;	Game\Stage\StartZ# = 0
			
			Game\Gameplay\TimeResult = Game\Gameplay\Time
			Goal = 1
			
			o\Timer = 0
			o\State = 1
			
			
		;	GoalEnd(o,p,d)
			
			
	;Game\State = GAME_STATE_END
	;Delete_Player_and_Stage()
;	Delay 7000
	
			
	;		MenuSStage$=Game\List\Stagehub
		EndIf
	EndIf

EndIf

If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 120.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 115.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 120.5) And o\State = 0 Then
	
		; ALIGN HEAD ------------
	If (p\Objects\Mesh_Head <> 0) Then
		If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) And (p\Flags\HeadTrackEnabled = 1) Then
			
			p\HeadTrackTimer# = MilliSecs() + 300
			
			PointEntity(p\Objects\Mesh_HeadPoint,o\Entity)
			TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
			TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
		EndIf
	EndIf
	; -------------------------
	
EndIf

	End Function
	
	; =========================================================================================================
	; Object_GoalInvis_Create
	; =========================================================================================================

	; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/ 
	; GOAL 
	; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/ 

	; Creates a New GoalInvis Object 

Function Object_GoalInvis_Create.tObject(x#, y#, z#, goaldestination$, radius#, ends, sx#, sy#, sz#, usepos) 
	Select Game\State 
	Case GAME_STATE_END : Game_Startup_Update() 
	End Select 

	o.tObject = New tObject 

	o\ObjType = OBJTYPE_GOALINVIS 

	o\Position = New tVector 
	o\Position\x# = x# 
	o\Position\y# = y# 
	o\Position\z# = z# 
	o\GoalDestination$ = goaldestination$
	o\Radius#	= radius#
	o\Ends = ends
	
	o\UsePos = usepos
	
	o\StartX# = sx#
	o\StartY# = sy#
	o\StartZ# = sz#

	o\Entity = CreatePivot()
;	o\Entity = CreateCube()

;	If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then 
;	o\IValues[0] = CreateQuad(3, 1.1, 1.5, 0.5) 
;	EntityTexture(o\IValues[0], Textures_Shadow) 
;	PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True) 

;	o\FValues[0] = PickedNX#() 
;	o\FValues[1] = PickedNY#() 
;	o\FValues[2] = PickedNZ#() 

;	MoveEntity(o\IValues[0], 0, 4.0, 0) 
;	ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1)) 
;	EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1)) 
;	End If 

;	EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, OBJECT_VIEWDISTANCE_MAX#) 
;	ScaleEntity o\Entity, 0.0, 0.0, 0.0 
	TranslateEntity o\Entity, x#, y#, z#
	
	Return o 

	End Function
	
	; =========================================================================================================
	; Object_GoalInvis_Update
	; =========================================================================================================
	; Updates the Goal object 

Function Object_GoalInvis_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\Position\x# = EntityX#(o\Entity)
	o\Position\y# = EntityY#(o\Entity)
	o\Position\z# = EntityZ#(o\Entity)
	
;	If (Not (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 5.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 20) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 13.5)) And (ChannelPlaying(Channel_GoalIdle)=False) Then
;	Channel_GoalIdle = PlaySound(Sound_GoalIdle)
;	EndIf

	
;	RotateEntity o\Entity, 0, Float#(MilliSecs())*0.2, 0 
;	If (o\IValues[0] <> 0) Then 
;	RotateEntity o\IValues[0], 0, Float#(MilliSecs())*0.2, 0 
;	AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2) 

;	End If 


	; Player collided with Goal 
	If Online = 0 Then
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius# And o\Gotten = 0 And Game\Transitioning = 0) Then
;	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 5.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 20) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 13.5) Then
	StopChannel(Channel_RingIdle)
	StopChannel Game\MusicChn
	
	
	
	Game\Stage\StartPos = 1
	
	Game\Transitioning = 1
	PostEffect_Create_FadeOut(0.035, 10, 10, 10)
	o\Timer = MilliSecs() + 450
	o\Gotten = 1
	
;	FreeEntity o\Entity 
;	Delete o\Position
;	Delete o
;	Return
EndIf

If (o\Gotten = 1) Then
	StopChannel(Channel_RingIdle)
	StopChannel Game\MusicChn
	
	Game\Stage\Properties\Music = 0
	Game\Stage\Properties\StartMusic = 0
EndIf

If (o\Timer < MilliSecs() And o\Gotten = 1) Then
	If (o\UsePos = 1) Then
		Game\Stage\StartPos = 1
	Game\Stage\StartX# = o\StartX#
	Game\Stage\StartY# = o\StartY#
	Game\Stage\StartZ# = o\StartZ#
	
	p\CheckpointX# = o\StartX#
	p\CheckpointY# = o\StartY#
	p\CheckpointZ# = o\StartZ#
ElseIf (o\UsePos = 0) Then
	Game\Stage\StartPos = 0
;	Game\Stage\StartPosDel = 1
;	Game\Stage\StartX# = 0
;	Game\Stage\StartY# = 0
;	Game\Stage\StartZ# = 0
EndIf

Game\Stage\Ends = o\Ends
Game\Stage\GoalDestination = o\GoalDestination
	
	;Game\State = GAME_STATE_END
	;Delete_Player_and_Stage()
			
	;		MenuSStage$=Game\List\Stagehub
GoalEnd(p,d)
	
EndIf
EndIf

	End Function
	
	; =========================================================================================================
	; Object_CamLock_Create
	; =========================================================================================================
	; Creates a New Spring Object	
	
Function Object_CamLock_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, radius#=135, ctype=1)
	
		o.tObject = New tObject
		
		o\ObjType = OBJTYPE_CAMLOCK
		
		o\Position = New tVector	
		o\Position\x# = x#
		o\Position\y# = y#
		o\Position\z# = z#
		o\Radius# = radius#
		o\Rotation = New tVector
		o\Rotation\x# = rx#
		o\Rotation\y# = ry#
		o\Rotation\z# = rz#
		
		o\cType = ctype
		;o\Rail# = Rail#
		;o\StopPlayer# = StopPlayer#
		
		o\Entity = CreatePivot()
		;EntityType o\Entity, 2
	;	RotateEntity o\Entity, rx#, ry#, rz#
		ScaleEntity o\Entity, 1, 1, 1
		TranslateEntity o\Entity, x#, y#, z#
		TurnEntity o\Entity, rx#, ry#, rz#
		
		Return o
		
	End Function

	; =========================================================================================================
	; Object_CamLock_Update
	; =========================================================================================================
	; Updates the spring object	
	
	Function Object_CamLock_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
		vx# = o\Position\x# - EntityX( p\Objects\Entity )
		vy# = o\Position\y# - EntityY( p\Objects\Entity )
		vz# = o\Position\z# - EntityZ( p\Objects\Entity )
		rx# = EntityPitch( p\Objects\Entity )
		ry# = EntityYaw( p\Objects\Entity )
		rz# = EntityRoll( p\Objects\Entity )
		
		CamRadius# = o\Radius#
		
		
		For c.tCamera = Each tCamera
		
			If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius#) Then
			
				;	c\Held = 1
					
					Select o\cType
							
						Case 0
							
							c\Held = 1
							
					;		Vector_LinearInterpolation(c\Position, c\TargetPosition, Gameplay_Camera_Smoothness#*d\Delta)
					;		Vector_LinearInterpolation(c\Rotation, c\TargetRotation, Gameplay_Camera_Smoothness#*d\Delta)
							
					;		c\TargetRotation\x# = EntityPitch( o\Entity ) ;rx#
					;		c\TargetRotation\y# = EntityYaw( o\Entity )+90 ;ry#
					;		c\TargetRotation\z# = EntityRoll( o\Entity ) ;rz#
					;		c\Rotation\x# = EntityPitch( o\Entity ) ;rx#
					;		c\Rotation\y# = EntityYaw( o\Entity )+90 ;ry#
					;		c\Rotation\z# = EntityRoll( o\Entity ) ;rz#
					;		
					;		c\Position		 = Vector(o\Position\x#, o\Position\y#, o\Position\z#)
					;		c\TargetPosition		 = Vector(o\Position\x#, o\Position\y#, o\Position\z#)
							
												;		c\TargetRotation\x# = EntityPitch( o\Entity ) ;rx#
							
							CamRotX# = EntityPitch( o\Entity ) ;rx#
							CamRotY# = EntityYaw( o\Entity ) ;ry#
							CamRotZ# = EntityRoll( o\Entity ) ;rz#
							
							CamPosX#		 = o\Position\x#
							CamPosY#		 = o\Position\y#
							CamPosZ#		 = o\Position\z#
							
				;			Vector_Set(c\TargetPosition, o\Position\x#, o\Position\y#, o\Position\z#)
							
						Case 1
							
							c\Held = 1
							
					;		Vector_LinearInterpolation(c\Position, c\TargetPosition, Gameplay_Camera_Smoothness#*d\Delta)
					;		Vector_LinearInterpolation(c\Rotation, c\TargetRotation, Gameplay_Camera_Smoothness#*d\Delta)
								
							CamRotX# = EntityPitch( o\Entity ) ;rx#
							CamRotY# = EntityYaw( o\Entity ) ;ry#
							CamRotZ# = EntityRoll( o\Entity ) ;rz#
							
							CamPosX#		 = o\Position\x#
							CamPosY#		 = o\Position\y#
							CamPosZ#		 = o\Position\z#
					
				;	Vector_Set(c\TargetPosition, o\Position\x#, o\Position\y#, o\Position\z#)
					
						Case 2
							
							c\Held = 2
					
				;	c\TargetPosition		 = Vector(o\Position\x#, o\Position\y#, o\Position\z#)
					
				;	Vector_LinearInterpolation(c\Position, c\TargetPosition, Gameplay_Camera_Smoothness#*d\Delta)
				;	Vector_LinearInterpolation(c\Rotation, c\TargetRotation, Gameplay_Camera_Smoothness#*d\Delta)
					
				;	PointEntity (c\Entity, p\Objects\Entity)
					
				;	c\Rotation\x# = EntityPitch#(c\Entity)
				;	c\Rotation\y# = EntityYaw#(c\Entity)
				;	c\Rotation\z# = EntityRoll#(c\Entity)
					
				;	c\Rotation\x# = EntityPitch( c\Entity ) ;rx#
				;	c\Rotation\y# = EntityYaw( c\Entity ) ;ry#
				;	c\Rotation\z# = EntityRoll( c\Entity ) ;rz#
					
				;	c\TargetRotation\x# = EntityPitch#(c\Entity)
				;	c\TargetRotation\y# = EntityYaw#(c\Entity)
				;	c\TargetRotation\z# = EntityRoll#(c\Entity)
					
					CamPosX#		 = o\Position\x#
					CamPosY#		 = o\Position\y#
					CamPosZ#		 = o\Position\z#
					
				;	c\Position = Vector(o\Position\x#,o\Position\y#,o\Position\z#)
				;	c\TargetPosition		 = Vector(o\Position\x#, o\Position\y#, o\Position\z#)
					
				;	Vector_Set(c\TargetPosition, o\Position\x#, o\Position\y#, o\Position\z#)
					
			End Select
					
				;c\Target = p
			
	;	ElseIf (Abs(EntityX(p\Objects\Entity) - o\Position\x) >= o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) >= o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) >= o\Radius#) Then
	;	Else
				
	;		c\Held = 0
				;c\UseCoord = 0
				
		EndIf
			
		Next
		
End Function

	; =========================================================================================================
	; Object_Bomber_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Bomber_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	
	o\ObjType = OBJTYPE_BOMBER
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadMesh("Objects\Bomber\Bomber.b3d")
	
		;Animate o\Entity
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#	
	EntityType(o\Entity,1)
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_Bomber_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Bomber_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\Position\x# = EntityX(o\Entity)
	o\Position\y# = EntityY(o\Entity)
	o\Position\z# = EntityZ(o\Entity)
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 75) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 75) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 75) Then
		
		MoveEntity(o\Entity,0,0,1.5*Game\DeltaTime\Delta#)
		PointEntity(o\Entity, p\Objects\Entity)
		If (ChannelPlaying (Channel_Bomber) = False) Then
			Channel_Bomber = PlaySnd(Sound_Bomber, o\Entity, 2)
		EndIf
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			;	p\EnemyX# = o\Position\x#
			;	p\EnemyY# = o\Position\y#
			;	p\EnemyZ# = o\Position\z#
	;	HomingTarget()
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 1000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x#
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .75)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .75)
	;	p\Motion\Speed\x# = 0
	;	p\Motion\Speed\y# = 0
	;	p\Motion\Speed\z# = 0
		
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
		
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50) Then
		
		; ALIGN HEAD ------------
	If (p\Objects\Mesh_Head <> 0) Then
		If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) And (p\Flags\HeadTrackEnabled = 1) Then
			
			p\HeadTrackTimer# = MilliSecs() + 300
			
			PointEntity(p\Objects\Mesh_HeadPoint,o\Entity)
			TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
			TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
		EndIf
	EndIf
	; -------------------------
EndIf
	
	;Hurt
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 4.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 4.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 4.5) And p\Attacking = 0) Then
		
		; Hurt Code
		
		Channel_Explode = PlaySnd(Sound_Explode, o\Entity, 2)
		
		FreeEntity o\Entity
		Delete o\Position
		Delete o
		Return
		
	EndIf
	
		;---------------------
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False) And (p\Action = ACTION_HOMING)) Then
		
	;	p\Animation\Direction# = ATan2(dz#, dx#)+90
		
		DestroyObject(o, p, 1)
		FreeEntity o\Entity
		
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
		
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False)) Then
		
		DestroyObject(o, p, 1)
		
		StopChannel(Channel_Bomber)
		FreeEntity o\Entity
		
;		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
;		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
		
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5 And (p\Attacking = 1) And (p\Motion\Ground = True)) Then
		
		DestroyObject(o, p, 2)
		
		StopChannel(Channel_Bomber)
		FreeEntity o\Entity
		
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And p\Attacking = 0) Then
		
		ObjectHurt(p, 1)
		
	;	PointEntity(p\Objects\Mesh, o\Entity)
	;	TurnEntity(p\Objects\Mesh,-60,180,0)
	;	TFormPoint(-60,180,0,o\Entity, p\Objects\Mesh)
	;	p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
		
		StopChannel(Channel_Bomber)
		Channel_Explode = PlaySnd(Sound_Explode, o\Entity, 2)
		
		FreeEntity o\Entity
		Delete o\Position
		Delete o
		Return
		
	EndIf
	;---------------------
		
End Function

	; =========================================================================================================
	; Object_TrickHoop_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_TrickHoop_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_TRICKHOOP
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadAnimMesh("Objects\TrickHoops\TrickHoop.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, -1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	Animate o\Entity
	
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_TrickHoop_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_TrickHoop_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 12.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 12.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 12.5) Then
		
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40
		
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 8.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 8.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 8.5) And o\State = 0 Then
			
			o\State = 1
			o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
			TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
			p\Motion\Ground = False
			p\Action = ACTION_SPRING; : StopChannel(Channel_Stomp)
			p\Motion\Speed\y# = 1
			RotateEntity(p\Objects\Mesh,x#,y#,z#)
			p\Motion\Speed\x = TFormedX()*1.8
			p\Motion\Speed\y = TFormedY()*1.8
			p\Motion\Speed\z = TFormedZ()*1.8
			
				; Transform the position vector to World space
			TFormVector 0, 1, 0, o\Entity, 0					
			PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
			
				; Sproing!
			Channel_THoop = playsnd(Sound_THoop, o\Entity, 1)
			
				; Apply motion blur
				;PostEffect_Create_MotionBlur(0.85)
			Game\Gameplay\Score = Game\Gameplay\Score + 1000
		EndIf
		
	EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_Hoop_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Hoop_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_HOOP
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadAnimMesh("Objects\Hoops\Hoop.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, -1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	Animate o\Entity
	
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_Hoop_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Hoop_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	;ID = ID + 1
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 12.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 12.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 12.5) Then
		
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40
		
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 8.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 8.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 8.5) And o\State = 0 Then
			
			o\State = 1
			o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
			TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
			p\Action = ACTION_SPRING
			p\Motion\Ground = False
			RotateEntity(p\Objects\Mesh,x#,y#,z#)
			p\Motion\Speed\x = TFormedX()*1.8
			p\Motion\Speed\y = TFormedY()*1.8
			p\Motion\Speed\z = TFormedZ()*1.8
			
				; Transform the position vector to World space
			TFormVector 0, 1, 0, o\Entity, 0					
			PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
			
				; Sproing!
			Channel_Hoop = playsnd(Sound_Hoop, o\Entity, 1)
			
				; Apply motion blur
				;PostEffect_Create_MotionBlur(0.85)
			
		EndIf
		
	EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_FlyEnemy_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_FlyEnemy_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
;		Expl = CreateTemplate()
;		SetEmitter(o\Entity, Expl)
;SetTemplateInterval(Expl, 39)
;SetTemplateEmitterLifeTime(Expl, -1)
;SetTemplateParticleLifeTime(Expl, 39, 39)
;SetTemplateAnimTexture(Expl, "Textures\AnimParticle-002.png", 1, 1, 64, 64, 39)
;SetTemplateOffset(Expl, -3, 3, -3, 3, -3, 3)
;SetTemplateVelocity(Expl, -.04, .04, -.04, .04, -.04, .04)
;SetTemplateSize(Expl, 3, 3)
	
	
	o\ObjType = OBJTYPE_FLYENEMY
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Rotation = New tVector
	
			; Make a shadow
;	If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then
;		o\IValues[0] = CreateQuad(8, 8, 4, 4)
;		EntityTexture(o\IValues[0], Textures_Shadow)
;		PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True)
		
;		o\FValues[0] = PickedNX#()
;		o\FValues[1] = PickedNY#()
;		o\FValues[2] = PickedNZ#()
		
;		MoveEntity(o\IValues[0], 0, 0.2, 0)
;		ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1))
;		EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1))
;	End If
	
	o\Entity = LoadAnimMesh("Objects\FlyEnemies\Enemy.b3d")
	o\Pointer = CreatePivot()
	
	Animate o\Entity
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
;	ScaleEntity o\Pointer, 3.1, 3.1, 3.1
	TranslateEntity o\Entity, x#, y#, z#
	TranslateEntity o\Pointer, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	TurnEntity o\Pointer, rx#, ry#, rz#
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_FlyEnemy_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_FlyEnemy_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	o\Position\x# = EntityX(o\Entity)
	o\Position\y# = EntityY(o\Entity)
	o\Position\z# = EntityZ(o\Entity)
	
	
	
	
	;AlignToVector (o\Entity, EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity),EntityZ#(p\Objects\Entity), 2, 0.6)
	
	; TURN THE ROBOT
	If o\State = 0 Then
		
		If (ChannelPlaying(o\Channel)=False) Then o\Channel = PlaySnd(Sound_Spinner, o\Entity, 0.7)
		
		PointEntity o\Pointer, p\Objects\Entity
	;	TurnEntity(o\Pointer,0,180,0)
	;	TFormPoint(0,180,0,p\Objects\Entity,o\Pointer)
		
	;o\Rotation\y# = CountToValue#(o\Rotation\y#, EntityYaw#(o\Pointer), 1.7, d\Delta#)
		o\Rotation\y# = EntityYaw#(o\Pointer)
		RotateEntity o\Entity, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
		
	Else
		StopChannel o\Channel
		
	EndIf
	
			;HOMTARGETPIC HAX
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 115) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 115) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 115 And (p\Action = ACTION_JUMP Or p\Action = ACTION_JUMPPART2 Or p\Action = ACTION_FALL Or p\Action = ACTION_TRICK1 Or p\Action = ACTION_SPRING Or p\Action = ACTION_AIRROLL Or p\Action = ACTION_HOMING Or p\Action = ACTION_SHOCK)) And (Not (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50)) Then
;		HomFar = CreateTemplate()
;		SetTemplateEmitterBlend(HomFar, 1)
;		SetTemplateInterval(HomFar, 3)
;		SetTemplateEmitterLifeTime(HomFar, 5)
;		SetTemplateParticleLifeTime(HomFar, 3, 3)
;		SetTemplateTexture(HomFar, "Textures\HomFar.png", 2, 1)
;		SetTemplateOffset(HomFar, 0, 0, 0, 0, 0, 0)
;		SetTemplateVelocity(HomFar, 0, 0, 0, 0, 0, 0)
;		SetTemplateAlphaVel(HomFar, True)
;		SetTemplateSize(HomFar, 13, 13, .9, .9)
;		SetTemplateMaxParticles(HomFar,1)
;		SetEmitter(o\Entity,HomFar)
;		MoveEntity(o\Entity, 0.5*d\Delta#, 0, 0)
		
		
		
	;	o\Rotation\y# = CountToValue#(o\Rotation\y#, EntityYaw#(o\Pointer), 3, d\Delta#)
		
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And o\State = 0 And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			;	p\EnemyX# = o\Position\x#
			;	p\EnemyY# = o\Position\y#
			;	p\EnemyZ# = o\Position\z#
	;	HomingTarget()
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 1000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x#
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .95)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .95)
	;	p\Motion\Speed\x# = 0
	;	p\Motion\Speed\y# = 0
	;	p\Motion\Speed\z# = 0
		
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
		
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 40) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 40) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 40) Then
		
		; ALIGN HEAD ------------
;	If (p\Objects\Mesh_Head <> 0) Then
	;	If (p\Motion\GroundSpeed# < 0.3) And (p\Flags\HeadTrackEnabled = 1) Then
;		If (p\Flags\HeadTrackEnabled = 1) Then
		
;			p\HeadTrackTimer# = MilliSecs() + 300
			
		;	TFormPoint(o\Position\x#,o\Position\y#,o\Position\z#,p\Objects\Mesh_Head,p\Objects\Entity)
		;	AlignToVector(p\Objects\Mesh_Head, TFormedX#, TFormedY#, TFormedZ#, 2)
		;	AlignToVector(p\Objects\Mesh_HeadPoint, o\Position\x#, o\Position\y#, o\Position\z#, 1)
		;	AlignToVector(p\Objects\Mesh_Head, o\Position\x#, o\Position\y#, o\Position\z#, 3)
			;TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
		;	TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
			
;			hx# = EntityX( o\Entity ) - EntityX( p\Objects\Entity )
;			hy# = EntityY( o\Entity ) - EntityY( p\Objects\Entity )
;			hz# = EntityZ( o\Entity ) - EntityZ( p\Objects\Entity )
;			PointEntity(p\Objects\Mesh_Head, o\Entity, 0)
		;	TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
;			TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
			
		; Smoothly align the missile cone to the vector.
		;	TFormVector(0,90,0,p\Objects\Mesh_HeadPoint,p\Objects\Entity)
		;	AlignToVector( p\Objects\Mesh_HeadPoint, hx#, hy#, hz#, 1, 0.95 )
			
		;	TurnEntity(p\Objects\Mesh_HeadPoint,90,0,0,1)
;		EndIf
;	EndIf
		
		If (p\Objects\Mesh_Head <> 0) Then
			If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) And (p\Flags\HeadTrackEnabled = 1) Then
				
				p\HeadTrackTimer# = MilliSecs() + 300
				
				PointEntity(p\Objects\Mesh_HeadPoint,o\Entity)
				TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
			;	TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
			EndIf
		EndIf
	; -------------------------
	
	EndIf
		
	;---------------------
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False) And o\State = 0 And (p\Action = ACTION_HOMING)) Then
		
	;	p\Animation\Direction# = ATan2(dz#, dx#)+90
		
		StopChannel(Channel_Spinner)
		
		DestroyObject(o, p, 1)
	;	FreeEntity o\Entity
		o\State = 1
		o\Timer = 0
		
		If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
		If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
		If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False)) And o\State = 0 Then
		
		StopChannel(Channel_Spinner)
		
		DestroyObject(o, p, 1)
	;	FreeEntity o\Entity
		o\State = 1
		o\Timer = 0
		
		If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
		If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
		If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
		
	;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
	;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5 And (p\Attacking = 1) And (p\Motion\Ground = True)) And o\State = 0 Then
		
		StopChannel(Channel_Spinner)
		
		DestroyObject(o, p, 2)
	;	FreeEntity o\Entity
		o\State = 1
		o\Timer = 0
		
		If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
		If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
		If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And o\State = 0 And p\Attacking = 0) Then
		
		ObjectHurt(p, 1)
		
	;	PointEntity(p\Objects\Mesh, o\Entity)
	;	TurnEntity(p\Objects\Mesh,-60,180,0)
	;	TFormPoint(-60,180,0,o\Entity, p\Objects\Mesh)
	;	p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
		
	EndIf
	;---------------------
	
	If o\State = 1 Then
		HideEntity o\Entity
		o\Timer = o\Timer + GlobalTimer
	Else
		ShowEntity o\Entity
	EndIf
	
	If o\Timer > 6000 Then o\State = 0
	
	
	
End Function

	; =========================================================================================================
	; Object_Enemy_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Enemy_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, attacks=0, model$)
	
	o.tObject = New tObject
	
;		Expl = CreateTemplate()
;		SetEmitter(o\Entity, Expl)
;SetTemplateInterval(Expl, 39)
;SetTemplateEmitterLifeTime(Expl, -1)
;SetTemplateParticleLifeTime(Expl, 39, 39)
;SetTemplateAnimTexture(Expl, "Textures\AnimParticle-002.png", 1, 1, 64, 64, 39)
;SetTemplateOffset(Expl, -3, 3, -3, 3, -3, 3)
;SetTemplateVelocity(Expl, -.04, .04, -.04, .04, -.04, .04)
;SetTemplateSize(Expl, 3, 3)
	
	
	o\ObjType = OBJTYPE_ENEMY
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Rotation = New tVector
	
	o\Speed	= New tVector
	o\Speed\X# = 0
	o\Speed\Y# = -2
	o\Speed\Z# = 0
	
	o\Attacks = attacks
	
		; Make a shadow
	If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then
		o\IValues[0] = CreateQuad(8, 8, 4, 4)
		EntityTexture(o\IValues[0], Textures_Shadow)
		PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True)
		
		o\FValues[0] = PickedNX#()
	;	o\FValues[1] = PickedNY#()
	;	o\FValues[2] = PickedNZ#()
		
		MoveEntity(o\IValues[0], 0, 0.2, 0)
		ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1))
		EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1))
	End If
	
	o\Entity = LoadAnimMesh("Objects\" + model$)
	o\Pointer = CreatePivot()
	
	RecursiveExtractAnimSeq(o\Entity,	1,		28)	; Idle
	RecursiveExtractAnimSeq(o\Entity,	29,		68)	; Hit
	;RecursiveExtractAnimSeq(o\Entity,	69,		94)	; Shoot
	RecursiveExtractAnimSeq(o\Entity,	69,		89)	; Aim
	
	RecursiveAnimate(o\Entity, 1, 0.8, 1, 0)
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	TranslateEntity o\Pointer, x#, y#, z#
	TurnEntity o\Pointer, rx#, ry#, rz#
	
	o\Pivot = CreatePivot()
	PositionEntity(o\Pivot, x#, y#, z#)
	
	EntityType(o\Pivot, COLLISION_SPEWRING)
	EntityRadius(o\Pivot, 2.0)
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_Enemy_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Enemy_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = o\Position\y# - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	PositionEntity(o\Entity, EntityX#(o\Pivot), EntityY#(o\Pivot), EntityZ#(o\Pivot))
	
	o\Position\x# = EntityX#(o\Pivot)
	o\Position\y# = EntityY#(o\Pivot, True)
	o\Position\z# = EntityZ#(o\Pivot)
	
	; TURN THE ROBOT
	If o\State = 0 Then
		
		PointEntity o\Pointer, p\Objects\Entity
		TurnEntity(o\Pointer,0,180,0)
	;	TFormPoint(0,180,0,p\Objects\Entity,o\Pointer)
		
	;o\Rotation\y# = CountToValue#(o\Rotation\y#, EntityYaw#(o\Pointer), 1.7, d\Delta#)
		o\Rotation\y# = EntityYaw#(o\Pointer)
	RotateEntity o\Entity, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
	
EndIf
	
;	If o\State = 0 Then
	
;	If o\IValues[0] <> 0 Then
;		
;	If ( (o\Position\y#-2)<EntityY#(o\IValues[0], True)) Then
;		If (-o\Speed\y#>0.2) Then o\Speed\y# = -(o\Speed\y#+0.85) Else o\Speed\y#=0
;		If (o\Speed\z#>0.0) Then o\Speed\z#=o\Speed\z#-0.01
;	End If
	
;	If ( (o\Position\y#-2)>EntityY#(o\IValues[0])) Then o\Speed\y# = o\Speed\y#-0.06
;	MoveEntity(o\Pivot, o\Speed\x#*d\Delta, o\Speed\y#*d\Delta, o\Speed\z*d\Delta)
	
;EndIf
	
;EndIf
	
;	PointEntity (o\Entity, p\Objects\Entity)
	
ATan2# (vy#, vx#)

o\Timer = o\Timer + GlobalTimer
	
				;HOMTARGETPIC HAX
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 75) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 75) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 75 And (p\Action = ACTION_JUMP Or p\Action = ACTION_JUMPPART2 Or p\Action = ACTION_FALL Or p\Action = ACTION_TRICK1 Or p\Action = ACTION_SPRING Or p\Action = ACTION_AIRROLL Or p\Action = ACTION_HOMING Or p\Action = ACTION_SHOCK)) And (Not (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50)) Then
;		HomFar = CreateTemplate()
;		SetTemplateEmitterBlend(HomFar, 1)
;		SetTemplateInterval(HomFar, 3)
;		SetTemplateEmitterLifeTime(HomFar, 5)
;		SetTemplateParticleLifeTime(HomFar, 3, 3)
;		SetTemplateTexture(HomFar, "Textures\HomFar.png", 2, 1)
;		SetTemplateOffset(HomFar, 0, 0, 0, 0, 0, 0)
;		SetTemplateVelocity(HomFar, 0, 0, 0, 0, 0, 0)
;		SetTemplateAlphaVel(HomFar, True)
;		SetTemplateSize(HomFar, 13, 13, .9, .9)
;		SetTemplateMaxParticles(HomFar,1)
;		SetEmitter(o\Entity,HomFar)
	;	RecursiveAnimate(o\Entity, 3, 0.8, 1, 0)
		
		If (o\State = 0) And o\Attacks = 1 Then
		
			If (o\Timer > Rand(5000,7000)) Then
			
			Channel_Shoot = PlaySnd(Sound_Shoot, o\Entity)
			PlaySnd(Sound_Shoot, o\Entity)
			PlaySnd(Sound_Shoot, o\Entity)
			PlaySnd(Sound_Shoot, o\Entity)
			obj.tObject = Object_Missile_Create(o\Position\x#,o\Position\y#,o\Position\z#)
			
			o\Timer = 0
			
		EndIf
	
Else
	o\Timer = 0
EndIf
	
;Else
;	o\Timer = 0
;	RecursiveAnimate(o\Entity, 1, 0.8, 1, 0)
		
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True And o\Entity <> 0) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			;	p\EnemyX# = o\Position\x#
			;	p\EnemyY# = o\Position\y#
			;	p\EnemyZ# = o\Position\z#
		;HomingTarget()
		If (o\State = 0) Then
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 1000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x#
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
	EndIf
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .95)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .95)
	;	p\Motion\Speed\x# = 0
	;	p\Motion\Speed\y# = 0
	;	p\Motion\Speed\z# = 0
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			If p\TurnTimer > 100 Then PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50.5) Then
		
		; ALIGN HEAD ------------
		If (p\Objects\Mesh_Head <> 0) Then
			If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) And (p\Flags\HeadTrackEnabled = 1) Then
				
				p\HeadTrackTimer# = MilliSecs() + 300
				
				PointEntity(p\Objects\Mesh_HeadPoint,o\Entity)
				TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
				TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
			EndIf
		EndIf
	; -------------------------
	
		If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False) And (p\Action = ACTION_HOMING)) Then
			If (o\State = 0) Then
	;	p\Animation\Direction# = ATan2(dz#, dx#)+90
		
	;	SetEmitter(p\Objects\Entity, Game\Others\Explode)
		DestroyObject(o, p, 1, 1)
	;	FreeEntity o\Entity
		
		o\State = 1
		RecursiveAnimate(o\Entity, 3, 1.2, 2, 0)
		
		If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
		If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
		If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	EndIf
	
		If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False)) Then
			If (o\State = 0) Then
				DestroyObject(o, p, 1, 1)
		;	FreeEntity o\Entity
			
			o\State = 1
			RecursiveAnimate(o\Entity, 3, 1.2, 2, 0)
			
			If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
			If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
			If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
			
	;		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
	;		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
			
		;	Delete o\Position
		;	Delete o
			Return
		EndIf
		EndIf
		
		If (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5 And (p\Attacking = 1) And (p\Motion\Ground = True)) Then
			If (o\State = 0) Then
				DestroyObject(o, p, 2, 1)
		;	FreeEntity o\Entity
			o\State = 1
			RecursiveAnimate(o\Entity, 3, 1.2, 2, 0)
			
			If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
			If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
			If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
			
		;	Delete o\Position
		;	Delete o
			Return
		EndIf
	EndIf
		
		If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And p\Attacking = 0) Then
			If (o\State = 0) Then
			ObjectHurt(p, 1)
			
	;		PointEntity(p\Objects\Mesh, o\Entity)
	;		TurnEntity(p\Objects\Mesh,-60,180,0)
	;		TFormPoint(-60,180,0,o\Entity, p\Objects\Mesh)
	;		p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
			
		EndIf
		EndIf
	;---------------------
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_Balloon_Create
	; =========================================================================================================
	; Creates a New Balloon Object	

Function Object_Balloon_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
;		Expl = CreateTemplate()
;		SetEmitter(o\Entity, Expl)
;SetTemplateInterval(Expl, 39)
;SetTemplateEmitterLifeTime(Expl, -1)
;SetTemplateParticleLifeTime(Expl, 39, 39)
;SetTemplateAnimTexture(Expl, "Textures\AnimParticle-002.png", 1, 1, 64, 64, 39)
;SetTemplateOffset(Expl, -3, 3, -3, 3, -3, 3)
;SetTemplateVelocity(Expl, -.04, .04, -.04, .04, -.04, .04)
;SetTemplateSize(Expl, 3, 3)
	
	
	o\ObjType = OBJTYPE_BALLOON
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadMesh("Objects\Balloon\Balloon.b3d")
;	Animate o\Entity
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_Balloon_Update
	; =========================================================================================================
	; Updates the Balloon object	

Function Object_Balloon_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = (p\EnemyY# + 2.6) - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = (o\Position\y# + 1.6) - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	o\Position\x# = EntityX(o\Entity)
	o\Position\y# = EntityY(o\Entity)
	o\Position\z# = EntityZ(o\Entity)
	
	;AlignToVector (o\Entity, EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity),EntityZ#(p\Objects\Entity), 2, 0.6)
	
	
	
			;HOMTARGETPIC HAX
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 75) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 75) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 75 And (p\Action = ACTION_JUMP Or p\Action = ACTION_JUMPPART2 Or p\Action = ACTION_FALL Or p\Action = ACTION_TRICK1 Or p\Action = ACTION_SPRING Or p\Action = ACTION_AIRROLL Or p\Action = ACTION_HOMING Or p\Action = ACTION_SHOCK)) And (Not (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50)) Then
;		HomFar = CreateTemplate()
;		SetTemplateEmitterBlend(HomFar, 1)
;		SetTemplateInterval(HomFar, 3)
;		SetTemplateEmitterLifeTime(HomFar, 5)
;		SetTemplateParticleLifeTime(HomFar, 3, 3)
;		SetTemplateTexture(HomFar, "Textures\HomFar.png", 2, 1)
;		SetTemplateOffset(HomFar, 0, 0, 0, 0, 0, 0)
;		SetTemplateVelocity(HomFar, 0, 0, 0, 0, 0, 0)
;		SetTemplateAlphaVel(HomFar, True)
;		SetTemplateSize(HomFar, 13, 13, .9, .9)
;		SetTemplateMaxParticles(HomFar,1)
;		SetEmitter(o\Entity,HomFar)
;		MoveEntity(o\Entity, 0.5*d\Delta#, 0, 0)
		
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 50) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 50) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 50 And o\State = 0 And p\Action = ACTION_JUMPDASH And EntityVisible(p\Objects\Entity, o\Entity)=True) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
			;	p\EnemyX# = o\Position\x#
			;	p\EnemyY# = o\Position\y#
			;	p\EnemyZ# = o\Position\z#
	;	HomingTarget()
		p\Action = ACTION_HOMING : p\HomingTimer = MilliSecs() + 1000 : p\Motion\Speed\y# = 0 : p\EnemyZ# = o\Position\z# : p\EnemyY# = o\Position\y# : p\EnemyX# = o\Position\x#
		;createls5trail()
				;p\Motion\Speed\x# = 0
				;p\Motion\Speed\z# = 0
		
	EndIf
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 55) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 55) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 55 And p\Action = ACTION_HOMING) Then
				;PositionEntity(p\Objects\Entity,o\Position\x#,o\Position\y#,o\Position\z#)
		If (p\HomingTimer < MilliSecs()) Then
			p\Action = ACTION_FALL
		EndIf
	;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .95)
	;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .95)
	;	p\Motion\Speed\x# = 0
	;	p\Motion\Speed\y# = 0
	;	p\Motion\Speed\z# = 0
		
		If (p\EnemyPicked = 0) Then
			
			p\EnemyPicked = 1
			p\Homing = 1
			p\Objects\Home = 1
			
			p\Objects\Enemy = o\Entity
			
			p\rx#=EntityX#(p\Objects\Enemy)
			p\ry#=EntityY#(p\Objects\Enemy)
			p\rz#=EntityZ#(p\Objects\Enemy)
			
			p\dx#=p\rx#-EntityX#(p\Objects\Entity)
			p\dz#=p\rz#-EntityZ#(p\Objects\Entity)
			
			diff# = Abs(p\Animation\Direction# - (ATan2(p\dz#, p\dx#)+90))
			
				; Align the LightDevice
			PointEntity p\Objects\LightDevice, p\Objects\Enemy
			p\Animation\Direction# = ATan2(p\dz#, p\dx#)+90
			
		;		p\Motion\Speed\x# = 0
		;		p\Motion\Speed\y# = 0
		;		p\Motion\Speed\z# = 0
		EndIf
		
	EndIf
	
	;---------------------
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False) And o\State = 0 And (p\Action = ACTION_HOMING)) Then
		
	;	p\Animation\Direction# = ATan2(dz#, dx#)+90
		
		DestroyObject(o, p, 1, 2)
	;	FreeEntity o\Entity
		o\Timer = 0
		o\State = 1
		HideEntity o\Entity
		
		
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*3.3
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*3.3
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False)) And o\State = 0 Then
		
		DestroyObject(o, p, 1, 2)
	;	FreeEntity o\Entity
		o\Timer = 0
		o\State = 1
		HideEntity o\Entity
		
		
;		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*3.3
;		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*3.3
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5 And (p\Attacking = 1) And (p\Motion\Ground = True)) And o\State = 0 Then
		
		DestroyObject(o, p, 2, 2)
	;	FreeEntity o\Entity
		o\Timer = 0
		o\State = 1
		HideEntity o\Entity
		
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	;---------------------
	
;	If o\State = 1 Then o\Timer = o\Timer + GlobalTimer
	
	
	If o\State = 1 Then
		HideEntity o\Entity
		o\Timer = o\Timer + GlobalTimer
	Else
		ShowEntity o\Entity
	EndIf
	
	If o\Timer > 4000 Then o\State = 0
	
	
	
End Function

	; =========================================================================================================
	; Object_DashPad_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_DashPad_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, pwr#=4.8, cam=1)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_DASHPAD
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	o\Rotation = New tVector
	o\Rotation\x# = rx#
	o\Rotation\y# = ry#
	o\Rotation\z# = rz#
	o\pwr# = pwr#
	o\cam = cam
	
	o\Entity = LoadAnimMesh("Objects\DashPads\DashPad.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, 1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	Animate o\Entity
	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_DashPad_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_DashPad_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	rx# = EntityPitch(o\Entity)
	ry# = EntityYaw(o\Entity)
	rz# = EntityRoll(o\Entity)
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 12.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 12.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 12.5) Then
		
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40
		
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 7.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 7.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 7.5) And o\State = 0 Then
			
			o\State = 1
			o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
			TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
			
			p\Motion\Speed\x = TFormedX()*o\pwr#
			p\Motion\Speed\y = TFormedY()*o\pwr#
			p\Motion\Speed\z = TFormedZ()*o\pwr#
			
			If Not(o\Rotation\x#=0 And o\Rotation\z#=0) Then p\Animation\Direction#=o\Rotation\y#+180
			For c.tCamera=Each tCamera
				If o\cam=1 Then c\TargetRotation\y#=o\Rotation\y#
			Next
			
				; Transform the position vector to World space
			TFormVector 0, 1, 0, o\Entity, 0					
			PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
			
			;	TurnEntity p\Objects\Entity, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
			;	TurnEntity p\Objects\Mesh, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
			
			;p\Animation\Direction# = Abs(ry#)
			
				;p\Animation\Align\x# = o\Rotation\x#
				;p\Animation\Align\y# = o\Rotation\y#
				;p\Animation\Align\z# = o\Rotation\z#
			
				; Sproing!
			Channel_DashPad = PlaySnd(Sound_DashPad, o\Entity, 1)
			If (Not p\Action = ACTION_SPINDASH) Then
				p\Action = ACTION_COMMON
			EndIf
			If (p\Action = ACTION_SPINDASH) Then
				p\Action = ACTION_SPINDASH
			EndIf
				; Apply motion blur
				;PostEffect_Create_MotionBlur(0.85)
			
		EndIf
		
	EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_DashPadNew_Create
	; =========================================================================================================

Function Object_DashPadNew_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, pwr#=4.8, cam=1)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_DASHPADNEW
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	o\Rotation = New tVector
	o\Rotation\x# = rx#
	o\Rotation\y# = ry#
	o\Rotation\z# = rz#
	o\pwr# = pwr#
	o\cam = cam
	
	o\Entity = LoadAnimMesh("Objects\DashPads\DashPadNew.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, 1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	Animate o\Entity
	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_DashPadNew_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_DashPadNew_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	rx# = EntityPitch(o\Entity)
	ry# = EntityYaw(o\Entity)
	rz# = EntityRoll(o\Entity)
	
	o\Position\x# = EntityX(o\Entity)
	o\Position\y# = EntityY(o\Entity)
	o\Position\z# = EntityZ(o\Entity)
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 12.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 12.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 12.5) Then
		
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40
		
		If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 7.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 7.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 7.5) And o\State = 0 And p\Motion\Ground = True Then
			
			o\State = 1
			o\FValues[0] = 0.0
			
				; Transform the Speed vector to the Player space
			TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
			
			
			
		;	p\Motion\Speed\x = TFormedX()*o\pwr#
		;	p\Motion\Speed\y = TFormedY()*o\pwr#
		;	p\Motion\Speed\z = TFormedZ()*o\pwr#
			
		;	If Not(o\Rotation\x#=0 And o\Rotation\z#=0) Then p\Animation\Direction#=o\Rotation\y#+90
			p\Animation\Direction#=o\Rotation\y#
			For c.tCamera=Each tCamera
				If o\cam=1 Then c\TargetRotation\y#=o\Rotation\y#+180
			Next
			
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*o\pwr
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*o\pwr
			p\Motion\Ground = True
			
			
				; Transform the position vector to World space
			TFormVector 0, 1, 0, o\Entity, 0					
		;	PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
			
		;	PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*1, o\Position\z# + TFormedZ()*4
			
			;	TurnEntity p\Objects\Entity, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
			;	TurnEntity p\Objects\Mesh, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
			
			;p\Animation\Direction# = Abs(ry#)
			
				;p\Animation\Align\x# = o\Rotation\x#
				;p\Animation\Align\y# = o\Rotation\y#
				;p\Animation\Align\z# = o\Rotation\z#
			
				; Sproing!
			Channel_DashPad = PlaySnd(Sound_DashPad, o\Entity, 1)
			If (p\Action <> ACTION_SPINDASH And p\Action <> ACTION_SLIDE) Then
				p\Action = ACTION_COMMON
			EndIf
			If (p\Action = ACTION_SPINDASH) Then
				p\Action = ACTION_SPINDASH
			EndIf
			
			If (p\Action = ACTION_SLIDE) Then
				p\Action = ACTION_SLIDE
			EndIf
				; Apply motion blur
				;PostEffect_Create_MotionBlur(0.85)
		EndIf
			
		
	EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
	;	ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
	
End Function

	; =========================================================================================================
	; Object_Check_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Check_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_CHECK
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	o\Gotten = 0
	
	o\Entity = LoadAnimMesh("Objects\Checkpoint\Checkpoint.b3d")
	
	RecursiveExtractAnimSeq(o\Entity,	1,		18)	; Spin
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
		;EntityRadius(o\Entity,5,5)
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_Check_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Check_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 12.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 7.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 12.5 And o\Gotten = 0) Then
		p\CheckpointX# = EntityX(p\Objects\Entity)
		p\CheckpointY# = EntityY(p\Objects\Entity) + 2.0
		p\CheckpointZ# = EntityZ(p\Objects\Entity)
		RecursiveAnimate(o\Entity, 3, 0.3, 1, 0)
		o\Gotten = 1
			;If ChannelPlaying(Channel_Check) = False Then
		Channel_Check = PlaySnd(Sound_Check, o\Entity, 1)
	EndIf
;			Game\Gameplay\CheckTimer = MilliSecs() + 400
;			If (Game\Gameplay\CheckTimer < MilliSecs()) Then
;			Game\Gameplay\CheckTimer = MilliSecs() + 400
;			EndIf
;		If (Game\Gameplay\CheckTimer = MilliSecs() + 400) Then
;	SetTemplateEmitterBlend(check, 1)
;	SetTemplateInterval(check, 1)
;	SetTemplateEmitterLifeTime(check, 8)
;	SetTemplateParticleLifeTime(check, 75, 85)
;	SetTemplateTexture(check, "Textures\RingP.png", 2, 1)
;	SetTemplateOffset(check, 0, 0, 10, 10, 0, 0)
;	SetTemplateVelocity(check, -.04, .04, .1, .2, -.04, .04)
;	SetTemplateAlphaVel(check, True)
;	SetTemplateSize(check, 3, 3, .9, .9)
;	;SetTemplateSizeVel(home, 1, 1)
;	SetTemplateMaxParticles(check,3)
;	SetEmitter(o\Entity,check)
;	EndIf
;			Gotten = 0
	
			;If (Abs(EntityX(p\Objects\Entity) - o\Position\x) > 6.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) > 6.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) > 6.5) And (Game\Gameplay\Check = 0) Then
	
End Function

	; =========================================================================================================
	; Object_NPC_Create
	; =========================================================================================================

Function Object_NPC_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, speech$, model$, sound$="", animated=0, radius#=7)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_NPC
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Rotation = New tVector
	o\Rotation\x# = rx#
	o\Rotation\y# = ry#
	o\Rotation\z# = rz#
	
	o\Animated = animated
	o\Sound$ = Load3DSound(sound$)
	o\Speech$ = speech$
	
	If (o\Animated = 1) Then o\Entity = LoadAnimMesh(model$) : Animate o\Entity
	If (o\Animated = 0) Then o\Entity = LoadMesh(model$)
	
	o\Radius# = radius#
	
	EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, OBJECT_VIEWDISTANCE_MAX#)
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#	
	TurnEntity o\Entity, rx#, ry#, rz#
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_NPC_Update
	; =========================================================================================================

Function Object_NPC_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = p\EnemyY# - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = o\Position\y# - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius#) And (Input\Pressed\Interact) And (Game\Gameplay\NPCTalking = 0) Then
		
	;	Game\Gameplay\NPCTextTimer = MilliSecs() + 3000
		Game\Gameplay\NPCTalking = 1 : Input\Pressed\Interact = False
		Game\Gameplay\NPCText$ = o\Speech$
		p\Motion\Speed\x# = 0
		p\Motion\Speed\z# = 0
		StopChannel(Channel_NPC)
		Channel_NPC=PlaySnd(o\Sound$, o\Entity, 1)
			
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius#) And (Game\Gameplay\NPCTalking = 1) Then
		
	; ALIGN HEAD ------------
		If (p\Objects\Mesh_Head <> 0) Then
			If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) And (p\Flags\HeadTrackEnabled = 1) Then
				
				p\HeadTrackTimer# = MilliSecs() + 300
				
				PointEntity(p\Objects\Mesh_HeadPoint,o\Entity)
				TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
				TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
			EndIf
		EndIf
	; -------------------------
		EndIf
	
End Function

	; =========================================================================================================
	; Object_DashRamp_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_DashRamp_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_DASHRAMP
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	o\Rotation = New tVector
	o\Rotation\x# = rx#
	o\Rotation\y# = ry#
	o\Rotation\z# = rz#
	
	
	o\Entity = LoadAnimMesh("Objects\DashRamp\DashRamp.b3d")
	
	o\IValues[0] = LoadMesh("Objects\Quad.b3d", o\Entity)
	EntityBlend o\IValues[0], 3
	EntityFX o\IValues[0], 1+16
	EntityTexture o\IValues[0], LoadTexture("Textures\Woosh.png")
	EntityAlpha o\IValues[0], 0.0
	TranslateEntity o\IValues[0], 0, 1, 0
	o\FValues[0] = 0
	
	ScaleEntity o\Entity, 1.5, 1.5, 1.5
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	
	;CreateShadowCaster o\Entity
	
	Animate o\Entity
	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_DashRamp_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_DashRamp_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
			;If Not p\Motion\Ground Then PositionEntity p\Objects\Entity, (EntityX(p\Objects\Entity)*39 + o\Position\x#)/40, (EntityY(p\Objects\Entity)*39 + o\Position\y#)/40, (EntityZ(p\Objects\Entity)*39 + o\Position\z#)/40
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 8.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 8.5) And o\State = 0 Then
		
		o\State = 1
		o\FValues[0] = 0.0
		
				; Transform the Speed vector to the Player space
		TFormVector 0, 1, 0, o\Entity, p\Objects\Entity
		p\Action = ACTION_RAMP
		p\Motion\Ground = False
		p\Motion\Speed\x = TFormedX()*2.5
		p\Motion\Speed\y = TFormedY()*2.5
		p\Motion\Speed\z = TFormedZ()*2.5
		
				; Transform the position vector to World space
		TFormVector 0, 1, 0, o\Entity, 0					
		PositionEntity p\Objects\Entity, o\Position\x# + TFormedX()*4, o\Position\y# + TFormedY()*4, o\Position\z# + TFormedZ()*4
		
		If Not(o\Rotation\x#=0 And o\Rotation\z#=0) Then p\Animation\Direction#=o\Rotation\y#+180
		
				;AlignToVector(p\Objects\Entity,o\Rotation\x#,o\Rotation\y#,o\Rotation\z#, 2)
				;TurnEntity p\Objects\Entity, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
				;TurnEntity p\Objects\Mesh, o\Rotation\x#, o\Rotation\y#, o\Rotation\z#
			;	p\Animation\Align\x# = o\Rotation\x#
			;	p\Animation\Align\y# = o\Rotation\y#
			;	p\Animation\Align\z# = o\Rotation\z#
		
				; Sproing!
		Channel_DashRamp = PlaySnd(Sound_DashRamp, o\Entity, 1)
		
				; Apply motion blur
				;PostEffect_Create_MotionBlur(0.85)
		
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 15.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 10.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 15.5) And o\State = 0 Then
	
			; Align Head
;	If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) Then
;		
;		PointEntity(p\Objects\Mesh_Head,o\Entity)
;		TurnEntity(p\Objects\Mesh_Head,0,180,0)
;		TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_Head)
;	EndIf
;	If (EntityYaw(p\Objects\Mesh_Head) > 60) Then
;		RotateEntity(p\Objects\Mesh_Head,0,59.9,0)
;	EndIf
;	If (EntityYaw(p\Objects\Mesh_Head) < -60) Then
;		RotateEntity(p\Objects\Mesh_Head,0,-60.1,0)
;	EndIf
;	If (EntityPitch(p\Objects\Mesh_Head) > 50) Then
;		RotateEntity(p\Objects\Mesh_Head,49.9,0,0)
;	EndIf
;	If (EntityPitch(p\Objects\Mesh_Head) < -50) Then
;		RotateEntity(p\Objects\Mesh_Head,-50.1,0,0)
;	EndIf
	
EndIf
	
	If o\State = 1 Then
		
		o\FValues[0] = o\FValues[0] + 0.05*d\Delta
		ScaleEntity o\IValues[0], 1+o\FValues[0]*6, 3+o\FValues[0]*6, 1+o\FValues[0]*6
		TurnEntity o\IValues[0], 0, 5*d\Delta, 0
		EntityAlpha o\IValues[0], (1-o\FValues[0])*0.7
		
		ScaleEntity o\Entity, 1.5, 1.5 + Sin (o\FValues[0]*180)*1.5, 1.5
		
		If o\FValues[0] >= 1.0 Then
			ScaleEntity o\IValues[0], 1, 1, 1
			o\FValues[0] = 0
			o\State = 0
			ScaleEntity o\Entity, 1.5, 1.5, 1.5
		EndIf
		
	EndIf
	
End Function

	; =========================================================================================================
	; Object_AmbSound_Create
	; =========================================================================================================

Function Object_AmbSound_Create.tObject(x#, y#, z#, sound$, volume#=1)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_AMBSOUND
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = CreatePivot()
	
	o\SndVolume# = volume#
	
	o\Sound = Load3DSound(Game\Path$ + "\Sounds\" + sound$)
	SoundLoaded$ = Game\Path$ + "\Sounds\" + sound$
	
;	EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, OBJECT_VIEWDISTANCE_MAX#)
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_AmbSound_Update
	; =========================================================================================================

Function Object_AmbSound_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\Position\x# = EntityX#(o\Entity)
	o\Position\y# = EntityY#(o\Entity)
	o\Position\z# = EntityZ#(o\Entity)
	
	ex# = p\EnemyX# - EntityX( p\Objects\Entity ) 
	ey# = p\EnemyY# - EntityY( p\Objects\Entity )
	ez# = p\EnemyZ# - EntityZ( p\Objects\Entity )
	
	If (ChannelPlaying(o\Channel) = False) Then
	
		o\Channel = PlaySnd(o\Sound, o\Entity, o\SndVolume#)
	;PlaySnd(Sounds[o\SoundNum]\sound)
		
	EndIf
	
	If (Paused = 1) Then PauseChannel(o\Channel) : Else ResumeChannel(o\Channel)
	
End Function

	; =========================================================================================================
	; Object_PickUp_Create
	; =========================================================================================================
	; Creates a New SpewRing Object

Function Object_PickUp_Create.tObject(x#, y#, z#, sx#, sy#, sz#, model$, animated, sound$)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_PICKUP
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Sound = Load3DSound("Object\Props\"+sound$)
	
;	o\Speed	= New tVector
;	o\Speed\x# = Rnd#(-0.1,0.1)
;	o\Speed\y# = Rnd#(0.8,1.5)
;	o\Speed\z# = Rnd#(-0.1,0.1)
	
	o\Pivot = CreatePivot()
	PositionEntity(o\Pivot, x#, y#, z#)
		;For p.tPlayer = Each tPlayer
	;RotateEntity(o\Pivot, 0, Rnd#(0.0,359.9), 0)
		;Next
	
	o\Speed	= New tVector
	;If direction#=-1 Then
	;	RotateEntity(o\Pivot, 0, Rnd#(0.0,359.9), 0)
	o\Speed\x# = 0
	o\Speed\y# = -2
	o\Speed\z# = 0
	;Else
	;	RotateEntity(o\Pivot, 0, direction#, 0)
	;	o\Speed\x# = 0.0
	;	o\Speed\y# = 1.0
	;	o\Speed\z# = 0.1
	;End If
	
;	o\Entity = LoadMesh("Objects\Rings\Ring_LOD.b3d", o\Pivot)
	If model = "" Then
		o\Entity = CreateCube()
Else
	o\Entity = LoadMesh("Objects\Props\"+model$)
EndIf
	
		; Make a shadow
	If (LinePick(x#, y#, z#, 0, -OMEGA#, 0)<>0) Then
		o\IValues[0] = CreateQuad(8, 8, 4, 4)
		EntityTexture(o\IValues[0], Textures_Shadow)
		PositionEntity(o\IValues[0], PickedX#(), PickedY#(), PickedZ#(), True)
		
		o\FValues[0] = PickedNX#()
		o\FValues[1] = PickedNY#()
		o\FValues[2] = PickedNZ#()
		
		MoveEntity(o\IValues[0], 0, 0.2, 0)
		ScaleEntity(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1), 1-Min#((y#-PickedY#())/120, 1))
		EntityAlpha(o\IValues[0], 1-Min#((y#-PickedY#())/120, 1))
	End If
	
	EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, OBJECT_VIEWDISTANCE_MAX#)
	ScaleEntity o\Entity, sx#, sy#, sz#
	ScaleEntity o\Pivot, 2, 2, 2
	EntityType(o\Pivot, COLLISION_SPEWRING)
	EntityRadius(o\Pivot, 2.0)
	TranslateEntity o\Entity, x#, y#, z#
	
	If animated = 1 Then Animate o\Entity
	
	o\Timer = 500
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_PickUp_Update
	; =========================================================================================================
	; Updates the ring object

Function Object_PickUp_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	px#=EntityX#(p\Objects\Entity)
	py#=EntityY#(p\Objects\Entity)
	pz#=EntityZ#(p\Objects\Entity)
	
	If (ChannelPlaying(o\Channel)=False) Then o\Channel = PlaySnd(o\Sound, o\Entity, 1)
	
	
;	For c.tCamera = Each tCamera
;		UpdateLOD(o\LOD, c\Entity)
;	Next
	
;	o\Timer = o\Timer+(d\TimeCurrentFrame-d\TimePreviousFrame)
	
	; Stage End Failsafe
	If (Goal = 1 Or Game\Transitioning = 1 Or KeyDown(KEY_HOME)) And o\cState = 1 Then
		EntityType(o\Pivot, COLLISION_SPEWRING)
		EntityParent (o\Pivot, 0)
		EntityParent (o\Entity, 0)
		o\Timer = 0
		o\Timer = MilliSecs()+1000
		o\cState = 0
	EndIf
	
	; Align Shadow
	
	If (o\IValues[0] <> 0) Then
		If (LinePick(o\Position\x#, o\Position\y#-2, o\Position\z#, 0, -OMEGA#, 0)<>0) Then
			AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
			PositionEntity o\IValues[0], o\Position\x#, PickedY#()+.2, o\Position\z#, True
		Else
			AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
			PositionEntity o\IValues[0], o\Position\x#, EntityY#(o\IValues[0]), o\Position\z#, True
		End If
	End If
	
	PositionEntity(o\Entity, EntityX#(o\Pivot), EntityY#(o\Pivot), EntityZ#(o\Pivot))
	
	o\Position\x# = EntityX#(o\Pivot)
	o\Position\y# = EntityY#(o\Pivot, True)
	o\Position\z# = EntityZ#(o\Pivot)
	
	o\Timer = o\Timer + (game\deltaTime\timeCurrentFrame-Game\DeltaTime\TimePreviousFrame)
	
	If o\cState = 0 Then
	
		If ( (o\Position\y#-2)<EntityY#(o\IValues[0], True)) Then
			If (-o\Speed\y#>0.2) Then o\Speed\y# = -(o\Speed\y#+0.85) Else o\Speed\y#=0
			If (o\Speed\z#>0.0) Then o\Speed\z#=(o\Speed\z#-0.09)
			If (o\Speed\z#<0.0) Then o\Speed\z#=0
		;	If o\Speed\Z# < 0 Then o\Speed\Z# = o\Speed\Z# + 0.1*d\Delta#
		;	If (o\Speed\z#>0.0) Then o\Speed\z#=o\Speed\z#-0.01
		;	If (o\Speed\x#>0.0) Then o\Speed\x#=o\Speed\x#-0.01
		End If
	If ( (o\Position\y#-2)>EntityY#(o\IValues[0])) Then o\Speed\y# = o\Speed\y#-0.06
		MoveEntity(o\Pivot, o\Speed\x#*d\Delta, o\Speed\y#*d\Delta, o\Speed\z*d\Delta)
		
	EndIf
	
If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 8.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 8.5) And o\Timer > 300 And o\cState = 0 And Input\Pressed\Interact = True Then
		
	EntityType(o\Pivot, 0)
		
	PlaySnd(Sound_PickUpVox, p\Objects\Entity)
	
	RotateEntity o\Pivot, 0, p\Animation\Direction# + 180, EntityRoll#(p\Objects\Mesh)
	RotateEntity o\Entity, 0, p\Animation\Direction# + 180, EntityRoll#(p\Objects\Mesh)
		
		EntityParent (o\Pivot, p\Objects\PickablePivot)
		EntityParent (o\Entity, p\Objects\PickablePivot)
		PositionEntity o\Pivot, 0, 0, -2
		PositionEntity o\Entity, 0, 0, -2
		o\cState = 1
		
		o\Timer = 0
		o\Timer = o\Timer + GlobalTimer
		
	EndIf
	
	If o\Timer > 300 And o\cState = 1 And Input\Pressed\Interact = True Then
		
		If p\Motion\GroundSpeed# > 1.3 Or p\Motion\Ground = False Then
		
		EntityType(o\Pivot, COLLISION_SPEWRING)
		
		PlaySnd(Sound_ThrowVox, p\Objects\Entity)
		
		o\Speed\x# = 0
		o\Speed\y# = 1
		
		If p\Motion\GroundSpeed# > 1.3 Then
			o\Speed\z# = p\Motion\GroundSpeed# + 0.002
		Else
			o\Speed\z# = 0.8
		EndIf
		
	;	EntityParent (o\IValues[0], 0)
		EntityParent (o\Pivot, 0)
		EntityParent (o\Entity, 0)
		o\cState = 0
		
		o\Timer = 0
		o\Timer = MilliSecs()+1000
		
		RotateEntity o\Pivot, 0, p\Animation\Direction# + 180, 0
		RotateEntity o\Entity, 0, p\Animation\Direction# + 180, 0
		
	EndIf
	If p\Motion\GroundSpeed# <= 1.3 And p\Motion\Ground = True Then
		
		EntityType(o\Pivot, COLLISION_SPEWRING)
		
		o\Speed\x# = 0
		o\Speed\y# = 0.2
		o\Speed\z# = 0
		
	;	EntityParent (o\IValues[0], 0)
		EntityParent (o\Pivot, 0)
		EntityParent (o\Entity, 0)
		o\cState = 0
		
		o\Timer = 0
		o\Timer = MilliSecs()+1000
		
		RotateEntity o\Pivot, 0, p\Animation\Direction# + 180, 0
		RotateEntity o\Entity, 0, p\Animation\Direction# + 180, 0
	EndIf
EndIf
	
	If o\cState = 1 Then
		
		
	;	EntityParent (o\IValues[0], p\Objects\PickablePivot)
		EntityParent (o\Pivot, p\Objects\PickablePivot)
		EntityParent (o\Entity, p\Objects\PickablePivot)
	;	PositionEntity o\Pivot, 0, 0, -2
	;	PositionEntity o\Entity, 0, 0, -2
	;	EntityType(o\Pivot, 0)
	;	RotateEntity o\Pivot, 0, p\Animation\Direction# + 180, 0
	;	RotateEntity o\Entity, 0, p\Animation\Direction# + 180, 0
	;	PositionEntity o\Pivot, EntityX#(p\Objects\PickablePivot), EntityY#(p\Objects\PickablePivot), EntityZ#(p\Objects\PickablePivot), 1
	;	PositionEntity o\Entity, EntityX#(p\Objects\PickablePivot), EntityY#(p\Objects\PickablePivot), EntityZ#(p\Objects\PickablePivot), 1
		
;	MoveEntity o\Pivot, 0, 0, -2
	Else
	;	EntityType(o\Pivot, COLLISION_SPEWRING)
		EntityParent (o\Pivot, 0)
		EntityParent (o\Entity, 0)
		
	EndIf
	
;	If (o\IValues[0] <> 0) Then
;		If (LinePick(o\Position\x#, o\Position\y#-2, o\Position\z#, 0, -OMEGA#, 0)<>0) Then
;			RotateEntity o\IValues[0], 0, EntityYaw#(o\Entity), 0
;			AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
;			PositionEntity o\IValues[0], o\Position\x#, PickedY#()+.2, o\Position\z#, True
;		Else
;			RotateEntity o\IValues[0], 0, EntityYaw#(o\Entity), 0
;			AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
;			PositionEntity o\IValues[0], o\Position\x#, EntityY#(o\IValues[0]), o\Position\z#, True
;		End If
;	End If
	
End Function

	; =========================================================================================================
	; Object_Hint_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Hint_Create.tObject(x#, y#, z#, Hint$)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_HINT
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	o\Sound = LoadSound(Game\Path$ + "\Hints\" + Hint$)
;	SoundLoaded$ = Game\Path$ + "\Hints\" + sound$
		;o\Rotation\x# = rx#
		;o\Rotation\y# = ry#
		;o\Rotation\z# = rz#
		;o\Rail# = Rail#
		;o\StopPlayer# = StopPlayer#
	
	o\Entity = LoadAnimMesh("objects/hintring/hint.b3d")
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#, rz#
	;CreateShadowCaster o\Entity
	Animate(o\Entity)
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_Hint_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Hint_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = o\Position\y# - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
		;o\Hint# = Hint#
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 9.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 9.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 9.5) Then; And (Game\Gameplay\HintTime < MilliSecs) Then
				;Sounds[0] = SoundLoad(Path$ + "Hints\Hint1.wav")
		PlaySnd(Sound_Hint)
		Channel_Hint = PlaySound(o\Sound)
	;	PlaySound(Sounds[o\Sound]\sound)
				;Game\Gameplay\Hint = o\Hint
		FreeEntity o\Entity; : FreeShadowCaster o\Entity 
		Game\Gameplay\HintTime = MilliSecs() + 5000
		Delete o\Position
		Delete o
		Return
	EndIf
	
End Function

	; =========================================================================================================
	; Object_Hintinvis_Create
	; =========================================================================================================
	; Creates a New Spring Object	

Function Object_Hintinvis_Create.tObject(x#, y#, z#, Hint$, radius#)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_HINTINVIS
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	o\Sound = LoadSound(Game\Path$ + "\Hints\" + Hint$)
	o\Radius# = radius#
	SoundLoaded$ = Game\Path$ + "\Hints\" + Hint$
		;o\Rotation\x# = rx#
		;o\Rotation\y# = ry#
		;o\Rotation\z# = rz#
		;o\Rail# = Rail#
		;o\StopPlayer# = StopPlayer#
	
	o\Entity = CreatePivot()
		;EntityType o\Entity, 2
	;	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	;	TurnEntity o\Entity, rx#, ry#, rz#
	;CreateShadowCaster o\Entity
	;	Animate(o\Entity)
	
	Return o
	
End Function

	; =========================================================================================================
	; Object_Hintinvis_Update
	; =========================================================================================================
	; Updates the spring object	

Function Object_Hintinvis_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	vx# = o\Position\x# - EntityX( p\Objects\Entity )
	vy# = o\Position\y# - EntityY( p\Objects\Entity )
	vz# = o\Position\z# - EntityZ( p\Objects\Entity )
		;o\Hint# = Hint#
	
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < o\Radius#) Then; And (Game\Gameplay\HintTime < MilliSecs) Then
				;Sounds[0] = SoundLoad(Path$ + "Hints\Hint1.wav")
			;	PlaySnd(Sound_Hint)
		Channel_Hint = PlaySound(o\Sound)
	;	PlaySound(Sounds[o\Sound]\sound)
				;Game\Gameplay\Hint = o\Hint
		FreeEntity o\Entity; : FreeShadowCaster o\Entity 
		Game\Gameplay\HintTime = MilliSecs() + 5000
		Delete o\Position
		Delete o
		Return
	EndIf
	
End Function

	; =========================================================================================================
	; Object_Gravity_Create
	; =========================================================================================================
	; Creates a New Gravity Object	

Function Object_Gravity_Create.tObject(x#, y#, z#, transtime#, duration#, tx#, ty#, tz#, radius#)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_GRAVITY
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Duration = duration#
	o\TransTime# = transtime#
	o\TiltX# = tx#
	o\TiltY# = ty#
	o\TiltZ# = tz#
	o\Radius# = radius#
	
	o\Entity = CreatePivot()
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#	
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_Gravity_Update
	; =========================================================================================================
	; Updates the Gravity object	

Function Object_Gravity_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\Position\x# = EntityX#(o\Entity)
	o\Position\y# = EntityY#(o\Entity)
	o\Position\z# = EntityZ#(o\Entity)
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < o\Radius#)) Then
		
		Stage_Action_Tilt(o\TransTime#, o\Duration#, o\TiltX#, o\TiltY#, o\TiltZ#)
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	
	
End Function

	; =========================================================================================================
	; Object_Music_Create
	; =========================================================================================================
	; Creates a New Music Object	

Function Object_Music_Create.tObject(x#, y#, z#, music$, radius#, loops)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_MUSIC
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Music = LoadSound("Music/"+music$)
	
	;If loops = 1 Then LoopSound o\Music
	
	o\Loops = loops
	
	o\Radius# = radius#
	
	o\Entity = CreatePivot()
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	
	o\State = 0
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_Music_Update
	; =========================================================================================================
	; Updates the Music object	

Function Object_Music_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\Position\x# = EntityX#(o\Entity)
	o\Position\y# = EntityY#(o\Entity)
	o\Position\z# = EntityZ#(o\Entity)
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < o\Radius#) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < o\Radius#) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < o\Radius#)) And (o\State = 0) Then
		
		Game\Stage\Properties\Music = o\Music
		StopChannel(Game\MusicChn)
		
		Game\MusicChn = PlaySound(Game\Stage\Properties\Music)
		Game\Stage\Properties\MusicLoops = o\Loops
		o\State = 1
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	
End Function
	
	; =========================================================================================================
	; Object_Terminal_Create
	; =========================================================================================================
	; Creates a New Terminal Object	
	
Function Object_Terminal_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, buttons, stageinput=1, stage1$, stage2$, stage3$, stage4$, stage5$, stage6$, stage7$, stage8$, stage9$, stage10$)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_TERMINAL
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Rotation = New tVector
	o\Rotation\x# = rx#
	o\Rotation\y# = ry#
	o\Rotation\z# = rz#
	
	o\Buttons = buttons
	
	o\Stage1$ = stage1$
	o\Stage2$ = stage2$
	o\Stage3$ = stage3$
	o\Stage4$ = stage4$
	o\Stage5$ = stage5$
	o\Stage6$ = stage6$
	o\Stage7$ = stage7$
	o\Stage8$ = stage8$
	o\Stage9$ = stage9$
	o\Stage10$ = stage10$
	
	o\Menu_StageInput = stageinput
	
	o\HeadFocus = CreatePivot()
	
	o\Entity = LoadAnimMesh("objects/terminal/terminal.b3d")
		;EntityType o\Entity, 2
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	TranslateEntity o\HeadFocus, x#, y#, z#
	TurnEntity o\Entity, rx#, ry#+180, rz#
	TurnEntity o\HeadFocus, rx#, ry#+180, rz#
	
	MoveEntity o\HeadFocus, 0, 12, -20
	
	o\State = 0
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_Terminal_Update
	; =========================================================================================================
	; Updates the Terminal object

Global ObjMenu_Win

Global ObjMenu_B1
Global ObjMenu_B2
Global ObjMenu_B3
Global ObjMenu_B4
Global ObjMenu_B5
Global ObjMenu_B6
Global ObjMenu_B7
Global ObjMenu_B8
Global ObjMenu_B9
Global ObjMenu_B10

Global ObjMenu_Edit
Global ObjMenu_Go

Global ObjMenu_Exit

Function Object_Terminal_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\Position\x# = EntityX#(o\Entity)
	o\Position\y# = EntityY#(o\Entity)
	o\Position\z# = EntityZ#(o\Entity)
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 10) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 10) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 10)) And (Input\Pressed\Interact) And (p\UsingTerminal = 0) Then
		
		StartDraw()
		ObjMenu_Win = GUI_CreateWindow(40, 100*Game_Window_Scale#, 500, 200, "Terminal", "", False)
		
	;	ObjMenu_B1 = GUI_CreateButton(ObjMenu_Win, 10, 20, 75, 21, Game\Stage\List\Folder1$, "", True)
		If (o\Buttons >= 1) Then ObjMenu_B1 = GUI_CreateButton(ObjMenu_Win, 10, 50, 75, 21, o\Stage1$, "", True)
		If (o\Buttons >= 2) Then ObjMenu_B2 = GUI_CreateButton(ObjMenu_Win, 90, 50, 75, 21, o\Stage2$, "", True)
		If (o\Buttons >= 3) Then ObjMenu_B3 = GUI_CreateButton(ObjMenu_Win, 170, 50, 75, 21, o\Stage3$, "", True)
		If (o\Buttons >= 4) Then ObjMenu_B4 = GUI_CreateButton(ObjMenu_Win, 250, 50, 75, 21, o\Stage4$, "", True)
		If (o\Buttons >= 5) Then ObjMenu_B5 = GUI_CreateButton(ObjMenu_Win, 330, 50, 75, 21, o\Stage5$, "", True)
		
		If (o\Buttons >= 6) Then ObjMenu_B6 = GUI_CreateButton(ObjMenu_Win, 10, 80, 75, 21, o\Stage6$, "", True)
		If (o\Buttons >= 7) Then ObjMenu_B7 = GUI_CreateButton(ObjMenu_Win, 90, 80, 75, 21, o\Stage7$, "", True)
		If (o\Buttons >= 8) Then ObjMenu_B8 = GUI_CreateButton(ObjMenu_Win, 170, 80, 75, 21, o\Stage8$, "", True)
		If (o\Buttons >= 9) Then ObjMenu_B9 = GUI_CreateButton(ObjMenu_Win, 250, 80, 75, 21, o\Stage9$, "", True)
		If (o\Buttons >= 10) Then ObjMenu_B10 = GUI_CreateButton(ObjMenu_Win, 330, 80, 75, 21, o\Stage10$, "", True)
		
		If o\Menu_StageInput = 1 Then ObjMenu_Edit = GUI_CreateEdit(ObjMenu_Win, 10, 20, 180, "") : ObjMenu_Go = GUI_CreateButton(ObjMenu_Win, 200, 20, 75, 21, "GO", "", True)
		
		ObjMenu_Exit = GUI_CreateButton(ObjMenu_Win, 10, 120, 75, 21, "Exit", "", True)
		
		Channel_PCOpen = PlaySnd(Sound_PCOpen, o\Entity)
		
		p\UsingTerminal = 1
		
		p\Motion\Speed\x# = 0
		p\Motion\Speed\z# = 0
		
		EndDraw()
		
	;	Delete o\Position
	;	Delete o
		Return
	EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 18) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 18) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 18)) And (p\UsingTerminal = 1) Then
	
			; UPDATE TERMINAL MENU
		
		
		If (GUI_AppEvent() = ObjMenu_Go) And (GUI_Message(ObjMenu_Edit, "GetText") <> "") Then StartGame(GUI_Message(ObjMenu_Edit, "GetText"), Sound = 1) : GUI_Message(ObjMenu_Win, "Close") : Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0
		
		
	If (GUI_AppEvent() = ObjMenu_B1) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage1$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B2) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage2$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B3) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage3$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B4) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage4$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B5) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage5$, Sound = 1)
	
	If (GUI_AppEvent() = ObjMenu_B6) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage6$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B7) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage7$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B8) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage8$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B9) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage9$, Sound = 1)
	If (GUI_AppEvent() = ObjMenu_B10) Then Game\StageSelecting = 0 : Game\StartGame = 1 : p\UsingTerminal = 0 : GUI_Message(ObjMenu_Win, "Close") : StartGame(o\Stage10$, Sound = 1)
	
	
	
	If (GUI_AppEvent() = ObjMenu_Exit) Then
		
	;Channel_PCClose = PlaySnd(Sound_PCClose, o\Entity)
		
		MoveMouse(GAME_WINDOW_W Shr 1, GAME_WINDOW_H Shr 1)
		FlushMouse()
		p\UsingTerminal = 0
		GUI_Message(ObjMenu_Win, "Close")
		SetFont GameFont
	EndIf
		; ------------------------
	
EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 20) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 20) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 20)) And (p\UsingTerminal = 1) Then
			; ALIGN HEAD ------------
		If (p\Objects\Mesh_Head <> 0) Then
			If ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#)=0) And (p\Flags\HeadTrackEnabled = 1) Then
				
				p\HeadTrackTimer# = MilliSecs() + 300
				
				PointEntity(p\Objects\Mesh_HeadPoint,o\HeadFocus)
				TurnEntity(p\Objects\Mesh_HeadPoint,0,180,0)
				TFormPoint(0,180,0,o\Entity,p\Objects\Mesh_HeadPoint)
			EndIf
		EndIf
	; -------------------------
	EndIf
	
End Function


; =========================================================================================================
; Object_Platform_Create
; =========================================================================================================
; Creates a New Monitor Object	
Function Object_Platform_Create.tObject(x#, y#, z#)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_PLATFORM
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadMesh("Objects\Platform\Platform.b3d")
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#	
	
	Return o
	
End Function

; =========================================================================================================
; Object_Platform_Update
; =========================================================================================================
; Updates the Platform object	
Function Object_Platform_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	
	; Player collided with Platform
	If ( (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 8.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 8.5) ) And (p\Flags\Platform=0) Then		
		p\Flags\Platform = o\Entity
		;p\Action=ACTION_COMMON
	Else
		p\Flags\Platform=0
	EndIf
End Function


; =========================================================================================================
; Object_Missile_Create
; =========================================================================================================
; Creates a New Missile Object	
Function Object_Missile_Create.tObject(x#, y#, z#)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_MISSILE
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	o\Entity = LoadMesh("Objects\Missile\Missile.b3d")
	;o\Entity = CreateCube()
	ScaleEntity o\Entity, 1.1, 1.1, 1.1
	TranslateEntity o\Entity, x#, y#, z#
	EntityType o\Entity, COLLISION_SPEWRING
	
	Return o
	
End Function

; =========================================================================================================
; Object_Missile_Update
; =========================================================================================================
; Updates the Missile object	
Function Object_Missile_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\TurnTimer = o\TurnTimer + (GlobalTimer)
	
	o\Timer = o\Timer + (GlobalTimer)
	
	;Smooth Turn
	If o\TurnTimer > 200 Then PointEntity o\Entity, p\Objects\Entity : o\TurnTimer = 0
	
	MoveEntity(o\Entity, 0, 0, 0.4*d\Delta#)
	
	
	; --------
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False) And o\State = 0 And (p\Action = ACTION_HOMING)) Then
		
	;	p\Animation\Direction# = ATan2(dz#, dx#)+90
		
		DestroyObject(o, p, 1)
		FreeEntity o\Entity
	;	o\State = 1
	;	o\Timer = 0
		
		If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
		If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
		If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
		
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
		
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If ((Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And (p\Attacking = 1) And (p\Motion\Ground = False)) And o\State = 0 Then
		
		DestroyObject(o, p, 1)
		FreeEntity o\Entity
	;	o\State = 1
	;	o\Timer = 0
		
		If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
		If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
		If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
		
	;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
	;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
		
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5 And (p\Attacking = 1) And (p\Motion\Ground = True)) And o\State = 0 Then
		
		DestroyObject(o, p, 2)
		FreeEntity o\Entity
	;	o\State = 1
	;	o\Timer = 0
		
		If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
		If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
		If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
		
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If (Abs(EntityX(p\Objects\Entity) - EntityX#(o\Entity)) < 6.5) And (Abs(EntityY(p\Objects\Entity) - EntityY#(o\Entity)) < 6.5) And (Abs(EntityZ(p\Objects\Entity) - EntityZ#(o\Entity)) < 6.5) And p\Action <> ACTION_HOMING Then
		
		ObjectHurt(p, 1)
		
	;	PointEntity(p\Objects\Mesh, o\Entity)
	;	TurnEntity(p\Objects\Mesh,-60,180,0)
	;	TFormPoint(-60,180,0,o\Entity, p\Objects\Mesh)
	;	p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
		
	;	StopChannel(Channel_Bomber)
		Channel_Explode = PlaySnd(Sound_Explode, o\Entity, 2)
		
		FreeEntity o\Entity
		Delete o\Position
		Delete o
		Return
		
	EndIf
	; --------
	
	If o\Timer > 4000 Then
		
		FreeEntity o\Entity
		Delete o\Position
		Delete o
		Return
		
	EndIf
	
End Function

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   MUTLIPLAYER
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; =========================================================================================================
	; Object_Player2_Create
	; =========================================================================================================
	; Creates a New Monitor Object	

Function Object_Player2_Create.tObject(x#, y#, z#, rx#=0, ry#=0, rz#=0, number)
	
	o.tObject = New tObject
	
	o\ObjType = OBJTYPE_PLAYER2
	
	o\Position = New tVector	
	o\Position\x# = x#
	o\Position\y# = y#
	o\Position\z# = z#
	
	;o\Number = number
	
;	If Player2Character=0
	o\Entity = CreatePivot()
	o\Entity1 = CopyEntity(Mesh_Sonic)
	o\Where = CreateSphere()
;	EndIf 
	
	
;	EntityAutoFade(o\Entity, OBJECT_VIEWDISTANCE_MIN#, 90000)
;	EntityType o\Entity1, 2
	ScaleEntity o\Entity1, 1, 1, 1
	TranslateEntity o\Entity1, x#, y#, z#	
	
	ScaleEntity o\Where, 20, 20, 20
	TranslateEntity o\Where, x#, y#+20, z#
	EntityColor(o\Where,255,155,155)
	EntityFX(o\Where, 1)
	
	Return o
	
End Function


	; =========================================================================================================
	; Object_Player2_Update
	; =========================================================================================================
	; Updates the Player2 object	

Function Object_Player2_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
		;If Player2Character=0
		;o\Entity = LoadMesh("Objects\Mplayersign\Sign.b3d")
		;EndIf 
;	If Player2Character=0
	;	o\Entity = Mesh_Sonic
;	EndIf 
	
;	Player2PositionX=EntityX(o\Entity)
;	Player2PositionY=EntityY(o\Entity)
;	Player2PositionZ=EntityZ(o\Entity)
	
	;Player2Animation=p\Animation\Animation
	
;	Player2RotationX=EntityPitch(o\Entity)
;	Player2RotationY=EntityYaw(o\Entity)
;	Player2RotationZ=EntityRoll(o\Entity)
	
;	Player2PositionX = EntityX#(o\Entity1)
;	Player2PositionY = EntityY#(o\Entity1)
;	Player2PositionZ = EntityZ#(o\Entity1)
	
;	If Player2connected=1
	
	PositionEntity o\Entity1, Player2PositionX,Player2PositionY+0.3,Player2PositionZ
	RotateEntity o\Entity1, Player2RotationX, Player2RotationY, Player2RotationZ
	
	PositionEntity o\Where, Player2PositionX,Player2PositionY+70,Player2PositionZ
	
	If WherePlayers = 1 Then
		ShowEntity o\Where
	Else
		HideEntity o\Where
	EndIf
	
	PositionEntity o\Entity, Player2PositionX,Player2PositionY,Player2PositionZ
	
;	For c.tCamera = Each tCamera
;		RenderEntity o\Where, c\Entity, 2
;	Next
	
	
	
	
	If (Player2Animation<>Player2OldAnimation) Then
		
		Select Player2Animation
			Case 0
				RecursiveAnimate(o\Entity1, 1, 0.5, 1, 8)
			Case 1
				RecursiveAnimate(o\Entity1, 1, 0.9, 2, 0)
				;	p\Animation\Time# = 0.0
			Case 2
				RecursiveAnimate(o\Entity1, 1, 2.2, 3, 3)
				;	p\Animation\Time# = 0.0
				;	RecursiveSetAnimTime(o\Entity1, 0, 3)
			Case 3
				RecursiveAnimate(o\Entity1, 1, 1.3, 4, 4)
			Case 4
				RecursiveAnimate(o\Entity1, 1, 0.8, 5, 8)
			Case 5
				RecursiveAnimate(o\Entity1, 1, 0.7, 6, 0)
			Case 6
				RecursiveAnimate(o\Entity1, 3, 0.8, 7, 0)
				;	p\Animation\Time# = 0.0
			Case 7
				RecursiveAnimate(o\Entity1, 3, 0.4, 8, 0)
			Case 8
				RecursiveAnimate(o\Entity1, 1, 0.6, 9, 0)
			Case 9
				RecursiveAnimate(o\Entity1, 1, 0.6, 10, 0)
			Case 10
				RecursiveAnimate(o\Entity1, 1, 0.6, 11, 0)
			Case 11
				RecursiveAnimate(o\Entity1, 1, -1.3, 4, 8)
			Case 12
				RecursiveAnimate(o\Entity1, 1, 0.5, 12, 8)
			Case 13
				RecursiveAnimate(o\Entity1, 1, 1.0, 13, 4)
			Case 14
				RecursiveAnimate(o\Entity1, 1, 0.9, 13, 0)
				p\Animation\Time# = 0.0
			Case 15
				RecursiveAnimate(o\Entity1, 1, 0.9, 14, 0)
			Case 16
				RecursiveAnimate(o\Entity1, 1, 0.9, 15, 11)
			Case 17
				RecursiveAnimate(o\Entity1, 1, 0.9, 16, 15)
			Case 18
				RecursiveAnimate(o\Entity1, 1, 0.9, 17, 0)
			Case 19
				RecursiveAnimate(o\Entity1, 1, 0.9, 18, 0)
			Case 20
				RecursiveAnimate(o\Entity1, 3, 0.8, 19, 0)
			Case 21
				RecursiveAnimate(o\Entity1, 1, 0.85, 20, 7)
			Case 22
				RecursiveAnimate(o\Entity1, 3, 0.5, 21, 3)
				PlayRandomIdleSound()
			Case 23
				RecursiveAnimate(o\Entity1, 3, 0.5, 22, 3)
				PlayRandomIdleSound()
			Case 24
				RecursiveAnimate(o\Entity1, 3, 0.5, 23, 4)
			Case 25
				RecursiveAnimate(o\Entity1, 1, 1.5, 24, 4)
			Case 26
				RecursiveAnimate(o\Entity1, 1, 0.5, 25, 6)
			Case 27
				RecursiveAnimate(o\Entity1, 1, 2.3, 4, 4)
			Case 28
				RecursiveAnimate(o\Entity1, 1, 1.6, 27, 12)
			Case 29
				RecursiveAnimate(o\Entity1, 3, 0.6, 28, 3)
			Case 30
				RecursiveAnimate(o\Entity1, 3, 0.6, 29, 7)
			Case 31
				RecursiveAnimate(o\Entity1, 3, 0.55, 30, 5)
				p\Animation\Time# = 0.0
				
			Case 32
				RecursiveAnimate(o\Entity1, 3, 0.9, 31, 5)
			Case 33
				RecursiveAnimate(o\Entity1, 1, 1.0, 32, 5)
			Case 34
				RecursiveAnimate(o\Entity1, 1, 0.8, 33, 9)
			Case 35
				RecursiveAnimate(o\Entity1, 3, 0.8, 34, 9)
			Case 36
				RecursiveAnimate(o\Entity1, 1, 1.7, 35, 0)
			Case 37
				RecursiveAnimate(o\Entity1, 1, 1.7, 36, 0)
			Case 38
				RecursiveAnimate(o\Entity1, 1, 1.7, 37, 0)
			Case 39
				RecursiveAnimate(o\Entity1, 1, 1.7, 38, 0)
		End Select

		

Player2OldAnimation = Player2Animation
EndIf

;EndIf

End Function

Function DestroyObject(o.tObject, p.tPlayer, Scenario, Sound=1)
	
	p\EnemyPicked = 0
;	If (o\IValues[3]) Then FreeEntity o\IValues[3] : o\IValues[3]=0
;	If (o\IValues[2]) Then FreeEntity o\IValues[2] : o\IValues[2]=0
;	If (o\IValues[1]) Then FreeEntity o\IValues[1] : o\IValues[1]=0
;	If (o\IValues[0]) Then FreeEntity o\IValues[0] : o\IValues[0]=0
	
	Select Scenario
				Case 1
					
			; Air Kill
					PlayRandomDashSound()
					
					p\Motion\Speed\y# = 1.5
					
					If p\BB_InMove = 0 Then
						p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.4
						p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.4
					Else
					EndIf
					
					If p\Motion\Speed\y# < 0 Then 
						p\Motion\Speed\y# = -p\Motion\Speed\y#
					EndIf
					
					If (Sound = 1) Then
						PlayRandomDamageSound()
					EndIf
					
					If (Sound = 2) Then
						PlaySound(Sound_Balloon)
					EndIf
					
					If (p\Motion\Ground = False) Then
						RandomTrick()
						p\Action = ACTION_HOMEJUMP
						p\Motion\Speed\y# = 1
					EndIf
					If (p\Motion\Ground = True) Then
						
					EndIf
					
					
			;		FreeEntity o\Entity
			;		
			;		Delete o\Position
			;		Delete o
			;		Return
					
				Case 2
					
				; Ground Kill
					
					
					If (Sound = 1) Then
						PlayRandomDamageSound()
					EndIf
					
					If (Sound = 2) Then
						Channel_Balloon = PlaySound(Sound_Balloon)
					EndIf
					
					If (Sound = 3) Then
						Channel_TV = PlaySound(Sound_TV)
					EndIf
					
				;	FreeEntity o\Entity
				;	
				;	Delete o\Position
				;	Delete o
				;	Return
					
			End Select
End Function

Function ObjectHurt(p.tPlayer, Scenario)
	If (p\HurtTimer < MilliSecs()) Then
	
	Select Scenario
		Case 1
			
			RingLossCount(p)
			
			Channel_Hurt = PlaySound(Sound_Hurt)
			PlayRandomHurtSound
			
			If ((Game\Gameplay\Rings >= 1) And (p\HurtTimer < MilliSecs())) Then
				
				
				
				p\Motion\Speed\y# = 0.7
				p\Motion\Speed\x# = Sin(p\Animation\Direction#)*-1.3
				p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*-1.3
			p\Motion\Ground = False
			p\Action = ACTION_HURT
			
			
			p\LoseRings = 1
			p\LoseLife = 0
			
			For d.tDeltaTime = Each tDeltaTime
			;	TriDRingLoss(p, d)
			Next
			
			Game\Gameplay\Rings = Game\Gameplay\Rings - p\RingLossCount
			If (p\HurtTimer < MilliSecs()) Then p\HurtTimer = MilliSecs() + 3500
		EndIf
		
		If ((Game\Gameplay\Rings <= 0) And (p\HurtTimer < MilliSecs())) Then
			
			p\Motion\Speed\y# = 1.7
			p\Motion\Speed\x# = 0
			p\Motion\Speed\z# = 0
			p\Motion\Ground = False
			p\Action = ACTION_HURT
			
			p\LoseRings = 0
			p\LoseLife = 1
			
		EndIf
		
	Case 2
		p\Motion\Speed\y# = 1.7
		p\Motion\Speed\x# = 0
		p\Motion\Speed\z# = 0
		p\Motion\Ground = False
		p\Action = ACTION_HURT
		
		Channel_Hurt = PlaySound(Sound_Hurt)
		PlayRandomHurtSound
		
		p\LoseRings = 0
		p\LoseLife = 1
		
	Case 3
		BackToCheckpoint(p)
		p\Action = ACTION_HURT
		p\LoseRings = 0
		p\LoseLife = 0
		
	Case 4
		p\Motion\Speed\y# = 0.7
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*-1.0
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*-1.0
		p\Motion\Ground = False
		p\Action = ACTION_HURT
		
		p\LoseRings = 0
		p\LoseLife = 0
		
	Case 5
		p\Motion\Ground = False
		p\Action = ACTION_FALL
		p\FallTimer = 6000
		
		p\LoseRings = 0
		p\LoseLife = 0
		
End Select

EndIf

End Function

Function RingLossCount(p.tPlayer)
	;Game\Gameplay\Rings = Game\Gameplay\Rings - p\RingLossCount
	
	If (Game\Gameplay\Rings >= 20) Then
		p\RingLossCount = 20
	EndIf
	
	If (Game\Gameplay\Rings < 20) Then
		p\RingLossCount = Game\Gameplay\Rings
	EndIf
	
;	Game\Gameplay\OneHLife		= 0
;	Game\Gameplay\TwoHLife		= 0
;	Game\Gameplay\ThreeHLife	= 0
;	Game\Gameplay\FourHLife		= 0
;	Game\Gameplay\FiveHLife		= 0
;	Game\Gameplay\SixHLife	= 0
;	Game\Gameplay\SevenHLife		= 0
;	Game\Gameplay\EightHLife		= 0
;	Game\Gameplay\NineHLife	= 0
End Function
			
			
					
	
	
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   ALL OBJECTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

Function HomingTarget()
;	For o.tObject = Each tObject
;	PositionEntity HomingTarget,o\Position\x#,o\Position\y#,o\Position\z#
;	If Channel_Target = False Then
;		Channel_Target = PlaySound (Sound_Target)
;	EndIf
;Next
End Function
	
Function Objects_Update(d.tDeltaTime)
	
	; Setup some local variables
	Local nObj.tObject
	Local edNearest#
	Local edDistance#
	
	; Set the starting distance to 10,000
	edNearest# = 15000
	
	; Footstep Sounds
		For p.tPlayer = Each tPlayer
			
			If (Game\Gameplay\Foot_Walk=1) Then
		;	If (p\Frame > 11) And (p\Frame < 19) And (p\Animation\Animation = 1) And (p\Step1Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 1 : p\Step2Done = 0
		;	If (p\Frame > 24) And (p\Frame < 30) And (p\Animation\Animation = 1) And (p\Step2Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 0 : p\Step2Done = 1
				If (p\Frame > Game\Gameplay\Foot_WFrame1-3) And (p\Frame < Game\Gameplay\Foot_WFrame1+3) And (p\Animation\Animation = 1) And (p\Step1Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 1 : p\Step2Done = 0
				If (p\Frame > Game\Gameplay\Foot_WFrame2-3) And (p\Frame < Game\Gameplay\Foot_WFrame2+3) And (p\Animation\Animation = 1) And (p\Step2Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 0 : p\Step2Done = 1
		EndIf
			
		If (Game\Gameplay\Foot_Jog1=1) Then
		;	If (p\Frame > 11) And (p\Frame < 19) And (p\Animation\Animation = 13) And (p\Step1Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 1 : p\Step2Done = 0
		;	If (p\Frame > 24) And (p\Frame < 30) And (p\Animation\Animation = 13) And (p\Step2Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 0 : p\Step2Done = 1
			If (p\Frame > Game\Gameplay\Foot_J1Frame1-3) And (p\Frame < Game\Gameplay\Foot_J1Frame1+3) And (p\Animation\Animation = 13) And (p\Step1Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 1 : p\Step2Done = 0
			If (p\Frame > Game\Gameplay\Foot_J1Frame2-3) And (p\Frame < Game\Gameplay\Foot_J1Frame2+3) And (p\Animation\Animation = 13) And (p\Step2Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 0 : p\Step2Done = 1
		EndIf
		
		If (Game\Gameplay\Foot_Jog1=1) Then
			If (p\Frame > Game\Gameplay\Foot_J1Frame1-3) And (p\Frame < Game\Gameplay\Foot_J1Frame1+3) And (p\Animation\Animation = 14) And (p\Step1Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 1 : p\Step2Done = 0
			If (p\Frame > Game\Gameplay\Foot_J1Frame2-3) And (p\Frame < Game\Gameplay\Foot_J1Frame2+3) And (p\Animation\Animation = 14) And (p\Step2Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 0 : p\Step2Done = 1
		EndIf
		
		If (Game\Gameplay\Foot_Run=1) Then
			If (p\Frame > Game\Gameplay\Foot_R1Frame1-3) And (p\Frame < Game\Gameplay\Foot_R1Frame1+3) And (p\Animation\Animation = 19) And (p\Step1Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 1 : p\Step2Done = 0 : SetEmitter(p\Objects\TempPiv, p\Particles\Step1)
			If (p\Frame > Game\Gameplay\Foot_R1Frame2-3) And (p\Frame < Game\Gameplay\Foot_R1Frame2+3) And (p\Animation\Animation = 19) And (p\Step2Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 0 : p\Step2Done = 1 : SetEmitter(p\Objects\TempPiv, p\Particles\Step1)
		EndIf
		
		If (Game\Gameplay\Foot_Sprint=1) Then
			If (p\Frame > Game\Gameplay\Foot_R2Frame1-3) And (p\Frame < Game\Gameplay\Foot_R2Frame1+3) And (p\Animation\Animation = 2) And (p\Step1Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 1 : p\Step2Done = 0 : SetEmitter(p\Objects\TempPiv, p\Particles\Step2)
			If (p\Frame > Game\Gameplay\Foot_R2Frame2-3) And (p\Frame < Game\Gameplay\Foot_R2Frame2+3) And (p\Animation\Animation = 2) And (p\Step2Done = 0) Then 		PlayRandomStepSound() : p\Step1Done = 0 : p\Step2Done = 1 : SetEmitter(p\Objects\TempPiv, p\Particles\Step2)
		EndIf
		
	; Idle
		;	If (p\Frame > 118) And (p\Animation\Idle = 0) Then p\Animation\Idle = 1 : RandomIdle(p, d)
			
		;	If (p\Frame > 68) And (p\Animation\Idle = 1) Then p\Animation\Animation = 0
		
		;PositionEntity HomingTarget,p\EnemyX#,p\EnemyY#,p\EnemyZ#
		;HandleSprite HomingTarget,EnemyX#,EnemyY#
			
	For o.tObject = Each tObject
							; Get object distance
			edDistance# = EntityDistance(p\Objects\Entity, o\Entity)
			
									; Check if the distance is less than the current distance, and make it the player's object.
			If edDistance# < edNearest# Then 
				edNearest# = edDistance#
				nObj.tObject = o
				
					; Set nearest object
				If o\Homing=True Then p\Objects\Obj = o
			
			;	If (p\Action = ACTION_RINGDASH) Then
					
						; !! need code to check if ring is in front or behind Sonic
					
		;	If (p\Objects\Ring = o\Entity) Or (p\Objects\Enemy = o\Entity) Then
		;		p\FValues[0]=edNearest#
		;	Else
		;		p\FValues[0]=100
		;	EndIf
				
				; ----------------------
				; RING DASH CODE
				; ----------------------
			
	;			If (o\ObjType=OBJTYPE_RING And o\Gotten = 0 And p\HasRingTarget = 0) Then
	;			
	;					; !! need code to check if ring is in front or behind Sonic
	;			;	If o\Entity <> 0 Then
	;				p\Objects\Ring = o\Entity
	;				
	;			;	If (p\Action = ACTION_RINGDASH) Then
	;			;		p\HasRingTarget = 1
	;			;	Else
	;			;		p\HasRingTarget = 0
	;			;	EndIf
	;				
	;				p\FValues[0]=edNearest#
	;			Else
	;				p\FValues[0]=100
	;			End If
	;			
	;		EndIf
		EndIf
			
			;If (p\Action = ACTION_HOMING Or o\ObjType=OBJTYPE_RING) Then
		;		If p\Objects\Enemy = o\Entity Or p\Objects\Ring = o\Entity
		;		p\FValues[0]=edNearest#
		;	Else
		;		p\FValues[0]=100
		;	EndIf
	;	EndIf
	;	EndIf
			
			
			;	If ((o\ObjType=OBJTYPE_ENEMY) Or (o\ObjType=OBJTYPE_FLYENEMY) Or (o\ObjType=OBJTYPE_SPRING) Or (o\ObjType=OBJTYPE_SPRING2) Or (o\ObjType=OBJTYPE_SPRING3) Or (o\ObjType=OBJTYPE_SPRING4) Or (o\ObjType=OBJTYPE_BOMBER) Or (o\ObjType=OBJTYPE_MONITOR) Or (o\ObjType=OBJTYPE_HOMINGNODE) Or (o\ObjType=OBJTYPE_BALLOON)) Then
				
			
				
						; !! need code to check if enemy is in front or behind Sonic
				
			;	p\Objects\Enemy = o\Entity
			;	p\FValues[0]=edNearest#
		;	Else
		;		p\FValues[0]=100
		;	End If
	;	End If
		
		
		
		; Terminal FailSafe
		If (GUI_AppEvent() = ObjMenu_Exit) Then
			
	;Channel_PCClose = PlaySnd(Sound_PCClose, o\Entity)
			
			MoveMouse(GAME_WINDOW_W Shr 1, GAME_WINDOW_H Shr 1)
			FlushMouse()
			p\UsingTerminal = 0
			GUI_Message(ObjMenu_Win, "Close")
			SetFont GameFont
		EndIf
		; ------------------
		
			
						; Point the homing device
			If (p\Objects\Obj<>Null) Then
				PointEntity p\Objects\AimingDevice, p\Objects\Obj\Entity
			End If
			
			Select o\ObjType
				Case OBJTYPE_PLAYER2
					Object_Player2_Update(o, p, d)
			End Select
			
			If o\Entity <> 0 Then
				
				
				
				If (EntityDistance(p\Objects\Entity, o\Entity) < OBJECT_VIEWDISTANCE_MAX#) Then
					ShowEntity(o\Entity)
					Select o\ObjType
				
						Case OBJTYPE_NULL
						
						Case OBJTYPE_RING
							Object_Ring_Update(o, p, d)
							
						Case OBJTYPE_SPRING
							Object_Spring_Update(o, p, d)	
							
						Case OBJTYPE_SPRING2
							Object_Spring2_Update(o, p, d)	
							
						Case OBJTYPE_SPRING3
							Object_Spring3_Update(o, p, d)	
							
						Case OBJTYPE_SPRING4
							Object_Spring4_Update(o, p, d)	
	
						Case OBJTYPE_MONITOR
							Object_Monitor_Update(o, p, d)								
				
						Case OBJTYPE_BUMPER
							Object_Bumper_Update(o, p, d)	
							
						Case OBJTYPE_GOAL
							Object_Goal_Update(o, p, d)	
							
						Case OBJTYPE_GOALINVIS
							Object_GoalInvis_Update(o, p, d)
							
						Case OBJTYPE_CAMLOCK
							Object_CamLock_Update(o, p, d)
							
						Case OBJTYPE_HOMINGNODE
							Object_HomingNode_Update(o, p, d)
							
						Case OBJTYPE_BOMBER
							Object_Bomber_Update(o, p, d)
							
						Case OBJTYPE_HOOP
							Object_Hoop_Update(o, p, d)
							
						Case OBJTYPE_TRICKHOOP
							Object_TrickHoop_Update(o, p, d)
							
						Case OBJTYPE_FLYENEMY
							Object_FlyEnemy_Update(o, p, d)
							
						Case OBJTYPE_ENEMY
							Object_Enemy_Update(o, p, d)
							
						Case OBJTYPE_DASHPAD
							Object_DashPad_Update(o, p, d)
							
						Case OBJTYPE_CHECK
							Object_Check_Update(o, p, d)
							
						Case OBJTYPE_SPEWRING
							Object_SpewRing_Update(o, p, d)
							
						Case OBJTYPE_NPC
							Object_NPC_Update(o, p, d)
							
						Case OBJTYPE_DASHPADNEW
							Object_DashPadNew_Update(o, p, d)
							
						Case OBJTYPE_RINGLOST
							Object_RingLost_Update(o, p, d)
							
						Case OBJTYPE_DASHRAMP
							Object_DashRamp_Update(o, p, d)
							
						Case OBJTYPE_AMBSOUND
							Object_AmbSound_Update(o, p, d)
							
						Case OBJTYPE_BALLOON
							Object_Balloon_Update(o, p, d)
							
						Case OBJTYPE_PICKUP
							Object_PickUp_Update(o, p, d)
							
						Case OBJTYPE_DESTSPRING
							Object_DestSpring_Update(o, p, d)
							
						Case OBJTYPE_HINTINVIS
							Object_Hintinvis_Update(o, p, d)
							
						Case OBJTYPE_HINT
							Object_Hint_Update(o, p, d)
							
						Case OBJTYPE_GRAVITY
							Object_Gravity_Update(o, p, d)
							
						Case OBJTYPE_MUSIC
							Object_Music_Update(o, p, d)
							
						Case OBJTYPE_TERMINAL
							Object_Terminal_Update(o, p, d)
							
						Case OBJTYPE_PLATFORM
							Object_Platform_Update(o, p, d)
							
						Case OBJTYPE_MISSILE
							Object_Missile_Update(o, p, d)
							
				;		Case OBJTYPE_PLAYER2
				;			Object_Player2_Update(o, p, d)
							
						
					End Select
				Else
					HideEntity(o\Entity)
				End If
				
				
				
			EndIf
		Next
	Next
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D