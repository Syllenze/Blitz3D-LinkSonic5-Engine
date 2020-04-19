;--------------------------------------------------------------------------
;I normally keep this as an include but it makes it easier if I add it here
;--------------------------------------------------------------------------

Const trail_update=1				; trail update frequency (lower number updates more often)
Const maxVerts=999					; maximum number of verts per trail object (polygons * 2)

Type trail
	Field id,num,brush,surface,Mesh
	Field tdx#[maxVerts], tdy#[maxVerts], tdz#[maxVerts]
	Field update%,alpha#, alpha1#
	Field point1, point2
	
End Type

Global TrailPivot = CreatePivot()
Global TrailBlend
Global TrailTime#
Global TrailTime2


;-----------------
;update all trails
;-----------------
Function UpdateTrails()
	
	Local x#,y#,z#
	
;loop through all trails
	For t.trail = Each trail
		TrailTime# = TrailTime# - (GlobalTimerFloat#*0.00016)
		If (TrailTime# < 0) Then TrailTime# = 0
		
	; Move the trail pieces along.
		t\update=t\update+1 : If t\update=trail_update Then t\update=0
		
	;update				
		If t\update=0
			For i=2 To CountVertices(t\surface)-1
				t\tdx[i] = (VertexX(t\surface,i-2) - VertexX(t\surface,i))/trail_update
				t\tdy[i] = (VertexY(t\surface,i-2) - VertexY(t\surface,i))/trail_update
				t\tdz[i] = (VertexZ(t\surface,i-2) - VertexZ(t\surface,i))/trail_update
			;	t\alpha=1.0-Float(i)/30
				t\alpha#=TrailTime#
			;	If t\alpha#<0 Then t\alpha#=0 
			;	VertexColor t\Surface,i,255,255,255,TrailTime#
				EntityAlpha(t\mesh, TrailTime#)
			;	BrushAlpha(t\brush, TrailTime#)
			;	VertexAlpha(t\Surface, TrailTime#)
				VertexAlpha t\surface, TrailTime#
				
			Next
		End If
		
		For i=2 To CountVertices(t\surface)-1
			VertexCoords(t\surface,i,VertexX(t\surface,i)+t\tdx[i],VertexY(t\surface,i)+t\tdy[i],VertexZ(t\surface,i)+t\tdz[i])
		Next
		
	;position the first two verts at the back of the ship
		VertexCoords(t\surface,0,EntityX(t\point1,1),EntityY(t\point1,1),EntityZ(t\point1,1))
		VertexCoords(t\surface,1,EntityX(t\point2,1),EntityY(t\point2,1),EntityZ(t\point2,1))
		
				;		If (t\mesh<>0) Then
		
		
	;	EntityAlpha(t\mesh, TrailTime#)
		;	EndIf
		
	Next
	
End Function


;---------------------
;create a trail object
;---------------------

Function CreateTrail(point1,point2,polys=20,startalpha#=1.0,num#=1,trailtype=1,id=1)
	
	Local x#,y#,z#
	
	
	t.trail=New trail
	
	;create mesh and set properties
	t\Mesh = CreateMesh()
    t\id=id
	t\brush = CreateBrush()
	If (TrailTex <> 0) Then BrushTexture(t\Brush,TrailTex)
	;t\brush = LoadBrush(TrailTex)
	BrushBlend t\brush,3
	BrushFX t\brush,2+16
	EntityBlend t\mesh,3
	EntityFX t\mesh,2+16
	t\surface = CreateSurface(t\mesh, t\brush)
	;t\surface = CreateSurface(t\mesh)
	t\alpha=0.5
	
	
	
	;check there are two trail strat points
	If point1=False Then RuntimeError "must specify 'point1' for one side of the trail" Else t\point1=point1
	If point2=False Then RuntimeError "must specify 'point2' for one side of the trail" Else t\point2=point2
	
	;mid pount between two trial objects
	x=(EntityX(t\point1,1)+EntityX(t\point2,1))/2
	y=(EntityY(t\point1,1)+EntityY(t\point2,1))/2
	z=(EntityZ(t\point1,1)+EntityZ(t\point2,1))/2
	
	;create polygons
	For i=0 To polys
		AddVertex t\surface,x,y,z,Float(i)/Float(polys),1,0
		AddVertex t\surface,x,y,z,Float(i)/Float(polys),0,0
		VertexNormal t\surface,i,0,-1,0
		VertexNormal t\surface,i+1,0,-1,0
		
		If i>0
			AddTriangle t\surface,i*2,i*2-1,i*2-2
			AddTriangle t\surface,i*2,i*2+1,i*2-1
		End If
		
	;	EntityTexture(t\mesh,TrailTex)
		
	Next
	
End Function

;--------------------------------
;free trail and delete trail type
;--------------------------------
Function free_trail(id=1)
	
	For t.trail=Each trail
		If t\id=id
			FreeEntity t\mesh
;			FreeEntity t\id
			Delete t
		EndIf
	Next
	
	Return True
	
End Function

;--------------------------------
;free trail and delete trail type
;--------------------------------
Function Hide_trail(id=1)
	
	For t.trail=Each trail
		If t\id=id
			HideEntity t\mesh
;			FreeEntity t\id
			;Delete t
		EndIf
	Next
	
End Function

;--------------------------------
;free trail and delete trail type
;--------------------------------
Function Show_trail(id=1)
	
	For t.trail=Each trail
		If t\id=id
			ShowEntity t\mesh
;			FreeEntity t\id
			;Delete t
		EndIf
	Next
	
End Function


;----------------------------------------------------------------------
;These are just handy functions that have nothing to do with the trails
;----------------------------------------------------------------------


Function CreateLS5Trail(Tp=1)
	
	For p.tPlayer = Each tPlayer
	e1=CreatePivot(p\Objects\PPivot)
	e2=CreatePivot(p\Objects\PPivot)
	e3=CreatePivot(p\Objects\PPivot)
	e4=CreatePivot(p\Objects\PPivot)
	e5=CreatePivot(p\Objects\PPivot)
	e6=CreatePivot(p\Objects\PPivot)
	e7=CreatePivot(p\Objects\PPivot)
	e8=CreatePivot(p\Objects\PPivot)
	e9=CreatePivot(p\Objects\PPivot)
	e10=CreatePivot(p\Objects\PPivot)
	e11=CreatePivot(p\Objects\PPivot)
	e12=CreatePivot(p\Objects\PPivot)
	e13=CreatePivot(p\Objects\PPivot)
	e14=CreatePivot(p\Objects\PPivot)
	e15=CreatePivot(p\Objects\PPivot)
	e16=CreatePivot(p\Objects\PPivot)
	e17=CreatePivot(p\Objects\PPivot)
	e18=CreatePivot(p\Objects\PPivot)
	e19=CreatePivot(p\Objects\PPivot)
	e20=CreatePivot(p\Objects\PPivot)
	CreateTrail(e1,e2,20,1.0,1,Tp)
	CreateTrail(e2,e3,20,1.0,1,Tp)
	CreateTrail(e3,e4,20,1.0,1,Tp)
	CreateTrail(e4,e5,20,1.0,1,Tp)
	CreateTrail(e5,e6,20,1.0,1,Tp)
	CreateTrail(e6,e7,20,1.0,1,Tp)
	CreateTrail(e7,e8,20,1.0,1,Tp)
	CreateTrail(e8,e9,20,1.0,1,Tp)
	CreateTrail(e9,e10,20,1.0,1,Tp)
	CreateTrail(e10,e11,20,1.0,1,Tp)
	CreateTrail(e11,e12,20,1.0,1,Tp)
	CreateTrail(e12,e13,20,1.0,1,Tp)
	CreateTrail(e13,e14,20,1.0,1,Tp)
	CreateTrail(e14,e15,20,1.0,1,Tp)
	CreateTrail(e15,e16,20,1.0,1,Tp)
	CreateTrail(e16,e17,20,1.0,1,Tp)
	CreateTrail(e17,e18,20,1.0,1,Tp)
	CreateTrail(e18,e19,20,1.0,1,Tp)
	CreateTrail(e19,e20,20,1.0,1,Tp)
	CreateTrail(e20,e1,20,1.0,1,Tp)
	EntityParent(e1,p\Objects\PPivot,0)
	EntityParent(e2,p\Objects\PPivot,0)
	EntityParent(e3,p\Objects\PPivot,0)
	EntityParent(e4,p\Objects\PPivot,0)
	EntityParent(e5,p\Objects\PPivot,0)
	EntityParent(e6,p\Objects\PPivot,0)
	EntityParent(e7,p\Objects\PPivot,0)
	EntityParent(e8,p\Objects\PPivot,0)
	EntityParent(e9,p\Objects\PPivot,0)
	EntityParent(e10,p\Objects\PPivot,0)
	EntityParent(e11,p\Objects\PPivot,0)
	EntityParent(e12,p\Objects\PPivot,0)
	EntityParent(e13,p\Objects\PPivot,0)
	EntityParent(e14,p\Objects\PPivot,0)
	EntityParent(e15,p\Objects\PPivot,0)
	EntityParent(e16,p\Objects\PPivot,0)
	EntityParent(e17,p\Objects\PPivot,0)
	EntityParent(e18,p\Objects\PPivot,0)
	EntityParent(e19,p\Objects\PPivot,0)
	EntityParent(e20,p\Objects\PPivot,0)
	MoveEntity e1,-3,0,0 : MoveEntity e2,-2.9,0.9,0 : MoveEntity e3,-2.4,1.8,0 : MoveEntity e4,-1.8,2.4,0 : MoveEntity e5,-0.9,2.9,0 : MoveEntity e6,0,3,0 : MoveEntity e7,0.9,2.9,0 : MoveEntity e8,1.8,2.4,0 : MoveEntity e9,2.4,1.8,0 : MoveEntity e10,2.9,0.9,0 : MoveEntity e11,3,0,0 : MoveEntity e12,2.9,-0.9,0 : MoveEntity e13,2.4,-1.8,0 : MoveEntity e14,1.8,-2.4,0 : MoveEntity e15,0.9,-2.9,0 : MoveEntity e16,0,-3,0 : MoveEntity e17,-0.9,-2.9,0 : MoveEntity e18,-1.8,-2.4,0 : MoveEntity e19,-2.4,-1.8,0 : MoveEntity e20,-2.9,-0.9,0
	Next
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D