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
Type tPlayer
		; Player shortcutz
		Field Objects.tPlayer_Objects
		Field Motion.tPlayer_Motion
		Field Flags.tPlayer_Flags
		Field Animation.tPlayer_Animation
		Field Particles.tPlayer_Particles
		
		Field Profile.tPlayer_Profile

		; Other values
		Field Character
		Field Action
		Field SpindashCharge#
		Field SDCharge#
		Field Attacking
		Field Frame
		Field HomingTimer
		Field SDTimer
		Field RingDashTimer
		Field JumpImageShow
		Field JumpTrailReset
		Field EnemyPicked
		Field FlashStatus
		Field Held
		Field TurnTimer
		
		Field SpringTimer#
		
		Field HeadIsTracking
		
		; Misc Timers
		Field AnimTimer
		Field AnimTimer1
		Field AnimTimer2
		Field AnimTimer3
		
		Field FlashTimer
		
		Field ActionBTimer
		
		Field HeadTrackTimer#
		
		Field FallTimer
		Field FallTimerStarted
		
		Field WillLandHard
		
		Field HomingEffectAlpha#
		Field HomingEffectAlphaNeg#
		
		Field BounceHeight#
		Field BounceHeightLimit#
		
		Field RingLossCount
		Field LoseRings
		Field LoseLife
		Field HurtTimer
		
		; Homing Stuff
		Field Homing
		Field RingDash
		Field CanRingDash
		Field HasRingTarget
		
		Field Step1Done
		Field Step2Done
		
		Field LandStopped
		Field LandMoving
		
		Field Charge1
		Field Charge2
		Field Charge3
		
		Field EnemyX#
		Field EnemyY#
		Field EnemyZ#
		
		Field StartPosX#
		Field StartPosY#
		Field StartPosZ#
		
	;	Field StartX#
	;	Field StartY#
	;	Field StartZ#
	;	Field StartPos
	;	Field StartPosDel
		
		Field DefStartX#
		Field DefStartY#
		Field DefStartZ#
		
		;Jump Image
		Field Im1X#
		Field Im1Y#
		Field Im1Z#
		
		Field Im2X#
		Field Im2Y#
		Field Im2Z#
		
		Field Im3X#
		Field Im3Y#
		Field Im3Z#
		
		Field PP_x#
		Field PP_y#
		Field PP_z#
		
		Field dx#
		Field dz#
		Field rx#
		Field ry#
		Field rz#
		
		Field CamX#
		Field CamY#
		Field CamZ#
		
		Field DestX#
		Field DestY#
		Field DestZ#
		
		Field Trail
		Field TrailOn
		Field TrailTimer
		
		Field StopsAtDest
		Field LaunchSpeed#
		
		Field DiveTimer
		Field UsingTerminal
		
		Field Grinding
		
		; IValues and FValues will be replaced soon with easier to read fields
		
		; IValues
		; 0=lightdash setter
		; 1=lightdash millisecs
		; 2=hard slam boolean
		Field IValues[4]
		Field IValues1[4]
		Field IValuesE[4]
		
		; FValues
		; 0=ring distance
		; 1=prev ring distance
		; 2=lightdash speed
		; 3=splash/slam alpha
		Field FValues#[4]
		Field FValues1[4]
		Field FValuesE#[4]
		
		;----------
		
		;Menu
		Field InMenu
		;----------
		
	;	Field Listener
		
		Field CheckPointX#
		Field CheckPointY#
		Field CheckPointZ#
		
		Field DashRunOutTimer
		
	; Barrier Blast
		Field BB_UpToSpeed
		Field BB_InMove
		Field BB_Timer
		Field BB_CanDo
		
		Field BB_Sub
		
	; Alternate Character Values
		
	End Type
	
	Type tSegment
		Field Entity
		Field x#,y#,z#
		Field segType
		Field Pivot
		Field AAMesh.tAAMesh
		Field IValues[3]
		Field FValues#[3]
		
		Field IValues1[3]
		Field FValues1#[3]
	End Type
	
		; Part types
	Const PARTTYPE_DEFAULT = 0
	Const PARTTYPE_JR = 1
	Const PARTTYPE_BLOOMINATOR = 2
	Const PARTTYPE_AFTERIMAGE = 3
	Const PARTTYPE_SPEEDEFFECT = 4


	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Objects
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Objects
		Field Entity
		Field Camera.tCamera
		Field Shadow
		Field JumpBall
		Field Mesh
		Field Mesh_Balance
		Field PointsToEntity
		
		Field RailPointer
		
		Field Mesh_Head
		Field Mesh_HeadPoint
		
		Field Mesh_Mouth
		
		Field HeadPitch#
		Field HeadYaw#
		Field HeadRoll#
		
		Field HeadOptX#
		Field HeadOptY#
		Field HeadOptZ#
		
		Field HeadPointPitch#
		Field HeadPointYaw#
		Field HeadPointRoll#
		
		Field Mesh_Arms
		Field Mesh_Torso
		Field Mesh_Legs
		Field Mesh_LHand
		Field Mesh_Shield
		Field Mesh_JumpBall
		Field Mesh_SpeedBubble
		Field Mesh_DashBubble
		Field Mesh_Spindash
	;	Field JumpImage2
	;	Field JumpImage3
		Field PPivot
		Field Box
		Field PickablePivot
		Field Pickable
		Field WallCubeDetect
		
		Field R_GrindAffector
		Field L_GrindAffector
		
		; Timers
		Field AnimTimer
		Field AnimTimer1
		Field AnimTimer2
		Field AnimTimer3
		
		; After-Image
		Field JumpImage1.tSegment[8]
		Field AfterImage.tSegment[8]
		Field SpeedEffect.tSegment[8]
		
		; Homing Objects
		Field Ring
		Field AimingDevice
		Field LightDevice
		Field Obj.tObject
		Field Enemy
		Field Home
		Field PointsToSpring
		
		Field Template
		Field Dust
		Field TempPiv
	End Type 
	
	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Motion
	; ---------------------------------------------------------------------------------------------------------
	; Contains information of player object's motion, such as speed and
	; other values.
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Motion
		Field Speed.tVector
		Field Align.tVector
		Field Ground
		
		;11SS11 Derp Tilting
		Field ConstantRate#
		
		Field Direction1#
		Field TurnSpeed#
		Field TiltMultiplier#
		
		Field TiltAmount#
		Field TiltAmountTotal#
		Field TiltAmountPlus#
		Field TiltSmoothed#
		
		Field TiltLimitHigh#
		Field TiltLimitLow#
		;-------------------
		
		Field Walking
		Field GroundSpeed#
		Field GroundSpeed1#
		Field HeadTransSpeed#
		Field StopSpeed#
		
		Field GroundNormalAlign.tVector	;!!!!!!!!!!!!!!!!!!!!!!!!
	End Type
	
	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Flags
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Flags
		Field AllowCommonInput
		Field AllowXZMovement
		Field AllowYMovement
		Field AllowSkidding
		Field Hurt
		Field Shield
		Field Invincibility
		Field SpeedSneakers
		Field Skidding
		Field Tilting
		
		Field HeadTrackEnabled
		Field BBlastEnabled
		Field Platform
	End Type

	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Animation
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Animation
		Field Animation
		Field PreviousAnimation
		Field Time#
		Field Direction#
		Field DirectionLocked#
		Field Tilt#
		Field Align.tVector
		Field Align1.tVector
		Field Idle
		
		Field ToJog
		
		; Footstep
		Field Foot_Walk
		Field Foot_Jog1
		Field Foot_Jog2
		Field Foot_Run
		Field Foot_Sprint
		
		Field Foot_WFrame1
		Field Foot_WFrame2
		
		Field Foot_J1Frame1
		Field Foot_J1Frame2
		
		Field Foot_J2Frame1
		Field Foot_J2Frame2
		
		Field Foot_R1Frame1
		Field Foot_R1Frame2
		
		Field Foot_R2Frame1
		Field Foot_R2Frame2
		Field SingleRunAnim
	End Type 
	
	Type tPlayer_AnimSequence
		Field Walk
		Field Run1
		Field Run2
		Field Run3
		Field Boost
		Field Idle
		Field Skid
		Field Fall
		Field Spring
		Field Pick
		Field JumpDash
	End Type
	
	; ---------------------------------------------------------------------------------------------------------	
	; tPlayerTag_Animation
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayerTag_Animation
		Field Animation
		Field PreviousAnimation
		Field Time#
		Field Direction#
		Field DirectionLocked#
		Field Tilt#
		Field Align.tVector
		Field Align1.tVector
		Field Idle
	End Type 
	
	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_AnimTrigs
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_AnimTrigs
		
		Field a1
		Field f1
		
		Field a2
		Field f2
		
	End Type
	
	; ---------------------------------------------------------------------------------------------------------	
	; tPlayer_Particles
	; ---------------------------------------------------------------------------------------------------------
	Type tPlayer_Particles
		
		Field Land
		Field SDash
		Field Step1
		Field Step2
		Field Rock
		
	End Type

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; Character values
	Const CHARACTER_SONIC			= 0
	Const CHARACTER_TAILS			= 1
	Const CHARACTER_KNUCKLES		= 2
	Const CHARACTER_TAG				= 3
	
	Global ModelSelect$
	
	; Action constants
	Const ACTION_COMMON				= 0
	Const ACTION_JUMP				= 1
	Const ACTION_CROUCH				= 2
	Const ACTION_SPINDASH			= 3
	Const ACTION_FALL				= 4
	Const ACTION_JUMPDASH			= 5
	Const ACTION_SPRING				= 6
	Const ACTION_SPRINGFALL			= 7
	Const ACTION_STOMP				= 8
	Const ACTION_HOMING				= 9
	Const ACTION_HOMEJUMP			= 10
	Const ACTION_ROLL				= 11
	Const ACTION_KICK				= 12
	Const ACTION_RINGDASH			= 13
	Const ACTION_LAND				= 14
	Const ACTION_SLIDE				= 15
	Const ACTION_JUMPPART2			= 16
	Const ACTION_STOMPPART2			= 17
	Const ACTION_HURT				= 18
	Const ACTION_RAMP				= 19
	Const ACTION_LANDHARD			= 20
	Const ACTION_BOUNCE				= 21
	Const ACTION_DESTSPRING			= 22
	Const ACTION_SKIDDING			= 23
	Const ACTION_DIVE				= 24
	Const ACTION_SPINDASHNEW		= 25
	Const ACTION_GLIDE				= 26
	Const ACTION_FLY				= 27
	Const ACTION_WALLHIT			= 28
	Const ACTION_CLIMB				= 29
	Const ACTION_LEDGE				= 30
	Const ACTION_DOUBLEJUMP			= 31
	Const ACTION_DIVEINTOROLL		= 32

	; Common values
	Global COMMON_XZACCELERATION#	= 0.20			;0.052875 ;0.046875
	Global COMMON_XZDECELERATION#	= 0.026
	Const COMMON_SKIDDINGFACTOR#	= 0.005
	
	Global COMMON_XZTOPSPEED#			= 5.7
	
	Global COMMON_XZTOPSPEEDSONIC#		= 6.1
	Global COMMON_XZTOPSPEEDTAILS#		= 4.2
	Global COMMON_XZTOPSPEEDKNUCKLES#	= 3.9
	
	Global COMMON_XZMAXSPEED#		= 10.0
	Const COMMON_XZABSOLUTESPEED#	= 10.0
	Global COMMON_YACCELERATION#	= 0.04
	Global COMMON_YTOPSPEED#		= -3.0
	
	Global COMMON_LIGHTDASH_SPEED#	= 3.8
	
	;Roll Code
	Const COMMON_ROLLWEIGHT#		= 0.10
	Const COMMON_ROLLWEIGHT_UP#		= 0.06
	Const COMMON_ROLLWEIGHT_DOWN#	= 0.1
	Global ROLL_WEIGHT_MULTIPLIER#	= 1.0

	; Motion values
	Global MOTION_GROUND#			= 0.55			;0.65
	Global MOTION_CEILING#			= -0.65
	Global MOTION_CEILING_STOP#		= -0.79
	Global MOTION_WALL_UP#			= -0.7
	Global MOTION_WALL_DOWN#		= 0.2
	Global MOTION_WALL_DIRECTION#	= 0.3

	; Jump values
	Const JUMP_STRENGHT#			= 1.4 ;1.4
	Const JUMP_STRENGHT_VARIABLE#	= 0.7 ;0.7
	
	; Alt Characters
	Global COMMON_GLIDEDESCENTIONRATE#	= -0.4
	
	;//////////////////////////////////////
	;//////////////////////////////////////
	; NEW ANTI-HOVERCRAFT CONSTANTS
	Global ANTI_SLIDING_FACTOR#		= 2.7 ; Default = 1
										  ; Higher = faster acceleration in the new direction.
										  ; 3 or more will cause faster acceleration when turning, 
										  ; then when moving straight!!
	
	Global TURNING_SHARPNESS#		= 2 ; Default = 3
										  ; Lower = faster turning. This is how quickly Sonic will turn
										  ; to face in the new direction. Not too low or sonic will look
										  ; jerky! Not too high (not more than 4) or Sonic will take too
										  ; long to turn around!
	
	Const MOTION_DEVIATION_FACTOR#	= -0.4 ; Default = -0.0
										  ; Lower = less likely to skid. Setting too high will cause too
										  ; much skidding. Too low and Sonic won't skid at all!
	
										  ; The following values are the amounts by which skidding and
										  ; compensation effect Sonic. Think of them as the trigger which
										  ; makes Sonic skid. Higher = more likely to compensate.
	Const DEVIATION_HIGH_FACTOR#	= 0.4 ; Default = 0.4
	Const DEVIATION_MILD_FACTOR#	= 0.95 ; Default = 0.95
	Const DEVIATION_LOW_FACTOR#		= 1.01 ; Default = 1.01
	Const DEVIATION_SUBTRACT#		= 0.01; Default = 0.02
	
											  ; Ammounts by which to compensate. Lower = sharper turning.
	Const HIGH_DEVIATION_COMPENSATION#	= 6.0 ; Default = 33.0 - 9.0
	Const MILD_DEVIATION_COMPENSATION#	= 3.5 ; Default = 19.0 - 7.5
	Const LOW_DEVIATION_COMPENSATION#	= 1.0 ; Default = 15.0 - 4.0
	;//////////////////////////////////////
	;//////////////////////////////////////
	
			; Declarate acceleration and speed vectors and setup.
	Global Accelerationp.tVector
	Global Speedp.tVector
	Global SpeedCompensation.tVector
	Global Speed_Length#
	Global DotProduct#

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Creation/Destruction and management ----
	; =========================================================================================================
	; Player_Create
	; =========================================================================================================
