Dim ID3_Generes$(126) : ; Include "ID3.bb"

Const BASSTRUE = 1    
Const BASSFALSE = 0  
Const BASS_OK = 0                
Const BASS_ERROR_MEM = 1        
Const BASS_ERROR_FILEOPEN = 2    
Const BASS_ERROR_DRIVER = 3      
Const BASS_ERROR_BUFLOST = 4    
Const BASS_ERROR_HANDLE = 5      
Const BASS_ERROR_FORMAT = 6      
Const BASS_ERROR_POSITION = 7    
Const BASS_ERROR_INIT = 8        
Const BASS_ERROR_START = 9      
Const BASS_ERROR_INITCD = 10    
Const BASS_ERROR_CDINIT = 11    
Const BASS_ERROR_NOCD = 12      
Const BASS_ERROR_CDTRACK = 13    
Const BASS_ERROR_ALREADY = 14 
Const BASS_ERROR_CDVOL = 15      
Const BASS_ERROR_NOPAUSE = 16    
Const BASS_ERROR_NOTAUDIO = 17  
Const BASS_ERROR_NOCHAN = 18    
Const BASS_ERROR_ILLTYPE = 19    
Const BASS_ERROR_ILLPARAM = 20  
Const BASS_ERROR_NO3D = 21      
Const BASS_ERROR_NOEAX = 22      
Const BASS_ERROR_DEVICE = 23    
Const BASS_ERROR_NOPLAY = 24    
Const BASS_ERROR_FREQ = 25      
Const BASS_ERROR_NOTFILE = 27    
Const BASS_ERROR_NOHW = 29      
Const BASS_ERROR_EMPTY = 31      
Const BASS_ERROR_NONET = 32      
Const BASS_ERROR_CREATE = 33    
Const BASS_ERROR_NOFX = 34      
Const BASS_ERROR_PLAYING = 35    
Const BASS_ERROR_NOTAVAIL = 37  
Const BASS_ERROR_DECODE = 38    
Const BASS_ERROR_DX = 39        
Const BASS_ERROR_TIMEOUT = 40    
Const BASS_ERROR_FILEFORM = 41  
Const BASS_ERROR_SPEAKER = 42    
Const BASS_ERROR_UNKNOWN = -1    
Const BASS_DEVICE_8BITS = 1      
Const BASS_DEVICE_MONO = 2      
Const BASS_DEVICE_3D = 4        
Const BASS_DEVICE_LEAVEVOL = 32 
Const BASS_DEVICE_NOTHREAD = 128 
Const BASS_DEVICE_LATENCY = 256 
Const BASS_DEVICE_VOL1000 = 512 
Const BASS_DEVICE_FLOATDSP = 1024 
Const BASS_DEVICE_SPEAKERS = 2048 
Const DSCAPS_CONTINUOUSRATE = 16 
Const DSCAPS_EMULDRIVER = 32 
Const DSCAPS_CERTIFIED = 64 
Const DSCAPS_SECONDARYMONO = 256    
Const DSCAPS_SECONDARYSTEREO = 512  
Const DSCAPS_SECONDARY8BIT = 1024    
Const DSCAPS_SECONDARY16BIT = 2048  
Const DSCCAPS_EMULDRIVER = DSCAPS_EMULDRIVER 
Const DSCCAPS_CERTIFIED = DSCAPS_CERTIFIED 
Const WAVE_FORMAT_1M08 = $1          
Const WAVE_FORMAT_1S08 = $2          
Const WAVE_FORMAT_1M16 = $4          
Const WAVE_FORMAT_1S16 = $8          
Const WAVE_FORMAT_2M08 = $10          
Const WAVE_FORMAT_2S08 = $20          
Const WAVE_FORMAT_2M16 = $40          
Const WAVE_FORMAT_2S16 = $80          
Const WAVE_FORMAT_4M08 = $100        
Const WAVE_FORMAT_4S08 = $200        
Const WAVE_FORMAT_4M16 = $400        
Const WAVE_FORMAT_4S16 = $800        
Const BASS_MUSIC_RAMP = 1        
Const BASS_MUSIC_RAMPS = 2      
Const BASS_MUSIC_LOOP = 4        
Const BASS_MUSIC_FT2MOD = 16    
Const BASS_MUSIC_PT1MOD = 32    
Const BASS_MUSIC_MONO = 64      
Const BASS_MUSIC_3D = 128        
Const BASS_MUSIC_POSRESET = 256 
Const BASS_MUSIC_SURROUND = 512 
Const BASS_MUSIC_SURROUND2 = 1024 
Const BASS_MUSIC_STOPBACK = 2048 
Const BASS_MUSIC_FX = 4096      
Const BASS_MUSIC_CALCLEN = 8192 
Const BASS_MUSIC_NONINTER = 16384 
Const BASS_MUSIC_FLOAT = $10000    
Const BASS_MUSIC_DECODE = $200000 
Const BASS_MUSIC_NOSAMPLE = $400000 
Const BASS_SAMPLE_8BITS = 1          
Const BASS_SAMPLE_FLOAT = 256        
Const BASS_SAMPLE_MONO = 2            
Const BASS_SAMPLE_LOOP = 4            
Const BASS_SAMPLE_3D = 8              
Const BASS_SAMPLE_SOFTWARE = 16      
Const BASS_SAMPLE_MUTEMAX = 32        
Const BASS_SAMPLE_VAM = 64            
Const BASS_SAMPLE_FX = 128            
Const BASS_SAMPLE_OVER_VOL = 65536    
Const BASS_SAMPLE_OVER_POS = 131072  
Const BASS_SAMPLE_OVER_DIST = 196608 
Const BASS_MP3_SETPOS = 131072        
Const BASS_STREAM_AUTOFREE = 262144  
Const BASS_STREAM_RESTRATE = 524288  
Const BASS_STREAM_BLOCK = 1048576    
Const BASS_STREAM_DECODE = $200000  
Const BASS_STREAM_META = $400000    
Const BASS_STREAM_FILEPROC = $800000 
Const BASS_SPEAKER_FRONT = $1000000 
Const BASS_SPEAKER_REAR = $2000000  
Const BASS_SPEAKER_CENLFE = $3000000 
Const BASS_SPEAKER_REAR2 = $4000000 
Const BASS_SPEAKER_LEFT = $10000000 
Const BASS_SPEAKER_RIGHT = $20000000 
Const BASS_SPEAKER_FRONTLEFT = BASS_SPEAKER_FRONT Or BASS_SPEAKER_LEFT 
Const BASS_SPEAKER_FRONTRIGHT = BASS_SPEAKER_FRONT Or BASS_SPEAKER_RIGHT 
Const BASS_SPEAKER_REARLEFT = BASS_SPEAKER_REAR Or BASS_SPEAKER_LEFT 
Const BASS_SPEAKER_REARRIGHT = BASS_SPEAKER_REAR Or BASS_SPEAKER_RIGHT 
Const BASS_SPEAKER_CENTER = BASS_SPEAKER_CENLFE Or BASS_SPEAKER_LEFT 
Const BASS_SPEAKER_LFE = BASS_SPEAKER_CENLFE Or BASS_SPEAKER_RIGHT 
Const BASS_SPEAKER_REAR2LEFT = BASS_SPEAKER_REAR2 Or BASS_SPEAKER_LEFT 
Const BASS_SPEAKER_REAR2RIGHT = BASS_SPEAKER_REAR2 Or BASS_SPEAKER_RIGHT 
Const BASS_TAG_ID3 = 0    
Const BASS_TAG_ID3V2 = 1 
Const BASS_TAG_OGG = 2    
Const BASS_TAG_HTTP = 3  
Const BASS_TAG_ICY = 4    
Const BASS_TAG_META = 5  
Const BASS_3DMODE_NORMAL = 0 
Const BASS_3DMODE_RELATIVE = 1 
Const BASS_3DMODE_OFF = 2 
Const EAX_ENVIRONMENT_GENERIC = 0 
Const EAX_ENVIRONMENT_PADDEDCELL = 1 
Const EAX_ENVIRONMENT_ROOM = 2 
Const EAX_ENVIRONMENT_BATHROOM = 3 
Const EAX_ENVIRONMENT_LIVINGROOM = 4 
Const EAX_ENVIRONMENT_STONEROOM = 5 
Const EAX_ENVIRONMENT_AUDITORIUM = 6 
Const EAX_ENVIRONMENT_CONCERTHALL = 7 
Const EAX_ENVIRONMENT_CAVE = 8 
Const EAX_ENVIRONMENT_ARENA = 9 
Const EAX_ENVIRONMENT_HANGAR = 10 
Const EAX_ENVIRONMENT_CARPETEDHALLWAY = 11 
Const EAX_ENVIRONMENT_HALLWAY = 12 
Const EAX_ENVIRONMENT_STONECORRIDOR = 13 
Const EAX_ENVIRONMENT_ALLEY = 14 
Const EAX_ENVIRONMENT_FOREST = 15 
Const EAX_ENVIRONMENT_CITY = 16 
Const EAX_ENVIRONMENT_MOUNTAINS = 17 
Const EAX_ENVIRONMENT_QUARRY = 18 
Const EAX_ENVIRONMENT_PLAIN = 19 
Const EAX_ENVIRONMENT_PARKINGLOT = 20 
Const EAX_ENVIRONMENT_SEWERPIPE = 21 
Const EAX_ENVIRONMENT_UNDERWATER = 22 
Const EAX_ENVIRONMENT_DRUGGED = 23 
Const EAX_ENVIRONMENT_DIZZY = 24 
Const EAX_ENVIRONMENT_PSYCHOTIC = 25 
Const EAX_ENVIRONMENT_COUNT = 26 
Const EAX_PRESET_GENERIC = 1 
Const EAX_PRESET_PADDEDCELL = 2 
Const EAX_PRESET_ROOM = 3 
Const EAX_PRESET_BATHROOM = 4 
Const EAX_PRESET_LIVINGROOM = 5 
Const EAX_PRESET_STONEROOM = 6 
Const EAX_PRESET_AUDITORIUM = 7 
Const EAX_PRESET_CONCERTHALL = 8 
Const EAX_PRESET_CAVE = 9 
Const EAX_PRESET_ARENA = 10 
Const EAX_PRESET_HANGAR = 11 
Const EAX_PRESET_CARPETEDHALLWAY = 12 
Const EAX_PRESET_HALLWAY = 13 
Const EAX_PRESET_STONECORRIDOR = 14 
Const EAX_PRESET_ALLEY = 15 
Const EAX_PRESET_FOREST = 16 
Const EAX_PRESET_CITY = 17 
Const EAX_PRESET_MOUNTAINS = 18 
Const EAX_PRESET_QUARRY = 19 
Const EAX_PRESET_PLAIN = 20 
Const EAX_PRESET_PARKINGLOT = 21 
Const EAX_PRESET_SEWERPIPE = 22 
Const EAX_PRESET_UNDERWATER = 23 
Const EAX_PRESET_DRUGGED = 24 
Const EAX_PRESET_DIZZY = 25 
Const EAX_PRESET_PSYCHOTIC = 26 
Const BASS_SYNC_POS = 0 
Const BASS_SYNC_MUSICPOS = 0 
Const BASS_SYNC_MUSICINST = 1 
Const BASS_SYNC_END = 2 
Const BASS_SYNC_MUSICFX = 3 
Const BASS_SYNC_META = 4 
Const BASS_SYNC_SLIDE = 5 
Const BASS_SYNC_MESSAGE = $20000000 
Const BASS_SYNC_MIXTIME = $40000000 
Const BASS_SYNC_ONETIME = $80000000 
Const CDCHANNEL = 0        
Const RECORDCHAN = 1      
Const BASS_ACTIVE_STOPPED = 0 
Const BASS_ACTIVE_PLAYING = 1 
Const BASS_ACTIVE_STALLED = 2 
Const BASS_ACTIVE_PAUSED = 3 
Const BASS_SLIDE_FREQ = 1 
Const BASS_SLIDE_VOL = 2 
Const BASS_SLIDE_PAN = 4 
Const BASS_CDID_IDENTITY = 0 
Const BASS_CDID_UPC = 1 
Const BASS_CDID_CDDB = 2 
Const BASS_CDID_CDDB2 = 3 
Const BASS_DATA_AVAILABLE= 0          
Const BASS_DATA_FFT512   = $80000000 
Const BASS_DATA_FFT1024  = $80000001 
Const BASS_DATA_FFT2048  = $80000002 
Const BASS_DATA_FFT512S  = $80000010 
Const BASS_DATA_FFT1024S = $80000011 
Const BASS_DATA_FFT2048S = $80000012 
Const BASS_DATA_FFT_NOWINDOW = $20    
Const BASS_INPUT_OFF = $10000 
Const BASS_INPUT_ON = $20000 
Const BASS_INPUT_LEVEL = $40000 
Const BASS_INPUT_TYPE_MASK = $ff000000 
Const BASS_INPUT_TYPE_UNDEF = $00000000 
Const BASS_INPUT_TYPE_DIGITAL = $01000000 
Const BASS_INPUT_TYPE_LINE = $02000000 
Const BASS_INPUT_TYPE_MIC = $03000000 
Const BASS_INPUT_TYPE_SYNTH = $04000000 
Const BASS_INPUT_TYPE_CD = $05000000 
Const BASS_INPUT_TYPE_PHONE = $06000000 
Const BASS_INPUT_TYPE_SPEAKER = $07000000 
Const BASS_INPUT_TYPE_WAVE = $08000000 
Const BASS_INPUT_TYPE_AUX = $09000000 
Const BASS_INPUT_TYPE_ANALOG = $0a000000 
Const BASS_NET_TIMEOUT = 0 
Const BASS_NET_BUFFER = 1 
Const BASS_FILEPOS_DECODE = 0 
Const BASS_FILEPOS_DOWNLOAD = 1 
Const BASS_FILEPOS_END = 2 
Const BASS_FILE_CLOSE = 0 
Const BASS_FILE_READ = 1 
Const BASS_FILE_QUERY = 2 
Const BASS_FILE_LEN = 3 
Const BASS_OBJECT_DS = 1                      
Const BASS_OBJECT_DS3DL = 2                  
Const BASS_VAM_HARDWARE = 1 
Const BASS_VAM_SOFTWARE = 2 
Const BASS_VAM_TERM_TIME = 4 
Const BASS_VAM_TERM_DIST = 8 
Const BASS_VAM_TERM_PRIO = 16 
Const BASS_3DALG_DEFAULT = 0 
Const BASS_3DALG_OFF = 1 
Const BASS_3DALG_FULL = 2 
Const BASS_3DALG_LIGHT = 3 
Const BASS_FX_CHORUS = 0                  
Const BASS_FX_COMPRESSOR = 1              
Const BASS_FX_DISTORTION = 2              
Const BASS_FX_ECHO = 3                    
Const BASS_FX_FLANGER = 4                
Const BASS_FX_GARGLE = 5                  
Const BASS_FX_I3DL2REVERB = 6            
Const BASS_FX_PARAMEQ = 7                
Const BASS_FX_REVERB = 8                  
Const BASS_FX_PHASE_NEG_180 = 0 
Const BASS_FX_PHASE_NEG_90 = 1 
Const BASS_FX_PHASE_ZERO = 2 
Const BASS_FX_PHASE_90 = 3 
Const BASS_FX_PHASE_180 = 4

