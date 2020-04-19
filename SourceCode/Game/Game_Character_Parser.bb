;,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=.,.=`;
;==============================================================================================================;
; BlitzSonic Freerunner Engine -- An improved version of the BlitzSonic engine
; BlitzSonic engine created by Damizean, Mark the Echidna, and the BlitzSonic team.
; Version 1.4, August 6, 2012
;
; Project Freerunner and Sonic Freerunner (C) 2010 Freerunner Team
;
;--------------------------------------------------------------------------------------------------------------;
;
; This code is subject to a creative commons license. Please read 
; below for more information about the use and distribution of the 
; source code. If you use this code for your own project, please 
; give credit where needed. Thank you.
;
; -Team Freerunner
;
;==============================================================================================================;
;,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=.,.=`;
;
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
; 1. The origin of this software must not be misrepres ented; you must not
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
;
;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;	Project Title : Sonic Freerunner                                                                           ;
; ============================================================================================================ ;
;	Author : Team Freerunner                                                                                   ;
;	Email :                                                                                                    ;
;	Version: 1.4                                                                                               ;
;	Date: 8/6/2012                                                                                             ;
;                                                                                                              ;
;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;
; 6/13/2012

Type tPlayer_Profile
	Field Mesh
	Field PhysicsSet
	Field Physics.tPlayer_Physics
	Field AnimSequence.tPlayer_AnimSequence
End Type
; This type holds the physics for a particular player.
; The A and B represent Above water, and Below water.
; TODO:	add acceleration/deceleration
Type tPlayer_Physics
	; Running
	Field A_TopSpeed#				; TopSpeed refers to the max speed you can achieve on flat ground.
	Field A_MaxSpeed#				; MaxSpeed is the top speed you can go down hills - set a bit faster than TopSpeed.
	Field A_AbsoluteSpeed#			; AbsoluteSpeed is the absolute top speed you can go!
	Field B_TopSpeed#
	Field B_MaxSpeed#
	Field B_AbsoluteSpeed#
	
	; Acceleration
	Field A_Acceleration#
	Field B_Acceleration#
	
	; Deceleration
	Field A_Deceleration#
	Field B_Deceleration#
	
	;Jumping
	Field A_JumpPower#
	Field A_JumpDashPower#
	Field A_HomingPower#
	Field B_JumpPower#
	Field B_JumpDashPower#
	Field B_HomingPower#
	
	; Rolling
	Field A_SpindashPower#
	Field A_MaxSpindashAngle#		; Max angle Sonic can spindash
	Field A_RollingWeightUp#
	Field A_RollingWeightDown#
	Field B_SpindashPower#
	Field B_MaxSpindashAngle#
	Field B_RollingWeightUp#
	Field B_RollingWeightDown#
	
	; Falling
	Field A_Gravity#
	Field A_MaxFallSpeed#		; Max speed by which Sonic can fall.
	Field A_SlowSkydive#
	Field A_FastSkydive#
	Field A_StompSpeed#
	Field B_Gravity#
	Field B_MaxFallSpeed#
	Field B_SlowSkydive#
	Field B_FastSkydive#
	Field B_StompSpeed#
	
	; Grinding
	Field A_GrindWeight#
	Field A_GrindFriction#
	Field B_GrindWeight#
	Field B_GrindFriction#
	
	; Turning
	Field A_NewDirectionFactor#
	Field A_TurningResponse#
	Field A_DeviationMotion#
	Field A_DeviationSubtract#
	Field A_DeviationHigh#
	Field A_DeviationMild#
	Field A_DeviationLow#
	Field A_DeviationCompHigh#
	Field A_DeviationCompMild#
	Field A_DeviationCompLow#
End Type
	
	; ---------------------------------------------------------------------------------------------------------
	; Load_Characters_List
	; ---------------------------------------------------------------------------------------------------------
	; TODO:	-fix path name
	;		-finish physics parse
	;		-physics assignment number needs to be set automatically when a character is detected
	;		-make function to create physics profiles with default values
	;		-set a default physics profile when physics are loaded

;Dim LoadedSounds(99)

