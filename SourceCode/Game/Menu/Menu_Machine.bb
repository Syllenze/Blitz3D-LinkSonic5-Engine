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
;               20/01/2008 - Started working on Menu engine.                                                   ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO: - Implement pretty much everything                                                                   ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
; 	STRUCTURES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\

	; ---------------------------------------------------------------------------------------------------------
	; tMenu_Machine
	; ---------------------------------------------------------------------------------------------------------
	Type tMenu_Machine
		Field Interface.tMenu_Interface
		Field NewInterface.tMenu_Interface
		Field NumInterfaces
		Field Interfaces.tMenu_Interface[MENU_NUM_INTERFACES]
		Field State
		Field Control
		Field ControlReference.tMenu_Control
	End Type

	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; Menu constants
	Const MENU_STATE_PRESTART			= 0
	Const MENU_STATE_START			    = 1
	Const MENU_STATE_STEP				= 2
	Const MENU_STATE_PREEND				= 3
	Const MENU_STATE_END				= 4	
	Const MENU_STATE_CHANGE				= 5

	Const MENU_NUM_INTERFACES			= 100
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Creation/Destruction and management ----
	; =========================================================================================================
	; Menu_Machine_Create
	; =========================================================================================================
	Function Menu_Machine_Create.tMenu_Machine()
		m.tMenu_Machine = New tMenu_Machine
			m\Interface 		= Null
			m\NumInterfaces		= 0
			m\State				= MENU_STATE_PRESTART
			m\Control			= 0
			m\ControlReference	= Null
		Return m
	End Function

	; =========================================================================================================
	; Menu_Machine_Destroy
	; =========================================================================================================
	Function Menu_Machine_Destroy(m.tMenu_Machine)

	End Function


	; =========================================================================================================
	; Menu_Machine_AttachInterface
	; =========================================================================================================
	Function Menu_Machine_AttachInterface(m.tMenu_Machine, i.tMenu_Interface)
		If (m\NumInterfaces = 0) Then : m\Interface = i : m\NewInterface = i : End If
		
		m\Interfaces[m\NumInterfaces] = i
		m\NumInterfaces = m\NumInterfaces+1

	End Function


	; =========================================================================================================
	; Menu_Machine_DetachInterface
	; =========================================================================================================
	Function Menu_Machine_DetachInterface(m.tMenu_Machine, i.tMenu_Interface)
		For j=0 To m\NumInterfaces-1
			If (m\Interfaces[j] = i) Then
				Menu_Machine_DetachInterfaceById(m, j)
				Return
			End If
		Next
	End Function


	; =========================================================================================================
	; Menu_Machine_DetachInterfaceById
	; =========================================================================================================
	Function Menu_Machine_DetachInterfaceById(m.tMenu_Machine, id)
		If (j < 0 Or j >= m\NumInterfaces) Then Return

		For j = id+1 To m\NumInterfaces-1
			m\Interfaces[j-1] = m\Interfaces[j]
		Next
		m\Interfaces[m\NumInterfaces-1] = Null
		m\NumInterfaces = m\NumInterfaces-1
	End Function


	; =========================================================================================================
	; Menu_Machine_FindInterface
	; =========================================================================================================
	Function Menu_Machine_FindInterface.tMenu_Interface(m.tMenu_Machine, Name$)
		For j=0 To m\NumInterfaces-1
			If (m\Interfaces[j]\Name$ = Name$) Then Return m\Interfaces[j]
		Next
		Return Null
	End Function


	; =========================================================================================================
	; Menu_Machine_Update
	; =========================================================================================================
	Function Menu_Machine_Update(m.tMenu_Machine)

		Select m\State
			Case MENU_STATE_PRESTART
				Menu_Callback(m, m\Interface, "PreStart")
				m\State = MENU_STATE_START
			Case MENU_STATE_START
				; Callback. When the callback returns true, next state is activated
				If (Menu_Callback(m, m\Interface, "Start")=True) Then m\State = MENU_STATE_STEP
			Case MENU_STATE_STEP
				; Manage selection change

				; Callback
				Menu_Callback(m, m\Interface, "Step")

				; Check for interface change
				If (m\Interface <> m\NewInterface) Then m\State = MENU_STATE_PREEND
			Case MENU_STATE_PREEND
				Menu_Callback(m, m\Interface, "PreEnd")
				m\State = MENU_STATE_END
			Case MENU_STATE_END
				; Callback. When the callback returns true, next state is activated
				If (Menu_Callback(m, m\Interface, "End")=True) Then m\State = MENU_STATE_CHANGE
			Case MENU_STATE_CHANGE
				; Callback
				Menu_Callback(m, m\Interface, "Change")

				; Change interface and state
				m\Interface = m\NewInterface
				m\State	    = MENU_STATE_START
		End Select
		
	End Function


	; =========================================================================================================
	; Menu_Machine_Render
	; =========================================================================================================
	Function Menu_Machine_Render(m.tMenu_Machine)
		Menu_Callback(m, m\Interface, "Pre-Render")
			For i = 0 To m\Interface\NumControls-1
			
				Select m\Interface\Controls[i]\Kind
				
					Case MENU_CONTROL_TEXT
						Menu_Control_Text_Render(m\Interface\Controls[i])
						
					Case MENU_CONTROL_IMAGE
						Menu_Control_Image_Render(m\Interface\Controls[i])
						
				End Select
				
			Next
		Menu_Callback(m, m\Interface, "Post-Render")
	End Function

;	; =========================================================================================================
;	; Menu_Callback
;	; =========================================================================================================
;	Function Menu_Callback(m.tMenu_Machine, i.tMenu_Interface, Callback$)
;		Select i\Name$
;			Case "Test01"
;				Select Callback$
;					Case "Start"
;					
;					Case "Step"
;					
;					Case "End"
;					
;					Case "Change"
;					
;					Case "Pre-Render"
;					
;					Case "Post-Render"
;					
;				End Select
;			Case "Test02"
;				Select Callback$
;					Case "Start"
;					
;					Case "Step"
;					
;					Case "End"
;					
;					Case "Change"
;					
;					Case "Pre-Render"
;					
;					Case "Post-Render"
;					
;				End Select
;		End Select
;	End Function 