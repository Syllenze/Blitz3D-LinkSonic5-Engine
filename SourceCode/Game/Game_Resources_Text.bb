;*************************************************************************
;
;tx,ty=x,y coords
;
;tl=length of input characters
;
;*************************************************************************

;Global get=GetKey()

Function text_input(tx,ty,tl)
	
	FlushKeys()
	
	;Color 56,78,112:Rect tx,ty,(tl+3)*6,15,0;box
	;Color 215,218,227:Rect tx+1,ty+1,((tl+3)*6)-2,13,1;box
;	Color 56,78,112:Rect tx,ty,(tl+3)*6,22,0;Outbox
	Color 255,255,255:Rect tx+1,ty+1,((tl+3)*6)-2,20,1;box
	Color 56,78,112:Rect tx+2,ty+3,6,10;cursor
	geted$="":get=0:gg=0:ad=1:Flip
	
	While Not get
		
		get=GetKey()
		
;numpad numbers
		If KeyHit(82) Then get=48;0
		If KeyHit(79) Then get=49;1
		If KeyHit(80) Then get=50;2
		If KeyHit(81) Then get=51;3
		If KeyHit(75) Then get=52;4
		If KeyHit(76) Then get=53;5
		If KeyHit(77) Then get=54;6
		If KeyHit(71) Then get=55;7
		If KeyHit(72) Then get=56;8
		If KeyHit(73) Then get=57;9
		If KeyHit(83) Then get=46;comma
		
		If KeyHit(43) Then get=92;backslash
		If KeyHit(53) Then get=47;slash
		
		ge$=Chr$(get)
		
		
;Exclude various keys
		If KeyHit(211) Then get=8
		If KeyHit(53) Then get=0
		If KeyHit(43) Then get=0
		If (KeyHit(197) Or KeyHit(199) Or KeyHit(200) Or KeyHit(201) ) Then get=0
		If (KeyHit(203) Or KeyHit(205) Or KeyHit(207) Or KeyHit(208) ) Then get=0
		If (KeyHit(209) Or KeyHit(210) Or KeyHit(181) Or KeyHit(15) ) Then get=0
		
		If get
			
			If get = 27 Then Menu_SkipType() ;Menu_Cancel();esc
			
			If gg>tl And get <> 8 Then Menu_SkipType() ; Menu_SkipType()
			
			
			If get = 8
				ad=-1
				Else ad=1
			EndIf
			
			gg=gg+ad
			
			If gg<0
				gg=0
				Menu_Cancel()
			;	Menu_SkipType()
			EndIf
			
			If get = 13 And geted$<>"" Then Exit
			If get = 13 And geted$="" Then Return
			
			geted$=Left$(geted$,gg)
			
			If get <>8	
				Color 215,218,227:Rect (tx+3)+(gg-1)*6,ty+3,6,10	
				Color 10,10,82:Rect (tx+3)+gg*6,ty+3,6,10
				
			Else		
				
				Color 10,10,82:Rect (tx+3)+gg*6,ty+3,6,10	
				Color 215,218,227:Rect (tx+3)+(gg+1)*6,ty+3,6,10
				
			EndIf
			
			If get<>8
				Text (tx+4)+(gg-1)*6,ty+1,ge$
				geted$=geted$+ge$
			EndIf
				
			
			
			
		EndIf;if get
		
		
		.SkipType
		If MouseDown(2)
			FlushMouse():FlushKeys();:Delay 150
			geted$=""
			Return geted$
			
		EndIf
		
		If MouseDown(1) And geted$<>""
			FlushMouse():FlushKeys();:Delay 150
			Return geted$
		EndIf
		
		If (geted$<>"") Then
			SetFont SystemFont
			Game\Path$	= "Stages/"+geted$+"/"
			Game\State = GAME_STATE_START
			PlaySound Sound_SelectLevel
			HidePointer()
			ChannelVolume (Game\MusicChn, 0.4)
			Game\StageSelecting = 0
		EndIf
		
		Flip
		get=0
		
		.Cancel
		Game\TypeStage = 0
		FlushMouse():FlushKeys();:Delay 150	
		If get=27 Then geted$=""
		Return geted$
		
	Wend
	
End Function			
;*************************************************************************
;~IDEal Editor Parameters:
;~C#Blitz3D