Function Load_Characters_List(Char$)
	
	;ChangeDir( CurrentDir()+"Characters\" )
	;WorkingDirectory = ReadDir( CurrentDir() )
	
		; Repeat the following forever until no more files can be found.
	;	Repeat
		
		; Read next file. If there are no more files, close the directory, set back the root directory, and exit the loop.
	WorkingFile$ = "Characters\"+Char$
	;WorkingFile$=NextFile$( WorkingDirectory )
		;	If WorkingFile$ = "" Then CloseDir( WorkingDirectory ) : ChangeDir( ".." ) : Exit
		
		; Skip root, parent, and current directorys
		;	If WorkingFile$ = "." Then WorkingFile$=NextFile$( WorkingDirectory )
		;	If WorkingFile$ =  ".." Then WorkingFile$=NextFile$( WorkingDirectory )
		;	If WorkingFile$ = CurrentDir() Then WorkingFile$=NextFile$( WorkingDirectory )
		
			t=FileType( WorkingFile$ )
			
	;	Forever
		
		; If its a folder...
			If t=2 Then
			
			; Parse the Character.xml file and set the root node. Exit the game if any errors are returned.
				RootNode	= xmlLoad("Characters\"+Char$+"\Character.xml")
			If (xmlErrorCount()>0) Then RuntimeError("LoadCharacterList() -> Error loading xml: Character.xml not found in "+WorkingFile$)
				
				; Create a new profile
				pp.tPlayer_Profile = New tPlayer_Profile
				
				; Count nodes
				For i=1 To xmlNodeChildCount(RootNode)
				
					; Get the child node.
					RootChildNode = xmlNodeChild(RootNode, i)
					
					; Find out what type it is
					Select xmlNodeNameGet$(RootChildNode)
							
				;		Case "mesh"
				;			MeshFilename$	= xmlNodeAttributeValueGet(RootChildNode, "file")
				;			Mesh			= LoadAnimMesh("Characters\" + MeshFilename$) : DebugLog("Characters\" + MeshFilename$)
							
							
							
							
							
				;		Case "sounds"
				;			Name$			= xmlNodeAttributeValueGet(RootChildNode, "name")
				;			Number			= xmlNodeAttributeValueGet(RootChildNode, "number")
							
				;			DebugLog("Characters\"+Char$+"\Sounds\"+file$)
							
							
							
				;		LoadXMLSounds()
							
								; Read the data from the xml
				;			S1 = xmlNodeFind("1", RootChildNode)
				;			If (S1 <> 0) Then
				;				file$ = xmlNodeAttributeValueGet(S1, "filename")
				;				animation = xmlNodeAttributeValueGet(S1, "animation")
				;				frame = xmlNodeAttributeValueGet(S1, "frame")
				;			EndIf
							
								; Load the sound into the Sounds array, into the slot specified by the variable called number
						;	Sounds[number] = SoundLoad(Path$+file$)
							
						Case "footstep"
						;	For p.tPlayer = Each tPlayer
							
								SceneAnim = xmlNodeFind("anim", RootChildNode)
								
								If (SceneAnim <> 0) Then
									Game\Gameplay\Foot_Walk	= Float(xmlNodeAttributeValueGet(SceneAnim, "walk"))
									Game\Gameplay\Foot_Jog1	= Float(xmlNodeAttributeValueGet(SceneAnim, "jog"))
							;	Game\Gameplay\Foot_Jog2		= Float(xmlNodeAttributeValueGet(SceneAnim, "jog2"))
									Game\Gameplay\Foot_Run	= Float(xmlNodeAttributeValueGet(SceneAnim, "run"))
								Game\Gameplay\Foot_Sprint	= Float(xmlNodeAttributeValueGet(SceneAnim, "sprint"))
							EndIf
							
							SceneFrames = xmlNodeFind("frames", RootChildNode)
							
							If (SceneFrames <> 0) Then
								
								Game\Gameplay\Foot_WFrame1 = xmlNodeAttributeValueGet(SceneFrames, "walkframe1")
								Game\Gameplay\Foot_WFrame2 = xmlNodeAttributeValueGet(SceneFrames, "walkframe2")
								
								Game\Gameplay\Foot_J1Frame1 = xmlNodeAttributeValueGet(SceneFrames, "jogframe1")
								Game\Gameplay\Foot_J1Frame2 = xmlNodeAttributeValueGet(SceneFrames, "jogframe2")
								
								Game\Gameplay\Foot_R1Frame1 = xmlNodeAttributeValueGet(SceneFrames, "runframe1")
								Game\Gameplay\Foot_R1Frame2 = xmlNodeAttributeValueGet(SceneFrames, "runframe2")
								
								Game\Gameplay\Foot_R2Frame1 = xmlNodeAttributeValueGet(SceneFrames, "sprintframe1")
								Game\Gameplay\Foot_R2Frame2 = xmlNodeAttributeValueGet(SceneFrames, "sprintframe2")
								
						;	p\Animation\Foot_J1Frame1 = Float(xmlNodeAttributeValueGet(SceneFrames, "jogframe1"))
						;	p\Animation\Foot_J1Frame2 = Float(xmlNodeAttributeValueGet(SceneFrames, "jogframe2"))
							EndIf
							
							
						Case "sound"
							
								; Set local variables' default values
							
							file$		= ""
							number		= 0
							SoundArrayEnd = 0
							
															; Read the data from the xml
							file$ = xmlNodeAttributeValueGet(RootChildNode, "filename")
							number = xmlNodeAttributeValueGet(RootChildNode, "number")
						;	number		= number + 1
							frame = xmlNodeAttributeValueGet(RootChildNode, "frame")
							anim = xmlNodeAttributeValueGet(RootChildNode, "animation")
							
							If number > 0 Then SoundsLoaded = 1
							
								; Load the sound into the Sounds array, into the slot specified by the variable called number
							
							If SoundsLoaded = 1 Then Sounds1[number] = SoundLoad1("Characters\"+Char$+"\Sounds\"+file$, anim, frame); : DebugLog ("Sound " + number)
							
							SoundArrayEnd = number
							
						;	Sounds[number]\Frame = frame
						;	Sounds[number]\Anim = anim
								
						;	Next
							
				;		Case "information"
				;			Name$			= xmlNodeAttributeValueGet(RootChildNode, "name")
				;			Number			= xmlNodeAttributeValueGet(RootChildNode, "number")
							
							
				;		Case "sequence"
				;			SeqStart	= 0
				;			SeqEnd		= 0
				;			SeqNum		= 0
				;			
				;			SeqStart	= xmlNodeAttributeValueGet(RootChildNode, "start")
				;			SeqEnd		= xmlNodeAttributeValueGet(RootChildNode, "end")
						;SeqNum		= xmlNodeAttributeValueGet(ModelsChildNode, "number")
				;			RecursiveExtractAnimSeq(Mesh, SeqStart, SeqEnd)
							
				;		Case "flags"
							
				;			For p.tPlayer = Each tPlayer
				;			p\Animation\SingleRunAnim = 0
				;			p\Animation\SingleRunAnim = xmlNodeAttributeValueGet(RootChildNode, "singlerunanim")
				;		Next
							
							
				;		Case "physics"
				;			; Create a new physics object
				;			py.tPlayer_Physics = Create_Player_Physics()
				;			
				;			; Get the assignment number so we can select it later.
				;			PhysicsSet	= xmlNodeAttributeValueGet(RootChildNode, "assignment")
				;			
				;			; These 2 lines are useless for now. they start a new loop in a sub-child
				;			;For j=1 To xmlNodeChildCount(RootChildNode)
				;			;PhysicsChildNode = xmlNodeChild(RootChildNode, j)
				;			
				;			; Look for nodes and read data
				;			PhysicsRunning = xmlNodeFind("running", RootChildNode)
				;			If (PhysicsRunning<>0) Then
				;				py\A_TopSpeed#		= Float(xmlNodeAttributeValueGet(PhysicsRunning, "A_TopSpeed"))
				;				py\A_MaxSpeed#		= Float(xmlNodeAttributeValueGet(PhysicsRunning, "A_MaxSpeed"))
				;				py\A_AbsoluteSpeed#	= Float(xmlNodeAttributeValueGet(PhysicsRunning, "A_AbsoluteSpeed"))
				;				py\B_TopSpeed#		= Float(xmlNodeAttributeValueGet(PhysicsRunning, "B_TopSpeed"))
				;				py\B_MaxSpeed#		= Float(xmlNodeAttributeValueGet(PhysicsRunning, "B_MaxSpeed"))
				;				py\B_AbsoluteSpeed#	= Float(xmlNodeAttributeValueGet(PhysicsRunning, "B_AbsoluteSpeed"))
				;			EndIf
				;			PhysicsJumping = xmlNodeFind("jumping", RootChildNode)
				;			If (PhysicsJumping<>0) Then
				;				py\A_JumpPower#		= Float(xmlNodeAttributeValueGet(PhysicsJumping, "A_JumpPower"))
				;				py\A_JumpDashPower#	= Float(xmlNodeAttributeValueGet(PhysicsJumping, "A_JumpDashPower"))
				;				py\A_HomingPower#	= Float(xmlNodeAttributeValueGet(PhysicsJumping, "A_HomingPower"))
				;				py\B_JumpPower#		= Float(xmlNodeAttributeValueGet(PhysicsJumping, "B_JumpPower"))
				;				py\B_JumpDashPower#	= Float(xmlNodeAttributeValueGet(PhysicsJumping, "B_JumpDashPower"))
				;				py\B_HomingPower#	= Float(xmlNodeAttributeValueGet(PhysicsJumping, "B_HomingPower"))
				;			EndIf
				;			PhysicsRolling = xmlNodeFind("rolling", RootChildNode)
				;			If (PhysicsRolling<>0) Then
				;				py\A_SpindashPower#			= Float(xmlNodeAttributeValueGet(PhysicsRolling, "A_SpindashPower"))
				;				py\A_MaxSpindashAngle#		= Float(xmlNodeAttributeValueGet(PhysicsRolling, "A_MaxSpindashAngle"))
				;				py\A_RollingWeightUp#		= Float(xmlNodeAttributeValueGet(PhysicsRolling, "A_RollWeightUp"))
				;				py\A_RollingWeightDown#		= Float(xmlNodeAttributeValueGet(PhysicsRolling, "A_RollWeightDown"))
				;				py\B_SpindashPower#			= Float(xmlNodeAttributeValueGet(PhysicsRolling, "B_SpindashPower"))
				;				py\B_MaxSpindashAngle#		= Float(xmlNodeAttributeValueGet(PhysicsRolling, "B_MaxSpindashAngle"))
				;				py\B_RollingWeightUp#		= Float(xmlNodeAttributeValueGet(PhysicsRolling, "B_RollWeightUp"))
				;				py\B_RollingWeightDown#		= Float(xmlNodeAttributeValueGet(PhysicsRolling, "B_RollWeightDown"))
				;			EndIf
				;			PhysicsFalling = xmlNodeFind("falling", RootChildNode)
				;			If (PhysicsFalling<>0) Then
				;				py\A_Gravity#		= Float(xmlNodeAttributeValueGet(PhysicsFalling, "A_Gravity"))
				;				py\A_MaxFallSpeed#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "A_MaxFallSpeed"))
				;				py\A_SlowSkydive#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "A_SlowSkydive"))
				;				py\A_FastSkydive#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "A_FastSkydive"))
				;				py\A_StompSpeed#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "A_StompSpeed"))
				;				py\B_Gravity#		= Float(xmlNodeAttributeValueGet(PhysicsFalling, "B_Gravity"))
				;				py\B_MaxFallSpeed#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "B_MaxFallSpeed"))
				;				py\B_SlowSkydive#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "B_SlowSkydive"))
				;				py\B_FastSkydive#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "B_FastSkydive"))
				;				py\B_StompSpeed#	= Float(xmlNodeAttributeValueGet(PhysicsFalling, "B_StompSpeed"))
				;			EndIf
				;			PhysicsGrinding = xmlNodeFind("grinding", RootChildNode)
				;			If (PhysicsGrinding<>0) Then
				;				py\A_GrindWeight#	= Float(xmlNodeAttributeValueGet(PhysicsGrinding, "A_GrindWeight"))
				;				py\A_GrindFriction#	= Float(xmlNodeAttributeValueGet(PhysicsGrinding, "A_GrindFriction"))
				;				py\B_GrindWeight#	= Float(xmlNodeAttributeValueGet(PhysicsGrinding, "B_GrindWeight"))
				;				py\B_GrindFriction#	= Float(xmlNodeAttributeValueGet(PhysicsGrinding, "B_GrindFriction"))
				;			EndIf
				;			PhysicsTurning = xmlNodeFind("turning", RootChildNode)
				;			If (PhysicsTurning<>0) Then
				;				py\A_NewDirectionFactor#	= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_NewDirectionFactor"))
				;				py\A_TurningResponse#		= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_TurningResponse"))
				;				py\A_DeviationMotion#		= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationMotion"))
				;				py\A_DeviationSubtract#		= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationSubtract"))
				;				py\A_DeviationHigh#			= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationHigh"))
				;				py\A_DeviationMild#			= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationMild"))
				;				py\A_DeviationLow#			= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationLow"))
				;				py\A_DeviationCompHigh#		= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationCompHigh"))
				;				py\A_DeviationCompMild#		= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationCompMild"))
				;				py\A_DeviationCompLow#		= Float(xmlNodeAttributeValueGet(PhysicsTurning, "A_DeviationCompLow"))
				;			EndIf
					End Select
				Next
			End If
		
		; We are done reading, so delete the root node.
			xmlNodeDelete(RootNode)
		;	Return mesh
		
		; Move on to the next file
	;	Forever
	
	;End If

	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Create_Player_Physics
	; ---------------------------------------------------------------------------------------------------------
	; Returns a new player physics object and fills in default values
	; TODO: -fill in data
	Function Create_Player_Physics.tPlayer_Physics()
		py.tPlayer_Physics = New tPlayer_Physics
		
		; Running
		py\A_TopSpeed#			= 3.3	; TopSpeed refers to the max speed you can achieve on flat ground.
		py\A_MaxSpeed#			= 7.5	; MaxSpeed is the top speed you can go down hills - set a bit faster than TopSpeed.
		py\A_AbsoluteSpeed#		= 10.0	; AbsoluteSpeed is the absolute top speed you can go!
		py\B_TopSpeed#			= 3.3
		py\B_MaxSpeed#			= 7.5
		py\B_AbsoluteSpeed#		= 10.0
		
		; Acceleration
		
		; Deceleration
		
		; ## These values need to be filled in; 0's were put in temporarily.
		;Jumping
		py\A_JumpPower#			= 0
		py\A_JumpDashPower#		= 0
		py\A_HomingPower#		= 0
		py\B_JumpPower#			= 0
		py\B_JumpDashPower#		= 0
		py\B_HomingPower#		= 0
		
		; Rolling
		py\A_SpindashPower#		= 0
		py\A_MaxSpindashAngle#	= .5	; Max angle Sonic can spindash
		py\A_RollingWeightUp#	= 0.06
		py\A_RollingWeightDown#	= 0.1
		py\B_SpindashPower#		= 0
		py\B_MaxSpindashAngle#	= 0
		py\B_RollingWeightUp#	= 0.06
		py\B_RollingWeightDown#	= 0.1
		
		; Falling
		py\A_Gravity#			= 0.04
		py\A_MaxFallSpeed#		= -3.4	; Maximum falling speed
		py\A_SlowSkydive#		= 0.1
		py\A_FastSkydive#		= 0.2
		py\A_StompSpeed#		= 0.3
		py\B_Gravity#			= 0.04
		py\B_MaxFallSpeed#		= -3.4
		py\B_SlowSkydive#		= 0.1
		py\B_FastSkydive#		= 0.2
		py\B_StompSpeed#		= 0.3
		
		; Grinding
		py\A_GrindWeight#		= 0.10
		py\A_GrindFriction#		= 0
		py\B_GrindWeight#		= 0
		py\B_GrindFriction#		= 0
		
		; Turning
		;py\A_SkidingFactor			= 0.002
		py\A_NewDirectionFactor#	= 2.2	; Default = 1
											; Higher = faster acceleration in the new direction.
											; 3 or more will cause faster acceleration when turning, 
											; then when moving straight!!
											
		py\A_TurningResponse#		= 0.8	; Default = 3
											; Lower = faster turning. This is how quickly Sonic will turn
											; to face in the new direction. Not too low or sonic will look
											; jerky! Not too high (not more than 4) or Sonic will take too
											; long to turn around!
											
		py\A_DeviationMotion#		= -0.5	; Default = -0.0
											; Lower = less likely to skid. Setting too high will cause too
											; much skidding. Too low and Sonic won't skid at all!
											; The following values are the ammounts by which skidding and
											; compensation effect Sonic. Think of them as the trigger which
											; makes Sonic skid. Higher = more likely to compensate.
		py\A_DeviationSubtract#		= 0.011	; Default = 0.02
		py\A_DeviationHigh#			= 0.4	; Default = 0.4
		py\A_DeviationMild#			= 0.95	; Default = 0.95
		py\A_DeviationLow#			= 1.01	; Default = 1.01
		py\A_DeviationCompHigh#		= 4.0	; Default = 33.0
		py\A_DeviationCompMild#		= 5.0	; Default = 19.0
		py\A_DeviationCompLow#		= 6.0	; Default = 15.0
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D