Function BASS_GetStringVersion$()
	Local Version,HiWord,LoWord
	Version = BASS_GetVersion()
	HiWord  = Version And $0000FFFF
	LoWOrd  = (Version And $FFFF0000) Shr 16
	Return HiWord + "." + LoWord
End Function

Function BASS_GetDeviceCount()
	Local I
	While True
		If BASS_GetDeviceDescriptionInt(I) Then
			I = I + 1
		Else
			Return I
		EndIf
	Wend
End Function

Function BASS_GetDeviceDescription$(devnum)
	Local Pointer,BnkDescription,Description$,I,Char
	Pointer = BASS_GetDeviceDescriptionInt(devnum)
	If Not Pointer Then
		Return ""
	Else
		BnkDescription = CreateBank(256)
		RtlMoveMemory1(BnkDescription,Pointer,256)
		For I = 0 To 255
			Char = PeekByte(BnkDescription,I)
			If Char = 0 Then Exit
			Description$ = Description$ + Chr$(Char)
		Next
	EndIf
	FreeBank BnkDescription : Return Description$
End Function

Function BASS_StreamGetTagsCount(hHandle,tags)
	Local Pointer,Position,LastChar,Count
	Pointer = BASS_StreamGetTagsInt(hHandle,tags)
	If Not Pointer Then Return False
	
	If hHandle = BASS_TAG_ID3 Or hHandle = BASS_TAG_ID3V2 Then
		Return 7
	Else
		BnkChar = CreateBank(1)
		While True
			LastChar = Char
			RtlMoveMemory1(BnkChar,Pointer + Position,1)
			Position = Position + 1
			Char = PeekByte(BnkChar,0)
			If Char = 0 And LastChar = 0 Then
				Return Count
			ElseIf Char = 0 Then
				Count = Count + 1
			EndIf
		Wend
	EndIf