Function Player_Create.tPlayer(Character=0)
		; Create new player object
	p.tPlayer 	= New tPlayer
		
	;	If (xmlErrorCount()>0) Then PlaySound(Sound_Error) : RuntimeError("Something may be wrong with your character. Character path: Characters/" + ModelSelect$)

		; Create objects
		p\Objects 	= New tPlayer_Objects
		p\Motion  	= New tPlayer_Motion
		p\Flags		= New tPlayer_Flags
		p\Animation	= New tPlayer_Animation
		p\Particles	= New tPlayer_Particles
		
		p\Motion\Speed    = Vector(0, 0, 0)
		p\Motion\Align 	  = Vector(0, 1, 0)
		p\Animation\Align = Vector(0, 1, 0)
		
		; TILT
		p\Motion\TiltMultiplier# = -8.5
		p\Motion\TiltLimitHigh# = 170
		p\Motion\TiltLimitLow# = -p\Motion\TiltLimitHigh#
		
	;	p\Listener = CreateListener(p\Objects\Entity, 0, 1, 1)
		
		p\Motion\GroundNormalAlign = Vector(0, 0, 0)	;!!!!!!!!!!!!!!!
		
		p\DashRunOutTimer = 0
		p\Attacking = 0
		p\Homing = 0
		p\InMenu = 0
		
		p\LoseRings = 0
		p\LoseLife = 0
		p\BounceHeight# = 0.7
		
		p\Objects\Entity  = CreatePivot(Game\Stage\Root)
		p\Animation\Animation = 0
		
		p\Objects\AimingDevice = CreatePivot(p\Objects\Entity)
		p\Objects\LightDevice = CreatePivot(p\Objects\Entity)
		
		p\Objects\PointsToEntity  = CreatePivot(p\Objects\Entity)
	;	p\Objects\SpeedEffect = CopyEntity(Mesh_Sonic_SpeedEffect, Game\Stage\Root)
		
		p\Objects\PPivot	= CreatePivot()
	;	PositionEntity (p\Objects\PPivot, EntityX#(p\Objects\Entity)+2, EntityY#(p\Objects\Entity), EntityZ#(p\Objects\Entity))
	;	EntityParent (p\Objects\PPivot, p\Objects\Entity)
	;	RotateEntity (p\Objects\PPivot,90,0,0)
		
		p\Flags\AllowCommonInput = Trye
		p\Flags\AllowXZMovement  = True
		p\Flags\AllowYMovement   = True
		
		p\Flags\HeadTrackEnabled = HeadTrackingEnabled
		p\Flags\BBlastEnabled = BBlastEnabled
		
	;	p\Action = Game\Stage\StartAction
		
		; -----------------------------------
		; PARTICLE CREATE
		; -----------------------------------
		
		p\Objects\Template = CreateTemplate()
		p\Particles\Land = CreateTemplate()
	;	p\Particles\SDash = CreateTemplate()
		p\Particles\Step1 = CreateTemplate()
		p\Particles\Step2 = CreateTemplate()
		p\Particles\Rock = CreateTemplate()
		
	;	SetTemplateEmitterBlend(p\Objects\Template, 1)
	;	SetTemplateInterval(p\Objects\Template, 45)
		
	;	SetTemplateEmitterBlend(p\Particles\SDash, 1)
		
	;	SetTemplateEmitterBlend(p\Particles\Step1, 1)
		
			;	SetTemplateParticleLifeTime(p\Objects\Template, 60, 85)
		SetTemplateTexture(p\Objects\Template, "Textures\Dust.png", 2, 1)
		SetTemplateOffset(p\Objects\Template, -.3, .3, -.3, .3, -.3, .3)
		SetTemplateVelocity(p\Objects\Template, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(p\Objects\Template, True)
		
		SetTemplateTexture(p\Particles\Land, "Textures\Dust.png", 2, 1)
		SetTemplateOffset(p\Particles\Land, -.3, .3, -.3, .3, -.3, .3)
		SetTemplateVelocity(p\Particles\Land, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(p\Particles\Land, True)
		
		SetTemplateTexture(p\Particles\Step1, "Textures\Dust.png", 2, 1)
		SetTemplateOffset(p\Particles\Step1, -.3, .3, 0.3, 0.3, -.3, .3)
		SetTemplateVelocity(p\Particles\Step1, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(p\Particles\Step1, True)
		
		SetTemplateTexture(p\Particles\Step2, "Textures\Dust.png", 2, 1)
		SetTemplateOffset(p\Particles\Step2, -.3, .3, 0.3, 0.3, -.3, .3)
		SetTemplateVelocity(p\Particles\Step2, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(p\Particles\Step2, True)
		
		SetTemplateTexture(p\Particles\Rock, "Textures\Rock.png", 2, 1)
		SetTemplateOffset(p\Particles\Rock, -.3, .3, 0.3, 0.3, -.3, .3)
		SetTemplateVelocity(p\Particles\Rock, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(p\Particles\Rock, True)
		
		; -----------------------------------
		
		
		; Setup character values
		p\Character = Character
		Select p\Character
			Case CHARACTER_SONIC
				; Load mesh
				LoadModel(ModelSelect$, p)
				
				p\Objects\Mesh 			= CopyEntity(Mesh_Sonic, Game\Stage\Root)
			;	p\Objects\AnimTimer		= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer1	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer2	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer3	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
				
			;	If (FXEnabled = 1) Then CreateShadowCaster p\Objects\Mesh
				
				p\Objects\Box			= CreateCube()
				ScaleEntity (p\Objects\Box, 2, 2, 2)
				HideEntity p\Objects\Box
				
				SonTmp					= p\Objects\Template
				p\Objects\TempPiv		= CreatePivot()

				p\Objects\Mesh_Balance	= FindChild(p\Objects\Mesh, "Bone01")
			;	
				p\Objects\Mesh_HeadPoint 	= CreatePivot()
				EntityParent(p\Objects\Mesh_HeadPoint, p\Objects\Mesh)
				
				p\Objects\Mesh_Head 	= FindChild(p\Objects\Mesh, "HeadTrack")
				p\Objects\Mesh_Mouth 	= FindChild(p\Objects\Mesh, "MouthTrack")
				
			;	As not to undo all the Head Tracking code
				p\Objects\Mesh_Head		= CreatePivot()
				
				p\Objects\Mesh_Arms 	= FindChild(p\Objects\Mesh, "Arms")
				p\Objects\Mesh_Torso 	= FindChild(p\Objects\Mesh, "Torso")
				p\Objects\Mesh_Legs 	= FindChildEntity(p\Objects\Mesh, "Legs")
				p\Objects\Mesh_LHand 	= FindChild(p\Objects\Mesh, "L_Hand")
				
				CreateLS5Trail(1)
			;	Hide_Trail(p\Trail)
				
			;	p\Objects\Mesh_Head = FindChildEntity(p\Objects\Mesh, "Head")
				
			;	If (FindChild(p\Objects\Mesh, "Head")<>0) Then
			;		p\Objects\Mesh_Head = CopyMesh(FindChild(p\Objects\Mesh, "Head"))
			;	EndIf
				
				;p\Objects\Dust = LoadTexture("Textures\dust.png",4)
				
				If (Mesh_Sonic_SpeedBubble <> 0) Then
					p\Objects\Mesh_SpeedBubble = CopyEntity(Mesh_Sonic_SpeedBubble, Game\Stage\Root)
				ScaleEntity(p\Objects\Mesh_SpeedBubble, 1, 1, 1)
				Animate p\Objects\Mesh_SpeedBubble
				HideEntity p\Objects\Mesh_SpeedBubble
				AlignToVector p\Objects\Mesh_SpeedBubble, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2, 100
			EndIf
				
				If (Mesh_Sonic_SpeedBubble <> 0) Then
					p\Objects\Mesh_DashBubble = CopyEntity(Mesh_Sonic_DashBubble, Game\Stage\Root)
				ScaleEntity(p\Objects\Mesh_DashBubble, 1, 1, 1)
				HideEntity(p\Objects\Mesh_DashBubble)
				Animate p\Objects\Mesh_DashBubble
				AlignToVector p\Objects\Mesh_DashBubble, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2, 100
			EndIf
				
			;	For c.tCamera = Each tCamera
			;		CreateAAMesh(p\Objects\Mesh,c\Entity,5,3,1,0.0001,255,255,255,0.6,1.0,1,0)
			;Next
				
			;	TurnEntity(p\Objects\Mesh_SpeedBubble, 0, 180, 0)
			;	TurnEntity(p\Objects\Mesh_DashBubble, 0, 180, 0)
			
				

			Case CHARACTER_TAILS
				
												; Load mesh
				LoadModel(ModelSelect$, p)
				
				p\Objects\Mesh 			= CopyEntity(Mesh_Sonic, Game\Stage\Root)
			;	p\Objects\AnimTimer		= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer1	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer2	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer3	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
				If (FXEnabled = 1) Then CreateShadowCaster p\Objects\Mesh
				
				p\Objects\Box			= CreateCube()
				ScaleEntity (p\Objects\Box, 2, 2, 2)
				HideEntity p\Objects\Box
				
				SonTmp					= p\Objects\Template
				p\Objects\TempPiv		= CreatePivot()
				
				p\Objects\Mesh_Balance	= FindChild(p\Objects\Mesh, "Bone01")
				
				p\Objects\Mesh_HeadPoint 	= CreatePivot()
				EntityParent(p\Objects\Mesh_HeadPoint, p\Objects\Mesh)
				
				p\Objects\Mesh_Head 	= FindChild(p\Objects\Mesh, "HeadTrack")
				p\Objects\Mesh_Mouth 	= FindChild(p\Objects\Mesh, "MouthTrack")
				
			;	As not to undo all the Head Tracking code
			;	p\Objects\Mesh_Head		= CreatePivot()
				
				p\Objects\Mesh_Arms 	= FindChild(p\Objects\Mesh, "Arms")
				p\Objects\Mesh_Torso 	= FindChild(p\Objects\Mesh, "Torso")
				p\Objects\Mesh_Legs 	= FindChildEntity(p\Objects\Mesh, "Legs")
				p\Objects\Mesh_LHand 	= FindChild(p\Objects\Mesh, "L_Hand")
				
				CreateLS5Trail(1)
				
				
	;			Return p
				
			Case CHARACTER_KNUCKLES
				
								; Load mesh
				LoadModel(ModelSelect$, p)
				
				p\Objects\Mesh 			= CopyEntity(Mesh_Sonic, Game\Stage\Root)
			;	p\Objects\AnimTimer		= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer1	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer2	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
			;	p\Objects\AnimTimer3	= CopyEntity(Mesh_Sonic_AnimTimer, Game\Stage\Root)
				If (FXEnabled = 1) Then CreateShadowCaster p\Objects\Mesh
				
				p\Objects\Box			= CreateCube()
				ScaleEntity (p\Objects\Box, 2, 2, 2)
				HideEntity p\Objects\Box
				
				SonTmp					= p\Objects\Template
				p\Objects\TempPiv		= CreatePivot()
				
				p\Objects\Mesh_Balance	= FindChild(p\Objects\Mesh, "Bone01")
				
				p\Objects\Mesh_HeadPoint 	= CreatePivot()
				EntityParent(p\Objects\Mesh_HeadPoint, p\Objects\Mesh)
				
				p\Objects\Mesh_Head 	= FindChild(p\Objects\Mesh, "HeadTrack")
				p\Objects\Mesh_Mouth 	= FindChild(p\Objects\Mesh, "MouthTrack")
				
			;	As not to undo all the Head Tracking code
			;	p\Objects\Mesh_Head		= CreatePivot()
				
				p\Objects\Mesh_Arms 	= FindChild(p\Objects\Mesh, "Arms")
				p\Objects\Mesh_Torso 	= FindChild(p\Objects\Mesh, "Torso")
				p\Objects\Mesh_Legs 	= FindChildEntity(p\Objects\Mesh, "Legs")
				p\Objects\Mesh_LHand 	= FindChild(p\Objects\Mesh, "L_Hand")
				
				CreateLS5Trail(1)
				
				
				
		End Select
		
		; ---------------------------
		; LOAD CHARACTER SFX
		; ---------------------------
		
		; Jump
		Sound_JumpVox1				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/JumpVox1.wav")
		Channel_JumpVox1				= 0
		Sound_JumpVox2				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/JumpVox2.wav")
		Channel_JumpVox2				= 0
		Sound_JumpVox3				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/JumpVox3.wav")
		Channel_JumpVox3				= 0	
		
		; JumpDash
		Sound_DashVox1				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/DashVox1.wav")
		Channel_DashVox1				= 0
		Sound_DashVox2				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/DashVox2.wav")
		Channel_DashVox2				= 0
		Sound_DashVox3				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/DashVox3.wav")
		Channel_DashVox3				= 0	
		
		; Hurt
		Sound_HurtVox1				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/HurtVox1.wav")
		Channel_HurtVox1				= 0
		Sound_HurtVox2				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/HurtVox2.wav")
		Channel_HurtVox2				= 0
		Sound_HurtVox3				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/HurtVox3.wav")
		Channel_HurtVox3				= 0
		
		; Kick
		Sound_KickVox1				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/KickVox1.wav")
		Channel_KickVox1				= 0
		Sound_KickVox2				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/KickVox2.wav")
		Channel_KickVox2				= 0
		Sound_KickVox3				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/KickVox3.wav")
		Channel_KickVox3				= 0
		
		; Misc
		Sound_PickUpVox				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/PickUpVox.wav")
		Channel_PickUpVox				= 0
		Sound_ThrowVox				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/ThrowVox.wav")
		Channel_ThrowVox				= 0
		
		; Idle
		Sound_IdleVox1				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/IdleVox1.wav")
		Channel_IdleVox1				= 0
		Sound_IdleVox2				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/IdleVox2.wav")
		Channel_IdleVox2				= 0
		Sound_IdleVox3				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/IdleVox3.wav")
		Channel_IdleVox3				= 0
		Sound_IdleVox4				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/IdleVox4.wav")
		Channel_IdleVox4				= 0
		Sound_IdleVox5				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/IdleVox5.wav")
		Channel_IdleVox5				= 0
		Sound_IdleVox6				= Load3DSound("Characters/" + ModelSelect$ + "/Voice/IdleVox6.wav")
		Channel_IdleVox6				= 0
		
		; ---------------------------
		
	;	DebugLog(ModelSelect$ + "/Voice/KickVox3.wav")
		
		
;		p1.tPlayerTag 	= New tPlayerTag
;				; Create objects
;		p1\Objects 	= New tPlayerTag_Objects
;		p1\Motion  	= New tPlayerTag_Motion
;		p1\Flags		= New tPlayerTag_Flags
;		p1\Animation	= New tPlayerTag_Animation
;		
;				; Setup character values
;		p1\Character = Character
;		Select p1\Character
;	Case CHARACTERS_TAG
;		
;		; Create objects
;		p1\Objects 	= New tPlayerTag_Objects
;		p1\Motion  	= New tPlayerTag_Motion
;		p1\Flags		= New tPlayerTag_Flags
;		p1\Animation	= New tPlayerTag_Animation
;		
;		p1\Motion\Speed    = Vector(0, 0, 0)
;		p1\Motion\Align 	  = Vector(0, 1, 0)
;		p1\Animation\Align = Vector(0, 1, 0)
;		
;		p1\Objects\Entity  = CreatePivot(Game\Stage\Root)
;		
;		p1\Flags\AllowCommonInput = True
;		p1\Flags\AllowXZMovement  = True
;		p1\Flags\AllowYMovement   = True
;		
;				; Load mesh
;		p1\Objects\Mesh 			= CopyEntity(Mesh_Sonic, Game\Stage\Root)
;				;ScaleEntity(p\Objects\Mesh, GAME_SCALE#, GAME_SCALE#, GAME_SCALE#)
;		
;					;	p1\Objects\Mesh_Balance	= FindChild(p1\Objects\Mesh, "Bone01")
;					;	p1\Objects\Mesh_Head 	= FindChild(p1\Objects\Mesh, "Head")
;		p1\Objects\Mesh_Arms 	= FindChild(p1\Objects\Mesh, "Arms")
;		p1\Objects\Mesh_Torso 	= FindChild(p1\Objects\Mesh, "Torso")
;		p1\Objects\Mesh_Legs 	= FindChild(p1\Objects\Mesh, "Legs")
;		
					;	p1\Objects\Mesh_Spindash = CopyEntity(Mesh_Sonic_Spindash, Game\Stage\Root)
					;	p1\Objects\Mesh_JumpBall = CopyEntity(Mesh_Sonic_JumpBall, p\Objects\Mesh)
					;	ScaleEntity(p1\Objects\Mesh_Spindash, GAME_SCALE#, GAME_SCALE#, GAME_SCALE#)
					;	HideEntity p1\Objects\Mesh_Spindash
;End Select

		
		

		; Done
;		Return p
		
		
		
								; Create shadow quad
		p\Objects\Shadow = CreateQuad(8, 8, 4, 4, Game\Stage\Root)
		EntityTexture(p\Objects\Shadow, Textures_Shadow)
		
		; Setup pivot collision
		EntityType(p\Objects\Entity, COLLISION_PLAYER)
		EntityRadius(p\Objects\Entity, 2.20)
		
		; Setup the pivot for pickable entities
		p\Objects\PickablePivot = CreatePivot(p\Objects\Mesh)
		MoveEntity p\Objects\PickablePivot, 0, 0, -2
		
		; Setup the pivots for Grinding
		p\Objects\R_GrindAffector = CreateCylinder(8, 1, p\Objects\Mesh)
		MoveEntity p\Objects\R_GrindAffector, -1.5, 0, -1.5
		EntityType p\Objects\R_GrindAffector, COLLISION_GRINDAFFECTOR
	;	HideEntity p\Objects\R_GrindAffector
		EntityAlpha p\Objects\R_GrindAffector, 0
		
		p\Objects\L_GrindAffector = CreateCylinder(8, 1, p\Objects\Mesh)
		MoveEntity p\Objects\L_GrindAffector, 1.5, 0, -1.5
		EntityType p\Objects\L_GrindAffector, COLLISION_GRINDAFFECTOR
	;	HideEntity p\Objects\L_GrindAffector
		EntityAlpha p\Objects\L_GrindAffector, 0
		
		; CREATE WALL DETECTOR
		p\Objects\WallCubeDetect=CreateCube(p\Objects\Mesh)
		EntityType p\Objects\WallCubeDetect,COLLISION_BOXDETECT,1
		PositionEntity p\Objects\WallCubeDetect,0,1.1,-3.1
		EntityAlpha p\Objects\WallCubeDetect, 0
		
		; TEMPORARY OBJECT FOR CHECKING PICKABLE PIVOT!! REMOVE WHEN FINISHED EDITING!!
	;	p\Objects\Pickable = CreateCube(p\Objects\PickablePivot)
	;	MoveEntity p\Objects\Pickable, 0, 0, -2
		
		p\Objects\RailPointer = CreateCube()
		HideEntity p\Objects\RailPointer
		
		Create_AfterImages(p)
		Create_SpeedEffects(p)
	;	Create_Player_Jump_Trail(p)
		
		If (Game\CreatedCharacter = 1) Then
			For	c.tCamera= Each tCamera
				Camera_Bind(c, p)
			Next
		EndIf
		
		
		
		Return p
		
End Function

	; =========================================================================================================
	; CalculPlayerInfo
	; =========================================================================================================
Function CalculPlayerInfo(p.tPlayer)	
	PlayerPositionX=EntityX(p\Objects\Entity)
	PlayerPositionY=EntityY(p\Objects\Entity)
	PlayerPositionZ=EntityZ(p\Objects\Entity)
	
	PlayerAnimation=p\Animation\Animation
	
	PlayerRotationX=EntityPitch(p\Objects\Mesh)
	PlayerRotationY=EntityYaw(p\Objects\Mesh)
	PlayerRotationZ=EntityRoll(p\Objects\Mesh)
	
;	Player2PositionX=EntityX(p\Objects\Entity)
;	Player2PositionY=EntityY(p\Objects\Entity)
;	Player2PositionZ=EntityZ(p\Objects\Entity)
	
;	Player2Animation=p\Animation\Animation
	
;	Player2RotationX=EntityPitch(p\Objects\Mesh)
;	Player2RotationY=EntityYaw(p\Objects\Mesh)
;	Player2RotationZ=EntityRoll(p\Objects\Mesh)
End Function


	; =========================================================================================================
	; Player_Destroy
	; =========================================================================================================
Function Player_Destroy(p.tPlayer)
	
;	FreeEntity p\Listener
	
	For i = 0 To 3
			;If (p\Objects\JumpBall[i]\Entity<>0) Then
			;	FreeEntity p\Objects\JumpBall[i]\Entity
		;	Delete p\Objects\JumpImage1[i]
		;EndIf
		
			;If (p\Objects\AfterImage[i]\Entity<>0) Then
			;	FreeEntity p\Objects\AfterImage[i]\Entity
		;	Delete p\Objects\AfterImage[i]
		;EndIf
	;	For seg.tSegment = Each tSegment
	;		FreeEntity p\Objects\AfterImage[i]\Entity
	;	Next
	Next
	
	If (p\Objects\Mesh <> 0) Then FreeShadowCaster p\Objects\Mesh
		FreeEntity(p\Objects\Entity)
		FreeEntity(p\Objects\Mesh)
		FreeEntity p\Objects\Shadow
		FreeEntity(p\Objects\Mesh_DashBubble)
		FreeEntity(p\Objects\Mesh_SpeedBubble)
		FreeEntity(p\Objects\Box)
		FreeEntity Mesh_Sonic
		
		Delete p\Motion\Speed
		Delete p\Motion\Align
		Delete p\Animation\Align
		Delete p\Objects
		Delete p\Motion
		Delete p\Animation
		Delete p\Flags
		Delete p\Particles
	;	Delete p\AnimTrigs
		
		Free_Trail(1)
		
		
		
		
	End Function


	; =========================================================================================================
	; Player_Update
	; =========================================================================================================
Function Player_Update(p.tPlayer, d.tDeltaTime)
	
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	p\Motion\GroundSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
	p\TurnTimer = p\TurnTimer + GlobalTimer
	
	;EntityParent(p\Objects\R_GrindAffector, p\Objects\Entity)
	;EntityParent(p\Objects\L_GrindAffector, p\Objects\Entity)
	
	PositionEntity p\Objects\R_GrindAffector, -1.5, 0, -1.5
	PositionEntity p\Objects\L_GrindAffector, 1.5, 0, -1.5
	PositionEntity p\Objects\PointsToEntity, EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity)-2,EntityZ#(p\Objects\Entity)
	
		; Perform player's movement
		Player_Motion(p, d)
		
		Player_ParticleManagement(p, d)
		
		PositionEntity p\Objects\TempPiv, EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity)-2,EntityZ#(p\Objects\Entity)
		RotateEntity p\Objects\TempPiv, EntityPitch#(p\Objects\Mesh),EntityYaw#(p\Objects\Mesh)-2,EntityRoll#(p\Objects\Mesh)
	;	AlignToVector p\Objects\TempPiv, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2,100
		
		PositionEntity p\Objects\RailPointer, EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh)
		
		; UPDATE WALL DETECTOR
		PositionEntity p\Objects\WallCubeDetect,0,1.5,-3.1
		
	;	If (EntityDistance#(p\Objects\WallCubeDetect, p\Objects\Entity)>10) Then p\Action = ACTION_LEDGE
		
		
	;	For i = 1 To CountCollisions(p\Objects\WallCubeDetect)
	;	Col1 = GetEntityType(CollisionEntity(p\Objects\WallCubeDetect, CountCollisions(p\Objects\WallCubeDetect)))
			
		If p\Action = ACTION_GLIDE Then
			If CountCollisions(p\Objects\WallCubeDetect)>0 Then
				p\Action=ACTION_CLIMB
			EndIf
	EndIf
		
	
		
;	Next
		
		; Handle actions
		Player_Handle(p, d)
		Select p\Action
			Case ACTION_COMMON
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
			;	p\AbleToJumpDash = 0
				Player_Action_Common(p, d)
			Case ACTION_JUMP
				AirSlow()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
			;	p\AbleToJumpDash = 1
				Player_Action_Jump(p, d)
			Case ACTION_JUMPPART2
				AirSlow()
				p\Attacking = 1
				p\CanRingDash = 1
			;	---*JumpImage Code Inside Animation File*---
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
			;	p\AbleToJumpDash = 1
				Player_Action_JumpPart2(p, d)
			Case ACTION_CROUCH
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
			;	p\AbleToJumpDash = 1
				Player_Action_Crouch(p, d)
			Case ACTION_SPINDASH
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Spindash(p, d)
			Case ACTION_FALL
				AirSlow()
				p\Attacking = 0
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Fall(p, d)
			Case ACTION_JUMPDASH
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_JumpDash(p, d)
			Case ACTION_SPRING
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Spring(p, d)
			Case ACTION_SPRINGFALL
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_SpringFall(p, d)
			Case ACTION_STOMP
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 1
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Stomp(p, d)
			Case ACTION_HOMING
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Homing(p, d)
			Case ACTION_HOMEJUMP
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 1
			;	---*JumpImage Code Inside Animation File*---
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_HomeJump(p, d)
			Case ACTION_ROLL
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Roll(p, d)
			Case ACTION_KICK
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Kick(p, d)
			Case ACTION_RINGDASH
				p\JumpImageShow = 0
				p\CanRingDash = 0
				AirNormal()
				p\Attacking = 1
				p\LandMoving = 0 : p\LandStopped = 0
				Player_Action_RingDash(p, d)
			Case ACTION_LAND
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Land(p, d)
			Case ACTION_SLIDE
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Slide(p, d)
			Case ACTION_STOMPPART2
				AirNormal()
				p\Attacking = 1
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_StompPart2(p, d)
			Case ACTION_HURT
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Hurt(p, d)
			Case ACTION_RAMP
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\LandMoving = 0 : p\LandStopped = 0
				p\RingDash = 0
				Player_Action_Ramp(p, d)
			Case ACTION_LANDHARD
				AirNormal()
				p\Attacking = 0
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_LandHard(p, d)
			Case ACTION_BOUNCE
				p\Attacking = 1
				AirSlow()
				p\CanRingDash = 1
			;	---*JumpImage Code Inside Animation File*---
				p\RingDash = 0
				Player_Action_Bounce(p, d)
			Case ACTION_DESTSPRING
				p\Attacking = 0
				AirSlow()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_DestSpring(p, d)
			Case ACTION_SKIDDING
				p\Attacking = 0
				AirSlow()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Skidding(p, d)
			Case ACTION_DIVE
				p\Attacking = 0
				AirSlow()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Dive(p, d)
			Case ACTION_SPINDASHNEW
				p\Attacking = 1
				AirSlow()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_SpinDashNew(p, d)
			Case ACTION_GLIDE
				p\Attacking = 1
				AirNormal()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Glide(p, d)
			Case ACTION_FLY
				p\Attacking = 0
				AirNormal()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Fly(p, d)
			Case ACTION_WALLHIT
				p\Attacking = 0
				AirNormal()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_WallHit(p, d)
			Case ACTION_CLIMB
				p\Attacking = 0
				AirNormal()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Climb(p, d)
			Case ACTION_LEDGE
				p\Attacking = 0
				AirNormal()
				p\CanRingDash = 0
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_Ledge(p, d)
			Case ACTION_DOUBLEJUMP
				p\Attacking = 1
				AirNormal()
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_DoubleJump(p, d)
			Case ACTION_DIVEINTOROLL
				p\Attacking = 1
				AirNormal()
				p\CanRingDash = 1
				p\JumpImageShow = 0
				p\RingDash = 0
				Player_Action_DiveIntoRoll(p, d)
		End Select
		
		;StrLevel = BASS_ChannelGetLevel(Stream)
	;	TurnEntity(p\Objects\Mesh_Mouth,(-StrLevel),0,0)
		
	;	PlayerCollisions% = PlayerCollisions + CountCollisions(p\Objects\Entity)
	;	ResetEntity p\Objects\Entity
		
		For c.tCamera = Each tCamera
		For m.MeshStructure = Each MeshStructure
	;		If (m\CamEntity <> 0) And (m\Entity <> 0) Then
	;			If (MeshesIntersect(p\Objects\Box, m\CamEntity)=True) Then
	;				
	;				c\UseCoord = 1
	;				c\Held = 1
	;			ElseIf (MeshesIntersect(p\Objects\Box, m\Entity)=True) Then; And (MeshesIntersect(p\Objects\Box, m\CamEntity)=False)
	;				c\UseCoord = 0
	;				c\Held = 0
						
			;	If ChannelPlaying(Channel_Error)=False Then Channel_Error = PlaySound(Sound_Error)
	;		EndIf
	;	EndIf
	Next
Next
		
		; Set Up Timers
		
;p\AnimTimer = RecursiveAnimTime(p\Objects\AnimTimer)
;p\AnimTimer1 = RecursiveAnimTime(p\Objects\AnimTimer1)
;p\AnimTimer2 = RecursiveAnimTime(p\Objects\AnimTimer2)
;p\AnimTimer3 = RecursiveAnimTime(p\Objects\AnimTimer3)
		
		; Jumpdash Timer Failsafe
		
;If (Not p\Action = ACTION_JUMPDASH) Then RecursiveAnimate(p\Objects\AnimTimer1, 3, 1, 2, 0)

If p\Motion\Ground = True Then p\FallTimer = MilliSecs() + 300


If (p\Character = CHARACTER_SONIC) Then

		; BARRIER BLAST ------------

;p\BB_Timer = RecursiveAnimTime(p\Objects\AnimTimer1)
;p\BB_Sub = p\BB_Timer + (d\Delta#*(Game\Others\FPS*0.05))
		
		; Sonic slows to stop the BarrierBlast
		If (p\Flags\BBlastEnabled = 1) Then
			
			If ((SonicSpeed# <= 3.5) And (p\Action <> ACTION_JUMPDASH)) Then
			p\BB_InMove = 0
		;	RecursiveAnimate(p\Objects\AnimTimer, 3, 0.5, 2, 0)
			p\BB_UpToSpeed = 0
			p\BB_CanDo = 0
		;	p\BB_Timer = 0
			StopChannel(Channel_BlastWind)
		;	HideEntity p\Objects\Mesh_SpeedBubble
			If (Mesh_Sonic_SpeedBubble <> 0) Then EntityAlpha(p\Objects\Mesh_SpeedBubble, 0)
			BlastEffectTimer = 0
		EndIf
		
		; Sonic reaches speed to begin BarrierBlast buildup
		If (SonicSpeed# > 4.8 And p\BB_InMove = 0 And p\BB_UpToSpeed = 0) And (Not p\Action = ACTION_JUMPDASH) Then
		;	p\BB_InMove = 1
		;	RecursiveAnimate(p\Objects\AnimTimer, 3, 1, 1, 0)
		;	p\BB_Timer = p\BB_Timer + (d\Delta#*(Game\Others\FPS*0.05))
			p\BB_UpToSpeed = 1
		EndIf
		
		
		
		; Begin BarrierBlast
	;	If (p\BB_Timer < MilliSecs() And p\BB_UpToSpeed = 1 And p\BB_InMove = 0) Then
		
			If p\BB_UpToSpeed = 1 Then
				p\BB_Timer = p\BB_Timer + GlobalTimer;*(Game\Others\Frames*0.05))
			Else
				p\BB_Timer = 0
			EndIf
			
			; TRAIL STOP
			;For t.trail= Each trail
			;	If ((p\Homing = 0) And (Not p\Action = ACTION_JUMPDASH) And (Not p\Action = ACTION_STOMPPART2)) Then
			;		Hide_Trail(1)
			;	Else
			;		Show_Trail(1)
			;	EndIf
			;Next
			
			If (p\Action = ACTION_HOMING) Then p\Homing = 1 : Else p\Homing = 0 : p\EnemyPicked = 0
			
		
			If (p\BB_Timer > 2000 And p\BB_UpToSpeed = 1 And p\BB_InMove = 0 And p\Action <> ACTION_DIVE) Then
		;		p\BB_CanDo = 1
				p\BB_InMove = 1
				If (Mesh_Sonic_SpeedBubble <> 0) Then ShowEntity p\Objects\Mesh_SpeedBubble
			
				OverlayTimer# = 1
				BlastEffectTimer# = 3.5
			
				
	;		p\BB_Timer# = p\BB_Timer# + d\Delta#
	;	Else
	;		p\BB_Timer = 0
			EndIf
			
			; Press ActionB to BarrierBlast
		;	If ((p\BB_CanDo=1) And (Input\Pressed\ActionB)) Then
		;		OverlayTimer# = 2
		;		BlastEffectTimer# = 2
		;	p\BB_InMove = 1
			
			
		;	BBlast = PlaySnd(Sound_BBlast, p\Objects\Entity, 1)
			; Apply motion blur
		;	PostEffect_Create_MotionBlur(0.85)
			
		;EndIf
			
		
		If (p\BB_InMove = 1) Then
			BlastEffectTimer# = BlastEffectTimer# - (GlobalTimerFloat#*0.001)
			OverlayTimer# = OverlayTimer# - (GlobalTimerFloat#*0.001)
		;	If (OverlayTimer1# < 1) Then
			If (Mesh_Sonic_SpeedBubble <> 0) Then EntityAlpha(p\Objects\Mesh_SpeedBubble, BlastEffectTimer#)
			CustomPostprocessOverlay (OverlayTimer#, 1, 255, 255, 255, BBOverlay)
			
			p\BB_CanDo = 0
			
			
			
		;	EntityAlpha(p\Objects\Mesh_SpeedBubble, -(d\Delta#*0.3))
			
		;	If (BlastEffectTimer# < 300) Then
				
			;	ShowEntity p\Objects\Mesh_SpeedBubble
			;	If (p\IValues[3]<MilliSecs()) Then p\IValues[3]=MilliSecs()+50 : Spawn_AfterImage(EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh), EntityPitch#(p\Objects\Mesh), EntityYaw#(p\Objects\Mesh), EntityRoll#(p\Objects\Mesh))
		;	ElseIf (BlastEffectTimer# > 300) Then
			;	HideEntity p\Objects\Mesh_SpeedBubble
		;	EndIf
			
			If (p\ActionBTimer > 300) Then p\Action = ACTION_SPINDASHNEW
			
			If (p\Action <> ACTION_COMMON) And (p\Action <> ACTION_LAND) And (p\Action <> ACTION_SKIDDING) Then p\ActionBTimer = 0
			
			
			;If SBTimer# > 1000 Then
			;	ShowEntity p\Objects\Mesh_SpeedBubble
			;	Spawn_AfterImage(EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh), EntityPitch#(p\Objects\Mesh), EntityYaw#(p\Objects\Mesh), EntityRoll#(p\Objects\Mesh))
			;Else
			;	HideEntity p\Objects\Mesh_SpeedBubble
			;EndIf
			
		;	If (ChannelPlaying(Channel_BlastWind)=False) Then
		;		Channel_BlastWind = PlaySnd(Sound_BlastWind, p\Objects\Entity, 1)
		;	EndIf
		EndIf
	EndIf
EndIf
		; Bounce Height Limit
		If (p\BounceHeight# > p\BounceHeightLimit#) Then p\BounceHeight# = p\BounceHeightLimit#
		p\BounceHeightLimit# = 1.35
		
		; HEAD TRACKING UPDATE -----
		If (p\Objects\Mesh_Head <> 0) Then
		If (p\Flags\HeadTrackEnabled = 1) Then
			
			PositionEntity (p\Objects\Mesh_HeadPoint, EntityX#(p\Objects\Mesh_Head), EntityY#(p\Objects\Mesh_Head), EntityZ#(p\Objects\Mesh_Head))
			
			p\Objects\HeadPointPitch# = EntityPitch#(p\Objects\Mesh_HeadPoint)
			p\Objects\HeadPointYaw# = EntityYaw#(p\Objects\Mesh_HeadPoint)
			p\Objects\HeadPointRoll# = EntityRoll#(p\Objects\Mesh_HeadPoint)
			
			p\Objects\HeadPitch# = EntityPitch#(p\Objects\Mesh_Head)
			p\Objects\HeadYaw# = EntityYaw#(p\Objects\Mesh_Head)
			p\Objects\HeadRoll# = EntityRoll#(p\Objects\Mesh_Head)
			
			If (p\HeadTrackTimer# > MilliSecs()) And (EntityYaw(p\Objects\Mesh_HeadPoint) < 95) And (EntityYaw(p\Objects\Mesh_HeadPoint) > -95) And (EntityPitch(p\Objects\Mesh_HeadPoint) < 60) And (EntityPitch(p\Objects\Mesh_HeadPoint) > -60) Then
		;	If (p\HeadTrackTimer# > MilliSecs()) Then
			p\Objects\HeadOptX# = CountToValue#(p\Objects\HeadPitch#, p\Objects\HeadPointPitch#, 3, d\Delta#)
			p\Objects\HeadOptY# = CountToValue#(p\Objects\HeadYaw#, p\Objects\HeadPointYaw#, 3, d\Delta#)
			p\Objects\HeadOptZ# = CountToValue#(p\Objects\HeadRoll#, p\Objects\HeadPointRoll#, 3, d\Delta#)
		EndIf
		If (p\HeadTrackTimer# < MilliSecs()) Then
			p\Objects\HeadOptX# = CountToValue#(p\Objects\HeadPitch#, 0, 4, d\Delta#)
			p\Objects\HeadOptY# = CountToValue#(p\Objects\HeadYaw#, 0, 4, d\Delta#)
			p\Objects\HeadOptZ# = CountToValue#(p\Objects\HeadRoll#, 0, 4, d\Delta#)
		EndIf			
		RotateEntity(p\Objects\Mesh_Head, p\Objects\HeadOptX#, p\Objects\HeadOptY#, p\Objects\HeadOptZ#)
		
	EndIf
	EndIf
		
		; SPEED BUBBLE UPDATE ------
	SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
	
	If (p\Character = CHARACTER_SONIC) Then
		If (Mesh_Sonic_SpeedBubble <> 0) Then
		
		PositionEntity p\Objects\Mesh_SpeedBubble, EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh)
		;RotateEntity(p\Objects\Mesh_SpeedBubble, EntityPitch#(p\Objects\Mesh), EntityYaw#(p\Objects\Mesh), EntityRoll#(p\Objects\Mesh))
		PointEntity(p\Objects\Mesh_SpeedBubble, p\Objects\Entity)
		
		
		PositionEntity p\Objects\Mesh_DashBubble, EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh)
		;RotateEntity(p\Objects\Mesh_DashBubble, EntityPitch#(p\Objects\Mesh), p\Animation\Direction#, EntityRoll#(p\Objects\Mesh))
		PointEntity(p\Objects\Mesh_DashBubble, p\Objects\Entity)
		
	EndIf
EndIf

; CRASH INTO WALL
p\Motion\StopSpeed# = (p\Motion\GroundSpeed# - p\Motion\GroundSpeed1#)*90
p\Motion\GroundSpeed1# = p\Motion\GroundSpeed#

Select p\Character
		
	Case CHARACTER_SONIC

		If (p\Motion\StopSpeed# < -380 And p\Motion\Speed\y# > -0.7 And p\Motion\Speed\y# < 0.7) And (p\Action <> ACTION_DESTSPRING And p\Action <> ACTION_SPRINGFALL And p\Action <> ACTION_HURT And Goal <> 1) And (KeyDown(KEY_F1)=False And KeyDown(KEY_F2)=False) Then
	
	;	If (p\Motion\GroundSpeed# <= 0.4 And p\BB_InMove = 1) Then
		;	ObjectHurt(p, 4)
	p\Action = ACTION_WALLHIT
	Channel_WallHit = PlaySnd(Sound_WallHit, p\Objects\Entity)
	PlayRandomHurtSound()
	
	p\BB_InMove = 0
	p\BB_UpToSpeed = 0
	
;	HideEntity p\Objects\Mesh_SpeedBubble
EndIf

Case CHARACTER_TAILS
	If (p\Motion\StopSpeed# < -380 And p\Motion\Speed\y# > -0.7 And p\Motion\Speed\y# < 0.7) And (p\Action <> ACTION_DESTSPRING And p\Action <> ACTION_SPRINGFALL And p\Action <> ACTION_HURT And Goal <> 1) And (KeyDown(KEY_F1)=False And KeyDown(KEY_F2)=False) Then
		
	;	If (p\Motion\GroundSpeed# <= 0.4 And p\BB_InMove = 1) Then
		;	ObjectHurt(p, 4)
		p\Action = ACTION_WALLHIT
		Channel_WallHit = PlaySnd(Sound_WallHit, p\Objects\Entity)
		PlayRandomHurtSound()
		
		p\BB_InMove = 0
		p\BB_UpToSpeed = 0
		
;	HideEntity p\Objects\Mesh_SpeedBubble
	EndIf
	
Case CHARACTER_KNUCKLES
	
	If (p\Motion\StopSpeed# < -330 And p\Motion\Speed\y# > -0.7 And p\Motion\Speed\y# < 0.7) And (p\Action <> ACTION_DESTSPRING And p\Action <> ACTION_SPRINGFALL And p\Action <> ACTION_HURT And Goal <> 1) And (KeyDown(KEY_F1)=False And KeyDown(KEY_F2)=False) Then
		
	;	If (p\Motion\GroundSpeed# <= 0.4 And p\BB_InMove = 1) Then
		;	ObjectHurt(p, 4)
		p\Action = ACTION_WALLHIT
		Channel_WallHit = PlaySnd(Sound_WallHit, p\Objects\Entity)
		PlayRandomHurtSound()
		
		p\BB_InMove = 0
		p\BB_UpToSpeed = 0
		
;	HideEntity p\Objects\Mesh_SpeedBubble
	EndIf
	
End Select


; -------------------

If p\Motion\GroundSpeed# > 15 Then
;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*8.9
;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*8.9
	p\Motion\Speed\x# = p\Motion\Speed\x#*0.9
	p\Motion\Speed\z# = p\Motion\Speed\z#*0.9
EndIf

		
		; HOMING VALUE AND EFFECT
		If p\Action = ACTION_HOMING Then; Or p\Action = ACTION_DESTSPRING Then
			p\Homing = 1
		Else
			p\Homing = 0
		End If
		
		; Stop Glide Sound If Not Gliding
		If p\Action <> ACTION_GLIDE Then StopChannel(Channel_Glide)
		If p\Action <> ACTION_SPINDASHNEW Then StopChannel(Channel_SpinDash)
		
	;	If ((p\Action <> ACTION_JUMPDASH) And (p\Action <> ACTION_HOMING) And (p\Action <> ACTION_STOMPPART2)) Then
		;	Free_Trail(p\Trail)
		;	p\HomingTimer = 0
		;	p\TrailOn = 0
			
		;	If p\TrailTimer > 100 Then Free_Trail(p\Trail)
			
		;EndIf
		
	;	If (p\Action = ACTION_HOMING) Or (p\Action = ACTION_JUMPDASH) Or (p\Action = ACTION_STOMPPART2) Or (p\Action = ACTION_ROLL) Then
		If (p\Action = ACTION_HOMING) Or (p\Action = ACTION_STOMPPART2) Or (p\Action = ACTION_ROLL) Then
			TrailTime# = 1.5
			For t.Trail = Each Trail
				t\alpha# = TrailTime#
			Next
	;		Show_Trail(p\Trail)
	;	Else
	;		Hide_Trail(p\Trail)
		EndIf
		
		; -----
		
		; RINGDASH VALUE
		If p\Character = CHARACTER_SONIC Then
		If (p\Action = ACTION_RINGDASH) Then
			p\RingDash = 1
		Else
			p\RingDash = 0
		EndIf
	EndIf
		; -----
		
		; WHILE HURT, make player invincible
		
		If (p\HurtTimer > MilliSecs()) Then
			PlayerFlash(p)
		
		
	;		EntityAlpha p\Objects\Mesh, 0.5
		Else
			EntityAlpha p\Objects\Mesh, 1
	EndIf
		
		; -----
	
	If (Not p\Action = ACTION_DIVE) And (Not p\Action = ACTION_FLY) Then COMMON_YTOPSPEED# = -4.8
	
	If (p\Motion\Speed\y# < -2.9) And (Not p\Action = ACTION_DIVE) Then p\DiveTimer = p\DiveTimer + GlobalTimer : Else p\DiveTimer = 0
	
	If p\DiveTimer > 1700 Then
		If (Input\Pressed\ActionA) Then
			p\Action = ACTION_DIVE
		EndIf
	EndIf
	
	If p\Action <> ACTION_SPINDASHNEW Then p\SpindashCharge = 0
	
		
		; ALIGN HOMING EFFECT
	;	RotateEntity(p\Objects\PPivot,EntityPitch(p\Objects\Entity),p\Animation\Direction#,EntityRoll(p\Objects\Entity))
	;	RotateEntity(p\Objects\PPivot,EntityPitch(p\Objects\Entity),0,EntityRoll(p\Objects\Entity))
	;	AlignToVector(p\Objects\PPivot,p\EnemyX#,p\EnemyY#,p\EnemyZ#, 3)
	;	AlignToVector(p\Objects\PPivot,EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity),EntityZ#(p\Objects\Entity), 2, 0.7)
	PointEntity(p\Objects\PPivot, p\Objects\Entity)
	;	AlignToVector(p\Objects\PPivot, p\Animation\Align\x#, p\Animation\Align\y#, p\Animation\Align\y#, 2)
	;	RotateEntity(p\Objects\PPivot,90,90,90)
		
	;If (Abs(EntityX#(p\Objects\PPivot) - EntityX#(p\Objects\Entity)) > 0.1) And (Abs(EntityY#(p\Objects\PPivot) - EntityY#(p\Objects\Entity)) > 0.1) And (Abs(EntityZ#(p\Objects\PPivot) - EntityZ#(p\Objects\Entity)) > 0.1) Then
	;	MoveEntity(p\Objects\PPivot, 0, 2*d\Delta#, 0)
	;EndIf
		
	;	p\PP_x# = p\PP_x# + (EntityX#(p\Objects\PPivot) - EntityX#(p\Objects\PPivot)) * ( 1.4 * d\Delta# )
	;	p\PP_y# = p\PP_y# + (EntityY#(p\Objects\PPivot) - EntityY#(p\Objects\PPivot)) * ( 1.4 * d\Delta# )
	;	p\PP_z# = p\PP_z# + (EntityZ#(p\Objects\PPivot) - EntityZ#(p\Objects\PPivot)) * ( 1.4 * d\Delta# )
	;	PositionEntity(p\Objects\PPivot, p\PP_x#, p\PP_y#, p\PP_z#, True)
	PositionEntity(p\Objects\PPivot, EntityX#(p\Objects\Mesh),EntityY#(p\Objects\Mesh),EntityZ#(p\Objects\Mesh), True)
	;RotateEntity(p\Objects\PPivot, EntityPitch#(p\Objects\Mesh),EntityYaw#(p\Objects\Mesh),EntityRoll#(p\Objects\Mesh))
		
	;PointEntity(p\Objects\PPivot, p\Objects\Entity)
	;	If (p\Homing = 0) Then AlignToVector(p\Objects\PPivot,EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity),EntityZ#(p\Objects\Entity), 1, 0.7)
	;	If (p\Homing = 1) Then AlignToVector(p\Objects\PPivot,p\EnemyX#,p\EnemyY#,p\EnemyZ#, 1)
		
		; Change opacity based on speed and qualification
;		If (SonicSpeed# > 1.5 And p\Motion\Ground=True) Then
;			EntityAlpha(p\Objects\Mesh_SpeedBubble,(SonicSpeed#*1)-3.4)
;		Else
;			EntityAlpha(p\Objects\Mesh_SpeedBubble,0)
;		EndIf
	
	If (p\Character = CHARACTER_SONIC) Then
		If (Mesh_Sonic_DashBubble <> 0) Then
	If (p\Action = ACTION_JUMPDASH Or p\Action = ACTION_HOMING Or p\Action = ACTION_STOMPPART2) Then
			ShowEntity (p\Objects\Mesh_DashBubble)
		Else
			HideEntity (p\Objects\Mesh_DashBubble)
		EndIf
	EndIf
EndIf
		
		If (p\Motion\Speed\Y# > 0.6) Then p\FallTimer = MilliSecs()
		
		If (Not p\Action = ACTION_SPRING) Then p\SpringTimer# = 0
		If ((Not p\Action = ACTION_JUMPDASH) And (Not p\Action = ACTION_HOMING)) Then p\HomingTimer = 0
			
	;	RecursiveAnimate(p\Objects\Mesh_SpeedBubble,1,0.6,1,0)
		; ------------------
		
	;	If (p\JumpImageShow = 1) Then
	;		ShowEntity p\Objects\JumpImage1
	;		ShowEntity p\Objects\JumpImage2
	;		ShowEntity p\Objects\JumpImage3
	;	Else
	;		HideEntity p\Objects\JumpImage1
	;		HideEntity p\Objects\JumpImage2
	;		HideEntity p\Objects\JumpImage3
	;	EndIf
		If (p\Motion\Speed\y# < -2.2) Then p\WillLandHard = 1 : Else p\WillLandHard = 0
		
		If (p\JumpImageShow = 0) Then
			p\JumpTrailReset = 1
		EndIf
		
		If (p\JumpImageShow = 1) Then
			p\JumpTrailReset = 0
		EndIf
		
		
		If p\Character = CHARACTER_SONIC Then
			
		;	Update_Player_Jump_Trail(p, d)
			Update_AfterImages(p, d)
			Update_SpeedEffects(p, d)
		
		If (p\Action=ACTION_RINGDASH) Then; Or p\BB_InMove = 1) Then
			If (p\IValues[3]<MilliSecs()) Then p\IValues[3]=MilliSecs()+50 : Spawn_AfterImage(EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh), EntityPitch#(p\Objects\Mesh), EntityYaw#(p\Objects\Mesh), EntityRoll#(p\Objects\Mesh))
		EndIf
		
	;	If (p\Motion\GroundSpeed# > 3) Then; Or p\BB_InMove = 1) Then
			If (p\IValues[3]<MilliSecs()) Then p\IValues[3]=MilliSecs()+50 : Spawn_SpeedEffect(EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh), EntityPitch#(p\Objects\Mesh), EntityYaw#(p\Objects\Mesh), EntityRoll#(p\Objects\Mesh))
	;	EndIf
	EndIf
		
		SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2) * AnimationMultiplier#
		Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
		SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
		
		; ROLL VALUES ------
		If (p\Action = ACTION_ROLL) Then
			COMMON_XZDECELERATION#	= 0.026
		Else
			COMMON_XZDECELERATION#	= 0.022
		EndIf
		; ------------------
		
		;11SS11 Tilting
		TotalTilt# = (p\Motion\TiltAmountTotal# * p\Motion\TiltMultiplier#)
		
		p\Motion\TurnSpeed# = ((p\Animation\Direction# - p\Motion\Direction1#)*10);*Game\DeltaTime\Delta#
		p\Motion\Direction1# = p\Animation\Direction#
		
		If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1) And p\Motion\Ground = 1) Then p\Motion\TiltMultiplier# = -0.95 : p\Motion\TiltLimitHigh# = 32
		If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1) And p\Motion\Ground = 1) Then p\Motion\TiltMultiplier# = -0.93 : p\Motion\TiltLimitHigh# = 21
		
		If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1) And p\Motion\Ground = 0) Then p\Motion\TiltMultiplier# = -0.36 : p\Motion\TiltLimitHigh# = 3
		If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1) And p\Motion\Ground = 0) Then p\Motion\TiltMultiplier# = -0.10 : p\Motion\TiltLimitHigh# = 3
		
	;	p\Motion\TiltSmoothed# = RotateTowardsAngle1#(p\Motion\TiltSmoothed#, (p\Motion\TiltAmountTotal# * p\Motion\TiltMultiplier#), 0.6*d\Delta)
	;	Player_SubstractTowardsValue(p\Motion\TiltSmoothed#, d\Delta#, TotalTilt#)
		
		p\Motion\TiltSmoothed# = CountToValue#(p\Motion\TiltSmoothed#, (p\Motion\TurnSpeed# * p\Motion\TiltMultiplier#), 1.7, d\Delta#)
			
		TiltDifference# = AngleDifference#(EntityZ#(p\Objects\Mesh),(p\Motion\TiltAmountTotal# * p\Motion\TiltMultiplier#))
		
	;	If (p\Motion\TiltAmountTotal# > p\Motion\TiltSmoothed#) Then p\Motion\TiltSmoothed# = p\Motion\TiltAmountTotal#
		
		SetTemplateEmitterLifeTime(p\Objects\Template, -1)
		SetTemplateParticleLifeTime(p\Objects\Template, 60, 85)
		
			;EntityType (c\Entity, 0)
	;	For p.tPlayer = Each tPlayer
				;Dir# = Pi(p\Animation\Direction#)
				;TurnEntity p\Objects\Mesh, 0,0,((p\Motion\TurnSpeed# * (p\Animation\Direction#)) * Game\Deltatime\Delta#)
			;	p\Motion\TiltAmount# = 
			
		;Player_SubstractTowardsValue(p\Motion\TiltAmount#, Game\Deltatime\Delta#, p\Motion\TiltAmountTotal#)
		p\Motion\TiltAmountTotal# = (p\Motion\TurnSpeed#) / Game\Deltatime\Delta#
		If (p\Motion\TiltSmoothed# > p\Motion\TiltLimitHigh#) Then p\Motion\TiltSmoothed# = p\Motion\TiltLimitHigh#
		If (p\Motion\TiltSmoothed# < -p\Motion\TiltLimitHigh#) Then p\Motion\TiltSmoothed# = -p\Motion\TiltLimitHigh#
	;		TurnEntity p\Objects\Mesh, 0,0,p\Motion\TiltAmountTotal# * Game\Deltatime\Delta#
	;		If (p\Motion\Ground=True) Then
			If (TiltingEnabled=1 And p\Grinding = 0) Then TurnEntity p\Objects\Mesh, 0,0,(p\Motion\TiltSmoothed#); * Game\Deltatime\Delta#)
	;		EndIf
				
				;Air Speed Tilt
	;		If (p\Motion\Ground=False) Then
	;			TurnEntity p\Objects\Mesh, SonicSpeed#*-7,0,(p\Motion\TiltAmountTotal# * p\Motion\TiltMultiplier#); * Game\Deltatime\Delta#)
	;		EndIf
	;	Next
		
		;Turn Sharpness Update
				TURNING_SHARPNESS# = TURNING_SHARPNESS# * ((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2))*10)
				If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 5.5) And p\Motion\Ground = 1) Then TURNING_SHARPNESS# = 26
				If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1) And p\Motion\Ground = 1) Then TURNING_SHARPNESS# = 17.5
				If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1) And p\Motion\Ground = 1) Then TURNING_SHARPNESS# = 3
		
				If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) > 1) And p\Motion\Ground = 0) Then TURNING_SHARPNESS# = 4
			If (((Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)) < 1) And p\Motion\Ground = 0) Then TURNING_SHARPNESS# = 2

		; Animate
			Player_Animate(p, d)
			
			
			
		; SET PLAYER SPEED
		;	If (p\Motion\Ground = True) Then Player_SetSpeed(p, d)
			Player_SetSpeed(p, d)
			
		;Hub Function
		;Select Game\Stage\Properties\Hub
		;	Case 1; : COMMON_XZACCELERATION#	= 0.037 : COMMON_XZTOPSPEED#	= 1.8
		;	Case 0 
				
		;End Select
		
	;		If (p\Action = ACTION_SLIDE) Then
	;		COMMON_XZACCELERATION#	= 0.02
	;		COMMON_XZTOPSPEED#	= 0.02
	;	EndIf
		
			
		
	
	;MENU
	If p\InMenu = 1 Then
		If (Input\Pressed\Interact = True) Then
			p\InMenu = 0
		EndIf
	EndIf
	;-----
	
	For o.tObject = Each tObject
		If (p\CanRingDash = 1 And Input\Pressed\ActionC And p\Character = CHARACTER_SONIC And o\ObjType=OBJTYPE_RING) Then
		p\Action = ACTION_RINGDASH
	EndIf
Next
		
End Function

; -------------------------------------------------
	
Function AirSlow()
	
	For p.tPlayer = Each tPlayer
		
	If (p\Motion\Speed\y# > 3.7) Then
		If (p\Action <> ACTION_FLY) Then COMMON_YACCELERATION# = p\Motion\Speed\y#*0.019
	EndIf
;		
;		If (p\BB_InMove = 1) Then
;			
;			Select Game\Stage\Properties\Hub
;				Case 1 : COMMON_XZACCELERATION#	= 0.045 : COMMON_XZTOPSPEED#	= 1.35
;				Case 0 : COMMON_XZACCELERATION#	= 0.045 : COMMON_XZTOPSPEED#	= 5.7
;			End Select
;			
;		EndIf
;		
;		If (p\BB_InMove = 0) Then

;Select Game\Stage\Properties\Hub
;	Case 1 : COMMON_XZACCELERATION#	= 0.041 : COMMON_XZTOPSPEED#	= 1.35
;	Case 0
;		
;		If (SonicSpeed# < 0.6) Then COMMON_XZACCELERATION#	= 0.045
;		If (SonicSpeed# > 0.6) Then COMMON_XZACCELERATION#	= 0.041
;		COMMON_XZTOPSPEED#	= 4.1
;End Select

;EndIf



Next
	
End Function

Function AirNormal()
;
	For p.tPlayer = Each tPlayer
;		
;		If (p\BB_InMove = 1) Then
;		
;		Select Game\Stage\Properties\Hub
;			Case 1 : COMMON_XZACCELERATION#	= 0.045 : COMMON_XZTOPSPEED#	= 1.35
;			Case 0 : COMMON_XZACCELERATION#	= 0.068 : COMMON_XZTOPSPEED#	= 6.5
;		End Select
;		
;	EndIf
;	
;	If (p\BB_InMove = 0) Then
;		
;		Select Game\Stage\Properties\Hub
;			Case 1 : COMMON_XZACCELERATION#	= 0.045 : COMMON_XZTOPSPEED#	= 1.35
;			Case 0
;				If (SonicSpeed# < 1) Then COMMON_XZACCELERATION#	= 0.041
;				If (SonicSpeed# > 1) Then COMMON_XZACCELERATION#	= 0.039
;				COMMON_XZTOPSPEED#	= 4.1
;		End Select
;		
;	EndIf
;	
	If (p\Action <> ACTION_FLY) Then COMMON_YACCELERATION#	= 0.04
		
	Next
	
End Function

Function BackToCheckpoint(p.tPlayer)
	EntityType(p\Objects\Entity, 0)
	
	PositionEntity p\Objects\Entity, p\CheckPointX#, p\CheckPointY#, p\CheckPointZ#
	PositionEntity p\Objects\Mesh, p\CheckPointX#, p\CheckPointY#, p\CheckPointZ#						
	p\Motion\Speed\x = 0
	p\Motion\Speed\y = 0
	p\Motion\Speed\z = 0
			;	p\Action = ACTION_FALL
	
	EntityType(p\Objects\Entity, COLLISION_PLAYER)
	
	p\Action = Game\Stage\startaction$
	
	Game\Gameplay\Rings = 0
	Game\Gameplay\Lives = Game\Gameplay\Lives - 1
	
	p\LoseLife = 0
	For c.tCamera = Each tCamera
		c\Held = 0
	Next
End Function

Function Create_Player_Jump_Trail(p.tPlayer)
	If p\Character = CHARACTER_SONIC Then
		If (Mesh_Sonic_JumpImage <> 0) Then
	For i=0 To 7
		p\Objects\JumpImage1[i] = New tSegment
		p\Objects\JumpImage1[i]\Entity = CopyMesh(Mesh_Sonic_JumpImage, p\Objects\Entity)
		ShowEntity p\Objects\JumpImage1[i]\Entity
	Next
	EntityParent(p\Objects\JumpImage1[0]\Entity, p\Objects\Mesh)
	HideEntity p\Objects\JumpImage1[0]\Entity
	
	ScaleEntity(p\Objects\JumpImage1[1]\Entity, .9, .9, .9) : EntityAlpha(p\Objects\JumpImage1[1]\Entity, .5)
	ScaleEntity(p\Objects\JumpImage1[2]\Entity, .8, .8, .8) : EntityAlpha(p\Objects\JumpImage1[2]\Entity, .45)
	ScaleEntity(p\Objects\JumpImage1[3]\Entity, .7, .7, .7) : EntityAlpha(p\Objects\JumpImage1[3]\Entity, .4)
	ScaleEntity(p\Objects\JumpImage1[4]\Entity, .6, .6, .6) : EntityAlpha(p\Objects\JumpImage1[4]\Entity, .35)
	ScaleEntity(p\Objects\JumpImage1[5]\Entity, .5, .5, .5) : EntityAlpha(p\Objects\JumpImage1[5]\Entity, .3)
	ScaleEntity(p\Objects\JumpImage1[6]\Entity, .4, .4, .4) : EntityAlpha(p\Objects\JumpImage1[6]\Entity, .25)
	ScaleEntity(p\Objects\JumpImage1[7]\Entity, .3, .3, .3) : EntityAlpha(p\Objects\JumpImage1[7]\Entity, .2)
EndIf
EndIf
End Function

Function Update_Player_Jump_Trail(p.tPlayer, d.tDeltaTime)
	If p\Character = CHARACTER_SONIC Then
		If (Mesh_Sonic_JumpImage <> 0) Then
	For i=1 To 7
	;	RotateEntity(p\Objects\JumpImage1[i]\Entity, -Float#(MilliSecs())*1, p\Animation\Direction#, 0)
		
		If (p\JumpTrailReset = 0) Then
		
		p\Objects\JumpImage1[i]\x# = p\Objects\JumpImage1[i]\x# + (EntityX#(p\Objects\JumpImage1[0]\Entity) - EntityX#(p\Objects\JumpImage1[i]\Entity)) * ( 1.4 / (i*1.2) * d\Delta# )
		p\Objects\JumpImage1[i]\y# = p\Objects\JumpImage1[i]\y# + (EntityY#(p\Objects\JumpImage1[0]\Entity) - EntityY#(p\Objects\JumpImage1[i]\Entity)) * ( 1.4 / (i*1.2) * d\Delta# )
		p\Objects\JumpImage1[i]\z# = p\Objects\JumpImage1[i]\z# + (EntityZ#(p\Objects\JumpImage1[0]\Entity) - EntityZ#(p\Objects\JumpImage1[i]\Entity)) * ( 1.4 / (i*1.2) * d\Delta# )
		PositionEntity(p\Objects\JumpImage1[i]\Entity, p\Objects\JumpImage1[i]\x#, p\Objects\JumpImage1[i]\y#, p\Objects\JumpImage1[i]\z#, True)
	EndIf
	
	If (p\JumpTrailReset = 1) Then
		
		p\Objects\JumpImage1[i]\x# = EntityX#(p\Objects\Entity)
		p\Objects\JumpImage1[i]\y# = EntityY#(p\Objects\Entity)
		p\Objects\JumpImage1[i]\z# = EntityZ#(p\Objects\Entity)
		PositionEntity(p\Objects\JumpImage1[i]\Entity, p\Objects\JumpImage1[i]\x#, p\Objects\JumpImage1[i]\y#, p\Objects\JumpImage1[i]\z#, True)
	EndIf
	
	If p\Character = CHARACTER_SONIC Then
		If p\JumpImageShow = 1 Then
			ShowEntity p\Objects\JumpImage1[i]\Entity
		Else
			HideEntity p\Objects\JumpImage1[i]\Entity
		EndIf
	EndIf
Next
EndIf
EndIf
End Function

Function Jump_Trail_Reset(p.tPlayer)
	
;	p\JumpTrailReset = 1
;	
;	For i=0 To 4
;		PositionEntity(p\Objects\JumpImage1[i]\Entity, EntityX#(p\Objects\Entity), EntityY#(p\Objects\Entity), EntityZ#(p\Objects\Entity), True)
		;p\Objects\JumpImage1[i]\x#=EntityX#(p\Objects\Entity)
		;p\Objects\JumpImage1[i]\y#=EntityY#(p\Objects\Entity)
		;p\Objects\JumpImage1[i]\z#=EntityZ#(p\Objects\Entity)
;	Next
End Function

	; =========================================================================================================
	; Create_AfterImages
	; =========================================================================================================
	; After-images are experimental. Theres no CopyAnimMesh function!
	; Copies will either not animate (if you use CopyMesh), or wont paint (if you use CopyEntity).
	; You could get around this by using LoadAnimMesh() for each after-image,
	; but then you'd have to use RecursiveExtractAnimSeq() to get the sequences all over again!
	;
	; The only thing to do at this point is extract the animation into it's own file,
	; and LoadAnimMesh() for each image. Then, you could hide/unhide the running animation
	; when Sonic is boosting.
Function Create_AfterImages(p.tPlayer)
	If p\Character = CHARACTER_SONIC Then
		If (Mesh_Sonic_AfterImage <> 0) Then
	For i=0 To 3
		p\Objects\AfterImage[i] = New tSegment
		p\Objects\AfterImage[i]\Entity = CopyMesh(Mesh_Sonic, Game\Stage\Root)
		RecursiveEntityFX(p\Objects\AfterImage[i]\Entity, 1+2)
	Next
	RecursiveEntityPaint(p\Objects\AfterImage[0]\Entity, "", 0.5)
	RecursiveEntityPaint(p\Objects\AfterImage[1]\Entity, "", 0.4)
	RecursiveEntityPaint(p\Objects\AfterImage[2]\Entity, "", 0.3)
	RecursiveEntityPaint(p\Objects\AfterImage[3]\Entity, "", 0.2)
EndIf
EndIf
End Function

	; =========================================================================================================
	; Update_AfterImages
	; =========================================================================================================
Function Update_AfterImages(p.tPlayer, d.tDeltaTime)
	If (Mesh_Sonic_AfterImage <> 0) Then
	Local Follow_Value#
	For i=0 To 3
		RotateEntity(p\Objects\AfterImage[i]\Entity, EntityPitch#(p\Objects\Mesh), EntityYaw#(p\Objects\Mesh), EntityRoll#(p\Objects\Mesh))
		
		Select i
			Case 0 Follow_Value# = 0.5
			Case 1 Follow_Value# = 0.4
			Case 2 Follow_Value# = 0.3
			Case 3 Follow_Value# = 0.2
		End Select
		
		p\Objects\AfterImage[i]\x# = p\Objects\AfterImage[i]\x# + (EntityX#(p\Objects\Mesh) - EntityX#(p\Objects\AfterImage[i]\Entity)) * Follow_Value#
		p\Objects\AfterImage[i]\y# = p\Objects\AfterImage[i]\y# + (EntityY#(p\Objects\Mesh) - EntityY#(p\Objects\AfterImage[i]\Entity)) * Follow_Value#
		p\Objects\AfterImage[i]\z# = p\Objects\AfterImage[i]\z# + (EntityZ#(p\Objects\Mesh) - EntityZ#(p\Objects\AfterImage[i]\Entity)) * Follow_Value#
		PositionEntity(p\Objects\AfterImage[i]\Entity, p\Objects\AfterImage[i]\x#, p\Objects\AfterImage[i]\y#, p\Objects\AfterImage[i]\z#, True)
		
		a=0 ; This turns the after-images off. They are off for now until I make a running-only mesh.
		If a=1 Then
			ShowEntity p\Objects\AfterImage[i]\Entity
		Else
			HideEntity p\Objects\AfterImage[i]\Entity
		EndIf
	Next
EndIf
End Function

	; =========================================================================================================
	; Create_SpeedEffects
	; =========================================================================================================
	; After-images are experimental. Theres no CopyAnimMesh function!
	; Copies will either not animate (if you use CopyMesh), or wont paint (if you use CopyEntity).
	; You could get around this by using LoadAnimMesh() for each after-image,
	; but then you'd have to use RecursiveExtractAnimSeq() to get the sequences all over again!
	;
	; The only thing to do at this point is extract the animation into it's own file,
	; and LoadAnimMesh() for each image. Then, you could hide/unhide the running animation
	; when Sonic is boosting.
Function Create_SpeedEffects(p.tPlayer)
	If p\Character = CHARACTER_SONIC Then
		If (Mesh_Sonic_SpeedEffect <> 0) Then
			For i=0 To 3
				p\Objects\SpeedEffect[i] = New tSegment
				p\Objects\SpeedEffect[i]\Entity = CopyMesh(Mesh_Sonic, Game\Stage\Root)
				RecursiveEntityFX(p\Objects\SpeedEffect[i]\Entity, 1+2)
			Next
			RecursiveEntityPaint(p\Objects\SpeedEffect[0]\Entity, "", 0.5)
			RecursiveEntityPaint(p\Objects\SpeedEffect[1]\Entity, "", 0.4)
			RecursiveEntityPaint(p\Objects\SpeedEffect[2]\Entity, "", 0.3)
			RecursiveEntityPaint(p\Objects\SpeedEffect[3]\Entity, "", 0.2)
		EndIf
	EndIf
End Function

	; =========================================================================================================
	; Update_SpeedEffects
	; =========================================================================================================
Function Update_SpeedEffects(p.tPlayer, d.tDeltaTime)
	If (Mesh_Sonic_SpeedEffect <> 0) Then
		Local Follow_Value#
		For i=0 To 3
			RotateEntity(p\Objects\SpeedEffect[i]\Entity, EntityPitch#(p\Objects\Mesh), EntityYaw#(p\Objects\Mesh), EntityRoll#(p\Objects\Mesh))
			
			Select i
				Case 0 Follow_Value# = 0.5
				Case 1 Follow_Value# = 0.4
				Case 2 Follow_Value# = 0.3
				Case 3 Follow_Value# = 0.2
			End Select
			
			p\Objects\SpeedEffect[i]\x# = p\Objects\SpeedEffect[i]\x# + (EntityX#(p\Objects\Mesh) - EntityX#(p\Objects\SpeedEffect[i]\Entity)) * Follow_Value#
			p\Objects\SpeedEffect[i]\y# = p\Objects\SpeedEffect[i]\y# + (EntityY#(p\Objects\Mesh) - EntityY#(p\Objects\SpeedEffect[i]\Entity)) * Follow_Value#
			p\Objects\SpeedEffect[i]\z# = p\Objects\SpeedEffect[i]\z# + (EntityZ#(p\Objects\Mesh) - EntityZ#(p\Objects\SpeedEffect[i]\Entity)) * Follow_Value#
			PositionEntity(p\Objects\SpeedEffect[i]\Entity, p\Objects\SpeedEffect[i]\x#, p\Objects\SpeedEffect[i]\y#, p\Objects\SpeedEffect[i]\z#, True)
			
			a=0 ; This turns the after-images off. They are off for now until I make a running-only mesh.
			If a=1 Then
				ShowEntity p\Objects\SpeedEffect[i]\Entity
			Else
				HideEntity p\Objects\SpeedEffect[i]\Entity
			EndIf
		Next
	EndIf
End Function

Function Spawn_AfterImage.tSegment(x#=0, y#=0, z#=0, rx#=0, ry#=0, rz#=0)
	If (Mesh_Sonic_AfterImage <> 0) Then
	e.tSegment = New tSegment
	e\Entity = CopyEntity(Mesh_Sonic_AfterImage)
	e\segType = PARTTYPE_AFTERIMAGE
	e\IValues[0] = MilliSecs() + 10
	e\FValues[0] = 0.6
	
	PositionEntity e\Entity, x#, y#, z#
	RotateEntity e\Entity, rx#, ry#, rz#
	EntityFX e\Entity, 1+4
	EntityAlpha e\Entity, .6
	Return e
EndIf
End Function

Function Update_AfterImage(e.tSegment, d.tDeltaTime)
	If (Mesh_Sonic_AfterImage <> 0) Then
	If (e\IValues[0]<MilliSecs()) Then e\FValues[0] = e\FValues[0] - 0.02 : e\IValues[0] = MilliSecs() + 10
	EntityAlpha e\Entity, e\FValues[0]
	If (e\FValues[0]<0.0) Then FreeEntity e\Entity : Delete e
EndIf
End Function

Function Spawn_SpeedEffect.tSegment(x#=0, y#=0, z#=0, rx#=0, ry#=0, rz#=0)
	If (Mesh_Sonic_SpeedEffect <> 0) Then
		e.tSegment = New tSegment
		e\Entity = CopyEntity(Mesh_Sonic_SpeedEffect)
		e\segType = PARTTYPE_SPEEDEFFECT
		e\IValues1[0] = MilliSecs() + 10
		e\FValues1[0] = 0.6
		
		PositionEntity e\Entity, x#, y#, z#
		RotateEntity e\Entity, rx#, ry#, rz#
		EntityFX e\Entity, 1+4
		EntityAlpha e\Entity, .6
		Return e
	EndIf
End Function

Function Update_SpeedEffect(e.tSegment, d.tDeltaTime)
	If (Mesh_Sonic_SpeedEffect <> 0) Then
		If (e\IValues[0]<MilliSecs()) Then e\FValues[0] = e\FValues[0] - 0.02 : e\IValues[0] = MilliSecs() + 10
		EntityAlpha e\Entity, e\FValues[0]
		If (e\FValues[0]<0.0) Then FreeEntity e\Entity : Delete e
	EndIf
End Function

Function Segments_Update(d.tDeltaTime)
	Local edDistance#
	
		; Loop through players and segment objects
	For p.tPlayer = Each tPlayer
		For e.tSegment = Each tSegment
			
				; Get object distance
				;edDistance# = EntityDistance(p\Objects\Entity, s\Entity)
			
				;If (edDistance# < OBJECT_VIEWDISTANCE_MAX#) Then
					; Show the object
					;ShowEntity(s\Entity)
			
					; Select the segment type and update
			Select e\segType
					
				Case PARTTYPE_AFTERIMAGE
					Update_AfterImage(e, d)
				;	Update_Player_Jump_Trail(p, d)
					
				Case PARTTYPE_SPEEDEFFECT
					Update_SpeedEffect(e, d)
					
;				Case PARTTYPE_JR
;					Update_Jr_Splash_AAMesh(e, d)
					
			End Select
				;Else
					;HideEntity(s\Entity)
				;End If
		Next
	Next
End Function

Function PlayerFlash(p.tPlayer)
	
	Local Timer1
	Local Status
		
	If (p\FlashTimer < MilliSecs()) And (p\FlashStatus = 0) Then
		EntityAlpha p\Objects\Mesh, 0.1
		
		p\FlashTimer = MilliSecs()+65
		p\FlashStatus = 1
		EndIf
		If (p\FlashTimer < MilliSecs()) And (p\FlashStatus = 1) Then
			EntityAlpha p\Objects\Mesh, 1
			
			p\FlashTimer = MilliSecs()+65
			p\FlashStatus = 0
		EndIf
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D