Global SoundArrayEnd

;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
;	tSound type					;
;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
Type tSound
	Field sound
	Field channel
	Field name$
	Field frame
	Field anim
	Field playing
End Type

;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
;	SoundLoad function			;
;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
; New function for loading a sound is required.
Function SoundLoad.tSound(file$)
	q.tSound = New tSound
	q\sound = LoadSound(file$)
	q\channel = 0
	q\name$ = file$
	
;	If (useanim <> 0) Then q\frame# = frame#
	
	Return q
End Function

Function SoundLoad1.tSound(file$, anim, frame)
	q.tSound = New tSound
	q\sound = LoadSound(file$)
	q\channel = 0
	q\name$ = file$
	q\anim = anim
	q\frame = frame
	
;	If (useanim <> 0) Then q\frame# = frame#
	
	Return q
End Function

;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
;	SoundPlay function			;
;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
; New function to play the sound is optional...
; You can also play the sound using PlaySound(Sounds[0]\sound)
Function SoundPlay(q.tSound, volume#=1.0)
	If (q<>Null) Then
	;	For i = 0 To 255
		If q\sound <> 0 Then
			q\channel=PlaySound(q\sound)
			ChannelVolume(q\channel, volume#)
	;Next
		End If
	EndIf
End Function

; Create the main array of sounds
Global Sounds.tSound[255]
Global Sounds1.tSound[255]

Function LoadXMLSounds()
	
;	S1 = xmlNodeFind("1", RootChildNode)
;	If (S1 <> 0) Then
;		file$ = xmlNodeAttributeValueGet(S1, "filename")
;		p\AnimTrig\a1 = xmlNodeAttributeValueGet(S1, "animation")
;		frame = xmlNodeAttributeValueGet(S1, "frame")
;	EndIf
	
;	S2 = xmlNodeFind("2", RootChildNode)
;	If (S2 <> 0) Then
;		file$ = xmlNodeAttributeValueGet(S2, "filename")
;		p\AnimTrig\a2 = xmlNodeAttributeValueGet(S2, "animation")
;		frame = xmlNodeAttributeValueGet(S2, "frame")
;	EndIf
	
End Function


Function UpdateLoadedSounds()
	
		
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D