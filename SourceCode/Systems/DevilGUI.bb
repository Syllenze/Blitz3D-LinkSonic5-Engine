Type GUI_Application
	Field w, h
	Field skin.GUI_Skin
	Field mx, my, mh1, md1, mu1, oldmd1, mh1x, mh1y, mzs, PointerFrame
	Field gk
	Field gadox, gadoy, gadow
	Field old_event, event
	Field btn_focus.GUI_Button, hlp_button_pressedtime
End Type
Type GUI_Skin
	;Main
	Field Img_Pointer
	Field Fnt_Gadgets
	Field Pointer_Window
	Field Pointer_Scrollbar
	Field Pointer_Edit
	Field Pointer_Slider
	Field Snd_Event_Click
	;Window
	Field Img_Window_Tilebar
	Field Window_Tilebar_Height
	Field Img_Window_Border
	Field Img_Window_Icons
	Field Fnt_Window_Title
	Field Col_Window_Title_Active$
	Field Col_Window_Title_Inactive$
	Field Img_Window_BG
	Field Window_Title_Centered
	Field Window_Icon_Margin
	;Tab
	Field Img_Tab
	;TabPage
	Field Img_TabPage
	Field TabPage_Height
	Field Col_TabPage_Enabled$
	Field Col_TabPage_Disabled$
	Field Col_TabPage_Disabled_Shadow$
	Field TabPage_BeginOffset
	;GroupBox
	Field Img_GroupBox
	Field Col_GroupBox$
	;Button
	Field Img_Button
	Field Col_Button_Enabled$
	Field Col_Button_Disabled$
	Field Col_Button_Disabled_Shadow$
	Field Col_Button_Text$
	;CheckBox
	Field Img_CheckBox
	Field CheckBox_Width
	Field CheckBox_Height
	Field Col_CheckBox_Enabled$
	Field Col_CheckBox_Disabled$
	Field Col_CheckBox_Disabled_Shadow$
	;Radio
	Field Img_Radio
	Field Radio_Width
	Field Radio_Height
	Field Col_Radio_Enabled$
	Field Col_Radio_Disabled$
	Field Col_Radio_Disabled_Shadow$
	;Slider
	Field Img_Slider
	Field Slider_Height
	;Spinner
	Field Img_Spinner
	Field Col_Spinner_Enabled$
	Field Col_Spinner_Disabled$
	;Label
	Field Col_Label$
	;ComboBox
	Field Img_ComboBox
	Field Col_ComboBox_DropDown_Border$
	Field Col_ComboBox_DropDown_BG$
	Field Col_ComboBox_Enabled$
	Field Col_ComboBox_Disabled$
	Field Col_ComboBox_Highlight$
	Field Col_ComboBox_Selected$
	Field Col_ComboBox_Selected_Border$
	;ProgressBar
	Field Img_ProgressBar
	;ScrollBar
	Field Img_ScrollBar
	Field Img_ScrollBar_Buttons
	Field ScrollBar_Width
	Field ScrollBar_Button_Height
	;Icon
	Field Img_Icon
	Field Col_Icon_Enabled$
	Field Col_Icon_MouseDown$
	Field Col_Icon_Disabled$
	Field Col_Icon_Disabled_Shadow$
	;ListBox
	Field Img_ListBox
	Field Col_ListBox_Text$
	Field Col_ListBox_Selected$
	Field Col_ListBox_Selected_Border$
	Field Col_ListBox_Highlight$
	;Edit
	Field Img_Edit
	Field Col_Edit_Text$
	;Menu
	Field Img_Menu
	Field Col_Menu_Enabled$
	Field Col_Menu_Highlight$
	Field Col_Menu_Selected$
	Field Col_Menu_Selected_Border$
	Field Col_Menu_DropDown_Border$
	Field Col_Menu_DropDown_BG$
	Field Col_Menu_DropDown_Strip$
End Type
Type GUI_Param
	Field p1$, p2$
End Type
Type GUI_Window
	Field img_active, img_inactive, img_max
	Field x, y, w, h, txt$, icon
	Field btn_close, btn_close_op, btn_max, btn_max_op, btn_min, btn_min_op, btn_hlp, btn_hlp_op
	Field closed, maximized, minimized, locked
	Field px, py
	Field mnu_open
End Type
Type GUI_Tab
	Field owner
	Field img
	Field x, y, w, h, active_tabp.GUI_TabPage
End Type
Type GUI_TabPage
	Field owner
	Field img[3]
	Field txt$, icon, enabled
End Type
Type GUI_GroupBox
	Field owner
	Field img
	Field x, y, w, h, txt$
End Type
Type GUI_Button
	Field owner
	Field img[4]
	Field x, y, w, h, txt$, icon, enabled
	Field op
End Type
Type GUI_CheckBox
	Field owner
	Field x, y, w, h, txt$, enabled, checked
	Field op
End Type
Type GUI_Radio
	Field owner
	Field x, y, w, h, txt$, enabled, checked, gid
	Field op
End Type
Type GUI_Image
	Field owner
	Field x, y, w, h, img
End Type
Type GUI_Slider
	Field owner
	Field img
	Field x, y, w, value#, min#, max#, enabled
	Field pp
End Type
Type GUI_Spinner
	Field owner
	Field img[5]
	Field x, y, w, value#, min#, max#, inc#, enabled
	Field op1, op2
End Type
Type GUI_Label
	Field owner
	Field x, y, txt$, align
End Type
Type GUI_ComboBox
	Field owner
	Field img[3]
	Field x, y, w, txt$[GUI_StackSize], icon[GUI_StackSize], enabled
	Field opened, sel
End Type
Type GUI_ProgressBar
	Field owner
	Field img[1]
	Field x, y, w, h, status
End Type
Type GUI_ScrollBar
	Field owner
	Field img1, img2[2]
	Field x, y, h, status
	Field bh, bp, pp
End Type
Type GUI_Icon
	Field owner
	Field img[4]
	Field x, y, w, h, txt$, icon, enabled, gid, checked
	Field op
End Type
Type GUI_ListBox
	Field owner
	Field img
	Field x, y, w, h, txt$[GUI_StackSize], icon[GUI_StackSize]
	Field sel, scr
End Type
Type GUI_Edit
	Field owner
	Field img
	Field x, y, w, txt$
	Field active, pos
End Type
Type GUI_Menu
	Field owner
	Field txt$, enabled
	Field open, drx, dry
End Type
Global gui.GUI_Application
Const GUI_SkinVersion$ = "dgui_skinversion_1.2.7"
Const GUI_StackSize = 256

Function GUI_InitGUI(skin_path$)
gui = New GUI_Application
gui\w = GraphicsWidth()
gui\h = GraphicsHeight()
GUI_LoadSkin(skin_path$)
gui\old_event = -1
gui\event = -1
End Function

Function GUI_FreeGUI()
For gad.GUI_Window = Each GUI_Window
	GUI_Message(Handle(gad), "Close")
	GUI_DeleteGadgets(Handle(gad))
	Delete gad
Next
GUI_FreeSkin()
Delete gui
End Function

Function GUI_LoadSkin(path$)
If gui\skin <> Null Then
	Cls
	GUI_UpdateGUI(11)
	Flip 0