End Function

Function BASS_StreamGetTag$(hHandle,tags,num)
	Local Pointer,Position,LastChar,Count
	Pointer = BASS_StreamGetTagsInt(hHandle,tags)
	If Not Pointer Then Return ""
	
	If hHandle = BASS_TAG_ID3V1 Then Return "ID3V2"
	
	If hHandle = BASS_TAG_ID3 Then
		BnkID3 = CreateBank(128)
		RtlMoveMemory1(BnkID3,Pointer,128)
		If num = 0 Then     ; "ID3"
			For I = 0 To 2
				Tag$ = Tag$ + Chr$(PeekByte(BnkID3,I))
			Next
			Return Tag$
		ElseIf num = 1 Then ; Title
			For I = 3 To 32
				Tag$ = Tag$ + Chr$(PeekByte(BnkID3,I))
			Next
			Return Tag$
		ElseIf num = 2 Then ; Artist
			For I = 33 To 62
				Tag$ = Tag$ + Chr$(PeekByte(BnkID3,I))
			Next
			Return Tag$
		ElseIf num = 3 Then ; Album
			For I = 63 To 92
				Tag$ = Tag$ + Chr$(PeekByte(BnkID3,I))
			Next
			Return Tag$
		ElseIf num = 4 Then ; Year
			For I = 93 To 96
				Tag$ = Tag$ + Chr$(PeekByte(BnkID3,I))
			Next
			Return Tag$
		ElseIf num = 5 Then ; Comment
			For I = 97 To 126
				Tag$ = Tag$ + Chr$(PeekByte(BnkID3,I))
			Next
			Return Tag$
		ElseIf num = 6 Then ; Genre
			Return ID3_Generes$(PeekByte(BnkID3,127))
		EndIf
	Else
		BnkChar = CreateBank(1)
		While True
			LastChar = Char
			RtlMoveMemory1(BnkChar,Pointer + Position,1)
			Position = Position + 1
			Char = PeekByte(BnkChar,0)
			If Char = 0 Then Count = Count + 1
			If Count = num Then
				While True 
					RtlMoveMemory1(BnkChar,Pointer + Position,1)
					Position = Position + 1
					Char = PeekByte(BnkChar,0)
					If Char = 0 Then Return Tag$
					Tag$ = Tag$ + Chr$(Char)					
				Wend
			EndIf
		Wend
	EndIf
