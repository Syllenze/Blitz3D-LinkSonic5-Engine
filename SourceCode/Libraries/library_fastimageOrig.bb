;»нклюд библиотеки FastImage (верси€ 1.4x Freeware) дл€ Ѕлиц3ƒ (верси€ 1.98)
;(ц) 2007 либа сделана MixailV aka Monster^Sage [monster-sage@mail.ru]
;ѕри использовании библиотеки в своих работах об€зательно упоминание автора MixailV.
;«апрещено размещать и встраивать FastImage.dll внутри других DLL и им подобных.
;Ќе желательно встраивать FastImage.dll в EXE файлы.



;список доступных функций смотрите в хелпе и декларационном файле FastImage.decls



;данные флаги - константы об€зательно пригод€тс€
;подробнее про них читайте в хелпе

;CreateImageEx Flags (при создании картинки)
Const FI_AUTOFLAGS = -1
Const FI_NONE = 0
Const FI_MIDHANDLE = 1
Const FI_FILTEREDIMAGE = 2
Const FI_FILTERED = 2

;SetBlend Flags (при использовании бленда (смешивани€)
Const FI_SOLIDBLEND = 0
Const FI_ALPHABLEND = 1
Const FI_LIGHTBLEND = 2
Const FI_SHADEBLEND = 3
Const FI_MASKBLEND = 4
Const FI_MASKBLEND2 = 5
Const FI_INVALPHABLEND = 6

;ImageFonts Flags
Const FI_SMOOTHFONT=1

;DrawImagePart Wrap Flags
Const FI_NOWRAP = 0
Const FI_WRAPU = 1
Const FI_MIRRORU = 2
Const FI_WRAPV = 4
Const FI_MORRORV = 8
Const FI_WRAPUV = 5
Const FI_MIRRORUV = 10

;DrawPoly consts
Const FI_TRIANGLEFAN = 0
Const FI_TRIANGLESTRIP = 1
Const FI_LINESTRIP = 2
Const FI_COLOROVERLAY = 1

;D3D consts
Const D3DBLEND_ZERO = 1
Const D3DBLEND_ONE = 2
Const D3DBLEND_SRCCOLOR = 3
Const D3DBLEND_INVSRCCOLOR = 4
Const D3DBLEND_SRCALPHA = 5
Const D3DBLEND_INVSRCALPHA = 6
Const D3DBLEND_DESTALPHA = 7
Const D3DBLEND_INVDESTALPHA = 8
Const D3DBLEND_DESTCOLOR = 9
Const D3DBLEND_INVDESTCOLOR = 10
Const D3DBLEND_SRCALPHASAT = 11
Const D3DBLEND_BOTHSRCALPHA = 12
Const D3DBLEND_BOTHINVSRCALPHA = 13

;набор всех свойств библиотеки
Type FI_PropertyType
	Field Blend%
	Field Alpha#, Red%, Green%, Blue%
	Field ColorVertex0%, ColorVertex1%, ColorVertex2%, ColorVertex3%                                 	
	Field Rotation#, ScaleX#, ScaleY#
	Field MatrixXX#, MatrixXY#, MatrixYX#, MatrixYY#
	Field HandleX%, HandleY%
	Field OriginX%, OriginY%
	Field AutoHandle%, AutoFlags%
	Field LineWidth#
	Field ViewportX%, ViewportY%, ViewportWidth%, ViewportHeight%
	Field MipLevel%
	Field ProjScaleX#, ProjScaleY#, ProjRotation#
	Field ProjOriginX%, ProjOriginY%
	Field ProjHandleX%, ProjHandleY%
	Field Reserved%
End Type

;используем глобальную переменную FI_Property дл€ получени€ конкретного свойства из набора
;в программе получать весь набор свойств  нужно так:  GetProperty FI_Property
Global FI_Property.FI_PropertyType = New FI_PropertyType



;набор свойств любой картинки, созданной библиотекой (командой CreateImageEx)
Type FI_ImagePropertyType
	Field HandleX%
	Field HandleY%
	Field Width%
	Field Height%
	Field Frames%
	Field Flags%
End Type

;используем глобальную переменную FI_ImageProperty дл€ получени€ конкретного свойства
;из набора свойств любой картинки, созданной библиотекой (командой CreateImageEx)
;в программе получать весь набор свойств  нужно так:  GetImageProperty your_image, FI_ImageProperty
Global FI_ImageProperty.FI_ImagePropertyType = New FI_ImagePropertyType



Type FI_FontPropertyType
	Field Width
	Field Height
	Field FirstChar
	Field Kerning
	Field Chars[256]
End Type
Global FI_FontProperty.FI_FontPropertyType = New FI_FontPropertyType



;функци€ универсального "ручного" бленда (смешивани€ цветов)
;создана дл€ начинающих, еще не освоивших DirectX7
;значени€ src и dest должны быть в пределах от 1 до 10
Function SetCustomBlend(src%, dest%)
	SetCustomState 15,0				;DX7  SetRenderState ( D3DRENDERSTATE_AlphaTestEnable, False )
	SetCustomState 27,1				;DX7  SetRenderState ( D3DRENDERSTATE_AlphaBlendEnable, True )
	SetCustomState 19,src			;DX7  SetRenderState ( D3DRENDERSTATE_SrcBlend, src )
	SetCustomState 20,dest			;DX7  SetRenderState ( D3DRENDERSTATE_DestBlend, dest )
End Function



;вспомогательные функции, позвол€ющие не задавать каждый раз параметры с дефолтными значени€ми
Function CreateImageEx% (texure%, width%, height%, flags%=FI_AUTOFLAGS)
	Return CreateImageEx_(texure, width, height, flags)
End Function
Function DrawImageEx% (image%, x%, y%, frame%=0)
	Return DrawImageEx_(image, x, y, frame)
End Function
;Function ScaleImageEx% (image%, xscale%, yscale%)
;	Return ScaleImageEx_(image, xscale, yscale)
;End Function
Function DrawImageRectEx% (image%, x%, y%, width%, height%, frame%=0)
	Return DrawImageRectEx_(image, x, y, width, height, frame)
End Function
Function DrawImagePart% (image%, x%, y%, width%, height%, partX%=0, partY%=0, partWidth%=0, partHeight%=0, frame%=0, wrap%=FI_NOWRAP)
	Return DrawImagePart_(image, x, y, width, height, partX, partY, partWidth, partHeight, frame, wrap)
End Function
Function DrawPoly% (x%, y%, bank%, image%=0, frame%=0, Color%=FI_NONE)
	Return DrawPoly_(x, y, bank, image, frame, Color)
End Function
Function DrawRect% (x%, y%, width%, height%, fill%=1)
	DrawRect_ x, y, width, height, fill
End Function
Function DrawRectSimple% (x%, y%, width%, height%, fill%=1)
	DrawRectSimple_ x, y, width, height, fill
End Function

Function LoadImageFont% (filename$, flags%=FI_SMOOTHFONT)
	Local f, i, l$, r$, AnimTexture$, AnimTextureFlags, FrameWidth, FrameHeight, FrameCount, Texture, Image

	filename=Replace (filename,"/", "\")
	f = ReadFile(filename)
	If f=0 Then Return 0

	FI_FontProperty\Width=0
	FI_FontProperty\Height=0
	FI_FontProperty\FirstChar=0
	FI_FontProperty\Kerning=0
	For i=0 To 255
		FI_FontProperty\Chars[i]=0
	Next
	AnimTextureFlags=4

	While Not Eof(f) 
		l=Trim(ReadLine(f))
		i=Instr(l,"=",1)
		If Len(l)>0 And Left(l,1)<>";" And i>0 Then
			r=Trim(Right(l,Len(l)-i))
			l=Upper(Trim(Left(l,i-1)))
			Select l
				Case "ANIMTEXTURE"
					AnimTexture=r
				Case "ANIMTEXTUREFLAGS"
					AnimTextureFlags=Int(r)
				Case "FRAMEWIDTH"
					FrameWidth=Int(r)
				Case "FRAMEHEIGHT"
					FrameHeight=Int(r)
				Case "FRAMECOUNT"
					FrameCount=Int(r)
				Case "WIDTH"
					FI_FontProperty\Width=Int(r)
				Case "HEIGHT"
					FI_FontProperty\Height=Int(r)
				Case "FIRSTCHAR"
					FI_FontProperty\FirstChar=Int(r)
				Case "KERNING"
					FI_FontProperty\Kerning=Int(r)				
				Default
					If Int(l)>=0 And Int(l)<=255 Then
						FI_FontProperty\Chars[Int(l)]=Int(r)
					EndIf
			End Select
		EndIf
	Wend
	CloseFile f

	If Len(AnimTexture)>0 And FrameWidth>0 And FrameHeight>0 And FrameCount>0 Then
		If FI_FontProperty\Width=0 Then FI_FontProperty\Width=FrameWidth
		If FI_FontProperty\Height=0 Then FI_FontProperty\Height=FrameHeight
		If FrameCount>256 Then FrameCount=256
		For i=0 To (FrameCount-1)
			If FI_FontProperty\Chars[i]=0 Then FI_FontProperty\Chars[i]=FI_FontProperty\Width
		Next
		f=1
		Repeat
			i=Instr(filename,"\",f)
			If i<>0 Then f=i+1
		Until i=0
		Texture = LoadAnimTexture( Left(filename,f-1)+AnimTexture, (AnimTextureFlags And $33F) Or $139, FrameWidth, FrameHeight, 0, FrameCount)
		If flags=FI_SMOOTHFONT Then
			flags=FI_FILTEREDIMAGE
		Else
			flags=FI_NONE
		EndIf
		Image = CreateImageEx_( Texture, FrameWidth, FrameHeight, flags )
	;	Return CreateImageFont( Image, FI_FontProperty )
		Return CreateImageFont( FI_FontProperty )
	Else 
		Return 0
	EndIf
End Function

;Function DrawText% (txt$, x%, y%, centerX%=0, centerY%=0)
;	Return DrawText_(txt, x, y, centerX, centerY)
;End Function

Function DrawText% (txt$, x#, y#, centerX%=0, centerY%=0, maxWidth%=10000)
	Return DrawText_(txt, x, y, centerX, centerY, maxWidth)
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D