EndIf
GUI_FreeSkin()
gui\skin = New GUI_Skin
skin.GUI_Skin = gui\skin
CreateDir("GUI_Tmp")
file = ReadFile(path$)
If ReadString(file) <> GUI_SkinVersion$ Then RuntimeError "GUI Skin version expired." + Chr(10) + "Please download the latest update."
While Not Eof(file)
	name$ = ReadString(file)
	size = ReadInt(file)
	file2 = WriteFile("GUI_Tmp\" + name$)
	For i = 0 To size
		WriteByte file2, ReadByte(file)
	Next
	CloseFile file2
Wend
GUI_ReadParams()
;Main
skin\Img_Pointer = GUI_LoadImage("GUI_Tmp\Pointer.png", 20, 20, 12)
skin\Fnt_Gadgets = GUI_LoadFont(GUI_GetParam("Fnt_Gadgets$"))
skin\Pointer_Window = GUI_GetParam("Pointer_Window")
skin\Pointer_Scrollbar = GUI_GetParam("Pointer_Scrollbar")
skin\Pointer_Edit = GUI_GetParam("Pointer_Edit")
skin\Pointer_Slider = GUI_GetParam("Pointer_Slider")
skin\Snd_Event_Click = LoadSound("GUI_Tmp\Event_Click.wav")
;Window
skin\Window_Tilebar_Height = GUI_ImageHeight("GUI_Tmp\Window_Tilebar.png")
skin\Img_Window_Tilebar = GUI_LoadImage("GUI_Tmp\Window_Tilebar.png", 7, skin\Window_Tilebar_Height, 6)
skin\Img_Window_Border = GUI_LoadImage("GUI_Tmp\Window_Border.png", 4, 4, 12)
skin\Img_Window_Icons = GUI_LoadImage("GUI_Tmp\Window_Icons.png", -4, -5, 20)
skin\Fnt_Window_Title = GUI_LoadFont(GUI_GetParam("Fnt_Window_Title$"))
skin\Col_Window_Title_Active$ = GUI_GetParam("Col_Window_Title_Active$")
skin\Col_Window_Title_Inactive$ = GUI_GetParam("Col_Window_Title_Inactive$")
skin\Img_Window_BG = GUI_LoadImage("GUI_Tmp\Window_BG.png")
skin\Window_Title_Centered = GUI_GetParam("Window_Title_Centered")
skin\Window_Icon_Margin = GUI_GetParam("Window_Icon_Margin")
;Tab
skin\Img_Tab = GUI_LoadImage("GUI_Tmp\Tab.png", 10, 10, 9)
;TabPage
skin\TabPage_Height = GUI_ImageHeight("GUI_Tmp\TabPage.png")
skin\Img_TabPage = GUI_LoadImage("GUI_Tmp\TabPage.png", 4, skin\TabPage_Height, 12)
skin\Col_TabPage_Enabled$ = GUI_GetParam("Col_TabPage_Enabled$")
skin\Col_TabPage_Disabled$ = GUI_GetParam("Col_TabPage_Disabled$")
skin\Col_TabPage_Disabled_Shadow$ = GUI_GetParam("Col_TabPage_Disabled_Shadow$")
skin\TabPage_BeginOffset = GUI_GetParam("TabPage_BeginOffset")
;GroupBox
skin\Img_GroupBox = GUI_LoadImage("GUI_Tmp\GroupBox.png", 10, 10, 9)
skin\Col_GroupBox$ = GUI_GetParam("Col_GroupBox$")
;Button
skin\Img_Button = GUI_LoadImage("GUI_Tmp\Button.png", 10, 10, 45)
skin\Col_Button_Enabled$ = GUI_GetParam("Col_Button_Enabled$")
skin\Col_Button_Disabled$ = GUI_GetParam("Col_Button_Disabled$")
skin\Col_Button_Disabled_Shadow$ = GUI_GetParam("Col_Button_Disabled_Shadow$")
;CheckBox
skin\CheckBox_Width = GUI_ImageWidth("GUI_Tmp\CheckBox.png") / 8
skin\CheckBox_Height = GUI_ImageHeight("GUI_Tmp\CheckBox.png")
skin\Img_CheckBox = GUI_LoadImage("GUI_Tmp\CheckBox.png", skin\CheckBox_Width, skin\CheckBox_Height, 8)
skin\Col_CheckBox_Enabled$ = GUI_GetParam("Col_CheckBox_Enabled$")
skin\Col_CheckBox_Disabled$ = GUI_GetParam("Col_CheckBox_Disabled$")
skin\Col_CheckBox_Disabled_Shadow$ = GUI_GetParam("Col_CheckBox_Disabled_Shadow$")
;Radio
skin\Radio_Width = GUI_ImageWidth("GUI_Tmp\Radio.png") / 8
skin\Radio_Height = GUI_ImageHeight("GUI_Tmp\Radio.png")
skin\Img_Radio = GUI_LoadImage("GUI_Tmp\Radio.png", skin\Radio_Width, skin\Radio_Height, 8)
skin\Col_Radio_Enabled$ = GUI_GetParam("Col_Radio_Enabled$")
skin\Col_Radio_Disabled$ = GUI_GetParam("Col_Radio_Disabled$")
skin\Col_Radio_Disabled_Shadow$ = GUI_GetParam("Col_Radio_Disabled_Shadow$")
;Slider
skin\Slider_Height = GUI_ImageHeight("GUI_Tmp\Slider.png")
skin\Img_Slider = GUI_LoadImage("GUI_Tmp\Slider.png", 9, skin\Slider_Height, 7)
;Spinner
skin\Img_Spinner = GUI_LoadImage("GUI_Tmp\Spinner.png", -3, -6, 18)
skin\Col_Spinner_Enabled$ = GUI_GetParam("Col_Spinner_Enabled$")
skin\Col_Spinner_Disabled$ = GUI_GetParam("Col_Spinner_Disabled$")
;Label
skin\Col_Label$ = GUI_GetParam("Col_Label$")
;ComboBox
skin\Img_ComboBox = GUI_LoadImage("GUI_Tmp\ComboBox.png", -4, -4, 16)
skin\Col_ComboBox_DropDown_Border$ = GUI_GetParam("Col_ComboBox_DropDown_Border$")
skin\Col_ComboBox_DropDown_BG$ = GUI_GetParam("Col_ComboBox_DropDown_BG$")
skin\Col_ComboBox_Enabled$ = GUI_GetParam("Col_ComboBox_Enabled$")
skin\Col_ComboBox_Disabled$ = GUI_GetParam("Col_ComboBox_Disabled$")
skin\Col_ComboBox_Highlight$ = GUI_GetParam("Col_ComboBox_Highlight$")
skin\Col_ComboBox_Selected$ = GUI_GetParam("Col_ComboBox_Selected$")
skin\Col_ComboBox_Selected_Border$ = GUI_GetParam("Col_ComboBox_Selected_Border$")
;ProgressBar
skin\Img_ProgressBar = GUI_LoadImage("GUI_Tmp\ProgressBar.png", 10, 10, 18)
;ScrollBar
skin\ScrollBar_Width = GUI_ImageWidth("GUI_Tmp\ScrollBar.png") / 3
skin\ScrollBar_Button_Height = GUI_ImageHeight("GUI_Tmp\ScrollBar_Buttons.png") / 2
skin\Img_ScrollBar = GUI_LoadImage("GUI_Tmp\ScrollBar.png", skin\ScrollBar_Width, 10, 21)
skin\Img_ScrollBar_Buttons = GUI_LoadImage("GUI_Tmp\ScrollBar_Buttons.png", skin\ScrollBar_Width, skin\ScrollBar_Button_Height, 6)
;Icon
skin\Img_Icon = GUI_LoadImage("GUI_Tmp\Icon.png", 10, 10, 45)
skin\Col_Icon_Enabled$ = GUI_GetParam("Col_Icon_Enabled$")
skin\Col_Icon_MouseDown$ = GUI_GetParam("Col_Icon_MouseDown$")
skin\Col_Icon_Disabled$ = GUI_GetParam("Col_Icon_Disabled$")
skin\Col_Icon_Disabled_Shadow$ = GUI_GetParam("Col_Icon_Disabled_Shadow$")
;ListBox
skin\Img_ListBox = GUI_LoadImage("GUI_Tmp\ListBox.png", 10, 10, 9)
skin\Col_ListBox_Text$ = GUI_GetParam("Col_ListBox_Text$")
skin\Col_ListBox_Selected$ = GUI_GetParam("Col_ListBox_Selected$")
skin\Col_ListBox_Selected_Border$ = GUI_GetParam("Col_ListBox_Selected_Border$")
skin\Col_ListBox_Highlight$ = GUI_GetParam("Col_ListBox_Highlight$")
;Edit
skin\Img_Edit = GUI_LoadImage("GUI_Tmp\Edit.png", 10, 10, 9)
skin\Col_Edit_Text$ = GUI_GetParam("Col_Edit_Text$")
;Menu
skin\Img_Menu = GUI_LoadImage("GUI_Tmp\Menu.png", -3, -3, 9)
skin\Col_Menu_Enabled$ = GUI_GetParam("Col_Menu_Enabled$")
skin\Col_Menu_Highlight$ = GUI_GetParam("Col_Menu_Highlight$")
skin\Col_Menu_Selected$ = GUI_GetParam("Col_Menu_Selected$")
skin\Col_Menu_Selected_Border$ = GUI_GetParam("Col_Menu_Selected_Border$")
skin\Col_Menu_DropDown_Border$ = GUI_GetParam("Col_Menu_DropDown_Border$")
skin\Col_Menu_DropDown_BG$ = GUI_GetParam("Col_Menu_DropDown_BG$")
skin\Col_Menu_DropDown_Strip$ = GUI_GetParam("Col_Menu_DropDown_Strip$")
;Delete
CloseFile file
dir = ReadDir("GUI_Tmp\")
Repeat
	f$ = NextFile(dir)
	If f$ = "" Then Exit
	If FileType("GUI_Tmp\" + f$) = 1 Then DeleteFile "GUI_Tmp\" + f$
Forever
CloseDir dir
DeleteDir "GUI_Tmp"
GUI_CalculateAllGadgets()
End Function

Function GUI_FreeSkin()
If gui\skin = Null Then Return
skin.GUI_Skin = gui\skin
If skin\Img_Pointer Then FreeImage skin\Img_Pointer
If skin\Fnt_Gadgets Then FreeFont skin\Fnt_Gadgets
If skin\Snd_Event_Click Then FreeSound skin\Snd_Event_Click
If skin\Img_Window_Tilebar Then FreeImage skin\Img_Window_Tilebar
If skin\Img_Window_Border Then FreeImage skin\Img_Window_Border
If skin\Img_Window_Icons Then FreeImage skin\Img_Window_Icons
If skin\Fnt_Window_Title Then FreeFont skin\Fnt_Window_Title
If skin\Img_Window_BG Then FreeImage skin\Img_Window_BG
If skin\Img_Tab Then FreeImage skin\Img_Tab
If skin\Img_TabPage Then FreeImage skin\Img_TabPage
If skin\Img_GroupBox Then FreeImage skin\Img_GroupBox
If skin\Img_Button Then FreeImage skin\Img_Button
If skin\Img_CheckBox Then FreeImage skin\Img_CheckBox
If skin\Img_Radio Then FreeImage skin\Img_Radio
If skin\Img_Slider Then FreeImage skin\Img_Slider
If skin\Img_Spinner Then FreeImage skin\Img_Spinner
If skin\Img_ComboBox Then FreeImage skin\Img_ComboBox
If skin\Img_ProgressBar Then FreeImage skin\Img_ProgressBar
If skin\Img_ScrollBar Then FreeImage skin\Img_ScrollBar
If skin\Img_ScrollBar_Buttons Then FreeImage skin\Img_ScrollBar_Buttons
If skin\Img_Icon Then FreeImage skin\Img_Icon
If skin\Img_ListBox Then FreeImage skin\Img_ListBox
Delete skin
End Function

Function GUI_UpdateGUILS5(pointer_frame = -1, val)
	If val=1 Then
		skin.GUI_Skin = gui\skin
		gui\old_event = gui\event
		gui\event = -1
		GUI_UpdateInput(pointer_frame)
		GUI_UpdateWindow()
		GUI_DrawMouse()
		If gui\old_event = -1 And gui\event > -1 Then PlaySound skin\Snd_Event_Click
	EndIf
End Function

Function GUI_UpdateGUI(pointer_frame = -1)
		skin.GUI_Skin = gui\skin
		gui\old_event = gui\event
		gui\event = -1
		GUI_UpdateInput(pointer_frame)
		GUI_UpdateWindow()
		GUI_DrawMouse()
		If gui\old_event = -1 And gui\event > -1 Then PlaySound skin\Snd_Event_Click
End Function

Function GUI_CreateWindow(x = 0, y = 0, w = 100, h = 100, txt$ = "Window", icon$ = "", btn_close = True, btn_max = True, btn_min = True, btn_hlp = False)
gad.GUI_Window = New GUI_Window
id = Handle(gad)
If x = -1 Then x = gui\w / 2 - w / 2
If y = -1 Then y = gui\h / 2 - h / 2
gad\x = x
gad\y = y
gad\w = w
gad\h = h
gad\txt$ = txt$
gad\btn_close = btn_close
gad\btn_max = btn_max
gad\btn_min = btn_min
gad\btn_hlp = btn_hlp
GUI_Message(id, "SetIcon", icon$)
GUI_CalculateGadget(id)
gad\px = -1
Return id
End Function

Function GUI_UpdateWindow()
skin.GUI_Skin = gui\skin
;Bring to Front
ok = True
For gad.GUI_Window = Each GUI_Window
	If gad\maximized And gad\closed = False Then ok = False
Next
If ok Then
	gad = Last GUI_Window
	While gad <> Null
		If gad\closed = False Then
			If gad\minimized Then h = skin\Window_Tilebar_Height Else h = gad\h
			If GUI_MouseOver(gad\x, gad\y, gad\w, h) And gui\mh1 Then
				GUI_Message(Handle(gad), "BringToFront")
				Exit
			EndIf
		EndIf
		gad = Before gad
	Wend
EndIf
For gad.GUI_Window = Each GUI_Window
	If gad\closed = False Then
		gad_x = gad\x
		gad_y = gad\y
		gad_w = gad\w
		gad_h = gad\h
		If gad\maximized Then
			gad\x = 0
			gad\y = 0
			gad\w = gui\w
			gad\h = gui\h
		EndIf
		f = (gad = Last GUI_Window)
		If gad\minimized Then Viewport gad\x, gad\y, gad\w, skin\Window_Tilebar_Height
		If gad\maximized Then
			DrawImage gad\img_max, gad\x, gad\y
			GUI_Color(skin\Col_Window_Title_Active$)
		Else
			If f Then
				DrawImage gad\img_active, gad\x, gad\y
				GUI_Color(skin\Col_Window_Title_Active$)
			Else
				DrawImage gad\img_inactive, gad\x, gad\y
				GUI_Color(skin\Col_Window_Title_Inactive$)
			EndIf
		EndIf
		Viewport 0, 0, gui\w, gui\h
		SetFont skin\Fnt_Window_Title
		If gad\icon Then
			iw = ImageWidth(gad\icon)
			xo = iw + 6
			DrawImage gad\icon, gad\x + 6, gad\y + skin\Window_Tilebar_Height / 2 - ImageHeight(gad\icon) / 2
		Else
			xo = 0
		EndIf
		;Title
		iw = ImageWidth(skin\Img_Window_Icons)
		If skin\Window_Title_Centered Then x = gad\x + xo + (gad\w - gad\btn_close * iw - gad\btn_max * iw - gad\btn_min * iw - gad\btn_hlp * iw - xo) / 2 - StringWidth(gad\txt$) / 2 Else x = gad\x + 5 + xo
		Text x, gad\y + skin\Window_Tilebar_Height / 2, gad\txt$, False, True
		;Inner gadgets
		If gad\minimized = False Then GUI_UpdateGadgets(Handle(gad))
		;Buttons
		iw = ImageWidth(skin\Img_Window_Icons)
		ih = ImageHeight(skin\Img_Window_Icons)
		xo = gad\x + gad\w - iw - 5
		yo = gad\y + skin\Window_Tilebar_Height / 2 - ih / 2
		ff2 = False
		;Close button
		If gad\btn_close Then
			f = 0
			If gad = Last GUI_Window Then f = 1
			If GUI_MouseOver(xo, yo, iw, ih) Then f = 2: ff2 = True
			If f = 2 And GUI_MouseOver2(xo, yo, iw, ih) And gui\md1 And gad = Last GUI_Window Then f = 3
			If gui\mu1 And gad\btn_close_op Then GUI_Message(Handle(gad), "Close")
			If f = 3 Then gad\btn_close_op = True Else gad\btn_close_op = False
			DrawImage skin\Img_Window_Icons, xo, yo, 12 + f
			xo = xo - iw - skin\Window_Icon_Margin
		EndIf
		;Maximize button
		If gad\btn_max Then
			f = 0
			If gad = Last GUI_Window Then f = 1
			If GUI_MouseOver(xo, yo, iw, ih) Then f = 2: ff2 = True
			If f = 2 And GUI_MouseOver2(xo, yo, iw, ih) And gui\md1 And gad = Last GUI_Window Then f = 3
			If gui\mu1 And gad\btn_max_op Then GUI_Message(Handle(gad), "Maximize")
			If f = 3 Then gad\btn_max_op = True Else gad\btn_max_op = False
			DrawImage skin\Img_Window_Icons, xo, yo, 4 + f + 4 * gad\maximized
			xo = xo - iw - skin\Window_Icon_Margin
		EndIf
		;Minimize button
		If gad\btn_min Then
			f = 0
			If gad = Last GUI_Window Then f = 1
			If GUI_MouseOver(xo, yo, iw, ih) Then f = 2: ff2 = True
			If f = 2 And GUI_MouseOver2(xo, yo, iw, ih) And gui\md1 And gad = Last GUI_Window Then f = 3
			If gui\mu1 And gad\btn_min_op Then GUI_Message(Handle(gad), "Minimize")
			If f = 3 Then gad\btn_min_op = True Else gad\btn_min_op = False
			DrawImage skin\Img_Window_Icons, xo, yo, f + 8 * gad\minimized
			xo = xo - iw - skin\Window_Icon_Margin
		EndIf
		;Help button
		If gad\btn_hlp Then
			f = 0
			If gad = Last GUI_Window Then f = 1
			If GUI_MouseOver(xo, yo, iw, ih) Then f = 2: ff2 = True
			If f = 2 And GUI_MouseOver2(xo, yo, iw, ih) And gui\md1 And gad = Last GUI_Window Then f = 3
			If gui\mu1 And gad\btn_hlp_op Then
				gui\event = Handle(gad)
				gui\hlp_button_pressedtime = MilliSecs()
			EndIf
			If f = 3 Then gad\btn_hlp_op = True Else gad\btn_hlp_op = False
			DrawImage skin\Img_Window_Icons, xo, yo, f + 16
		EndIf
		gad\x = gad_x
		gad\y = gad_y
		gad\w = gad_w
		gad\h = gad_h
		;Push around
		If gad = Last GUI_Window And ff2 = False And gad\locked = False Then
			If gad\px <> -1 Then
				gad\x = gui\mx - gad\px
				gad\y = gui\my - gad\py
				gui\PointerFrame = skin\Pointer_Window
			EndIf
			If GUI_MouseOver(gad\x, gad\y, gad\w, skin\Window_Tilebar_Height) And gui\mh1 Then
				gad\px = gui\mx - gad\x
				gad\py = gui\my - gad\y
			Else
				If gui\md1 = False Then gad\px = -1
			EndIf
		EndIf
	EndIf
Next
End Function

Function GUI_CreateTab(owner, x = 0, y = 0, w = 100, h = 100)
gad.GUI_Tab = New GUI_Tab
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\h = h
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateTab(owner)
skin.GUI_Skin = gui\skin
For gad.GUI_Tab = Each GUI_Tab
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		DrawImage gad\img, gui\gadox, gui\gadoy + skin\TabPage_Height
		GUI_UpdateGadgets(Handle(gad))
	EndIf
Next
End Function

Function GUI_CreateTabPage(owner, txt$ = "TabPage", icon$ = "", enabled = True)
gad.GUI_TabPage = New GUI_TabPage
id = Handle(gad)
gad\owner = owner
If GUI_ParseID(gad\owner) <> "tab" Then RuntimeError "A tabpage can only be applied to a tab."
gad\txt$ = txt$
gad\enabled = enabled
If gad\enabled Then
	tab.GUI_Tab = Object.GUI_Tab(gad\owner)
	tab\active_tabp = gad
EndIf
GUI_Message(id, "SetIcon", icon$)
Return id
End Function

Function GUI_UpdateTabPage(owner)
skin.GUI_Skin = gui\skin
xo = skin\TabPage_BeginOffset
GUI_GadgetOffset(owner)
gadox = gui\gadox
gadoy = gui\gadoy
For gad.GUI_TabPage = Each GUI_TabPage
	If gad\owner = owner Then
		f = 0
		tab.GUI_Tab = Object.GUI_Tab(gad\owner)
		If gad\enabled Then
			f = 1
			If GUI_MouseOver(gadox + xo, gadoy, ImageWidth(gad\img[0]), skin\TabPage_Height) Then f = 2
			If f = 2 And gui\mh1 And tab\active_tabp <> gad And GUI_NoActiveGadgets() Then
				tab\active_tabp = gad
				gui\event = Handle(gad)
			EndIf
		EndIf
		If tab\active_tabp = gad Then f = 3
		DrawImage gad\img[f], gadox + xo, gadoy
		xo = xo + ImageWidth(gad\img[0])
		If f = 3 Then GUI_UpdateGadgets(Handle(gad))
	EndIf
Next
End Function

Function GUI_CreateGroupBox(owner, x = 0, y = 0, w = 100, h = 100, txt$ = "GroupBox")
gad.GUI_GroupBox = New GUI_GroupBox
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\h = h
gad\txt$ = txt$
GUI_CalculateGadget(id)
GUI_Message(id, "SetIcon", icon$)
Return id
End Function

Function GUI_UpdateGroupBox(owner)
skin.GUI_Skin = gui\skin
For gad.GUI_GroupBox = Each GUI_GroupBox
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		DrawImage gad\img, gui\gadox, gui\gadoy
		GUI_UpdateGadgets(Handle(gad))
	EndIf
Next
End Function

Function GUI_CreateButton(owner, x = 0, y = 0, w = 75, h = 21, txt$ = "Button", icon$ = "", enabled = True)
gad.GUI_Button = New GUI_Button
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\h = h
gad\txt$ = txt$
GUI_Message(id, "SetIcon", icon$)
gad\enabled = enabled
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateButton(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_Button = Each GUI_Button
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		f = 0
		If gad\enabled Then
			f = 1
			If gui\btn_focus = gad Then f = 3
			If GUI_MouseOver(gui\gadox, gui\gadoy, gad\w, gad\h) Then f = 2
			If gad\op And gui\md1 = False And f = 2 Then gui\event = Handle(gad)
			If f = 2 And GUI_MouseOver2(gui\gadox, gui\gadoy, gad\w, gad\h) And gui\md1 And gui\gadow Then gad\op = True Else gad\op = False
			If gui\mh1 And f > 1 Then gui\btn_focus = gad
			If gad\op Then f = 4
		EndIf
		DrawImage gad\img[f], gui\gadox, gui\gadoy
		If gad\icon Then DrawImage gad\icon, gui\gadox + gad\w / 2 - ImageWidth(gad\icon) / 2, gui\gadoy + gad\h / 2 - ImageHeight(gad\icon) / 2
		x = gui\gadox + gad\w / 2
		y = gui\gadoy + gad\h / 2
		If gad\enabled Then
			GUI_Color(skin\Col_Button_Enabled$)
			Text x, y, gad\txt$, True, True
		Else
			If skin\Col_Button_Disabled_Shadow$ <> "none" Then
				GUI_Color(skin\Col_Button_Disabled_Shadow$)
				Text x + 1, y + 1, gad\txt$, True, True
			EndIf
			GUI_Color(skin\Col_Button_Disabled$)
			Text x, y, gad\txt$, True, True
		EndIf
	EndIf
Next
End Function

Function GUI_CreateCheckBox(owner, x = 0, y = 0, txt$ = "CheckBox", enabled = True, checked = False)
gad.GUI_CheckBox = New GUI_CheckBox
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\txt$ = txt$
gad\enabled = enabled
gad\checked = checked
Return id
End Function

Function GUI_UpdateCheckBox(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_CheckBox = Each GUI_CheckBox
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		f = 0
		mo = GUI_MouseOver(gui\gadox, gui\gadoy, skin\CheckBox_Width + StringWidth(gad\txt$) + 3, skin\CheckBox_Height)
		If gad\op And gui\md1 = False And mo And gad\enabled Then
			gui\event = Handle(gad)
			gad\checked = 1 - gad\checked
		EndIf
		If mo And GUI_MouseOver2(gui\gadox, gui\gadoy, skin\CheckBox_Width + StringWidth(gad\txt$) + 3, skin\CheckBox_Height) And gui\md1 And gui\gadow Then gad\op = True Else gad\op = False
		If gad\enabled Then
			If gad\checked Then
				f = 5
				If mo And f = 5 Then f = 6
				If f = 6 And gad\op Then f = 7
			Else
				f = 2
				If mo And f = 2 Then f = 3
				If gad\op And f = 3 Then f = 4
			EndIf
		Else
			f = gad\checked
		EndIf
		DrawImage skin\Img_CheckBox, gui\gadox, gui\gadoy, f
		x = gui\gadox + skin\CheckBox_Width + 3
		y = gui\gadoy + skin\CheckBox_Height / 2
		If gad\enabled Then
			GUI_Color(skin\Col_CheckBox_Enabled$)
			Text x, y, gad\txt$, False, True
		Else
			If skin\Col_CheckBox_Disabled_Shadow$ <> "none" Then
				GUI_Color(skin\Col_CheckBox_Disabled_Shadow$)
				Text x + 1, y + 1, gad\txt$, False, True
			EndIf
			GUI_Color(skin\Col_CheckBox_Disabled$)
			Text x, y, gad\txt$, False, True
		EndIf
	EndIf
Next
End Function

Function GUI_CreateRadio(owner, x = 0, y = 0, txt$ = "Radio", gid = 0, enabled = True, checked = False)
gad.GUI_Radio = New GUI_Radio
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\txt$ = txt$
gad\enabled = enabled
gad\checked = checked
gad\gid = gid
Return id
End Function

Function GUI_UpdateRadio(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_Radio = Each GUI_Radio
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		f = 0
		mo = GUI_MouseOver(gui\gadox, gui\gadoy, skin\Radio_Width + StringWidth(gad\txt$) + 3, skin\Radio_Height)
		If gad\op And gui\md1 = False And mo And gad\enabled And gad\checked = False Then
			gui\event = Handle(gad)
			GUI_Message(Handle(gad), "SetChecked", True)
		EndIf
		If mo And GUI_MouseOver2(gui\gadox, gui\gadoy, skin\Radio_Width + StringWidth(gad\txt$) + 3, skin\Radio_Height) And gui\md1 And gui\gadow Then gad\op = True Else gad\op = False
		If gad\enabled Then
			If gad\checked Then
				f = 5
				If mo And f = 5 Then f = 6
				If f = 6 And gad\op Then f = 7
			Else
				f = 2
				If mo And f = 2 Then f = 3
				If gad\op And f = 3 Then f = 4
			EndIf
		Else
			f = gad\checked
		EndIf
		DrawImage skin\Img_Radio, gui\gadox, gui\gadoy, f
		x = gui\gadox + skin\Radio_Width + 3
		y = gui\gadoy + skin\Radio_Height / 2
		If gad\enabled Then
			GUI_Color(skin\Col_Radio_Enabled$)
			Text x, y, gad\txt$, False, True
		Else
			If skin\Col_Radio_Disabled_Shadow$ <> "none" Then
				GUI_Color(skin\Col_Radio_Disabled_Shadow$)
				Text x + 1, y + 1, gad\txt$, False, True
			EndIf
			GUI_Color(skin\Col_Radio_Disabled$)
			Text x, y, gad\txt$, False, True
		EndIf
	EndIf
Next
End Function

Function GUI_CreateImage(owner, x = 0, y = 0, w = -1, h = -1, img$ = "")
gad.GUI_Image = New GUI_Image
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\h = h
GUI_Message(id, "SetImage", img$)
Return id
End Function

Function GUI_UpdateImage(owner)
skin.GUI_Skin = gui\skin
For gad.GUI_Image = Each GUI_Image
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		If gad\img Then DrawImage gad\img, gui\gadox, gui\gadoy
	EndIf
Next
End Function

Function GUI_CreateSlider(owner, x = 0, y = 0, w = 100, value# = 0, min# = 0, max# = 100, enabled = True)
gad.GUI_Slider = New GUI_Slider
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\value# = value#
gad\min# = min#
gad\max# = max#
gad\enabled = enabled
gad\pp = -1
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateSlider(owner)
skin.GUI_Skin = gui\skin
For gad.GUI_Slider = Each GUI_Slider
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		DrawImage gad\img, gui\gadox, gui\gadoy
		val = GUI_Interpolate#(gad\value#, gad\min#, gad\max#, 0, gad\w)
		mo = GUI_MouseOver(gui\gadox + val - 4, gui\gadoy, 9, skin\Slider_Height)
		f = 0
		If gad\enabled Then
			f = 1
			If mo Then f = 2
			If gad\pp <> -1 Then f = 3
			;Push arround
			If gad\enabled Then
				If gad\pp <> -1 Then
					val = gui\mx - gui\gadox - gad\pp + w / 2
					gui\event = Handle(gad)
					gui\PointerFrame = skin\Pointer_Slider
				EndIf
				If ((mo And gui\mh1) Or gad\pp <> -1) Then
					gad\pp = gui\mx - gui\gadox - val + w / 2
				Else
					gad\pp = -1
				EndIf
				If gui\mu1 Then gad\pp = -1
				If val < 0 Then val = 0
				If val > gad\w Then val = gad\w
				gad\value# = GUI_Interpolate#(val, 0, gad\w, gad\min#, gad\max#)
			EndIf
		EndIf
		DrawImage skin\Img_Slider, gui\gadox + val - 4, gui\gadoy, 3 + f
	EndIf
Next
End Function

Function GUI_CreateSpinner(owner, x = 0, y = 0, w = 75, value# = 0, min# = 0, max# = 100, inc# = 1, enabled = True)
gad.GUI_Spinner = New GUI_Spinner
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\value# = value#
gad\min# = min#
gad\max# = max#
gad\inc# = inc#
gad\enabled = enabled
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateSpinner(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_Spinner = Each GUI_Spinner
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		h = ImageHeight(skin\Img_Spinner)
		iw = ImageWidth(skin\Img_Spinner)
		mo1 = GUI_MouseOver(gui\gadox + gad\w - iw, gui\gadoy, iw, h / 2)
		mo2 = GUI_MouseOver(gui\gadox + gad\w - iw, gui\gadoy + h / 2, iw, h / 2)
		f = 0
		If gad\enabled Then
			f = 1
			If mo1 Then f = 2
			If mo2 Then f = 3
			If gad\op1 And gui\mu1 Then
				gad\value# = gad\value# + gad\inc#
				gad\op1 = False
				gui\event = Handle(gad)
			EndIf
			If gad\op2 And gui\mu1 Then
				gad\value# = gad\value# - gad\inc#
				gad\op2 = False
				gui\event = Handle(gad)
			EndIf
			If mo1 And GUI_MouseOver2(gui\gadox + gad\w - iw, gui\gadoy, iw, h / 2) And gui\md1 Then
				gad\op1 = True
				f = 4
			Else
				gad\op1 = False
			EndIf
			If mo2 And GUI_MouseOver2(gui\gadox + gad\w - iw, gui\gadoy + h / 2, iw, h / 2) And gui\md1 Then
				gad\op2 = True
				f = 5
			Else
				gad\op2 = False
			EndIf
			If gad\value# < gad\min# Then gad\value# = gad\min#
			If gad\value# > gad\max# Then gad\value# = gad\max#
			GUI_Color(skin\Col_Spinner_Enabled$)
		Else
			GUI_Color(skin\Col_Spinner_Disabled$)
		EndIf
		DrawImage gad\img[f], gui\gadox, gui\gadoy
		Text gui\gadox + 4, gui\gadoy + h / 2, gad\value#, False, True
	EndIf
Next
End Function

Function GUI_CreateLabel(owner, x = 0, y = 0, txt$ = "Label", align$ = "Left")
gad.GUI_Label = New GUI_Label
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\txt$ = txt$
GUI_Message(id, "SetAlign", align$)
Return id
End Function

Function GUI_UpdateLabel(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
GUI_Color(skin\Col_Label$)
For gad.GUI_Label = Each GUI_Label
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		Text gui\gadox - gad\align * StringWidth(gad\txt$) * .5, gui\gadoy, gad\txt$, True
	EndIf
Next
End Function

Function GUI_CreateComboBox(owner, x = 0, y = 0, w = 75, enabled = True)
gad.GUI_ComboBox = New GUI_ComboBox
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\enabled = enabled
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateComboBox(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_ComboBox = Each GUI_ComboBox
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		ih = ImageHeight(skin\Img_ComboBox)
		f = 0
		If gad\enabled Then
			f = 1
			If GUI_MouseOver(gui\gadox, gui\gadoy, gad\w, ih) Then f = 2
			If f = 2 And gui\mh1 Then
				gad\opened = 1 - gad\opened
				PlaySound skin\Snd_Event_Click
			EndIf
			If gad\opened Then
				f = 3
				items = 0
				For i = GUI_StackSize To 0 Step -1
					If gad\txt$[i] <> "" Then
						items = i
						Exit
					EndIf
				Next
				h = (FontHeight() + 2) * (items + 1) + 2
				GUI_Color(skin\Col_ComboBox_DropDown_BG$)
				Rect gui\gadox, gui\gadoy + ih, gad\w, h
				GUI_Color(skin\Col_ComboBox_DropDown_Border$)
				Rect gui\gadox, gui\gadoy + ih, gad\w, h, False
				If gui\mh1 And GUI_MouseOver(gui\gadox, gui\gadoy, gad\w, h + ih) = False Then
					gad\opened = False
					PlaySound skin\Snd_Event_Click
				EndIf
				For i = 0 To items
					x = gui\gadox + 3
					y = gui\gadoy + ih + i * (FontHeight() + 2) + 1
					If GUI_MouseOver(x, y - 1, gad\w, FontHeight() + 3) Then
						GUI_Color(skin\Col_ComboBox_Selected$)
						Rect x - 2, y, gad\w - 2, FontHeight() + 2
						GUI_Color(skin\Col_ComboBox_Selected_Border$)
						Rect x - 2, y, gad\w - 2, FontHeight() + 2, False
						GUI_Color(skin\Col_ComboBox_Highlight$)
						If gui\mh1 Then
							gui\event = Handle(gad)
							gad\sel = i
							gad\opened = False
						EndIf
					Else
						GUI_Color(skin\Col_ComboBox_Enabled$)
					EndIf
					If gad\icon[i] Then
						xo = ImageWidth(gad\icon[i]) + 2
						DrawImage gad\icon[i], x, y + FontHeight() / 2 - ImageHeight(gad\icon[i]) / 2
					Else
						xo = 0
					EndIf
					Text x + xo, y, gad\txt$[i]
				Next
			EndIf
		EndIf
		DrawImage gad\img[f], gui\gadox, gui\gadoy
		If gad\enabled Then GUI_Color(skin\Col_ComboBox_Enabled$) Else GUI_Color(skin\Col_ComboBox_Disabled$)
		Text gui\gadox + 3, gui\gadoy + ih / 2, gad\txt$[gad\sel], False, True
	EndIf
Next
End Function

Function GUI_CreateProgressBar(owner, x = 0, y = 0, w = 100, h = 20, status = 0)
gad.GUI_ProgressBar = New GUI_ProgressBar
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\h = h
gad\status = status
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateProgressBar(owner)
skin.GUI_Skin = gui\skin
For gad.GUI_ProgressBar = Each GUI_ProgressBar
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		If gad\status < 0 Then gad\status = 0
		If gad\status > 100 Then gad\status = 100
		DrawImage gad\img[0], gui\gadox, gui\gadoy
		Viewport gui\gadox, gui\gadoy, GUI_Interpolate#(gad\status, 0, 100, 0, gad\w), gad\h
		DrawImage gad\img[1], gui\gadox, gui\gadoy
		Viewport 0, 0, gui\w, gui\h
	EndIf
Next
End Function

Function GUI_CreateScrollBar(owner, x = 0, y = 0, h = 100, status = 0)
gad.GUI_ScrollBar = New GUI_ScrollBar
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\h = h
gad\bh = gad\h / 3
gad\pp = -1
GUI_Message(id, "SetStatus", status)
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateScrollBar(owner)
skin.GUI_Skin = gui\skin
h = skin\ScrollBar_Button_Height
w = skin\ScrollBar_Width
For gad.GUI_ScrollBar = Each GUI_ScrollBar
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		;Button1
		f = 0
		If GUI_MouseOver(gui\gadox, gui\gadoy, w, h) Then f = 1
		If gui\md1 And GUI_MouseOver2(gui\gadox, gui\gadoy, w, h) Then f = 2
		DrawImage skin\Img_ScrollBar_Buttons, gui\gadox, gui\gadoy, f
		DrawImage skin\Img_ScrollBar, gui\gadox, gui\gadoy + h, 12 + f
		If f = 2 Then
			gad\bp = gad\bp - 1
			gui\event = Handle(gad)
		EndIf
		;Button2
		f = 0
		If GUI_MouseOver(gui\gadox, gui\gadoy + gad\h - h, w, h) Then f = 1
		If gui\md1 And GUI_MouseOver2(gui\gadox, gui\gadoy + gad\h - h, w, h) Then f = 2
		DrawImage skin\Img_ScrollBar_Buttons, gui\gadox, gui\gadoy + gad\h - h, 3 + f
		DrawImage skin\Img_ScrollBar, gui\gadox, gui\gadoy + gad\h - h - 10, 15 + f
		If f = 2 Then
			gad\bp = gad\bp + 1
			gui\event = Handle(gad)
		EndIf
		;Bar
		DrawImage gad\img1, gui\gadox, gui\gadoy + h + 10
		f = 0
		If GUI_MouseOver(gui\gadox, gui\gadoy + gad\bp - gad\bh / 2, w, gad\bh) Then f = 1
		If gad\pp <> -1 Then
			f = 2
			gad\bp = gui\my - gui\gadoy - gad\pp - h
			gui\event = Handle(gad)
			gui\PointerFrame = skin\Pointer_Scrollbar
		EndIf
		If f = 1 And gui\md1 And GUI_MouseOver2(gui\gadox, gui\gadoy + gad\bp - gad\bh / 2, w, gad\bh) Then
			gad\pp = gui\my - gui\gadoy - gad\bp - h
		Else
			If gui\mu1 Then gad\pp = -1
		EndIf
		If gad\bp < h + gad\bh / 2 Then gad\bp = h + gad\bh / 2 ElseIf gad\bp > gad\h - h - gad\bh / 2 Then gad\bp = gad\h - h - gad\bh / 2
		DrawImage gad\img2[f], gui\gadox, gui\gadoy + gad\bp - gad\bh / 2
	EndIf
Next
End Function

Function GUI_CreateIcon(owner, x = 0, y = 0, w = 21, h = 21, txt$ = "Icon", icon$ = "", enabled = True, gid = -1)
gad.GUI_Icon = New GUI_Icon
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\h = h
gad\txt$ = txt$
GUI_Message(id, "SetIcon", icon$)
gad\enabled = enabled
gad\gid = gid
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateIcon(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_Icon = Each GUI_Icon
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		f = 0
		mo = GUI_MouseOver(gui\gadox, gui\gadoy, gad\w, gad\h)
		If gad\enabled Then
			If mo Then f = 1
			If gad\op And gui\mu1 Then
				If mo Then
					gui\event = Handle(gad)
					If gad\gid > -1 Then
						For gad2.GUI_Icon = Each GUI_Icon
							If gad2\owner = gad\owner And gad2\gid = gad\gid And gad2 <> gad Then gad2\checked = False
						Next
						gad\checked = 1 - gad\checked
					EndIf
				EndIf
				gad\op = False
			EndIf
			If mo And gui\mh1 Then gad\op = True
			If gad\checked Then f = 3 + mo
			If gad\op And mo Then f = 2
		EndIf
		DrawImage gad\img[f], gui\gadox, gui\gadoy
		x = gui\gadox + gad\w / 2
		y = gui\gadoy + gad\h / 2
		If gad\icon Then DrawImage gad\icon, x - ImageWidth(gad\icon) / 2, y - ImageHeight(gad\icon) / 2
		If gad\enabled Then
			If gad\op And mo Then GUI_Color(skin\Col_Icon_MouseDown$) Else GUI_Color(skin\Col_Icon_Enabled$)
			Text x, y, gad\txt$, True, True
		Else
			If skin\Col_Icon_Disabled_Shadow$ <> "none" Then
				GUI_Color(skin\Col_Icon_Disabled_Shadow$)
				Text x + 1, y + 1, gad\txt$, True, True
			EndIf
			GUI_Color(skin\Col_Icon_Disabled$)
			Text x, y, gad\txt$, True, True
		EndIf
	EndIf
Next
End Function

Function GUI_CreateListBox(owner, x = 0, y = 0, w = 100, h = 100)
gad.GUI_ListBox = New GUI_ListBox
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\h = h
gad\scr = GUI_CreateScrollBar(id, gad\w - gui\skin\ScrollBar_Width, 0, gad\h)
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateListBox(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_ListBox = Each GUI_ListBox
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		DrawImage gad\img, gui\gadox, gui\gadoy
		cnt_items = 0
		For i = GUI_StackSize To 0 Step -1
			If gad\txt$[i] <> "" Then
				cnt_items = i + 1
				Exit
			EndIf
		Next
		bar_status = Int(GUI_Message(gad\scr, "GetStatus"))
		fh = FontHeight() + 2
		wh = fh * cnt_items + 4
		If wh > gad\h Then yo = (wh - gad\h) * bar_status * .01 - 2 Else yo = -2
		Viewport gui\gadox + 2, gui\gadoy + 2, gad\w - skin\ScrollBar_Width - 4, gad\h - 4
		For i = 0 To cnt_items - 1
			y = gui\gadoy + 2 + i * fh - yo
			If y - gui\gadoy > gad\h + 4 Then Exit
			If y - gui\gadoy > 2 - fh Then
				rx = gui\gadox + 2
				ry = y - 2
				rw = gad\w - skin\ScrollBar_Width - 4
				rh = fh
				If GUI_MouseOver(gui\gadox, gui\gadoy, gad\w, gad\h) Then
					If GUI_MouseOver(rx, ry, rw, rh) And gui\mh1 Then
						gad\sel = i
						gui\event = Handle(gad)
					EndIf
					If gui\mzs <> 0 Then GUI_Message(gad\scr, "SetStatus", bar_status - 10 * gui\mzs)
				EndIf
				If gad\sel = i Then
					GUI_Color(skin\Col_ListBox_Selected$)
					Rect rx, ry, rw, rh
					GUI_Color(skin\Col_ListBox_Selected_Border$)
					Rect rx, ry, rw, rh, False
					GUI_Color(skin\Col_ListBox_Highlight$)
				Else
					GUI_Color(skin\Col_ListBox_Text$)
				EndIf
				If gad\icon[i] Then
					xo = 6 + ImageWidth(gad\icon[i])
					DrawImage gad\icon[i], gui\gadox + 3, y - 2
				Else
					xo = 3
				EndIf
				Text gui\gadox + xo, y, gad\txt$[i]
			EndIf
		Next
		Viewport 0, 0, gui\w, gui\h
		GUI_UpdateScrollBar(Handle(gad))
	EndIf
Next
End Function

Function GUI_CreateEdit(owner, x = 0, y = 0, w = 100, txt$ = "")
gad.GUI_Edit = New GUI_Edit
id = Handle(gad)
gad\owner = owner
gad\x = x
gad\y = y
gad\w = w
gad\txt$ = txt$
GUI_CalculateGadget(id)
Return id
End Function

Function GUI_UpdateEdit(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
For gad.GUI_Edit = Each GUI_Edit
	If gad\owner = owner Then
		GUI_GadgetOffset(Handle(gad))
		h = FontHeight() + 6
		DrawImage gad\img, gui\gadox, gui\gadoy
		Viewport gui\gadox + 2, gui\gadoy + 2, gad\w - 4, h - 4
		GUI_Color(skin\Col_Edit_Text$)
		Text gui\gadox + 2, gui\gadoy + 3, gad\txt$
		
		;Get the string position of the cursor
		mp = Len(gad\txt$)
		pos = gui\mx - gui\gadox - 2
		For i = 1 To Len(gad\txt$)
			If StringWidth(Left(gad\txt$, i)) > pos Then
				mp = i - 1
				Exit
			EndIf
			
		Next
		
	;	If (KeyHit(KEY_ENTER)) Then
	;		Yn$ = gad\txt$
	;		HidePointer()
	;		PlaySound Sound_SelectLevel
	;		Game\Path$ = "Stages/" + Yn$ + "/"
	;		Game\State	= GAME_STATE_START
	;		Game\Gameplay\TypingStage = 0 : GUI_FreeGUI
	;	EndIf
		If GUI_MouseOver(gui\gadox, gui\gadoy, gad\w, h) Then gui\PointerFrame = skin\Pointer_Edit
		gad\active = GUI_MouseOver2(gui\gadox, gui\gadoy, gad\w, h)
		If gui\md1 Then gad\pos = mp
		
		;Update keyboard
		If gad\active Then
			txt1$ = Left(gad\txt$, gad\pos)
			txt2$ = Right(gad\txt$, Len(gad\txt$) - gad\pos)

			If KeyHit(203) Then gad\pos = gad\pos - 1 ElseIf KeyHit(205) Then gad\pos = gad\pos + 1
			If gad\pos < 0 Then gad\pos = 0 ElseIf gad\pos > Len(gad\txt$) Then gad\pos = Len(gad\txt$)
			If KeyHit(199) Then
				gad\pos = 0
			ElseIf KeyHit(207) Then
				gad\pos = Len(gad\txt$)
			ElseIf KeyHit(14) And gad\pos > 0 Then
				gad\pos = gad\pos - 1
				gad\txt$ = Left(txt1$, Len(txt1$) - 1) + txt2$
			ElseIf KeyHit(211) And gad\pos < Len(gad\txt$) Then
				gad\txt$ = txt1$ + Right(txt2$, Len(txt2$) -1)
			ElseIf KeyHit(57) Then
				gad\txt$ = txt1$ + " " + txt2$
				gad\pos = gad\pos + 1
			EndIf
			If gui\gk > 32 Then
				gad\txt$ = Left(gad\txt$, gad\pos) + Chr(gui\gk) + Right(gad\txt$, Len(gad\txt$) - gad\pos)
				gad\pos = gad\pos + 1
			EndIf
			pos_x = StringWidth(Left(gad\txt$, gad\pos))
			If Sin(MilliSecs() * .25) Then Rect gui\gadox + 2 + pos_x, gui\gadoy + 2, 1, h - 4
		EndIf
		Viewport 0, 0, gui\w, gui\h
	EndIf
Next
End Function

Function GUI_CreateMenu(owner, txt$ = "Menu")
gad.GUI_Menu = New GUI_Menu
id = Handle(gad)
gad\owner = owner
gad\txt$ = txt$
Return id
End Function

Function GUI_UpdateMenu(owner)
skin.GUI_Skin = gui\skin
SetFont skin\Fnt_Gadgets
Select GUI_ParseID$(owner)
	Case "win"
		win.GUI_Window = Object.GUI_Window(owner)
		gadox = win\x + 5
		gadoy = win\y + skin\Window_Tilebar_Height + 2
		x = 0
		For gad.GUI_Menu = Each GUI_Menu
			If gad\owner = owner Then
				w = StringWidth(gad\txt$) + 6
				mo = GUI_MouseOver(gadox + x - 1, gadoy, w + 1, FontHeight() + 4) Or gad\open
				gad\drx = gadox + x - 2
				gad\dry = gadoy + FontHeight() + 1
				If mo Then
					GUI_Color(skin\Col_Menu_Selected$)
					Rect gadox + x - 2, gadoy - 2, w + 2, FontHeight() + 4
					GUI_Color(skin\Col_Menu_Selected_Border$)
					Rect gadox + x - 2, gadoy - 2, w + 2, FontHeight() + 4, False
					GUI_Color(skin\Col_Menu_Highlight$)
					If gui\mh1 Then
						PlaySound skin\Snd_Event_Click
						win\mnu_open = 1 - win\mnu_open
					EndIf
				Else
					GUI_Color(skin\Col_Menu_Enabled$)
				EndIf
				Text gadox + x + 2, gadoy, gad\txt$
				If win\mnu_open Then
					If mo Then
						For mnu.GUI_Menu = Each GUI_Menu
							mnu\open = False
						Next
						gad\open = True
					EndIf
				Else
					For mnu.GUI_Menu = Each GUI_Menu
						mnu\open = False
					Next
				EndIf
				x = x + w
			EndIf
			If gad\open Then GUI_UpdateGadgets(Handle(gad))
		Next
	Case "mnu"
		For gad.GUI_Menu = Each GUI_Menu
			If gad\owner = owner Then
				mnut.GUI_Menu = Object.GUI_Menu(gad\owner)
				If mnut\open Then
					w = StringWidth(mnut\txt$)
					h = 0
					For mnu.GUI_Menu = Each GUI_Menu
						If mnu\owner = gad\owner Then
							h = h + FontHeight() + 4
							nw = StringWidth(mnu\txt$) + 40
							If nw > w Then w = nw
						EndIf
					Next
					GUI_Color(skin\Col_Menu_DropDown_BG$)
					Rect mnut\drx, mnut\dry, w, h
					GUI_Color(skin\Col_Menu_DropDown_Strip$)
					Rect mnut\drx, mnut\dry, 24, h
					GUI_Color(skin\Col_Menu_DropDown_Border$)
					Rect mnut\drx, mnut\dry, w, h, False
					y = 0
					For mnu.GUI_Menu = Each GUI_Menu
						If mnu\owner = gad\owner Then
							mo = GUI_MouseOver(mnut\drx, mnut\dry + y, w, FontHeight() + 4)
							If mo Then
								GUI_Color(skin\Col_Menu_Selected$)
								Rect mnut\drx + 1, mnut\dry + y + 1, w - 2, FontHeight() + 2
								GUI_Color(skin\Col_Menu_Selected_Border$)
								Rect mnut\drx + 1, mnut\dry + y + 1, w - 2, FontHeight() + 2, False
								GUI_Color(skin\Col_Menu_Highlight$)
								If gui\mh1 Then
									gui\event = Handle(mnu)
									Exit
								EndIf
							Else
								GUI_Color(skin\Col_Menu_Enabled$)
							EndIf
							Text mnut\drx + 25, mnut\dry + y + 2, mnu\txt$
							y = y + FontHeight() + 4
						EndIf
					Next
				EndIf
			EndIf
		Next
End Select
End Function

Function GUI_Message$(gad, msg$, p1$ = "", p2$ = "", p3$ = "")
skin.GUI_Skin = gui\skin
msg$ = Lower(msg$)
Select GUI_ParseID(gad)
	Case "win"
		win.GUI_Window = Object.GUI_Window(gad)
		Select msg$
			Case "setpos"
				win\x = p1$
				win\y = p2$
			Case "setsize"
				win\w = p1$
				win\h = p2$
				GUI_CalculateGadget(Handle(win))
			Case "settext"
				win\txt$ = p1$
			Case "bringtofront"
				Insert win After Last GUI_Window
			Case "close"
				win\closed = True
				FreeImage win\img_active
				FreeImage win\img_inactive
				FreeImage win\img_max
				GUI_DeleteGadgets(gad)
				PlaySound skin\Snd_Event_Click
			Case "maximize"
				win\maximized = 1 - win\maximized
				win\minimized = False
				PlaySound skin\Snd_Event_Click
			Case "minimize"
				win\minimized = 1 - win\minimized
				win\maximized = False
				PlaySound skin\Snd_Event_Click
			Case "seticon"
				If p1$ <> "" Then win\icon = GUI_LoadIcon(p1$)
			Case "getminimized"
				Return win\minimized
			Case "getmaximized"
				Return win\maximized
			Case "getx"
				Return win\x
			Case "gety"
				Return win\y
			Case "getwidth"
				Return win\w
			Case "getheight"
				Return win\h
			Case "center"
				win\x = gui\w / 2 - win\w / 2
				win\y = gui\h / 2 - win\h / 2
			Case "getclosed"
				Return win\closed
			Case "gettext"
				Return win\txt$
			Case "setlocked"
				win\locked = p1$
		End Select
	Case "tab"
		tab.GUI_Tab = Object.GUI_Tab(gad)
		Select msg$
			Case "setpos"
				tab\x = p1$
				tab\y = p2$
			Case "setsize"
				tab\w = p1$
				tab\h = p2$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return tab\x
			Case "gety"
				Return tab\y
			Case "getwidth"
				Return tab\w
			Case "getheight"
				Return tab\h
			Case "setindex"
				cnt = 0
				For tabp.GUI_TabPage = Each GUI_TabPage
					If tabp\owner = gad Then
						cnt = cnt + 1
						If cnt = p1$ And tabp\enabled Then tab\active_tabp = tabp
					EndIf
				Next
			Case "getindex"
				cnt = 0
				For tabp.GUI_TabPage = Each GUI_TabPage
					If tabp\owner = gad Then
						cnt = cnt + 1
						tab.GUI_Tab = Object.GUI_Tab(tabp\owner)
						If tab\active_tabp = tabp Then Exit
					EndIf
				Next
				Return cnt
		End Select
	Case "tabp"
		tabp.GUI_TabPage = Object.GUI_TabPage(gad)
		Select msg$
			Case "seticon"
				tabp\icon = GUI_LoadIcon(p1$)
				GUI_CalculateGadget(gad)
			Case "settext"
				tabp\txt$ = p1$
				GUI_CalculateGadget(gad)
			Case "gettext"
				Return tabp\txt$
			Case "setenabled"
				tabp\enabled = p1$
				GUI_CalculateGadget(gad)
			Case "getenabled"
				Return tabp\enabled
		End Select
	Case "grp"
		grp.GUI_GroupBox = Object.GUI_GroupBox(gad)
		Select msg$
			Case "setpos"
				grp\x = p1$
				grp\y = p2$
			Case "setsize"
				grp\w = p1$
				grp\h = p2$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return grp\x
			Case "gety"
				Return grp\y
			Case "getwidth"
				Return grp\w
			Case "getheight"
				Return grp\h
			Case "gettext"
				Return grp\txt$
			Case "settext"
				grp\txt$ = p1$
				GUI_CalculateGadget(gad)
		End Select
	Case "btn"
		btn.GUI_Button = Object.GUI_Button(gad)
		Select msg$
			Case "setpos"
				btn\x = p1$
				btn\y = p2$
			Case "setsize"
				btn\w = p1$
				btn\h = p2$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return btn\x
			Case "gety"
				Return btn\y
			Case "getwidth"
				Return btn\w
			Case "getheight"
				Return btn\h
			Case "seticon"
				btn\icon = GUI_LoadIcon(p1$)
			Case "getenabled"
				Return btn\enabled
			Case "setenabled"
				btn\enabled = p1$
			Case "gettext"
				btn\Txt$ = p1$
		End Select
	Case "chk"
		chk.GUI_CheckBox = Object.GUI_CheckBox(gad)
		Select msg$
			Case "setpos"
				chk\x = p1$
				chk\y = p2$
			Case "getx"
				Return chk\x
			Case "gety"
				Return chk\y
			Case "setenabled"
				chk\enabled = p1$
			Case "getenabled"
				Return chk\enabled
			Case "setchecked"
				chk\checked = p1$
			Case "getchecked"
				Return chk\checked
			Case "settext"
				chk\txt$ = p1$
			Case "gettext"
				Return chk\txt$
		End Select
	Case "rad"
		rad.GUI_Radio = Object.GUI_Radio(gad)
		Select msg$
			Case "setpos"
				rad\x = p1$
				rad\y = p2$
			Case "getx"
				Return rad\x
			Case "gety"
				Return rad\y
			Case "setenabled"
				rad\enabled = p1$
			Case "getenabled"
				Return rad\enabled
			Case "setchecked"
				For rad2.GUI_Radio = Each GUI_Radio
					If rad2\owner = rad\owner And rad\gid = rad2\gid Then rad2\checked = False
				Next
				rad\checked = True
			Case "getchecked"
				Return rad\checked
			Case "settext"
				rad\txt$ = p1$
			Case "gettext"
				Return rad\txt$
		End Select
	Case "img"
		img.GUI_Image = Object.GUI_Image(gad)
		Select msg$
			Case "setpos"
				img\x = p1$
				img\y = p2$
			Case "setsize"
				img\w = p1$
				img\h = p2$
				img2 = GUI_ResizeImage(img\img, img\w, img\h)
				FreeImage img\img
				img\img = img2
			Case "getx"
				Return img\x
			Case "gety"
				Return img\y
			Case "getwidth"
				Return img\w
			Case "getheight"
				Return img\h
			Case "setimage"
				If img\img Then FreeImage img\img
				If FileType(p1$) = 1 Then img\img = GUI_LoadIcon(p1$) Else img\img = p1$
				If img\w <> -1 Or img\h <> -1 Then
					img2 = GUI_ResizeImage(img\img, img\w, img\h)
					FreeImage img\img
					img\img = img2
				EndIf
			Case "getimage"
				Return img\img
		End Select
	Case "sld"
		sld.GUI_Slider = Object.GUI_Slider(gad)
		Select msg$
			Case "setpos"
				sld\x = p1$
				sld\y = p2$
			Case "setwidth"
				sld\w = p1$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return sld\x
			Case "gety"
				Return sld\y
			Case "getwidth"
				Return sld\w#
			Case "setvalue"
				sld\value# = p1$
			Case "getvalue"
				Return sld\value#
			Case "getminvalue"
				Return sld\min#
			Case "setminvalue"
				sld\min# = p1$
			Case "getmaxvalue"
				Return sld\max#
			Case "setmaxvalue"
				sld\max# = p1$
			Case "getenabled"
				Return sld\enabled
			Case "setenabled"
				sld\enabled = p1$
		End Select
	Case "spn"
		spn.GUI_Spinner = Object.GUI_Spinner(gad)
		Select msg$
			Case "setpos"
				spn\x = p1$
				spn\y = p2$
			Case "setwidth"
				spn\w = p1$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return spn\x
			Case "gety"
				Return spn\y
			Case "getwidth"
				Return spn\w
			Case "getvalue"
				Return spn\value#
			Case "setvalue"
				spn\value# = p1$
			Case "setminvalue"
				spn\min# = p1$
			Case "getminvalue"
				Return spn\min#
			Case "setmaxvalue"
				spn\max# = p1$
			Case "getmaxvalue"
				Return spn\max#
			Case "getenabled"
				Return spn\enabled
			Case "setenabled"
				spn\enabled = False
			Case "setinc"
				spn\inc# = p1$
			Case "getinc"
				Return spn\inc#
		End Select
	Case "lbl"
		lbl.GUI_Label = Object.GUI_Label(gad)
		Select msg$
			Case "setpos"
				lbl\x = p1$
				lbl\y = p2$
			Case "getx"
				Return lbl\x
			Case "gety"
				Return lbl\y
			Case "settext"
				lbl\txt$ = p1$
			Case "gettext"
				Return lbl\txt$
			Case "getalign"
				Select lbl\align
					Case -1
						Return "left"
					Case 0
						Return "center"
					Case 1
						Return "right"
				End Select
			Case "setalign"
				Select Lower(p1$)
					Case "left"
						lbl\align = -1
					Case "center"
						lbl\align = 0
					Case "right"
						lbl\align = 1
				End Select
		End Select
	Case "cmb"
		cmb.GUI_ComboBox = Object.GUI_ComboBox(gad)
		Select msg$
			Case "setpos"
				cmb\x = p1$
				cmb\y = p2$
			Case "setwidth"
				cmb\w = p1$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return cmb\x
			Case "gety"
				Return cmb\y
			Case "getwidth"
				Return cmb\w
			Case "clear"
				For i = 0 To GUI_StackSize
					cmb\txt$[i] = ""
					If cmb\icon[i] Then
						FreeImage cmb\icon[i]
						cmb\icon[i] = 0
					EndIf
				Next
				cmb\sel = 0
			Case "additem"
				If p1$ = -1 Then
					For i = GUI_StackSize To 0 Step -1
						If cmb\txt$[i] <> "" Then
							num = i + 1
							Exit
						EndIf
					Next
				Else
					num = p1$
				EndIf
				cmb\txt$[num] = p2$
				If FileType(p3$) = 1 Then cmb\icon[num] = GUI_LoadIcon(p3$)
			Case "setselected"
				cmb\sel = p1$
			Case "getselected"
				Return cmb\sel
			Case "gettext"
				Return cmb\txt$[cmb\sel]
			Case "settext"
				For i = 0 To GUI_StackSize
					If cmb\txt$[i] = p1$ Then cmb\sel = i
				Next
			Case "getenabled"
				Return cmb\enabled
			Case "setenabled"
				cmb\enabled = p1$
				If cmb\enabled = False Then cmb\opened = False
		End Select
	Case "prg"
		prg.GUI_ProgressBar = Object.GUI_ProgressBar(gad)
		Select msg$
			Case "setpos"
				prg\x = p1$
				prg\y = p2$
			Case "setsize"
				prg\w = p1$
				prg\h = p2$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return prg\x
			Case "gety"
				Return prg\y
			Case "getwidth"
				Return prg\w
			Case "getheight"
				Return prg\h
			Case "setstatus"
				prg\status = p1$
			Case "getstatus"
				Return prg\status
		End Select
	Case "scr"
		scr.GUI_ScrollBar = Object.GUI_ScrollBar(gad)
		Select msg$
			Case "setpos"
				scr\x = p1$
				scr\y = p2$
			Case "setheight"
				scr\h = p1$
				scr\bh = scr\h / 3
				GUI_CalculateGadget(gad)
			Case "getx"
				Return scr\x
			Case "gety"
				Return scr\y
			Case "getheight"
				Return scr\h
			Case "getstatus"
				Return GUI_Interpolate#(scr\bp, skin\ScrollBar_Button_Height + scr\bh / 2, scr\h - skin\ScrollBar_Button_Height - scr\bh / 2, 0, 100)
			Case "setstatus"
				scr\bp = GUI_Interpolate(p1$, 0, 100, skin\ScrollBar_Button_Height + scr\bh / 2, scr\h - skin\ScrollBar_Button_Height - scr\bh / 2)
			Case "setbarheight"
				scr\bh = p1$
				If scr\bh < 20 Then scr\bh = 20
				If scr\bh > scr\h - skin\ScrollBar_Button_Height * 2 Then scr\bh = scr\h - skin\ScrollBar_Button_Height * 2
				GUI_CalculateGadget(gad)
		End Select
	Case "ico"
		ico.GUI_Icon = Object.GUI_Icon(gad)
		Select msg$
			Case "setpos"
				ico\x = p1$
				ico\y = p2$
			Case "setsize"
				ico\w = p1$
				ico\h = p2$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return ico\x
			Case "gety"
				Return ico\y
			Case "getwidth"
				Return ico\w
			Case "getheight"
				Return ico\h
			Case "seticon"
				ico\icon = GUI_LoadIcon(p1$)
			Case "settext"
				ico\txt$ = p1$
			Case "gettext"
				Return ico\txt$
			Case "getenabled"
				Return ico\enabled
			Case "setenabled"
				ico\enabled = p1$
			Case "setgid"
				ico\gid = p1$
			Case "getgid"
				Return ico\gid
		End Select
	Case "lst"
		lst.GUI_ListBox = Object.GUI_ListBox(gad)
		Select msg$
			Case "setpos"
				lst\x = p1$
				lst\y = p2$
			Case "setsize"
				lst\w = p1$
				lst\h = p2$
				GUI_CalculateGadget(gad)
				GUI_Message(lst\scr, "SetHeight", lst\h)
				GUI_Message(lst\scr, "SetPos", lst\w - skin\ScrollBar_Width)
			Case "getx"
				Return lst\x
			Case "gety"
				Return lst\y
			Case "getwidth"
				Return lst\w
			Case "getheight"
				Return lst\h
			Case "clear"
				lst\sel = 0
				For i = 0 To GUI_StackSize
					lst\txt$[i] = ""
					If lst\icon[i] Then
						FreeImage lst\icon[i]
						lst\icon[i] = 0
					EndIf
				Next
			Case "additem"
				If p1$ = -1 Then
					For i = GUI_StackSize To 0 Step -1
						If lst\txt$[i] <> "" Then
							num = i + 1
							Exit
						EndIf
					Next
				Else
					num = p1$
				EndIf
				lst\txt$[num] = p2$
				If FileType(p3$) = 1 Then lst\icon[num] = GUI_LoadIcon(p3$)
			Case "getselected"
				Return lst\sel
			Case "setselected"
				lst\sel = p1$
			Case "gettext"
				Return lst\txt$[lst\sel]
			Case "settext"
				For i = 0 To GUI_StackSize
					If lst\txt$[i] = p1$ Then lst\sel = i
				Next
		End Select
	Case "edt"
		edt.GUI_Edit = Object.GUI_Edit(gad)
		Select msg$
			Case "setpos"
				edt\x = p1$
				edt\y = p2$
			Case "setwidth"
				edt\w = p1$
				GUI_CalculateGadget(gad)
			Case "getx"
				Return edt\x
			Case "gety"
				Return edt\y
			Case "getwidth"
				Return edt\w
			Case "gettext"
				Return edt\txt$
			Case "settext"
				edt\txt$ = p1$
		End Select
	Case "mnu"
		mnu.GUI_Menu = Object.GUI_Menu(gad)
		Select msg$
			Case "settext"
				mnu\txt$ = p1$
			Case "gettext"
				Return mnu\txt$
		End Select
		;...
End Select
End Function

Function GUI_AppEvent()
Return gui\event
End Function

Function GUI_GadgetOffset(gad, first_time = True)
skin.GUI_Skin = gui\skin
If first_time Then
	gui\gadox = 0
	gui\gadoy = 0
EndIf
Select GUI_ParseID(gad)
	Case "win"
		win.GUI_Window = Object.GUI_Window(gad)
		gui\gadox = gui\gadox + win\x
		gui\gadoy = gui\gadoy + win\y + skin\Window_Tilebar_Height
		gui\gadow = (win = Last GUI_Window)
		hwin = Handle(win)
		For mnu.GUI_Menu = Each GUI_Menu
			If mnu\owner = hwin Then
				SetFont skin\Fnt_Gadgets
				gui\gadoy = gui\gadoy + FontHeight() + 4
				Exit
			EndIf
		Next
	Case "tab"
		tab.GUI_Tab = Object.GUI_Tab(gad)
		gui\gadox = gui\gadox + tab\x
		gui\gadoy = gui\gadoy + tab\y
		GUI_GadgetOffset(tab\owner, False)
	Case "tabp"
		tabp.GUI_TabPage = Object.GUI_TabPage(gad)
		gui\gadoy = gui\gadoy + skin\TabPage_Height
		GUI_GadgetOffset(tabp\owner, False)
	Case "grp"
		grp.GUI_GroupBox = Object.GUI_GroupBox(gad)
		gui\gadox = gui\gadox + grp\x
		gui\gadoy = gui\gadoy + grp\y
		GUI_GadgetOffset(grp\owner, False)
	Case "btn"
		btn.GUI_Button = Object.GUI_Button(gad)
		gui\gadox = gui\gadox + btn\x
		gui\gadoy = gui\gadoy + btn\y
		GUI_GadgetOffset(btn\owner, False)
	Case "chk"
		chk.GUI_CheckBox = Object.GUI_CheckBox(gad)
		gui\gadox = gui\gadox + chk\x
		gui\gadoy = gui\gadoy + chk\y
		GUI_GadgetOffset(chk\owner, False)
	Case "rad"
		rad.GUI_Radio = Object.GUI_Radio(gad)
		gui\gadox = gui\gadox + rad\x
		gui\gadoy = gui\gadoy + rad\y
		GUI_GadgetOffset(rad\owner, False)
	Case "img"
		img.GUI_Image = Object.GUI_Image(gad)
		gui\gadox = gui\gadox + img\x
		gui\gadoy = gui\gadoy + img\y
		GUI_GadgetOffset(img\owner, False)
	Case "sld"
		sld.GUI_Slider = Object.GUI_Slider(gad)
		gui\gadox = gui\gadox + sld\x
		gui\gadoy = gui\gadoy + sld\y
		GUI_GadgetOffset(sld\owner, False)
	Case "spn"
		spn.GUI_Spinner = Object.GUI_Spinner(gad)
		gui\gadox = gui\gadox + spn\x
		gui\gadoy = gui\gadoy + spn\y
		GUI_GadgetOffset(spn\owner, False)
	Case "lbl"
		lbl.GUI_Label = Object.GUI_Label(gad)
		gui\gadox = gui\gadox + lbl\x
		gui\gadoy = gui\gadoy + lbl\y
		GUI_GadgetOffset(lbl\owner, False)
	Case "cmb"
		cmb.GUI_ComboBox = Object.GUI_ComboBox(gad)
		gui\gadox = gui\gadox + cmb\x
		gui\gadoy = gui\gadoy + cmb\y
		GUI_GadgetOffset(cmb\owner, False)
	Case "prg"
		prg.GUI_ProgressBar = Object.GUI_ProgressBar(gad)
		gui\gadox = gui\gadox + prg\x
		gui\gadoy = gui\gadoy + prg\y
		GUI_GadgetOffset(prg\owner, False)
	Case "scr"
		scr.GUI_ScrollBar = Object.GUI_ScrollBar(gad)
		gui\gadox = gui\gadox + scr\x
		gui\gadoy = gui\gadoy + scr\y
		GUI_GadgetOffset(scr\owner, False)
	Case "ico"
		ico.GUI_Icon = Object.GUI_Icon(gad)
		gui\gadox = gui\gadox + ico\x
		gui\gadoy = gui\gadoy + ico\y
		GUI_GadgetOffset(ico\owner, False)
	Case "lst"
		lst.GUI_ListBox = Object.GUI_ListBox(gad)
		gui\gadox = gui\gadox + lst\x
		gui\gadoy = gui\gadoy + lst\y
		GUI_GadgetOffset(lst\owner, False)
	Case "edt"
		edt.GUI_Edit = Object.GUI_Edit(gad)
		gui\gadox = gui\gadox + edt\x
		gui\gadoy = gui\gadoy + edt\y
		GUI_GadgetOffset(edt\owner, False)
	Default
		RuntimeError "GUI_GadgetOffset() failed."
End Select
End Function

Function GUI_UpdateGadgets(owner)
If GUI_ParseID(owner) = "tab" Then
	GUI_UpdateTabPage(owner)
Else
	GUI_UpdateTab(owner)
	GUI_UpdateGroupBox(owner)
	GUI_UpdateButton(owner)
	GUI_UpdateCheckBox(owner)
	GUI_UpdateRadio(owner)
	GUI_UpdateImage(owner)
	GUI_UpdateSlider(owner)
	GUI_UpdateSpinner(owner)
	GUI_UpdateLabel(owner)
	GUI_UpdateProgressBar(owner)
	GUI_UpdateScrollBar(owner)
	GUI_UpdateIcon(owner)
	GUI_UpdateListBox(owner)
	GUI_UpdateComboBox(owner)
	GUI_UpdateEdit(owner)
	GUI_UpdateMenu(owner)
EndIf
End Function

Function GUI_DeleteGadgets(owner)
For tab.GUI_Tab = Each GUI_Tab
	If tab\owner = owner Then
		GUI_DeleteGadgets(Handle(tab))
		FreeImage tab\img
		FreeImage tab\img
		Delete tab
	EndIf
Next
For tabp.GUI_TabPage = Each GUI_TabPage
	If tabp\owner = owner Then
		GUI_DeleteGadgets(Handle(tabp))
		For i = 0 To 3
			FreeImage tabp\img[i]
		Next
		If tabp\icon Then FreeImage tabp\icon
		Delete tabp
	EndIf
Next
For grp.GUI_GroupBox = Each GUI_GroupBox
	If grp\owner = owner Then
		GUI_DeleteGadgets(Handle(grp))
		FreeImage grp\img
		Delete grp
	EndIf
Next
For btn.GUI_Button = Each GUI_Button
	If btn\owner = owner Then
		For i = 0 To 4
			FreeImage btn\img[i]
		Next
		If btn\icon Then FreeImage btn\icon
		Delete btn
	EndIf
Next
For chk.GUI_CheckBox = Each GUI_CheckBox
	If chk\owner = owner Then Delete chk
Next
For rad.GUI_Radio = Each GUI_Radio
	If rad\owner = owner Then Delete rad
Next
For img.GUI_Image = Each GUI_Image
	If img\owner = owner Then
		If img\img Then FreeImage img\img
		Delete img
	EndIf
Next
For sld.GUI_Slider = Each GUI_Slider
	If sld\owner = owner Then
		FreeImage sld\img
		Delete sld
	EndIf
Next
For spn.GUI_Spinner = Each GUI_Spinner
	If spn\owner = owner Then
		For i = 0 To 5
			FreeImage spn\img[i]
		Next
		Delete spn
	EndIf
Next
For lbl.GUI_Label = Each GUI_Label
	If lbl\owner = owner Then Delete lbl
Next
For cmb.GUI_ComboBox = Each GUI_ComboBox
	If cmb\owner = owner Then
		For i = 0 To 3
			FreeImage cmb\img[i]
		Next
		For i = 0 To GUI_StackSize
			If cmb\icon[i] Then FreeImage cmb\icon[i]
		Next
		Delete cmb
	EndIf
Next
For prg.GUI_ProgressBar = Each GUI_ProgressBar
	If prg\owner = owner Then
		For i = 0 To 1
			FreeImage prg\img[i]
		Next
		Delete prg
	EndIf
Next
For scr.GUI_ScrollBar = Each GUI_ScrollBar
	If scr\owner = owner Then
		FreeImage scr\img1
		For i = 0 To 2
			FreeImage scr\img2[i]
		Next
		Delete scr
	EndIf
Next
For ico.GUI_Icon = Each GUI_Icon
	If ico\owner = owner Then
		For i = 0 To 4
			FreeImage ico\img[i]
		Next
		If ico\icon Then FreeImage ico\icon
		Delete ico
	EndIf
Next
For lst.GUI_ListBox = Each GUI_ListBox
	If lst\owner = owner Then
		GUI_DeleteGadgets(Handle(lst))
		FreeImage lst\img
		For i = 0 To GUI_StackSize
			If lst\icon[i] Then FreeImage lst\icon[i]
		Next
		Delete lst
	EndIf
Next
For edt.GUI_Edit = Each GUI_Edit
	If edt\owner = owner Then
		FreeImage edt\img
		Delete edt
	EndIf
Next
For mnu.GUI_Menu = Each GUI_Menu
	If mnu\owner = owner Then
		Delete mnu
	EndIf
Next
End Function

Function GUI_UpdateInput(pointer_frame)
gui\mx = MouseX()
gui\my = MouseY()
gui\oldmd1 = gui\md1
gui\md1 = MouseDown(1)
If gui\oldmd1 And gui\md1 = False Then gui\mu1 = True Else gui\mu1 = False
gui\mh1 = MouseHit(1)
If gui\mh1 Then
	gui\mh1x = gui\mx
	gui\mh1y = gui\my
EndIf
gui\mzs = MouseZSpeed()
If MilliSecs() - gui\hlp_button_pressedtime < 1000 Then
	If gui\mh1 Then gui\hlp_button_pressedtime = 0
	gui\PointerFrame = 7
Else
	If pointer_frame = -1 Then gui\PointerFrame = 0 Else gui\PointerFrame = pointer_frame
EndIf
gui\gk = GetKey()
End Function

Function GUI_DrawMouse()
Select gui\PointerFrame
	Case 0, 7, 11, 12
		xo = 0
		yo = 0
	Case 1, 2, 3, 4, 5, 6, 9
		xo = 10
		yo = 10
	Case 8
		xo = 9
		yo = 0
	Case 10
		xo = 0
		yo = 20
End Select
DrawImage gui\skin\Img_Pointer, gui\mx - xo, gui\my - yo, gui\PointerFrame
End Function

Function GUI_Color(col$)
Color GUI_Parse(col$, 0), GUI_Parse(col$, 1), GUI_Parse(col$, 2)
End Function

Function GUI_LoadFont(fnt$)
Return LoadFont(GUI_Parse(fnt$, 0), GUI_Parse(fnt$, 1), GUI_Parse(fnt$, 2), GUI_Parse(fnt$, 3), GUI_Parse(fnt$, 4))
End Function

Function GUI_ParseID$(gad)
If Object.GUI_Window(gad) <> Null Then Return "win"
If Object.GUI_Tab(gad) <> Null Then Return "tab"
If Object.GUI_TabPage(gad) <> Null Then Return "tabp"
If Object.GUI_GroupBox(gad) <> Null Then Return "grp"
If Object.GUI_Button(gad) <> Null Then Return "btn"
If Object.GUI_CheckBox(gad) <> Null Then Return "chk"
If Object.GUI_Radio(gad) <> Null Then Return "rad"
If Object.GUI_Image(gad) <> Null Then Return "img"
If Object.GUI_Slider(gad) <> Null Then Return "sld"
If Object.GUI_Spinner(gad) <> Null Then Return "spn"
If Object.GUI_Label(gad) <> Null Then Return "lbl"
If Object.GUI_ComboBox(gad) <> Null Then Return "cmb"
If Object.GUI_ProgressBar(gad) <> Null Then Return "prg"
If Object.GUI_ScrollBar(gad) <> Null Then Return "scr"
If Object.GUI_Icon(gad) <> Null Then Return "ico"
If Object.GUI_ListBox(gad) <> Null Then Return "lst"
If Object.GUI_Edit(gad) <> Null Then Return "edt"
If Object.GUI_Menu(gad) <> Null Then Return "mnu"
End Function

Function GUI_ImageWidth(path$)
If FileType(path$) <> 1 Then RuntimeError "Image " + path$ + " not found."
img = LoadImage(path$)
w = ImageWidth(img)
FreeImage img
Return w
End Function

Function GUI_ImageHeight(path$)
If FileType(path$) <> 1 Then RuntimeError "Image " + path$ + " not found."
img = LoadImage(path$)
h = ImageHeight(img)
FreeImage img
Return h
End Function

Function GUI_LoadImage(path$, w = 0, h = 0, frames = 0)
If FileType(path$) <> 1 Then RuntimeError "Image " + path$ + " not found."
If w < 0 Then w = GUI_ImageWidth(path$) / -w
If h < 0 Then h = GUI_ImageHeight(path$) / -h
If w = 0 Or h = 0 Or frames = 0 Then img = LoadImage(path$) Else img = LoadAnimImage(path$, w, h, 0, frames)
MaskImage img, 255, 0, 255
Return img
End Function

Function GUI_LoadIcon(path$)
If FileType(path$) = 1 Then
	img = LoadImage(path$)
	w = ImageWidth(img)
	h = ImageHeight(img)
	img2 = CreateImage(w, h)
	LockBuffer ImageBuffer(img)
	LockBuffer ImageBuffer(img2)
	For x = 0 To w - 1
		For y = 0 To h - 1
			rgb = ReadPixel(x, y, ImageBuffer(img))
			r = (rgb And $FF0000) / $10000
			g = (rgb And $FF00) / $100
			b = rgb And $FF
			If r = 0 And g = 0 And b = 0 Then r = 1
			If r = 255 And g = 0 And b = 0 Then
				r = 0
				g = 0
				b = 0
			EndIf
			WritePixel x, y, r * $10000 + g * $100 + b, ImageBuffer(img2)
		Next
	Next
	UnlockBuffer ImageBuffer(img2)
	FreeImage img
	Return img2
EndIf
End Function

Function GUI_CountItems(msg$, sep$ = ",")
count = 0
Repeat
	fas = Instr(msg$, sep$, fas + 1)
	If fas <> 0 Then count = count + 1
Until fas = 0
Return count + 1
End Function

Function GUI_Parse$(msg$, item, sep$ = ",")
count = 0
Repeat
	fas = Instr(msg$, sep$, fas + 1)
	count = count + 1
Until fas = 0 Or count = item
If fas = 0 And item > 0 Then Return
spos = fas + 1
epos = Instr(msg$ + sep$, sep$, fas + 1)
Return Mid(msg$, spos, epos - spos)
End Function

Function GUI_MouseOver(x, y, w, h)
If gui\mx > x And gui\my > y And gui\mx < x + w And gui\my < h + y Then Return True Else Return False
End Function

Function GUI_MouseOver2(x, y, w, h)
If gui\mh1x > x And gui\mh1y > y And gui\mh1x < x + w And gui\mh1y < h + y Then Return True Else Return False
End Function

Function GUI_DrawImage(img, x, y, w, h, frame = 0)
If w < 1 Or h < 1 Then Return
img2 = GUI_ResizeImage(img, w, h, frame)
MaskImage img2, 255, 0, 255
DrawImage img2, x, y
FreeImage img2
End Function

Function GUI_ReadParams()
Delete Each GUI_Param
dir = ReadDir("GUI_Tmp\")
Repeat
	f$ = NextFile(dir)
	If f$ = "" Then Exit
	If FileType("GUI_Tmp\" + f$) = 1 And Right(f$, "4") = ".ini" Then
		file = ReadFile("GUI_Tmp\" + f$)
		While Not Eof(file)
			l$ = ReadLine(file)
			l$ = Replace(l$, " ", "")
			l$ = Replace(l$, "~", " ")
			gp.GUI_Param = New GUI_Param
			gp\p1$ = GUI_Parse(l$, 0, "=")
			gp\p2$ = GUI_Parse(l$, 1, "=")
		Wend
		CloseFile file
	EndIf
Forever
CloseDir dir
End Function

Function GUI_GetParam$(p$)
For gp.GUI_Param = Each GUI_Param
	If gp\p1$ = p$ Then Return gp\p2$
Next
RuntimeError "Parameter " + p$ + " not found!"
End Function

Function GUI_NoActiveGadgets()
For cmb.GUI_ComboBox = Each GUI_ComboBox
	If cmb\opened Then Return False
Next
For mnu.GUI_Menu = Each GUI_Menu
	If mnu\open Then Return False
Next
Return True
End Function

Function GUI_ResizeImage(image, newwidth, newheight, frame = 0)
tbuffer = GraphicsBuffer()
oldwidth = ImageWidth(image)
oldheight = ImageHeight(image)
ni = CreateImage(newwidth + 1, oldheight)
dest = CreateImage(newwidth, newheight)
SetBuffer ImageBuffer(ni)
For x = 0 To newwidth
	DrawBlockRect image, x, 0, Floor(oldwidth * x / newwidth), 0, 1, oldheight, frame
Next
SetBuffer ImageBuffer(dest)
For y = 0 To newheight
	DrawBlockRect ni, 0, y, 0, Floor(oldheight * y / newheight), newwidth, 1
Next 
FreeImage ni
SetBuffer tbuffer
Return dest
End Function

Function GUI_Interpolate#(value#, vMin#, vMax#, retMin#, retMax#)
Return retMin# + (value# - vMin#) * (retMax# - retMin#) / (vMax# - vMin#)
End Function

Function GUI_CalculateGadget(gad)
gbuffer = GraphicsBuffer()
skin.GUI_Skin = gui\skin
Select GUI_ParseID(gad)
	Case "win"
		win.GUI_Window = Object.GUI_Window(gad)
		;img_active
		If win\img_active Then FreeImage win\img_active
		win\img_active = CreateImage(win\w, win\h)
		SetBuffer ImageBuffer(win\img_active)
		GUI_DrawImage(skin\Img_Window_Tilebar, 7, 0, win\w - 14, skin\Window_Tilebar_Height, 4)
		Viewport 0, 0, gui\w, gui\h
		DrawImage skin\Img_Window_Tilebar, 0, 0, 3
		DrawImage skin\Img_Window_Tilebar, win\w -7, 0, 5
		Viewport 4, skin\Window_Tilebar_Height, win\w - 8, win\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_BG, 0, 0
		Viewport 0, 0, gui\w, gui\h
		Viewport 0, skin\Window_Tilebar_Height, 4, win\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_Border, 0, 0, 3
		Viewport win\w - 4, skin\Window_Tilebar_Height, 4, win\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_Border, win\w, win\h, 5
		Viewport 4, win\h - 4, win\w - 8, 4
		TileImage skin\Img_Window_Border, win\w, win\h, 10
		Viewport 0, 0, gui\w, gui\h
		DrawImage skin\Img_Window_Border, 0, win\h - 4, 9
		DrawImage skin\Img_Window_Border, win\w - 4, win\h - 4, 11
		;img_inactive
		If win\img_inactive Then FreeImage win\img_inactive
		win\img_inactive = CreateImage(win\w, win\h)
		SetBuffer ImageBuffer(win\img_inactive)
		GUI_DrawImage(skin\Img_Window_Tilebar, 7, 0, win\w - 14, skin\Window_Tilebar_Height, 1)
		Viewport 0, 0, gui\w, gui\h
		DrawImage skin\Img_Window_Tilebar, 0, 0, 0
		DrawImage skin\Img_Window_Tilebar, win\w -7, 0, 2
		Viewport 4, skin\Window_Tilebar_Height, win\w - 8, win\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_BG, 0, 0
		Viewport 0, 0, gui\w, gui\h
		Viewport 0, skin\Window_Tilebar_Height, 4, win\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_Border, win\w, win\h, 0
		Viewport win\w - 4, skin\Window_Tilebar_Height, 4, win\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_Border, win\w, win\h, 2
		Viewport 4, win\h - 4, win\w - 8, 4
		TileImage skin\Img_Window_Border, win\w, win\h, 7
		Viewport 0, 0, gui\w, gui\h
		DrawImage skin\Img_Window_Border, 0, win\h - 4, 6
		DrawImage skin\Img_Window_Border, win\w - 4, win\h - 4, 8
		;img_max
		If win\img_max Then FreeImage win\img_max
		win\img_max = CreateImage(gui\w, gui\h)
		SetBuffer ImageBuffer(win\img_max)
		GUI_DrawImage(skin\Img_Window_Tilebar, 7, 0, gui\w - 14, skin\Window_Tilebar_Height, 4)
		Viewport 0, 0, gui\w, gui\h
		DrawImage skin\Img_Window_Tilebar, 0, 0, 3
		DrawImage skin\Img_Window_Tilebar, gui\w -7, 0, 5
		Viewport 4, skin\Window_Tilebar_Height, gui\w - 8, gui\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_BG, 0, 0
		Viewport 0, 0, gui\w, gui\h
		Viewport 0, skin\Window_Tilebar_Height, 4, gui\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_Border, win\w, win\h, 3
		Viewport gui\w - 4, skin\Window_Tilebar_Height, 4, gui\h - skin\Window_Tilebar_Height - 4
		TileImage skin\Img_Window_Border, win\w, win\h, 5
		Viewport 4, gui\h - 4, gui\w - 8, 4
		TileImage skin\Img_Window_Border, win\w, win\h, 10
		Viewport 0, 0, gui\w, gui\h
		DrawImage skin\Img_Window_Border, 0, gui\h - 4, 9
		DrawImage skin\Img_Window_Border, gui\w - 4, gui\h - 4, 11
	Case "tab"
		tab.GUI_Tab = Object.GUI_Tab(gad)
		;img
		If tab\img Then FreeImage tab\img
		tab\img = CreateImage(tab\w, tab\h - skin\TabPage_Height)
		SetBuffer ImageBuffer(tab\img)
		DrawImage skin\Img_Tab, 0, 0, 0
		DrawImage skin\Img_Tab, tab\w - 10, 0, 2
		DrawImage skin\Img_Tab, 0, tab\h - 10 - skin\TabPage_Height, 6
		DrawImage skin\Img_Tab, tab\w - 10, tab\h - 10 - skin\TabPage_Height, 8
		GUI_DrawImage(skin\Img_Tab, 10, 10, tab\w - 20, tab\h - 20 - skin\TabPage_Height, 4)
		GUI_DrawImage(skin\Img_Tab, 10, 0, tab\w - 20, 10, 1)
		GUI_DrawImage(skin\Img_Tab, 10, tab\h - 10 - skin\TabPage_Height, tab\w - 20, 10, 7)
		GUI_DrawImage(skin\Img_Tab, 0, 10, 10, tab\h - 20 - skin\TabPage_Height, 3)
		GUI_DrawImage(skin\Img_Tab, tab\w - 10, 10, 10, tab\h - 20 - skin\TabPage_Height, 5)
	Case "tabp"
		SetFont skin\Fnt_Gadgets
		tabp.GUI_TabPage = Object.GUI_TabPage(gad)
		If tabp\icon Then iw = ImageWidth(tabp\icon) Else iw = 0
		w = StringWidth(tabp\txt$) + 10 + iw
		If w < 10 Then w = 10
		For i = 0 To 3
			If tabp\img[i] Then FreeImage tabp\img[i]
			tabp\img[i] = CreateImage(w, skin\TabPage_Height)
			SetBuffer ImageBuffer(tabp\img[i])
			DrawImage skin\Img_TabPage, 0, 0, i * 3
			DrawImage skin\Img_TabPage, w - 4, 0, i * 3 + 2
			Viewport 4, 0, w - 8, skin\TabPage_Height
			TileImage skin\Img_TabPage, 0, 0, i * 3 + 1
			Viewport 0, 0, gui\w, gui\h
			If tabp\icon Then
				DrawImage tabp\icon, 3, skin\TabPage_Height / 2 - ImageHeight(tabp\icon) / 2 + 2
				iw = ImageWidth(tabp\icon) + 4
			Else
				iw = 0
			EndIf
			x = xo + iw + 4
			y = skin\TabPage_Height / 2
			If tabp\enabled Then
				GUI_Color(skin\Col_TabPage_Enabled$)
				Text x, y, tabp\txt$, False, True
			Else
				If skin\Col_TabPage_Disabled_Shadow$ <> "none" Then
					GUI_Color(skin\Col_TabPage_Disabled_Shadow$)
					Text x + 1, y + 1, tabp\txt$, False, True
				EndIf
				GUI_Color(skin\Col_TabPage_Disabled$)
				Text x, y, tabp\txt$, False, True
			EndIf
		Next
	Case "grp"
		SetFont skin\Fnt_Gadgets
		grp.GUI_GroupBox = Object.GUI_GroupBox(gad)
		;img
		If grp\img Then FreeImage grp\img
		grp\img = CreateImage(grp\w, grp\h)
		SetBuffer ImageBuffer(grp\img)
		w = StringWidth(grp\txt$) + 4
		DrawImage skin\Img_GroupBox, 0, 0, 0
		DrawImage skin\Img_GroupBox, grp\w - 10, 0, 2
		DrawImage skin\Img_GroupBox, 0, grp\h - 10, 6
		DrawImage skin\Img_GroupBox, grp\w - 10, grp\h - 10, 8
		Viewport 0, 10, 10, grp\h - 20
		TileImage skin\Img_GroupBox, 0, 10, 3
		Viewport grp\w - 10, 10, 10, grp\h - 20
		TileImage skin\Img_GroupBox, grp\w - 10, 10, 5
		Viewport 10 + w, 0, grp\w - 20 - w, 10
		TileImage skin\Img_GroupBox, 10, 0, 1
		Viewport 10, grp\h - 10, grp\w - 20, 10
		TileImage skin\Img_GroupBox, 10, grp\h - 10, 7
		Viewport 10, 10, grp\w - 20, grp\h - 20
		TileImage skin\Img_GroupBox, 10, 10, 4
		Viewport 0, 0, gui\w, gui\h
		GUI_Color(skin\Col_GroupBox$)
		Text 12, 5, grp\txt$, False, True
	Case "btn"
		btn.GUI_Button = Object.GUI_Button(gad)
		;img
		For i = 0 To 4
			If btn\img[i] Then FreeImage btn\img[i]
			btn\img[i] = CreateImage(btn\w, btn\h)
			SetBuffer ImageBuffer(btn\img[i])
			DrawImage skin\Img_Button, 0, 0, i * 9
			DrawImage skin\Img_Button, btn\w - 10, 0, 2 + i * 9
			DrawImage skin\Img_Button, 0, btn\h - 10, 6 + i * 9
			DrawImage skin\Img_Button, btn\w - 10, btn\h - 10, 8 + i * 9
			GUI_DrawImage(skin\Img_Button, 10, 10, btn\w - 20, btn\h - 20, 4 + i * 9)
			GUI_DrawImage(skin\Img_Button, 10, 0, btn\w - 20, 10, 1 + i * 9)
			GUI_DrawImage(skin\Img_Button, btn\w - 10, 10, 10, btn\h - 20, 5 + i * 9)
			GUI_DrawImage(skin\Img_Button, 0, 10, 10, btn\h - 20, 3 + i * 9)
			GUI_DrawImage(skin\Img_Button, 10, btn\h - 10, btn\w - 20, 10, 7 + i * 9)
		Next
	Case "sld"
		sld.GUI_Slider = Object.GUI_Slider(gad)
		;img
		If sld\img Then FreeImage sld\img
		sld\img = CreateImage(sld\w, skin\Slider_Height)
		SetBuffer ImageBuffer(sld\img)
		DrawImage skin\Img_Slider, 0, 0, 0
		DrawImage skin\Img_Slider, sld\w - 9, 0, 2
		Viewport 9, 0, sld\w - 18, skin\Slider_Height
		TileImage skin\Img_Slider, 0, 0, 1
		Viewport 0, 0, gui\w, gui\h
	Case "spn"
		SetFont skin\Fnt_Gadgets
		spn.GUI_Spinner = Object.GUI_Spinner(gad)
		;img
		For i = 0 To 5
			If spn\img[i] Then FreeImage spn\img[i]
			h = ImageHeight(skin\Img_Spinner)
			iw = ImageWidth(skin\Img_Spinner)
			spn\img[i] = CreateImage(spn\w, h)
			SetBuffer ImageBuffer(spn\img[i])
			DrawImage skin\Img_Spinner, 0, 0, i * 3
			Viewport iw, 0, spn\w - iw * 2, h
			TileImage skin\Img_Spinner, 0, 0, 1 + i * 3
			Viewport 0, 0, gui\w, gui\h
			DrawImage skin\Img_Spinner, spn\w - iw, 0, 2 + i * 3
		Next
	Case "cmb"
		SetFont skin\Fnt_Gadgets
		cmb.GUI_ComboBox = Object.GUI_ComboBox(gad)
		w = ImageWidth(skin\Img_ComboBox)
		h = ImageHeight(skin\Img_ComboBox)
		For i = 0 To 3
			If cmb\img[i] Then FreeImage cmb\img[i]
			cmb\img[i] = CreateImage(cmb\w, h)
			SetBuffer ImageBuffer(cmb\img[i])
			DrawImage skin\Img_ComboBox, 0, 0, i * 4
			DrawImage skin\Img_ComboBox, cmb\w - w, 0, 3 + i * 4
			DrawImage skin\Img_ComboBox, cmb\w - w * 2, 0, 2 + i * 4
			Viewport w, 0, cmb\w - w * 2, h
			TileImage skin\Img_ComboBox, 0, 0, 1 + i * 4
			Viewport 0, 0, gui\w, gui\h
		Next
	Case "prg"
		prg.GUI_ProgressBar = Object.GUI_ProgressBar(gad)
		;img[0]
		If prg\img[0] Then FreeImage prg\img[0]
		prg\img[0] = CreateImage(prg\w, prg\h)
		SetBuffer ImageBuffer(prg\img[0])
		DrawImage skin\Img_ProgressBar, 0, 0, 0
		DrawImage skin\Img_ProgressBar, prg\w - 10, 0, 2
		DrawImage skin\Img_ProgressBar, 0, prg\h - 10, 6
		DrawImage skin\Img_ProgressBar, prg\w - 10, prg\h - 10, 8
		GUI_DrawImage(skin\Img_ProgressBar, 0, 10, 10, prg\h - 20, 3)
		GUI_DrawImage(skin\Img_ProgressBar, prg\w - 10, 10, 10, prg\h - 20, 5)
		GUI_DrawImage(skin\Img_ProgressBar, 10, 0, prg\w - 20, 10, 1 + 0 * 9)
		GUI_DrawImage(skin\Img_ProgressBar, 10, prg\h - 10, prg\w - 20, 10, 7)
		GUI_DrawImage(skin\Img_ProgressBar, 10, 10, prg\w - 20, prg\h - 20, 4)
		;img[1]
		If prg\img[1] Then FreeImage prg\img[1]
		prg\img[1] = CreateImage(prg\w, prg\h)
		ip2 = CreateImage(30, 30)
		SetBuffer ImageBuffer(ip2)
		cnt = -1
		For y = 0 To 2
			For x = 0 To 2
				cnt = cnt + 1
				DrawImage skin\Img_ProgressBar, x * 10, y * 10, cnt + 9
			Next
		Next
		SetBuffer ImageBuffer(prg\img[1])
		img = GUI_ResizeImage(ip2, 30, prg\h)
		Viewport 3, 3, prg\w - 6, prg\h - 6
		TileImage img, 0, 0
		Viewport 0, 0, gui\w, gui\h
		FreeImage ip2
		FreeImage img
	Case "scr"
		scr.GUI_ScrollBar = Object.GUI_ScrollBar(gad)
		;img1
		If scr\img1 Then FreeImage scr\img1
		h = scr\h - skin\ScrollBar_Button_Height * 2 - 20
		scr\img1 = CreateImage(skin\ScrollBar_Width, h)
		SetBuffer ImageBuffer(scr\img1)
		Viewport 0, 0, skin\ScrollBar_Width, h
		TileImage skin\Img_ScrollBar, 0, 0, 18
		Viewport 0, 0, gui\w, gui\h
		;img2
		For i = 0 To 2
			If scr\img2[i] Then FreeImage scr\img2[i]
			scr\img2[i] = CreateImage(skin\ScrollBar_Width, scr\bh)
			SetBuffer ImageBuffer(scr\img2[i])
			DrawImage skin\Img_ScrollBar, 0, 0, i
			DrawImage skin\Img_ScrollBar, 0, scr\bh - 10, 6 + i
			Viewport 0, 10, skin\ScrollBar_Width, scr\bh - 20
			TileImage skin\Img_ScrollBar, 0, 0, 3 + i
			Viewport 0, 0, gui\w, gui\h
			DrawImage skin\Img_ScrollBar, 0, scr\bh / 2 - 5, 9 + i
		Next
	Case "ico"
		ico.GUI_Icon = Object.GUI_Icon(gad)
		For i = 0 To 4
			If ico\img[i] Then FreeImage ico\img[i]
			ico\img[i] = CreateImage(ico\w, ico\h)
			SetBuffer ImageBuffer(ico\img[i])
			DrawImage skin\Img_Icon, 0, 0, i * 9
			DrawImage skin\Img_Icon, ico\w - 10, 0, 2 + i * 9
			DrawImage skin\Img_Icon, 0, ico\h - 10, 6 + i * 9
			DrawImage skin\Img_Icon, ico\w - 10, ico\h - 10, 8 + i * 9
			GUI_DrawImage(skin\Img_Icon, 0, 10, 10, ico\h - 20, 3 + i * 9)
			GUI_DrawImage(skin\Img_Icon, ico\w - 10, 10, 10, ico\h - 20, 5 + i * 9)
			GUI_DrawImage(skin\Img_Icon, 10, 0, ico\w - 20, 10, 1 + i * 9)
			GUI_DrawImage(skin\Img_Icon, 10, ico\h - 10, ico\w - 20, 10, 7 + i * 9)
			GUI_DrawImage(skin\Img_Icon, 10, 10, ico\w - 20, ico\h - 20, 4 + i * 9)
		Next
	Case "lst"
		lst.GUI_ListBox = Object.GUI_ListBox(gad)
		If lst\img Then FreeImage lst\img
		lst\img = CreateImage(lst\w - skin\ScrollBar_Width, lst\h)
		SetBuffer ImageBuffer(lst\img)
		DrawImage skin\Img_ListBox, 0, 0, 0
		DrawImage skin\Img_ListBox, lst\w - skin\ScrollBar_Width - 10, 0, 2
		DrawImage skin\Img_ListBox, 0, lst\h - 10, 6
		DrawImage skin\Img_ListBox, lst\w - skin\ScrollBar_Width - 10, lst\h - 10, 8
		GUI_DrawImage(skin\Img_ListBox, 0, 10, 10, lst\h - 20, 3)
		GUI_DrawImage(skin\Img_ListBox, lst\w - skin\ScrollBar_Width - 10, 10, 10, lst\h - 20, 5)
		GUI_DrawImage(skin\Img_ListBox, 10, 0, lst\w - skin\ScrollBar_Width - 20, 10, 1)
		GUI_DrawImage(skin\Img_ListBox, 10, lst\h - 10, lst\w - skin\ScrollBar_Width - 20, 10, 7)
		GUI_DrawImage(skin\Img_ListBox, 10, 10, lst\w - skin\ScrollBar_Width - 20, lst\h - 20, 4)
	Case "edt"
		SetFont skin\Fnt_Gadgets
		edt.GUI_Edit = Object.GUI_Edit(gad)
		If edt\img Then FreeImage edt\img
		h = FontHeight() + 6
		edt\img = CreateImage(edt\w, h)
		SetBuffer ImageBuffer(edt\img)
		DrawImage skin\Img_Edit, 0, 0, 0
		DrawImage skin\Img_Edit, edt\w - 10, 0, 2
		DrawImage skin\Img_Edit, 0, h - 10, 6
		DrawImage skin\Img_Edit, edt\w - 10, h - 10, 8
		GUI_DrawImage(skin\Img_Edit, 0, 10, 10, h - 20, 3)
		GUI_DrawImage(skin\Img_Edit, edt\w - 10, 10, 10, h - 20, 5)
		GUI_DrawImage(skin\Img_Edit, 10, 0, edt\w - 20, 10, 1)
		GUI_DrawImage(skin\Img_Edit, 10, h - 10, edt\w - 20, 10, 7)
		GUI_DrawImage(skin\Img_Edit, 10, 10, edt\w - 20, h - 20, 4)
End Select
SetBuffer gbuffer
End Function

Function GUI_CalculateAllGadgets()
For win.GUI_Window = Each GUI_Window
	If win\closed = False Then GUI_CalculateGadget(Handle(win))
Next
For tab.GUI_Tab = Each GUI_Tab
	GUI_CalculateGadget(Handle(tab))
Next
For tabp.GUI_TabPage = Each GUI_TabPage
	GUI_CalculateGadget(Handle(tabp))
Next
For grp.GUI_GroupBox = Each GUI_GroupBox
	GUI_CalculateGadget(Handle(grp))
Next
For btn.GUI_Button = Each GUI_Button
	GUI_CalculateGadget(Handle(btn))
Next
For sld.GUI_Slider = Each GUI_Slider
	GUI_CalculateGadget(Handle(sld))
Next
For spn.GUI_Spinner = Each GUI_Spinner
	GUI_CalculateGadget(Handle(spn))
Next
For cmb.GUI_ComboBox = Each GUI_ComboBox
	GUI_CalculateGadget(Handle(cmb))
Next
For prg.GUI_ProgressBar = Each GUI_ProgressBar
	GUI_CalculateGadget(Handle(prg))
Next
For scr.GUI_ScrollBar = Each GUI_ScrollBar
	GUI_CalculateGadget(Handle(scr))
Next
For ico.GUI_Icon = Each GUI_Icon
	GUI_CalculateGadget(Handle(ico))
Next
For lst.GUI_ListBox = Each GUI_ListBox
	GUI_CalculateGadget(Handle(lst))
Next
For edt.GUI_Edit = Each GUI_Edit
	GUI_CalculateGadget(Handle(edt))
Next
End Function

Function GUI_MsgBox(title$ = "Message", msg$ = "Message", buttons = 1)
FlushKeys()
skin.GUI_Skin = gui\skin
SetFont skin\fnt_gadgets
w = 275
cnt_items = GUI_CountItems(msg$, Chr(10)) - 1
h = 100 + cnt_items * FontHeight()
For i = 0 To cnt_items
	nw = StringWidth(GUI_Parse(msg$, i, Chr(10))) + 50
	If nw > w Then w = nw
Next
win = GUI_CreateWindow(-1, -1, w, h, title$, "", True, False, False)
For i = 0 To cnt_items
	item$ = GUI_Parse(msg$, i, Chr(10))
	GUI_CreateLabel(win, w / 2, i * FontHeight() + 15, item$, "Center")
Next
bw = 75
bw2 = bw / 2
ct = w / 2
Select buttons
	Case 1
		btn1 = GUI_CreateButton(win, ct - bw2, h - 60, bw, 21, "OK")
	Case 2
		btn1 = GUI_CreateButton(win, ct - bw - 10, h - 60, bw, 21, "Yes")
		btn2 = GUI_CreateButton(win, ct + 10, h - 60, bw, 21, "No")
	Case 3
		btn1 = GUI_CreateButton(win, ct - bw - bw2 - 10, h - 60, bw, 21, "Yes")
		btn2 = GUI_CreateButton(win, ct - bw2, h - 60, bw, 21, "No")
		btn3 = GUI_CreateButton(win, ct + bw2 + 10, h - 60, bw, 21, "Abort")
	Default
		RuntimeError "Illegal number of MsgBox buttons."
End Select
Repeat
	Cls
	GUI_UpdateGUI()
	GUI_Message(win, "BringToFront")
	Select GUI_AppEvent()
		Case btn1
			ret = 1
		Case btn2
			ret = 2
		Case btn3
			ret = 3
		Default
			ret = 0
	End Select
	If ret Then Exit
	Flip
Until KeyHit(1) Or KeyHit(57) Or KeyHit(28) Or GUI_Message(win, "GetClosed")
GUI_Message(win, "Close")
Return ret
End Function

Function GUI_ColorPicker$()
win = GUI_CreateWindow(-1, -1, 350, 380, "Color Picker", "", True, False, False, True)
Img_Spectrum = GUI_CreateSpectrumImage()
imgSpectrum = GUI_CreateImage(win, 10, 10, -1, -1, Img_Spectrum)
GUI_CreateLabel(win, 10, 270, "Red:")
GUI_CreateLabel(win, 10, 295, "Green:")
GUI_CreateLabel(win, 10, 320, "Blue:")
sld_r = GUI_CreateSlider(win, 60, 270, 206)
sld_g = GUI_CreateSlider(win, 60, 295, 206)
sld_b = GUI_CreateSlider(win, 60, 320, 206)
spn_r = GUI_CreateSpinner(win, 270, 270, 70, 0, 0, 255, 1)
spn_g = GUI_CreateSpinner(win, 270, 295, 70, 0, 0, 255, 1)
spn_b = GUI_CreateSpinner(win, 270, 320, 70, 0, 0, 255, 1)
btnPick = GUI_CreateButton(win, 270, 180, 68, 21, "Pick color")
btnReset = GUI_CreateButton(win, 270, 205, 68, 21, "Reset color")
btnCancel = GUI_CreateButton(win, 270, 230, 68, 21, "Cancel")
GUI_CreateLabel(win, 270, 10, "Current color:")
Img_Color = CreateImage(70, 40)
imgColor = GUI_CreateImage(win, 270, 25, -1, -1, Img_Color)
Repeat
	Cls
	GUI_UpdateGUI(hitfield * 10)
	If GUI_Message(win, "GetClosed") Then ret$ = "Cancel": Exit
	GUI_Message(win, "BringToFront")
	GUI_GadgetOffset(imgSpectrum)
	
	mo = GUI_MouseOver(gui\gadox, gui\gadoy, 256, 256)
	hitfield = False
	If mo Then
		If gui\md1 Then
			hitfield = True
			rgb = ReadPixel(gui\mx - gui\gadox, gui\my - gui\gadoy, ImageBuffer(Img_Spectrum))
			col_r = (rgb And $FF0000) / $10000
			col_g = (rgb And $FF00) / $100
			col_b = rgb And $FF
			GUI_Message(spn_r, "SetValue", col_r)
			GUI_Message(spn_g, "SetValue", col_g)
			GUI_Message(spn_b, "SetValue", col_b)
			GUI_Message(sld_r, "SetValue", col_r / 2.55)
			GUI_Message(sld_g, "SetValue", col_g / 2.55)
			GUI_Message(sld_b, "SetValue", col_b / 2.55)
		EndIf
	EndIf
	
	Select GUI_AppEvent()
		Case spn_r, spn_g, spn_b
			col_r = GUI_Message(spn_r, "GetValue")
			col_g = GUI_Message(spn_g, "GetValue")
			col_b = GUI_Message(spn_b, "GetValue")
			GUI_Message(sld_r, "SetValue", Float(col_r) / 2.55)
			GUI_Message(sld_g, "SetValue", Float(col_g) / 2.55)
			GUI_Message(sld_b, "SetValue", Float(col_b) / 2.55)
		Case sld_r, sld_g, sld_b
			col_r = Int(GUI_Message(sld_r, "GetValue")) * 2.55
			col_g = Int(GUI_Message(sld_g, "GetValue")) * 2.55
			col_b = Int(GUI_Message(sld_b, "GetValue")) * 2.55
			GUI_Message(spn_r, "SetValue", col_r)
			GUI_Message(spn_g, "SetValue", col_g)
			GUI_Message(spn_b, "SetValue", col_b)
	End Select
	
	gbuffer = GraphicsBuffer()
	SetBuffer ImageBuffer(Img_Color)
	Color col_r, col_g, col_b
	Rect 0, 0, 70, 40
	SetBuffer gbuffer
	
	Select GUI_AppEvent()
		Case btnPick
			ret$ = col_r + "," + col_g + "," + col_b
		Case btnReset
			col_r = 0
			col_g = 0
			col_b = 0
			GUI_Message(spn_r, "SetValue", 0)
			GUI_Message(spn_g, "SetValue", 0)
			GUI_Message(spn_b, "SetValue", 0)
			GUI_Message(sld_r, "SetValue", 0)
			GUI_Message(sld_g, "SetValue", 0)
			GUI_Message(sld_b, "SetValue", 0)
		Case btnCancel
			ret$ = "Cancel"
		Default
			ret$ = ""
	End Select
	If Len(ret$) Then Exit
	Flip
Until KeyHit(1) Or KeyHit(57) Or KeyHit(28)
GUI_Message(win, "Close")
Return ret$
End Function


Dim colour#(1536, 3)
Function GUI_CreateSpectrumImage()
For i = 0 To 63
	colour#(i, 1) = 255
	colour#(i, 2) = i * 4
	colour#(i, 3) = 0
	colour#(i + 64, 1) = 255 - i * 4
	colour#(i + 64, 2) = 255
	colour#(i + 64, 3) = i * 4
	colour#(i + 128, 1) = 0
	colour#(i + 128, 2) = 255 - i * 4
	colour#(i + 128, 3) = 255
	colour#(i + 192, 1) = i * 4
	colour#(i + 192, 2) = 0
	colour#(i + 192, 3) = 255 - i * 4
Next
For i = 0 To 127
	colour#(i + 256, 1) = colour#(i, 1)
	colour#(i + 256, 2) = colour#(i, 2)
	colour#(i + 256, 3) = colour#(i, 3)
Next
img = CreateImage(256, 256)
LockBuffer ImageBuffer(img)
For y# = 0 To 127
	For x = 0 To 255
		r = colour#(x, 1) * y# / 127.0
		g = colour#(x, 2) * y# / 127.0
		b = colour#(x, 3) * y# / 127.0
		WritePixel x, y, r * $10000 + g * $100 + b, ImageBuffer(img)
		r = 255 - colour#(x + 128, 1) * y# / 127.0
		g = 255 - colour#(x + 128, 2) * y# / 127.0
		b = 255 - colour#(x + 128, 3) * y# / 127.0
		WritePixel x, 255 - y, r * $10000 + g * $100 + b, ImageBuffer(img)
	Next
Next
UnlockBuffer ImageBuffer(img)
Return img
End Function

Function GUI_PathBack$(path$)
cnt_items = GUI_CountItems(path$, "\")
item$ = GUI_Parse(path$, cnt_items - 2, "\")
ln = Len(item$)
Return Left(path$, Len(path$) - ln - 1)
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D