End Function

Function BASS_RecordGetDeviceCount()
   Local I
   While True
      If BASS_GetDeviceDescriptionInt(I) Then
         I = I + 1
      Else
         Return I + 1
      EndIf
   Wend
End Function

Function BASS_RecordGetDeviceDescription$(devnum)
   Local Pointer,BnkDescription,Description$,I,Char
   Pointer = BASS_RecordGetDeviceDescriptionInt(devnum)
   If Not Pointer Then
      Return ""
   Else
      BnkDescription = CreateBank(256)
      RtlMoveMemory1(BnkDescription,Pointer,256)
      For I = 0 To 255
         Char = PeekByte(BnkDescription,I)
         If Char = 0 Then Exit
         Description$ = Description$ + Chr$(Char)
      Next
   EndIf
   FreeBank BnkDescription : Return Description$
End Function 

Function BASS_RecordGetInputCount()
   Local I
   While True
      If BASS_RecordGetInputNameInt(I) Then
         I = I + 1
      Else
         Return I
      EndIf
   Wend
End Function

Function BASS_RecordGetInputName$(Input)
   Local Pointer,BnkDescription,Description$,I,Char
   Pointer = BASS_RecordGetInputNameInt(Input)
   If Not Pointer Then
      Return ""
   Else
      BnkDescription = CreateBank(256)
      RtlMoveMemory1(BnkDescription,Pointer,256)
      For I = 0 To 255
         Char = PeekByte(BnkDescription,I)
         If Char = 0 Then Exit
         Description$ = Description$ + Chr$(Char)
      Next
   EndIf
   FreeBank BnkDescription : Return Description$
End Function