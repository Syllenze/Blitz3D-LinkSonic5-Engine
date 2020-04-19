Global VERT_ITERATIONS	= 5 ; Number of times to correct constraints. Increasing will make constrains more springy, decreasing will make them more jello-like. Maximum recomended iterations: 10
Global VERT_GRAVITY#	= 0.1
Global VERT_FRICTION#	= 0.5
Global VERT_STIFFNESS#	= 0.1 ; This too has a significant effect on the constraint's springy-ness. It is better to modify this value, than the iterations value because slow down will occor with too many iterations.
Global VERT_STRETCH#	= 1.0 ; Multiplier. Ammount to stretch the length of the contraints. Used only during creation of the constraint.

Type tPointmass
	Field x#
	Field y#
	Field z#
	Field ox#
	Field oy#
	Field oz#
End Type

Type tConstraint
	Field p1.tPointmass
	Field p2.tPointmass
	Field length#
End Type

; --------------------------
; updatepointmasses()
; --------------------------
Function Vert_UpdatePointmasses()
	For p.tPointmass = Each tPointmass
		
		Local dx# = p\x - p\ox	;gets the velocity
		Local dy# = p\y - p\oy + VERT_GRAVITY	;added gravity here
		
		p\ox = p\x	;updates ox and oy
		p\oy = p\y
		
		p\x = p\x + dx#	;adds velocity to get new x and new y
		p\y = p\y + dy#
		
	;	If p\y > GraphicsHeight() Then
	;		p\y = GraphicsHeight()
	;		dx = p\x-p\ox
	;		dx = dx * VERT_FRICTION
	;		p\ox = p\ox +dx	;simulates friction
	;	EndIf
		
	Next
End Function

; ---------------------
; createpointmass
; ---------------------
Function Vert_CreatePointmass.tPointmass(x#,y#,vx#,vy#)	; x and y are coords for the verlet. vx and vy are velocity values for the verlet
	p.tPointmass = New tPointmass
	p\x = x
	p\y = y
	p\ox = x-vx	;gives the particle a starting velocity
	p\oy = y-vy

	Return p.tPointmass
End Function

; ---------------------------
; updateconstraints()
; ---------------------------
Function Vert_UpdateConstraints()
	For cnt = 0 To VERT_ITERATIONS-1	;this is necessary with many constraints to solve them correctly
		For c.tConstraint = Each tConstraint
			dist# = Sqr((c\p1\x-c\p2\x)^2 + (c\p1\y-c\p2\y)^2)	;distance formula
			
			diff# = dist#-c\length#	;shows the margin of error the update loop has created so it can be corrected
			
			dx# = c\p1\x-c\p2\x	;difference between x's and y's
			dy# = c\p1\y-c\p2\y
			
			If c\length > 0 Then	;prevents a divided by 0 error that may occur
				diff = diff / c\length
			Else
				diff = 0
			EndIf
			
			dx = dx * VERT_STIFFNESS
			dy = dy * VERT_STIFFNESS
			
			c\p1\x = c\p1\x - (diff*dx)
			c\p1\y = c\p1\y - (diff*dy)
			
			c\p2\x = c\p2\x + (diff*dx)
			c\p2\y = c\p2\y + (diff*dy)
		Next
	Next
End Function

; -----------------------------
; createconstraint
; -----------------------------
Function Vert_CreateConstraint.tConstraint(p1.tPointmass,p2.tPointmass)
	c.tConstraint = New tConstraint
	c\p1.tPointmass = p1.tPointmass
	c\p2.tPointmass = p2.tPointmass
	c\length# = Sqr((c\p1\x-c\p2\x)^2 + (c\p1\y-c\p2\y)^2) * VERT_STRETCH
	Return c.tConstraint
End Function