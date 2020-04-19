Dim work$(1,1)

Function wrapText%(x%, y%, msg$, wide%, fr%, fg%, fb%, br%, bg%, bb%, center% = 0)
;=======================================================================
; This function wraps text inside of a rounded rectangle with an option to
; center the text.
;=======================================================================
; x,y = upperleft coordinates of the rounded rectangle
;
; msg$ = text string that you wish to place in rounded rectangle
;
; wide = maximum width of button in pixels
;
; fr, fg, fb = text color (RGB triplet)
;
; br, bg, bb = button color (RGB triplet)
;
; center = when 'center' = 1 then each line of text is centered
;		   the default setting is zero. (no centering)
;=======================================================================
	Local rad%, initX%, initY%, txtHigh%, count%, nxtWord$, flag%, txtCenter%
	Local tLine$, temp$, lineCount%, boxWide%, boxHigh%, pad%, tLen%
	
	Dim work$(50,1)
	pad = 10 ;the padding can be adjusted to suit your particular needs
	rad = 9  ;radius of rounded corner - values 2 To 26 recommended
	diam = rad + rad ;diameter of circles
	initX = x
	initY = y
	txtWide = wide - pad*2
	txtHigh = StringHeight(msg$) ;height of current font in pixels
	y = y + pad
	Repeat
		count = count + 1
		nxtWord$ = Word$(msg$, count, " ")
		tLine$ = temp$
		temp$ = temp$ + nxtWord$ + " "
		If StringWidth(temp$) >= txtWide Then
			tLine$ = Trim(tLine$)
			lineCount = lineCount+1
			work$(lineCount,0) = Str(x)+" "+Str(y)
			work$(lineCount,1) = tLine$
			y = y + txtHigh
			temp$ = ""
			count = count-1
		End If
	Until nxtWord$ = ""
	
	tLine$ = Trim(tLine$)
	If work$(lineCount,1) <> tLine$ Then
		lineCount = lineCount + 1
		work$(lineCount,0) = Str(x)+" "+Str(y)
		work$(lineCount,1) = tLine$
	End If
	
	boxWide = wide
	boxHigh = lineCount * txtHigh + pad*2
	Color br, bg, bb
	;draw the filled circles To make rounded corners
	Oval initX, initY, diam, diam, True
	Oval initX, initY+boxHigh-diam-1, diam, diam, True
	Oval initX+boxWide-diam-1, initY, diam, diam, True
	Oval initX+boxWide-diam-1, initY+boxHigh-diam-1, diam, diam, True
	;fill in the space between circles using filled boxes
	Rect initX, initY+rad, boxWide-1, boxHigh-diam-1, True
	Rect initX+rad, initY, boxWide-diam-1, boxHigh-1, True
	Color fr, fg, fb
	For i = 1 To lineCount
		x = Word$(work$(i,0),1)
		y = Word$(work$(i,0),2)
		If center = 1 Then
			tLen = StringWidth(work$(i,1))
			txtCenter = (boxWide - tLen) Shr 1
			x = x + txtCenter
		Else 
			x = x + pad
		End If
		Text x, y, work$(i,1)
	Next
End Function

Function Word$(string2Chk$, n, delimiter$=" ")
	;initialize local variables
	Local count%, findDelimiter%, position%, current$
	
	count = 0
	findDelimiter = 0
	position = 1
	;current$ = ""
	
	;'n' must be greater than zero
	;otherwise exit function and return null string
	If n > 0 Then
		;strip leading and trailing spaces
		string2Chk$  = Trim(string2Chk$)
		;find the word(s)
		Repeat
			;first check if the delimiter occurs in string2Chk$
			findDelimiter% = Instr(string2Chk$,delimiter$,position)
			If findDelimiter <> 0 Then
				;extract current word in string2Chk$
				current$ = Mid$(string2Chk$,position,findDelimiter-position)
				;word extracted; increment counter
				count = count + 1
				;update the start position of the next pass
				position = findDelimiter + 1
				;if counter is same as n then exit loop
				If count = n Then findDelimiter = 0
			End If
		Until findDelimiter = 0
		;Special Case: only one word and no delimiter(s) or last word in string2Chk$
		If (count < n) And (position <= Len(string2Chk$)) Then
			current$ = Mid$(string2Chk$,position, Len(string2Chk$) - position+1)
			count = count + 1
			;looking for word that is beyond length of string2Chk$
			If count < n Then current$ = ""
		End If
	End If
	Return current$
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D