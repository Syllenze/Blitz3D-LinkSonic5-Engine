; MENU BUTTONS

Type tOption
	Field Scenario
	Field state
	Field x,y
	Field w,h
	Field xw,yh
End Type

Const ArraySize = 3

Global MousePressed = False
Global SelectBar.tOption=New tOption
SelectBar\x=40
SelectBar\y=50
SelectBar\w=170
SelectBar\h=66

Global OptionArray.tOption[ArraySize]
For i=0 To ArraySize
	OptionArray[i]=New tOption
	OptionArray[i]\x = 40
	OptionArray[i]\y = (i*64)+48
	OptionArray[i]\w = 168
	OptionArray[i]\h = 64
	OptionArray[i]\xw = OptionArray[i]\x + OptionArray[i]\w
	OptionArray[i]\yh = OptionArray[i]\y + OptionArray[i]\h
Next

; Update Option
Function UpdateOption(o.tOption)
	Select MouseDown(1)
		Case 0 : MousePressed=False
		Case 1
			If MouseX() > o\x And MouseX() < o\xw And MouseY() > o\y And MouseY() < o\yh And MousePressed=False Then
			;	Select o\Scenario
			;		Case 1
				Select o\state
					Case False
						o\state=True
						MousePressed=True
					Case True
						o\state=False
						MousePressed=True
				End Select
			;	End Select
			EndIf
	End Select
	
	; Draw the button
	Rect o\x,o\y,o\x+o\w,o\y+o\h,0
	Select o\state
		Case 0
			Text o\x,o\y,"Off"
		Case 1
			Text o\x,o\y,"On"
	End Select
	Text o\x,o\y+10,o\x+" "+o\y
	
End Function

Function UpdateSelectBar(o.tOption)
	If KeyHit(200) And o\state > 0 Then o\state = o\state - 1
	If KeyHit(208) And o\state < ArraySize Then o\state = o\state + 1
	
	o\x = OptionArray[o\state]\x
	o\y = OptionArray[o\state]\y
	
	; Run selector code
	If KeyHit(28) Then
	;	Text 620,460,"OK"
		Select OptionArray[o\state]\state
			Case False
				OptionArray[o\state]\state=True
				MousePressed=True
			Case True
				OptionArray[o\state]\state=False
				MousePressed=True
		End Select
	EndIf
	
	Color 255,255,128
	Rect o\x,o\y,o\w,o\h,0
	Text o\x+2,o\y+20,o\x+" "+o\y
	Color 255,255,255
	Text 250, 40, "Use the Arrow Keys to navigate though the menu"
	Text 250, 50, "...Or use the mouse to click an option"
	Text 250, 60, "Press ENTER to make a selection"
	Text 250, 80, "Press Left CTRL to open the stage select text box"
	
	If (KeyHit(KEY_CTRL_LEFT) And Game\TypeStage = 0) Then
		Game\TypeStage = 1
	EndIf
	
;	If (KeyHit(KEY_CTRL_LEFT) And Game\TypeStage = 1) Then
;		Game\TypeStage = 0
;	EndIf
	
	If (Game\TypeStage = 1) Then
		Text 155*GAME_WINDOW_SCALE#, 290*GAME_WINDOW_SCALE#, "Type the stage's name, and press ENTER"
		Text 155*GAME_WINDOW_SCALE#, 300*GAME_WINDOW_SCALE#, "Press ESC to exit the stage select text box"
	EndIf
End Function

; Update All
Function UpdateAll()
	For i=0 To ArraySize
		UpdateOption(OptionArray[i])
	Next
	UpdateSelectBar(SelectBar)
End Function

; HIDDEN MENU

Type Button
	Field x
	Field y
	Field sx
	Field sy
End Type

Type Window
	Field x
	Field y
	Field dx#
	Field dy#
	Field sx
	Field sy
	Field button1.Button
	Field button2.Button
	Field button3.Button
	Field button4.Button
End Type

Global hiddenmenu.Window = New Window
hiddenmenu\sx = GAME_WINDOW_W/2
hiddenmenu\sy = GAME_WINDOW_H/4
hiddenmenu\x = GAME_WINDOW_W/4
hiddenmenu\y = GAME_WINDOW_H/2+(GAME_WINDOW_H/4+100)
hiddenmenu\button1.Button = New Button
hiddenmenu\button1\x = 4
hiddenmenu\button1\y = 4
hiddenmenu\button1\sx = 64
hiddenmenu\button1\sy = 32

hiddenmenu\button2.Button = New Button
hiddenmenu\button2\x = 68
hiddenmenu\button2\y = 4
hiddenmenu\button2\sx = 64
hiddenmenu\button2\sy = 32
;~IDEal Editor Parameters:
;~C#Blitz3D