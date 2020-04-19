

;=======================================================================================
;  "SUN FLARES"
;  by David Barlia (a.k.a. Barliesque)
;  david@barliesque.com
;
;  Please feel free to make use of this code as you wish.
;  If you do use it in a project, a little credit would be nice.
;  Also, if you should happen to make some exciting improvements,
;  don't hesitate to post your changes (or alternate flares image?)
;  to the code archives.
;
;  This code makes use of a modified version of "Sprite COntrol" by SyntaxError.
;  If you are already using "Sprite Control" in your project, and want to add
;  this code, you will probably have no problem changing to this modified version.
;  See "Sprite Control.bb" for further info.
;
;=======================================================================================
Include "_SourceCode\Systems\System_SyntaxError_Sprite_Control.bb"

;CameraClsMode camera,True,True

;ClearTextureFilters

;-------------------------
; Set up Sprite Control
; and our flares
;-------------------------
Global ViewX=GraphicsWidth(),ViewY=GraphicsHeight()
Global ViewAspect# = Float(ViewX)/Float(ViewY)

Global FlareRed, FlareGreen, FlareBlue
Global Flare[15]

;-------------------------
;  Load the Scene
;-------------------------
;Scene = LoadAnimMesh("Media\Scene.b3d")
;Sun = FindChild(Scene,"Sun")
;Sky = FindChild(Scene,"Sky")
;SkyTurn# = 0.0


;----------------------------------------------
;  Set PickModes...
;    Use 1 for flare sources
;    Use 2 for objects that can block the sun
;----------------------------------------------
;Restore PickSettings
;Repeat
;		Read ChildName$, PickMode
;		If ChildName<>"" Then EntityPickMode FindChild(scene,ChildName$),PickMode
;Until ChildName=""
;
;
;
;.PickSettings
;Data "Sun", 1
;Data "Landscape", 2
;Data "Stand 1 Upper", 2
;Data "Stand 1 Lower", 2
;;Data "Stand 1 Frame", 2
;Data "Stand 2 Upper", 2
;Data "Stand 2 Lower", 2
;;data "Stand 2 Frame", 2
;Data "Stand 3 Upper", 2
;Data "Stand 3 Lower", 2
;;data "Stand 3 Frame", 2
;Data "Stand 4 Upper", 2
;Data "Stand 4 Lower", 2
;;data "Stand 4 Frame", 2
;Data "", 0

;* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

;-----------------------------------------------------

Function ClampValue(Original#, low#, high#)
	If Original<low  Then Return low
	If Original>high Then Return high
	Return Original
End Function

;-----------------------------------------------------

Function UpdateFlare(cam_entity,source)

	CameraProject cam_entity,EntityX(source,True),EntityY(source,True),EntityZ(source,True)
	SourceX# = ProjectedX#()
	SourceY# = ProjectedY#()
	x# = SourceX/ViewX
	y# = SourceY/ViewY

	Text 40,0,"X,Y:  " + X + ", " + Y

	SeeSource = CameraPick(cam_entity,SourceX,SourceY)
	If ((SeeSource = source) Or (SeeSource = 0)) And EntityInView(source,cam_entity) And (x>0 And x<=1) And (y>0 And y<=1)
		
		; Set flare color
    	FlareRed   = 255
		FlareGreen = 255
		FlareBlue  = 255
      
		scale# = ViewX/800.0
		Restore FlareData
		Read TotalFlares		
		For f=1 To TotalFlares
			Read Distance, FlareSize, ColorInfluence#, Alpha#, Frame
			flare_x# = SourceX - (((x-0.5)*2.0)*Distance)
			flare_y# = SourceY - (((y-0.5)*2.0)*(Distance/ViewAspect))

			r = (ColorInfluence * FlareRed   + 255.0*(1.0-ColorInfluence))
			g = (ColorInfluence * FlareGreen + 255.0*(1.0-ColorInfluence))
			b = (ColorInfluence * FlareBlue  + 255.0*(1.0-ColorInfluence))
			EntityColor Flare[f], r,g,b



			FlareSize = FlareSize * scale * (x + y + (Cos(Distance*0.45)/2.0) + 0.5)
   		ResizeImage3D Flare[f],FlareSize,FlareSize

			If lowest#(x,y)<0.1 Then
				EntityAlpha Flare[f],Alpha * lowest#(x,y)/0.2
			Else
				EntityAlpha Flare[f],Alpha
			EndIf

			DrawImage3D Flare[f],flare_x,flare_y,Frame
 	  		ShowEntity Flare[f]
		Next
		
   Else
 	   Restore FlareData
 	   Read TotalFlares
 	   For f=1 To TotalFlares
 	  		HideEntity Flare[f]
	   Next

   EndIf

End Function



; FLARE DATA:   Distance, FlareSize, ColorInfluence#, Alpha#, Frame
;
; Distance       - Maximum offset from source
; FlareSize      - Maximum size of flare
; ColorInfluence - How strongly colour of source affects the flare's colour
; Alpha          - Maximum alpha level (0.0 to 1.0)
; Frame          - Frame of the lens flare texture (1 to 16)

.FlareData
Data 15		
Data -100, 400, 1.00, 0.40,  1  ;Red Crescent (50% to 0%)
Data  -95,  35, 0.80, 0.40,  2  ;Orange/Yellow Gradient (80% to 0%)
Data    0, 130, 0.60, 1.00,  3  ;Bright Flare Center (100%)
Data   95,  35, 0.35, 0.50,  4  ;Purple Disc (60%)
Data  150,  45, 0.50, 0.30,  5  ;Blue Disc (60%)
Data  120,  70, 0.60, 0.40,  6  ;Blue Gradient (50% to 20%)
Data  200,  20, 0.90, 0.40,  7  ;Orange Disc (80%)
Data  250,   8, 0.10, 1.00, 15  ;Sharp point (100%)
Data  280,  15, 0.15, 1.00,  8  ;Fuzzy Star (100%)
Data  345,  80, 0.90, 0.20,  7  ;Orange Disc (50%)
Data  390,  60, 0.80, 0.60,  7  ;Orange Disc (80%)
Data  395,  30, 0.40, 0.40,  9  ;Green Disc (80%)
Data  460, 100, 0.90, 0.40, 10  ;Orange Gradiant (90% to 0%)
Data  550, 160, 0.70, 0.50, 11  ;Yellow Ring (90%) with Green Gradient (60% to 0%)
Data  800, 350, 0.80, 0.30, 12  ;Rainbow Halo (outside to in:  Red,Orange/Yellow,Violet) (50%)

;-----------------------------------------------------------

Function SetupFlares(filename$)

	FirstFlare = LoadAnimImage3D(filename$,4,4,1)
	Restore FlareData
	Read TotalFlares
	For i=1 To TotalFlares
 		 Read Distance, FlareSize, ColorInfluence#, Alpha#, Frame

		 Flare[i] = CopyImage3D(FirstFlare)
		
		 ResizeImage3D Flare[i],FlareSize,FlareSize
		 EntityAlpha Flare[i],Alpha
		 EntityBlend Flare[i],3
		 EntityOrder Flare[i],-100-i
		 midhandle3D Flare[i]
		 EntityColor Flare[i],255,255,255
		 HideEntity Flare[i]
	Next
	FreeImage3D(FirstFlare)

End Function

;-----------------------------------------------------------

Function lowest#(val1#, val2#)

   If val1<val2 Then
		Return val1
   Else
      Return val2
	EndIf
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D