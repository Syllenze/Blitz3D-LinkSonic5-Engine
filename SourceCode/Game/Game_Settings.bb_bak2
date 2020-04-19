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
; 	VARIABLES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Game global settings ----
	Global 	GAME_TITLE$			=	"Sonic the Hedgehog"
	Global 	GAME_WINDOW_W		=	1024
	Global 	GAME_WINDOW_H		=	786
	Global	GAME_WINDOW_DEPTH	=	0
	Global 	GAME_WINDOW_MODE	=	2
	Global	GAME_WINDOW_SCALE#	=	Float(GAME_WINDOW_W)/640.0
	Global	GAME_WINDOW_VSYNC	=	False

	; ---- Gameplay global settings ----
	Global	Gameplay_Control_Mode			=	0
	Global	Gameplay_Camera_Smoothness#		=	0.7
	Global	Gameplay_Camera_TargetPOV  		=	0
	Global	Gameplay_Camera_RotationX#		=	0
	Global	Gameplay_Camera_RotationY#		=	0
	Global	Gameplay_Camera_RotationSpeedX#	=	24
	Global	Gameplay_Camera_RotationSpeedY#	=	24

	; ---- Effects settings ----
	Global	Effects_Blur					=	False
	Global	Effects_TexSize					=	128

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig()
		; ---- Parse configuration file XML ----
		; Parse and acquire root node
		Node_Root = xmlLoad("Config.xml")

		; On any parser error, release a runtime error and exit. 
		If (xmlErrorCount()>0) Then RuntimeError("Game_LoadConfig() -> Error while parsin 'Config.xml'")

		; ---- Iterate through all the subsequent nodes -----
		For i = 1 To xmlNodeChildCount(Node_Root)
			; Get XML child node, wich will describe the part of the game to
			; configurate.
			Node_Child = xmlNodeChild(Node_Root, i)
			
			Select Lower$(xmlNodeNameGet$(Node_Child))
				Case "graphics"
					Game_LoadConfig_Graphics(Node_Child)
				Case "input"
					Game_LoadConfig_Input(Node_Child)
				Case "gameplay"
					Game_LoadConfig_Gameplay(Node_Child)
			End Select
		Next

		; ---- Done ----
		xmlNodeDelete(Node_Root)
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Graphics
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Graphics(Node_Root)
		; ---- Iterate through all the subsequent nodes -----
		For i = 1 To xmlNodeChildCount(Node_Root)
			; Get XML child node. These may be "screen" or "effects"
			Node_Child = xmlNodeChild(Node_Root, i)
			
			Select Lower$(xmlNodeNameGet$(Node_Child))
				Case "screen"
					Game_LoadConfig_Graphics_Screen(Node_Child)
				Case "effects"
					Game_LoadConfig_Graphics_Effects(Node_Child)
			End Select
		Next

		; ---- Done ----
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Graphics_Screen
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Graphics_Screen(Node_Root)
	
		; ---- Retrieve information nodes -----
		Node_Width$      = xmlNodeAttributeValueGet$(Node_Root, "width")
		Node_Height$     = xmlNodeAttributeValueGet$(Node_Root, "height")
		Node_Depth$      = xmlNodeAttributeValueGet$(Node_Root, "depth")
		Node_Fullscreen$ = xmlNodeAttributeValueGet$(Node_Root, "fullscreen")
		Node_VSync$      = xmlNodeAttributeValueGet$(Node_Root, "vsync")

		; ---- Apply obtained information -----
		If (Node_Width$ 	 <> "") Then GAME_WINDOW_W     = Int(Node_Width$)
		If (Node_Height$ 	 <> "") Then GAME_WINDOW_H     = Int(Node_Height$)
		If (Node_Depth$ 	 <> "") Then GAME_WINDOW_DEPTH = Int(Node_Depth$)
		If (Node_Fullscreen$ <> "") Then GAME_WINDOW_MODE  = 2-Int(Node_Fullscreen$)
		If (Node_VSync$		 <> "") Then GAME_WINDOW_VSYNC = Int(Node_VSync$)

		; ---- Calculate scale -----
		GAME_WINDOW_SCALE# = Float(GAME_WINDOW_W)/640.0

	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Graphics_Effects
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Graphics_Effects(Node_Root)
		; ---- Iterate through all the subsequent nodes -----
		For i = 1 To xmlNodeChildCount(Node_Root)
			; Get XML child node.
			Node_Child = xmlNodeChild(Node_Root, i)
			
			Select Lower$(xmlNodeNameGet$(Node_Child))
				Case "embm"
					Game_LoadConfig_Graphics_Effects_EMBM(Node_Child)
				Case "blur"
					Game_LoadConfig_Graphics_Effects_Blur(Node_Child)
			End Select
		Next

		; ---- Done ----
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Graphics_Effects_EMBM
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Graphics_Effects_EMBM(Node_Root)
		; ---- Retrieve information nodes -----
		Node_Enabled$    = xmlNodeAttributeValueGet$(Node_Root, "enabled")
		Node_PowOfTwo$   = xmlNodeAttributeValueGet$(Node_Root, "poweroftwo")
		Node_Debug$      = xmlNodeAttributeValueGet$(Node_Root, "debug")
		Node_BorderFix$  = xmlNodeAttributeValueGet$(Node_Root, "borderfix")

		; ---- Apply obtained information -----
		If (Node_Enabled$	 <> "") Then FxManager_Activated      = Int(Node_Enabled$)
		If (Node_PowOfTwo$ 	 <> "") Then FxManager_OnlyPowerOfTwo = Int(Node_PowOfTwo$)
		If (Node_Debug$ 	 <> "") Then FxManager_Debug          = Int(Node_Debug$)
		If (Node_BorderFix$	 <> "") Then FxManager_BorderFix      = Int(Node_BorderFix$)

	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Graphics_Effects_Blur
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Graphics_Effects_Blur(Node_Root)
		; ---- Retrieve information nodes -----
		Node_Enabled$    = xmlNodeAttributeValueGet$(Node_Root, "enabled")
		Node_TexSize$    = xmlNodeAttributeValueGet$(Node_Root, "texsize")

		; ---- Apply obtained information -----
		If (Node_Enabled$	 <> "") Then Effects_Blur		= Int(Node_Enabled$)
		If (Node_TexSize$ 	 <> "") Then Effects_TexSize	= Int(Node_TexSize$)

	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Input
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Input(Node_Root)
		; ---- Retrieve information nodes -----
		Node_MouseSpeed$   = xmlNodeAttributeValueGet$(Node_Root, "mouse_speed")
		Node_MouseSens$    = xmlNodeAttributeValueGet$(Node_Root, "mouse_sensitivy")
		Node_Gamepad$      = xmlNodeAttributeValueGet$(Node_Root, "gamepad")
		Node_GamepadThres$ = xmlNodeAttributeValueGet$(Node_Root, "gamepad_threshold")

		; ---- Apply obtained information -----
		If (Node_MouseSpeed$   <> "") Then Input_MouseSpeed       = Float(Node_MouseSpeed$)
		If (Node_MouseSens$    <> "") Then Input_MouseSensitivy   = Float(Node_MouseSens$)
		If (Node_Gamepad$ 	   <> "") Then Input_Gamepad          = Float(Node_Gamepad$)
		If (Node_GamepadThres$ <> "") Then Input_GamepadThreshold = Float(Node_GamepadThres$)

		; ---- Iterate through all the subsequent nodes -----
		For i = 1 To xmlNodeChildCount(Node_Root)
			; Get XML child node.
			Node_Child = xmlNodeChild(Node_Root, i)
			
			Select Lower$(xmlNodeNameGet$(Node_Child))
				Case "bind"
					Game_LoadConfig_Input_Bind(Node_Child)
			End Select
		Next

		; ---- Done ----
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Input_Bind
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Input_Bind(Node_Root)
		; ---- Retrieve information nodes -----
		Node_Action$    = xmlNodeAttributeValueGet$(Node_Root, "action")
		Node_Device$    = xmlNodeAttributeValueGet$(Node_Root, "device")
		Node_Button$    = xmlNodeAttributeValueGet$(Node_Root, "button")
		Node_AltDevice$ = xmlNodeAttributeValueGet$(Node_Root, "altdevice")
		Node_AltButton$ = xmlNodeAttributeValueGet$(Node_Root, "altbutton")

		; All these fields are required, launch a runtime error if any of them isn't there
		If (Node_Action$="" Or Node_Device$="" Or Node_Button$="" Or Node_AltDevice$="" Or Node_AltButton$="") Then
			RuntimeError("Game_LoadConfig() -> Required field missing on input binding ('Config.xml').")
		End If
		
		; ---- Find out wich action are we going to bind -----
		Action = 0
		Select Lower$(Node_Action$)
			Case "forward"			: Action = INPUT_BUTTON_UP
			Case "backwards"		: Action = INPUT_BUTTON_DOWN
			Case "left"				: Action = INPUT_BUTTON_LEFT
			Case "right"			: Action = INPUT_BUTTON_RIGHT
			Case "actiona"			: Action = INPUT_BUTTON_ACTION_A
			Case "actionb"			: Action = INPUT_BUTTON_ACTION_B
			Case "camera_up"		: Action = INPUT_BUTTON_CAMERA_UP
			Case "camera_down"		: Action = INPUT_BUTTON_CAMERA_DOWN
			Case "camera_left"		: Action = INPUT_BUTTON_CAMERA_LEFT
			Case "camera_right"		: Action = INPUT_BUTTON_CAMERA_RIGHT
			Case "camera_zoomin"	: Action = INPUT_BUTTON_CAMERA_ZIN
			Case "camera_zoomout"	: Action = INPUT_BUTTON_CAMERA_ZOUT
		End Select

		; ---- Find out the device and button pressed ----
		Device = INPUT_DEVICE_NONE
		Button = 0
		Select Lower$(Node_Device$)
			Case "none"
				; ---- Assign device -----
				Device = INPUT_DEVICE_NONE
			Case "keyboard"
				; ---- Assign device -----
				Device = INPUT_DEVICE_KEYBOARD

				; ---- Assign button -----
				For i=0 To 220
					If (Node_Button$ = Lower$(Keynames(i))) Then
						Button = i
						Exit
					End If
				Next
			Case "mouse"
				; ---- Assign device -----
				Device = INPUT_DEVICE_MOUSE

				; ---- Assign button -----
				Select Lower$(Node_Button$)
					Case "x-"		: Button = INPUT_MOUSE_XMINUS
					Case "x+"		: Button = INPUT_MOUSE_XPLUS
					Case "y-"		: Button = INPUT_MOUSE_YMINUS
					Case "y+"		: Button = INPUT_MOUSE_YPLUS
					Case "wheel-"	: Button = INPUT_MOUSE_WHEELMINUS
					Case "wheel+"	: Button = INPUT_MOUSE_WHEELPLUS
					Case "left"		: Button = INPUT_MOUSE_LEFT
					Case "right"	: Button = INPUT_MOUSE_RIGHT
					Case "middle"	: Button = INPUT_MOUSE_MIDDLE
				End Select
			Case "gamepad"
				; ---- Assign device -----
				Device = INPUT_DEVICE_GAMEPAD

				; ---- Assign button -----
				Select Lower$(Node_Button$)
					Case "x-"		: Button = INPUT_GAMEPAD_XMINUS
					Case "x+"		: Button = INPUT_GAMEPAD_XPLUS
					Case "y-"		: Button = INPUT_GAMEPAD_YMINUS
					Case "y+"		: Button = INPUT_GAMEPAD_YPLUS
					Case "z-"    	: Button = INPUT_GAMEPAD_ZMINUS
					Case "z+"    	: Button = INPUT_GAMEPAD_ZPLUS
					Case "u-"  		: Button = INPUT_GAMEPAD_UMINUS
					Case "u+"   	: Button = INPUT_GAMEPAD_UPLUS
					Case "v-"    	: Button = INPUT_GAMEPAD_VMINUS
					Case "v+"    	: Button = INPUT_GAMEPAD_VPLUS
					Case "yaw-"  	: Button = INPUT_GAMEPAD_YAWMINUS
					Case "yaw+"  	: Button = INPUT_GAMEPAD_YAWPLUS
					Case "pitch-"	: Button = INPUT_GAMEPAD_PITCHMINUS
					Case "pitch+"  	: Button = INPUT_GAMEPAD_PITCHPLUS
					Case "roll-" 	: Button = INPUT_GAMEPAD_ROLLMINUS
					Case "roll+" 	: Button = INPUT_GAMEPAD_ROLLPLUS
					Case "button1" 	: Button = INPUT_GAMEPAD_BUTTON+0
					Case "button2" 	: Button = INPUT_GAMEPAD_BUTTON+1
					Case "button3" 	: Button = INPUT_GAMEPAD_BUTTON+2
					Case "button4" 	: Button = INPUT_GAMEPAD_BUTTON+3
					Case "button5" 	: Button = INPUT_GAMEPAD_BUTTON+4
					Case "button6" 	: Button = INPUT_GAMEPAD_BUTTON+5
					Case "button7" 	: Button = INPUT_GAMEPAD_BUTTON+6
					Case "button8" 	: Button = INPUT_GAMEPAD_BUTTON+7
					Case "button9" 	: Button = INPUT_GAMEPAD_BUTTON+8
					Case "button10"	: Button = INPUT_GAMEPAD_BUTTON+9
					Case "button11"	: Button = INPUT_GAMEPAD_BUTTON+10
					Case "button12"	: Button = INPUT_GAMEPAD_BUTTON+11
					Case "button13"	: Button = INPUT_GAMEPAD_BUTTON+12
					Case "button14"	: Button = INPUT_GAMEPAD_BUTTON+13
					Case "button15"	: Button = INPUT_GAMEPAD_BUTTON+14
					Case "button16"	: Button = INPUT_GAMEPAD_BUTTON+15
				End Select
		End Select
		Input\Configuration[Action]\Device = Device
		Input\Configuration[Action]\Button = Button

		; ---- Find out the alternative device and alternative button pressed ----
		AltDevice = INPUT_DEVICE_NONE
		AltButton = 0
		Select Lower$(Node_AltDevice$)
			Case "none"
				; ---- Assign device -----
				AltDevice = INPUT_DEVICE_NONE
			Case "keyboard"
				; ---- Assign device -----
				AltDevice = INPUT_DEVICE_KEYBOAD

				; ---- Assign button -----
				For i=0 To 220
					If (Node_AltButton$ = Lower$(Keynames(i))) Then
						AltButton = i
						Exit
					End If
				Next
			Case "mouse"
				; ---- Assign device -----
				AltDevice = INPUT_DEVICE_MOUSE

				; ---- Assign button -----
				Select Lower$(Node_AltButton$)
					Case "x-"		: AltButton = INPUT_MOUSE_XMINUS
					Case "x+"		: AltButton = INPUT_MOUSE_XPLUS
					Case "y-"		: AltButton = INPUT_MOUSE_YMINUS
					Case "y+"		: AltButton = INPUT_MOUSE_YPLUS
					Case "wheel-"	: AltButton = INPUT_MOUSE_WHEELMINUS
					Case "wheel+"	: AltButton = INPUT_MOUSE_WHEELPLUS
					Case "left"		: AltButton = INPUT_MOUSE_LEFT
					Case "right"	: AltButton = INPUT_MOUSE_RIGHT
					Case "middle"	: AltButton = INPUT_MOUSE_MIDDLE
				End Select
			Case "gamepad"
				; ---- Assign device -----
				AltDevice = INPUT_DEVICE_GAMEPAD

				; ---- Assign button -----
				Select Lower$(Node_AltButton$)
					Case "x-"		: AltButton = INPUT_GAMEPAD_XMINUS
					Case "x+"		: AltButton = INPUT_GAMEPAD_XPLUS
					Case "y-"		: AltButton = INPUT_GAMEPAD_YMINUS
					Case "y+"		: AltButton = INPUT_GAMEPAD_YPLUS
					Case "z-"    	: AltButton = INPUT_GAMEPAD_ZMINUS
					Case "z+"    	: AltButton = INPUT_GAMEPAD_ZPLUS
					Case "u-"  		: AltButton = INPUT_GAMEPAD_UMINUS
					Case "u+"   	: AltButton = INPUT_GAMEPAD_UPLUS
					Case "v-"    	: AltButton = INPUT_GAMEPAD_VMINUS
					Case "v+"    	: AltButton = INPUT_GAMEPAD_VPLUS
					Case "yaw-"  	: AltButton = INPUT_GAMEPAD_YAWMINUS
					Case "yaw+"  	: AltButton = INPUT_GAMEPAD_YAWPLUS
					Case "pitch-"	: AltButton = INPUT_GAMEPAD_PITCHMINUS
					Case "pitch+"  	: AltButton = INPUT_GAMEPAD_PITCHPLUS
					Case "roll-" 	: AltButton = INPUT_GAMEPAD_ROLLMINUS
					Case "roll+" 	: AltButton = INPUT_GAMEPAD_ROLLPLUS
					Case "button1" 	: AltButton = INPUT_GAMEPAD_BUTTON+0
					Case "button2" 	: AltButton = INPUT_GAMEPAD_BUTTON+1
					Case "button3" 	: AltButton = INPUT_GAMEPAD_BUTTON+2
					Case "button4" 	: AltButton = INPUT_GAMEPAD_BUTTON+3
					Case "button5" 	: AltButton = INPUT_GAMEPAD_BUTTON+4
					Case "button6" 	: AltButton = INPUT_GAMEPAD_BUTTON+5
					Case "button7" 	: AltButton = INPUT_GAMEPAD_BUTTON+6
					Case "button8" 	: AltButton = INPUT_GAMEPAD_BUTTON+7
					Case "button9" 	: AltButton = INPUT_GAMEPAD_BUTTON+8
					Case "button10"	: AltButton = INPUT_GAMEPAD_BUTTON+9
					Case "button11"	: AltButton = INPUT_GAMEPAD_BUTTON+10
					Case "button12"	: AltButton = INPUT_GAMEPAD_BUTTON+11
					Case "button13"	: AltButton = INPUT_GAMEPAD_BUTTON+12
					Case "button14"	: AltButton = INPUT_GAMEPAD_BUTTON+13
					Case "button15"	: AltButton = INPUT_GAMEPAD_BUTTON+14
					Case "button16"	: AltButton = INPUT_GAMEPAD_BUTTON+15
				End Select
		End Select
		Input\Configuration[Action]\AlternateDevice = AltDevice
		Input\Configuration[Action]\AlternateButton = AltButton

		; ---- Done ----

	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Gameplay
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Gameplay(Node_Root)
		; ---- Iterate through all the subsequent nodes -----
		For i = 1 To xmlNodeChildCount(Node_Root)
			; Get XML child node.
			Node_Child = xmlNodeChild(Node_Root, i)
			
			Select Lower$(xmlNodeNameGet$(Node_Child))
				Case "camera"
					Game_LoadConfig_Gameplay_Camera(Node_Child)
				Case "control"
					Game_LoadConfig_Gameplay_Control(Node_Child)
			End Select
		Next

		; ---- Done ----
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Gameplay_Control
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Gameplay_Control(Node_Root)
		; ---- Retrieve information nodes -----
		Node_Mode$    		= xmlNodeAttributeValueGet$(Node_Root, "mode")

		; ---- Apply obtained information -----
		If (Node_Mode$ <> "") Then Gameplay_Control_Mode = Int(Node_Mode$)
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_LoadConfig_Gameplay_Camera
	; ---------------------------------------------------------------------------------------------------------
	Function Game_LoadConfig_Gameplay_Camera(Node_Root)
		; ---- Retrieve information nodes -----
		Node_Smoothness$	= xmlNodeAttributeValueGet$(Node_Root, "smothness")
		Node_RotationX$     = xmlNodeAttributeValueGet$(Node_Root, "rotation_x")
		Node_RotationY$     = xmlNodeAttributeValueGet$(Node_Root, "rotation_y")
		Node_RotationSpeedX$= xmlNodeAttributeValueGet$(Node_Root, "rotation_speed_x")
		Node_RotationSpeedY$= xmlNodeAttributeValueGet$(Node_Root, "rotation_speed_x")
		Node_TargetPOV$     = xmlNodeAttributeValueGet$(Node_Root, "targetpov")

		; ---- Apply obtained information -----
		If (Node_Smoothness$	 <> "") Then Gameplay_Camera_Smoothness#	 = Float#(Node_Smoothness$)
		If (Node_RotationX$      <> "") Then Gameplay_Camera_RotationX#      = Float#(Node_RotationX$)
		If (Node_RotationY$      <> "") Then Gameplay_Camera_RotationY#      = Float#(Node_RotationY$)
		If (Node_RotationSpeedX$ <> "") Then Gameplay_Camera_RotationSpeedX# = Float#(Node_RotationSpeedX$)
		If (Node_RotationSpeedY$ <> "") Then Gameplay_Camera_RotationSpeedY# = Float#(Node_RotationSpeedY$)
		If (Node_TargetPOV$      <> "") Then Gameplay_Camera_TargetPOV       = Int(Node_TargetPOV$)
	End Function