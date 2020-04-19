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
;               27/01/2008 - Major rewrite to use configuration files. Added SRB2 mode and Analog mode.        ;
;                            Also properly added Point of View camera mode.                                    ;
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

	Type tCamera
		; ---- Camera entity variables ----
		Field Entity

		; ---- Camera attributes in world space ----
		Field Position.tVector
		Field Rotation.tVector
		Field Alignment.tVector

		; ---- Camera values -----
		Field Mode
		Field Held
		Field DistanceFromCamera#
		Field DistanceFromTarget#
		Field FieldOfView#

		; ---- Target values -----
		Field Target.tPlayer
		Field TargetPosition.tVector
		Field TargetRotation.tVector
		Field TargetStationaryTimer
		
		; ---- ETC ----
		Field Listener
		Field UseCoord
		
	End Type

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Camera modes -----
	Const CAMERA_MODE_NORMAL	= 0
	Const CAMERA_MODE_TARGETPOV	= 1
	
	; ---- Camera distance values -----
	Const CAMERA_DISTANCE_NEAR#	= 4
	Const CAMERA_DISTANCE_FAR#  = 80

	; ---- Camera field-of-view values -----
	Const CAMERA_FOV_SPINDASH#	= 70
	Const CAMERA_FOV_NORMAL#	= 50

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; Camera_Create
	; ---------------------------------------------------------------------------------------------------------
	Function Camera_Create.tCamera()
		; ----- Create camera structure -----
		c.tCamera 	= New tCamera
			; Allocate vectors
			c\Position		 = Vector(0, 0, 0)
			c\Rotation		 = Vector(Gameplay_Camera_RotationX#, Gameplay_Camera_RotationY#, 0)
			c\Alignment		 = Vector(0, 1, 0)
			c\TargetPosition = Vector(0, 0, 0)
			c\TargetRotation = Vector(0, 0, 0)
			c\Held = 0
			
;			c\Bl=createblurlayer(c\Entity, 1,0,1,1, .95,1, o)
			
			; Create camera and include initial setup
			c\Entity 	= CreateCamera(Game\Stage\Root)
			InitParticles(c\Entity)
			
		;	For	p.tPlayer = Each tPlayer
			;	c\Listener = CreateListener(c\Entity, 0, 1, 1)
		;	Next

				; Setup camera
				CameraZoom(c\Entity, 		CAMERA_FOV_NORMAL#)
				CameraRange(c\Entity, 		0.1, 4800)
				CameraFogMode(c\Entity, 	1)
				CameraFogColor(c\Entity,	170, 208, 255)
				CameraFogRange(c\Entity, 	940, 1600)
				
		;		For p.tPlayer = Each tPlayer
		;			PW_SetParticle(p\Objects\Emt1, p\Objects\Entity, 1, 2, c\Entity, 0)
		;		Next

				; Setup camera values
				c\Mode					= Gameplay_Camera_TargetPOV
				c\DistanceFromCamera#	= (CAMERA_DISTANCE_NEAR#+CAMERA_DISTANCE_FAR#)*0.5
				c\DistanceFromTarget#	= c\DistanceFromCamera#
				c\FieldOfView#			= CAMERA_FOV_NORMAL#
				
				; Setup camera collision
				EntityType(c\Entity, COLLISION_CAMERA)
				EntityRadius(c\Entity, 1.5)

				; Initializate FX Manager
				FxManager_SetCamera(c\Entity, 0.02)
				
		; ---- Done ----
		Return c

	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Camera_Bind
	; ---------------------------------------------------------------------------------------------------------
Function Camera_Bind(c.tCamera, p.tPlayer)
		; ---- Attach player to camera -----
		c\Target = p
		p\Objects\Camera = c

		; ---- Done -----
	End Function
	
Function Camera_Update(c.tCamera, d.tDeltaTime, sun=0)
	
;	c\Held = 0
	
			; ---- If Cam is affected by Cam Mesh ----
;	If (c\UseCoord = 1) Then
	For p.tPlayer = Each tPlayer
		
		If (Abs(EntityX(p\Objects\Entity) - CamPosX#) < CamRadius#) And (Abs(EntityY(p\Objects\Entity) - CamPosY#) < CamRadius#) And (Abs(EntityZ(p\Objects\Entity) - CamPosZ#) < CamRadius#) Then
		
	If c\Held = 1 Then
		
	;	CameraZoom(c\Entity, 		CAMERA_FOV_NORMAL#)
		Vector_LinearInterpolation(c\Position, c\TargetPosition, 45*d\Delta)
		c\Position = Vector(CamPosX#,CamPosY#,CamPosZ#)
		c\TargetRotation\x# = CamRotX# ;rx#
		c\TargetRotation\y# = CamRotY# ;ry#
		c\TargetRotation\z# = CamRotZ# ;rz#
		c\Rotation\x# = CamRotX# ;rx#
		c\Rotation\y# = CamRotY# ;ry#
		c\Rotation\z# = CamRotZ# ;rz#
		c\Position		 = Vector(CamPosX#, CamPosY#, CamPosZ#)
		c\TargetPosition		 = Vector(CamPosX#,CamPosY#,CamPosZ#)
		Vector_Set(c\TargetPosition, CamPosX#, CamPosY#, CamPosZ#)
	EndIf
	
	If c\Held = 2 Then
		
		Vector_LinearInterpolation(c\Position, c\TargetPosition, 45*d\Delta)
			
		PointEntity (c\Entity, p\Objects\Entity)
		
	;	AlignToVector(c\Entity, EntityX#(p\Objects\Entity),EntityY#(p\Objects\Entity),EntityZ#(p\Objects\Entity), 2, 1)
				
		c\Rotation\x# = EntityPitch#(c\Entity)
		c\Rotation\y# = EntityYaw#(c\Entity)
		c\Rotation\z# = EntityRoll#(c\Entity)
		c\TargetRotation\x# = EntityPitch#(c\Entity)
		c\TargetRotation\y# = EntityYaw#(c\Entity)
		c\TargetRotation\z# = EntityRoll#(c\Entity)
				
		
			
				c\Position		 = Vector(CamPosX#, CamPosY#, CamPosZ#)
			c\TargetPosition		 = Vector(CamPosX#,CamPosY#,CamPosZ#)
			
			Vector_Set(c\TargetPosition, CamPosX#, CamPosY#, CamPosZ#)
			
		EndIf
		
	Else
		c\Held = 0
		
	EndIf
Next
		; ---- Don't do anything to the camera if there's no target.
	If (c\Target = Null) Then Return
	
	; ---- Setup and update the sun flares
;	If ((sun <> 0)) Then
	;	If (spritecamera=0) Then
	;		spritecamera = c\Entity
	;		spritepivot = CreateSpritePivot(c\Entity,1.01)
	;		SetupFlares("Textures\lens-flares.jpg")
	;	EndIf
	;	UpdateFlare(c\Entity, sun)
;		PointEntity sun, c\Entity
;	EndIf

		; ---- Camera rotation style depending on control mode ----
		Select Gameplay_Control_Mode

			Case PLAYER_MODE_MOUSELOOK
				; Mouselook mode is completely free view of the environment. Just rotate using the
				; values provided
				If (c\Held = 0) Then
				c\TargetRotation\y# = c\TargetRotation\y#-Input\Camera_AnalogX#*Gameplay_Camera_RotationSpeedX#
				c\TargetRotation\x# = Clamp#(180+c\TargetRotation\x+Input\Camera_AnalogY#*Gameplay_Camera_RotationSpeedY#, 100, 260)-180
				EndIf
				
			Case PLAYER_MODE_ANALOG
				; Analog mode works like mouse look, but also provides a degree of rotation when the player
				; is rotating.
				c\TargetRotation\y# = c\TargetRotation\y#-Input\Camera_AnalogX#*Gameplay_Camera_RotationSpeedX#*d\Delta
				c\TargetRotation\x# = Clamp#(180+c\TargetRotation\x+Input\Camera_AnalogY#*Gameplay_Camera_RotationSpeedY#*d\Delta, 100, 260)-180

				; Also, when the player pressed left or right, the camera rotates with them
				RotationX# = Cos(Input\Movement_Direction)*Input\Movement_Pressure
				RotationY# = Sin(Input\Movement_Direction)*Input\Movement_Pressure
				c\TargetRotation\y# = c\TargetRotation\y#-(RotationX#*Gameplay_Camera_RotationSpeedX#*0.7*d\Delta)
				If (RotationY#>0.0) Then c\TargetRotation\y# = c\TargetRotation\y#-(RotationY#*Gameplay_Camera_RotationSpeedX#*0.7*d\Delta)*Sgn(Input\Movement_AnalogX#)
				
				; Once the player is stationary, return back to the original position
				If (c\Target\Action = ACTION_COMMON And Vector_Length#(c\Target\Motion\Speed)=0.0 And Input\Camera_Pressure=0.0) Then
					c\TargetStationaryTimer# = c\TargetStationaryTimer# + d\Delta#
				Else
					c\TargetStationaryTimer# = 0
				End If
				
				If (c\TargetStationaryTimer# > 400) Then c\TargetRotation\y# = RotateTowardsAngle#(c\TargetRotation\y#, c\Target\Animation\Direction#+180, 0.6*d\Delta)
			Case PLAYER_MODE_SRB
				; Basically a copy of SRB2's system, wich has the camera always at the back of Sonic,
				; and the player only changes his position.
				c\TargetRotation\y# = c\TargetRotation\y#-Input\Movement_AnalogX#*Gameplay_Camera_RotationSpeedX#*d\Delta#
				c\TargetRotation\x# = c\TargetRotation\x#-Input\Camera_AnalogY#*Gameplay_Camera_RotationSpeedY#*d\Delta#
				
		End Select

		; ---- Manage zoom in and zoom out -----
		If (Input\Hold\CameraZoomIn)  Then c\DistanceFromTarget# = Clamp(c\DistanceFromTarget#-1, CAMERA_DISTANCE_NEAR#, CAMERA_DISTANCE_FAR#)
		If (Input\Hold\CameraZoomOut) Then c\DistanceFromTarget# = Clamp(c\DistanceFromTarget#+1, CAMERA_DISTANCE_NEAR#, CAMERA_DISTANCE_FAR#)

		; ---- If on spindash mode, change camera values -----
	;	If (c\Target\Action = ACTION_SPINDASH) Then
	;		c\FieldOfView#		= CAMERA_FOV_NORMAL#
	;		c\DistanceFromCamera# = c\DistanceFromCamera#
	;		c\DistanceFromCamera# = Interpolate#(c\DistanceFromCamera#, CAMERA_DISTANCE_NEAR#, 0.1*d\Delta)
	;		c\FieldOfView#		  = Interpolate(c\FieldOfView#, CAMERA_FOV_SPINDASH, 0.1*d\Delta)
	;	Else
		
		If (c\Held <> 0) Then
			c\DistanceFromCamera# = 0
			c\FieldOfView#		  = Interpolate(c\FieldOfView#, CAMERA_FOV_NORMAL, 0.1*d\Delta)
		EndIf
		
		If (c\Held = 0) Then
			c\DistanceFromCamera# = Interpolate#(c\DistanceFromCamera#, c\DistanceFromTarget#, 0.1*d\Delta)
			c\FieldOfView#		  = Interpolate(c\FieldOfView#, CAMERA_FOV_NORMAL, 0.1*d\Delta)
	;	End If

		; ---- Finally, change camera position around the target -----
		; Change position
			
			Vector_Set(c\TargetPosition, EntityX#(c\Target\Objects\Entity), EntityY#(c\Target\Objects\Entity), EntityZ#(c\Target\Objects\Entity))
		Vector_LinearInterpolation(c\Position, c\TargetPosition, Gameplay_Camera_Smoothness#*d\Delta)
		Vector_LinearInterpolation(c\Rotation, c\TargetRotation, Gameplay_Camera_Smoothness#*d\Delta)
		
		For p.tPlayer = Each tPlayer
			If c\Held = 0 Then
			If (p\Motion\Ground = True) Then Vector_LinearInterpolation(c\Alignment, c\Target\Animation\Align, 0.05+Vector_Length(c\Target\Motion\Speed)*0.07*d\Delta)
			If (p\Motion\Ground = False) Then Vector_LinearInterpolation(c\Alignment, c\Target\Animation\Align, 0.05+Vector_Length(c\Target\Motion\Speed)*0.05*d\Delta)
		EndIf
	Next
		
	;	Vector_LinearInterpolation(c\Alignment, c\Target\Animation\Align, 0.05+Vector_Length(c\Target\Motion\Speed)*0.00001*d\Delta)
		c\TargetRotation\z# = 0
		
	EndIf
		; Apply changes
		CameraZoom(c\Entity, 1/Tan#(c\FieldOfView#))
		EntityType(c\Entity, COLLISION_NONE)
		PositionEntity(c\Entity, c\Position\x#, c\Position\y#, c\Position\z#)
		EntityType(c\Entity, COLLISION_CAMERA)
		RotateEntity(c\Entity, 0, 0, 0)
		
		For p.tPlayer = Each tPlayer
			If (c\Mode = CAMERA_MODE_TARGETPOV And p\Action <> ACTION_CLIMB) And c\Held = 0 Then AlignToVector(c\Entity, c\Alignment\x#, c\Alignment\y#, c\Alignment\z#, 2) : ElseIf (c\Mode = CAMERA_MODE_NORMAL) Then EntityType(c\Entity, COLLISION_NONE)
	Next
		
		; Center Camera
	;	For p.tPlayer = Each tPlayer
	;		c\TargetRotation\y# = c\TargetRotation\y#-Input\Camera_AnalogX#*Gameplay_Camera_RotationSpeedX#
	;		SonicSpeed# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
		;	If (SonicSpeed# > 2.4) Then
		;		Player_SubstractTowardsValue(c\TargetRotation\y#, 0.06*d\Delta#, p\Animation\Direction#)
		;	EndIf
		;	Player_SubstractTowardsValue(p\Animation\Direction#, 0.06*d\Delta#, c\TargetRotation)
	;	Next
		TurnEntity(c\Entity, c\Rotation\x#, c\Rotation\y#, c\Rotation\z#)
		MoveEntity(c\Entity, 0, c\DistanceFromCamera#*0.25, -c\DistanceFromCamera#)
				
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Camera_Destroy
	; ---------------------------------------------------------------------------------------------------------
	; This function deletes the camera entity
Function Camera_Destroy(c.tCamera)
	FreeEntity c\Listener
		FreeEntity c\Entity
	;	FreeEntity ShadowCameraDefault
	;	FreeEntity ShadowLightDefault
	;	UnInitParticles(c\Entity)
		;Delete c\Listener
		StopChannel(Channel_NPC)
		Delete c\Position
		Delete c\Rotation
		Delete c\Alignment
		Delete c\Target
		Delete c\TargetPosition
		Delete c\TargetRotation
		Delete c
	End Function
;~IDEal Editor Parameters:
;~C#Blitz3D