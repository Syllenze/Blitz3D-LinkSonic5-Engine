	; =========================================================================================================
	; Player_Action_Glide
	; =========================================================================================================
Function Player_Action_Glide(p.tPlayer, d.tDeltaTime)
	
	If (COMMON_GLIDEDESCENTIONRATE# > -1.2) Then
		COMMON_GLIDEDESCENTIONRATE# = COMMON_GLIDEDESCENTIONRATE# - (GlobalTimerFloat#*0.00025)
	Else
		COMMON_GLIDEDESCENTIONRATE# = -1.2
	EndIf
	
	Select Game\Stage\Properties\Hub
		Case 0
	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2.9
	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2.9
Case 1
	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*1.8
	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*1.8
End Select

	p\Motion\Speed\y# = COMMON_GLIDEDESCENTIONRATE#
	
	If (ChannelPlaying (Channel_Glide) = False) Then Channel_Glide = PlaySound (Sound_Glide)
	
	If (Input\Hold\ActionA <> 1) Then
		p\Action = ACTION_FALL
	EndIf
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D