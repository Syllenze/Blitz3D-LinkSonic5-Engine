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
;   VARIABLES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/	

Global Mesh_Sonic
Global Mesh_Sonic_JumpImage
Global Mesh_Sonic_AfterImage
Global Mesh_Sonic_SpeedBubble
Global Mesh_Sonic_DashBubble
Global Mesh_Sonic_SpeedEffect
Global TrailTex

	; ---- Meshes ----
Function LoadModel(Model$, p.tPlayer)
;	Mesh_Sonic							= LoadAnimMesh("Characters/" + Model$ + "/Sonic.b3d")
	Mesh_Sonic = ParseModel(Model$)
;	Mesh_Sonic = Load_Characters_List(ModelSelect$)
	
	
	DebugLog ("Character name is: " + Model$)
	
	Mesh_Sonic_SpeedEffect				= LoadMesh("Characters\CharObjects\SpeedEffect\Effect.b3d")
	If (Mesh_Sonic_SpeedEffect <> 0) Then HideEntity(Mesh_Sonic_SpeedEffect)
	
	;Global Mesh_Sonic					= LoadAnimMesh("Characters/Test1/1.3DS")
;	RecursiveExtractAnimSeq(Mesh_Sonic,	0,		120)	; Idle
;	RecursiveExtractAnimSeq(Mesh_Sonic,	121,	150)	; Walking
;	RecursiveExtractAnimSeq(Mesh_Sonic,	151,	180)	; Running 2
;	RecursiveExtractAnimSeq(Mesh_Sonic,	181,	196)	; Spinning
;	RecursiveExtractAnimSeq(Mesh_Sonic,	197,	216)	; Falling
;	RecursiveExtractAnimSeq(Mesh_Sonic,	217,	235)	; Spring
;	RecursiveExtractAnimSeq(Mesh_Sonic,	236,	260)	; Land
;	RecursiveExtractAnimSeq(Mesh_Sonic,	261,	272)	; Spin Kick
;	RecursiveExtractAnimSeq(Mesh_Sonic,	273,	292)	; Slide
;	RecursiveExtractAnimSeq(Mesh_Sonic,	293,	300)	; StompPart2
;	RecursiveExtractAnimSeq(Mesh_Sonic,	301,	320)	; RingDash
;	RecursiveExtractAnimSeq(Mesh_Sonic,	321,	340)	; FallSpeed
;	RecursiveExtractAnimSeq(Mesh_Sonic,	341,	370)	; Jogging
;	RecursiveExtractAnimSeq(Mesh_Sonic,	371,	400)	; Hurt
;	RecursiveExtractAnimSeq(Mesh_Sonic,	401,	420)	; Dive 1
;	RecursiveExtractAnimSeq(Mesh_Sonic,	421,	430)	; Dive 2
;	RecursiveExtractAnimSeq(Mesh_Sonic,	431,	450)	; Ramp
;	RecursiveExtractAnimSeq(Mesh_Sonic,	451,	480)	; Running 1
;	RecursiveExtractAnimSeq(Mesh_Sonic,	481,	540)	; Land Hard
;	RecursiveExtractAnimSeq(Mesh_Sonic,	541,	556)	; Trick Loop
;	RecursiveExtractAnimSeq(Mesh_Sonic,	557,	626)	; Idle 1
;	RecursiveExtractAnimSeq(Mesh_Sonic,	627,	696)	; Idle 2
;	RecursiveExtractAnimSeq(Mesh_Sonic,	697,	720)	; Hop
;	RecursiveExtractAnimSeq(Mesh_Sonic,	721,	740)	; Skidding
;	RecursiveExtractAnimSeq(Mesh_Sonic,	741,	860)	; Win Idle
;	RecursiveExtractAnimSeq(Mesh_Sonic,	861,	890)	; Bar Swing
;	RecursiveExtractAnimSeq(Mesh_Sonic,	891,	920)	; Glide / Fly
;	RecursiveExtractAnimSeq(Mesh_Sonic,	921,	960)	; Wall Hit
;	RecursiveExtractAnimSeq(Mesh_Sonic,	961,	970)	; Jog Start
;	RecursiveExtractAnimSeq(Mesh_Sonic,	971,	990)	; Hurdle
;	RecursiveExtractAnimSeq(Mesh_Sonic,	991,	1050)	; Trick 1
;	RecursiveExtractAnimSeq(Mesh_Sonic,	1051,	1070)	; Trick 1
	HideEntity(Mesh_Sonic)
;	DebugLog ("Loading Characters/" + Model$ + "/Sonic.b3d")
	
	TrailTex					= LoadTexture("Characters/" + Model$ + "/Trail.png", 1+2+256)
	
	
;	If p\Character = CHARACTER_SONIC Then
	Mesh_Sonic_JumpImage					= LoadMesh("Characters/" + Model$ + "/JumpImage.b3d")
	If (Mesh_Sonic_JumpImage <> 0) Then HideEntity(Mesh_Sonic_JumpImage)
;	DebugLog ("Loading Characters/" + Model$ + "/JumpImage.b3d")
	
	Mesh_Sonic_AfterImage					= LoadMesh("Characters/" + Model$ + "/AfterImage.b3d")
	If (Mesh_Sonic_AfterImage <> 0) Then HideEntity(Mesh_Sonic_AfterImage)
;	DebugLog ("Loading Characters/" + Model$ + "/AfterImage.b3d")
	
	Mesh_Sonic_SpeedBubble					= LoadAnimMesh("Characters/" + Model$ + "/SpeedBubble.b3d")
	If (Mesh_Sonic_SpeedBubble <> 0) Then
		RecursiveExtractAnimSeq(Mesh_Sonic_SpeedBubble,	0,		21)		; Effect
		HideEntity(Mesh_Sonic_SpeedBubble)
