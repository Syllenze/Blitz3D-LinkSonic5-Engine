Global xx =0
	
Global Player2Connected=1
Global Player2Character=1

Global MPStage

;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
;Player 2 Values
;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


Global Player2PositionX=0
Global Player2PositionY=0
Global Player2PositionZ=0

Global Player2Animation=0
Global Player2OldAnimation=0

Global Player2RotationX=0
Global Player2RotationY=0
Global Player2RotationZ=0

Global Player2ChatText$ = ""

Global Player2Name$ = ""
Global Player2Chatting = 0
Global Player2SentMessage = 0


;////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
;Player 1 Values
;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Global PlayerPositionX=0
Global PlayerPositionY=0
Global PlayerPositionZ=0

Global PlayerAnimation=0

Global PlayerRotationX=0
Global PlayerRotationY=0
Global PlayerRotationZ=0

Global PlayerChatText$ = ""

Global PlayerName$ = ""

Const kUp% = 200, KDown% = 208, KLeft% = 203, KRight% = 205, fps% = 40, KF1% = 59, KF11% = 87
Const kShift% = 42, KTab% = 15
Global send_freq% = 4, chat$
Global width% = 400, height% = 100
Global logging% = False
Global myname$
Global sendupdates% = True
Global DebugInfo% = 0
Global WindowCount% = 0
Global fullscreen%
Global Host%

Type pdata
	Field x#,y#,r#,name$,net_id%
	Field xvel#,yvel#,rvel#
End Type

Type Info
	Field txt$
End Type
;~IDEal Editor Parameters:
;~C#Blitz3D