Type Transition
	Field startValue#
	Field endValue#
	Field startTime#
	Field endTime#
	Field cosine#
End Type

Function CreateTransition.Transition(startValue#,endValue#,mSecs#,cosine#=1.0)
	Local t.Transition=New Transition
	t\startTime=MilliSecs()
	t\endTime=t\startTime+mSecs
	t\startValue=startValue
	t\endValue=endValue
	t\cosine=cosine
	Return t.Transition
End Function

Function GetTransitionValue#(t.Transition)
	If t.Transition=Null Then Return -9999
	Local straightProportion#=(MilliSecs()-t\startTime)/(t\endTime-t\startTime)
	If CheckTransition(t.Transition)>=0 Then straightProportion=1.0
	Local cosineProportion#=(1-Cos(straightProportion*180))*.5
	Local finalProportion#=cosineProportion*t\cosine+straightProportion*(1.0-t\cosine)
	Return finalProportion*(t\endValue-t\startValue)+t\startValue
End Function

Function PurgeTransitions(age=0)
	Local t.Transition
	For t.Transition=Each Transition
		If MilliSecs()>(t\endTime+age) Then Delete t.Transition
	Next
End Function

Function CheckTransition(t.Transition)
	Return MilliSecs()-t\endTime
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D