EndIf
;	DebugLog ("Loading Characters/" + Model$ + "/SpeedBubble.b3d")
	
Mesh_Sonic_DashBubble= LoadAnimMesh("Characters/" + Model$ + "/DashBubble.b3d")
If (Mesh_Sonic_DashBubble <> 0) Then 
RecursiveExtractAnimSeq(Mesh_Sonic_DashBubble,	0,		21)		; Effect
HideEntity(Mesh_Sonic_DashBubble)
;	DebugLog ("Loading Characters/" + Model$ + "/DashBubble.b3d")
EndIf
;EndIf
	
End Function
	
	; --------
	
;	Global Mesh_Sonic_AnimTimer			= LoadAnimMesh("Objects/AnimTimer.b3d")
;RecursiveExtractAnimSeq(Mesh_Sonic_AnimTimer,	0,		1000)	; Animation
;RecursiveExtractAnimSeq(Mesh_Sonic_AnimTimer,	0,		1)		; Reset
;HideEntity(Mesh_Sonic_AnimTimer)
	
	
	
;	Global Mesh_Sonic_Spindash			= LoadAnimMesh("Characters/Sonic/Spindash.b3d")
;	HideEntity(Mesh_Sonic_Spindash)

;	Global Mesh_Sonic_JumpBall			= LoadAnimMesh("Characters/Sonic/Jump.b3d")
;	HideEntity(Mesh_Sonic_JumpBall)



Function ParseModel(Model$);(pp.tPlayer_Profile)
	
	number = 0
	
	mesh = 0
	ModelsRootNode = xmlLoad("Characters/"+Model$+"/Character.xml")
	
	If (xmlErrorCount()>0) Then RuntimeError("Error while parsing '1.xml'")
		;UserMesh = 0
	For i=1 To xmlNodeChildCount(ModelsRootNode)
		ModelsChildNode = xmlNodeChild(ModelsRootNode, i)
		Select xmlNodeNameGet$(ModelsChildNode)
				
			Case "Mesh"
				MeshFilename$	= xmlNodeAttributeValueGet(ModelsChildNode, "file")
				mesh		= LoadAnimMesh("Characters/"+MeshFilename$)
				
			Case "Sequence"
				SeqStart	= 0
				SeqEnd		= 0
				SeqNum		= 0
				
				SeqStart	= xmlNodeAttributeValueGet(ModelsChildNode, "start")
				SeqEnd		= xmlNodeAttributeValueGet(ModelsChildNode, "end")
				RecursiveExtractAnimSeq(mesh, SeqStart, SeqEnd)
				
				;Case "Run1" pp\AnimSequence\Run1 = ParseSeq(ModelsChildNode, mesh)
				;Case "Run2" pp\AnimSequence\Run2 = ParseSeq(ModelsChildNode, mesh)
				
	;		Case "footstep"
	;					;	For p.tPlayer = Each tPlayer
	;			
	;			SceneAnim = xmlNodeFind("anim", ModelsChildNode)
	;			
	;			If (SceneAnim <> 0) Then
	;				Game\Gameplay\Foot_Walk	= Float(xmlNodeAttributeValueGet(SceneAnim, "walk"))
	;				Game\Gameplay\Foot_Jog1	= Float(xmlNodeAttributeValueGet(SceneAnim, "jog"))
	;						;	Game\Gameplay\Foot_Jog2		= Float(xmlNodeAttributeValueGet(SceneAnim, "jog2"))
	;				Game\Gameplay\Foot_Run	= Float(xmlNodeAttributeValueGet(SceneAnim, "run"))
	;				Game\Gameplay\Foot_Sprint	= Float(xmlNodeAttributeValueGet(SceneAnim, "sprint"))
	;			EndIf
	;				;		Case "sound"
	;			
	;							; Set local variables' default values
	;			
	;			file$		= ""
	;			number		= 0
	;			SoundArrayEnd = 0
	;			
	;														; Read the data from the xml
	;			file$ = xmlNodeAttributeValueGet(ModelsChildNode, "filename")
	;		;	number = xmlNodeAttributeValueGet(ModelsChildNode, "number")
	;			number		= number + 1
	;			frame = xmlNodeAttributeValueGet(ModelsChildNode, "frame")
	;			anim = xmlNodeAttributeValueGet(ModelsChildNode, "animation")
	;			
	;			If number > 0 Then SoundsLoaded = 1
	;			
	;							; Load the sound into the Sounds array, into the slot specified by the variable called number
	;			
	;			If SoundsLoaded = 1 Then Sounds1[number] = SoundLoad1("Characters\"+Model$+"\Sounds\"+file$, anim, frame)
	;			
	;			SoundArrayEnd = number
				
		End Select
	Next
	
		;If (UserMesh<>0) Then FreeEntity UserMesh
	xmlNodeDelete(ModelsRootNode)
	Return mesh
	
;	Load_Characters_List(ModelSelect$)
End Function

Function ParseSeq(node, mesh)
	SeqStart	= 0
	SeqEnd		= 0
	SeqSpeed#	= 1
	SeqStart	= xmlNodeAttributeValueGet(node, "start")
	SeqEnd		= xmlNodeAttributeValueGet(node, "end")
	;SeqSpeed#	= xmlNodeAttributeValueGet(node, "speed")
	Seq = RecursiveExtractAnimSeq(mesh, SeqStart, SeqEnd)
	Return Seq
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D