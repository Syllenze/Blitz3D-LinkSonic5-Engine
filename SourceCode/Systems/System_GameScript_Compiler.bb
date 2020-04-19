;__ GameScript [Compiler] _________________________________________
;Copyright (C) 2005			John Judnich

;Variables
Const MAX_VARFIELDS = 64, MAX_SUBFIELDS = 64
Const DATATYPE_VOID = -1, DATATYPE_INTEGER = 0, DATATYPE_FLOAT = 1, DATATYPE_STRING = 2, DATATYPE_CUSTOM = 3	;Numbers in order of precedence, 0 being lowest
Const SCOPE_GLOBAL = 0, SCOPE_LOCAL = 1, SCOPE_STATIC = 2, SCOPE_CONSTANT = 3, SCOPE_TYPEFIELD = 4	;SCOPE_TYPEFIELD is similar to SCOPE_GLOBAL, except it is refferenced differently (Car.Speed instead of Speed)
Type Var
	Field Name$
	Field DataType				;DataType = DATATYPE_
	Field CustomType.TypeDef	;If DataType = DATATYPE_CUSTOM then this is the typedef this custom variable is
	Field TypeDef.TypeDef		;Type definition this variable belongs to
	Field Scope, Owner.Func		;Scope = SCOPE_
	Field Value$				;Value of a SCOPE_CONSTANT variable
	Field CustomTypeTemp$		;Temporary fields
	Field DefinitionPos			;Location of variable definition
	
	Field CodeNum
End Type
Type TypeDef
	Field Name$
	Field VarField.Var[MAX_VARFIELDS]
	Field VarFields

	Field CodeNum
End Type

;Functions
Const MAX_PARAMS = 64
Global GSC_MainFunc.Func, GSC_CurrentFunc.Func, GSC_PreviousFunction.Func
Type Func
	Field Name$
	Field ReturnType		;ReturnType = DATATYPE_
	Field Param.Var[MAX_VARFIELDS], Params
	Field Label, EndLabel
End Type

;Labels
Global GSC_LabelIndex

;Compile messages
Const MAX_MESSAGES = 64
Global GSC_Errors, GSC_Warnings
Const MESSAGE_WARNING = 0, MESSAGE_ERROR = 1
Dim GSC_Message$(MAX_MESSAGES)
Dim GSC_MessageType(MAX_MESSAGES)
Dim GSC_MessageLin(MAX_MESSAGES)

;Pcodes
Const ASM_MOV = 1
Const ASM_MFC = 2
Const ASM_MFA = 3
Const ASM_XFA = 4
Const ASM_XFC = 39
Const ASM_NEW = 5
Const ASM_DEL = 6
Const ASM_JMP = 7
Const ASM_JZ  = 8
Const ASM_END = 9
Const ASM_PUSH = 10
Const ASM_POP  = 11
Const ASM_AND = 12
Const ASM_OR  = 13
Const ASM_XOR = 14
Const ASM_NOT = 15
Const ASM_NEG = 16
Const ASM_cLT = 17
Const ASM_cGT = 18
Const ASM_cLE = 19
Const ASM_cGE = 20
Const ASM_cEQ = 21
Const ASM_cNE = 22
Const ASM_ADD = 23
Const ASM_SUB = 24
Const ASM_MUL = 25
Const ASM_DIV = 26
Const ASM_SHL = 27
Const ASM_SHR = 28
Const ASM_CPUSH = 30
Const ASM_CPOP  = 31
Const ASM_VPOP  = 32
Const ASM_XPOP  = 33
Const ASM_LBL   = 34
Const ASM_MOD   = 35
Const ASM_CALL   = 36

;Const ASM_PRINT = 100

;File codes (used when saving binary execution data)
Global GSC_CodeIndex
Const FILE_NULL =		0
Const FILE_VAR_VAR =	1
Const FILE_VAR =		2
Const FILE_LBL =		3
Const FILE_INT =		4
Const FILE_VAR_FLT =	5
Const FILE_VAR_INT =	6
Const FILE_VAR_STR =	7
Const FILE_VAR_TYP =	8
Const FILE_CALL =		9

Global REG_EAX.Var
Global REG_EBX.Var
Global REG_ECX.Var

;Parser
Const TOKEN_UNKNOWN = -1
Const TOKEN_EOF = -2
Const TOKEN_STRING = 0
Const TOKEN_NUMBER = 1
Const TOKEN_IDENTIFIER = 2

Const TOKEN_ADD = 100		;+
Const TOKEN_SUB = 101		;-
Const TOKEN_MUL = 102		;*
Const TOKEN_DIV = 103		;/
Const TOKEN_OPENPAR = 104	;(
Const TOKEN_CLOSEPAR = 105	;)
Const TOKEN_EQ = 106		;=
Const TOKEN_LT = 107		;<
Const TOKEN_GT = 108		;>
Const TOKEN_LE = 109		;<=
Const TOKEN_GE = 110		;>=
Const TOKEN_NE = 111		;<>
Const TOKEN_PERIOD = 112	;.
Const TOKEN_COMMA = 113		;,
Const TOKEN_TERMINATOR = 114;;	

Const TOKEN_LOCAL = 200
Const TOKEN_GLOBAL = 201
Const TOKEN_STATIC = 202
Const TOKEN_CONSTANT = 203

Const TOKEN_IF = 300
Const TOKEN_THEN = 301
Const TOKEN_ELSE = 302
Const TOKEN_ENDIF = 303

Const TOKEN_LET = 400
Const TOKEN_END = 401
Const TOKEN_GOTO = 402
Const TOKEN_LABEL = 403

Const TOKEN_CUSTOMTYPE = 500
Const TOKEN_INTEGERTYPE = 501
Const TOKEN_FLOATTYPE = 502
Const TOKEN_STRINGTYPE = 503
Const TOKEN_VOIDTYPE = 504

Const TOKEN_BEGINTYPE = 600
Const TOKEN_ENDTYPE = 601
Const TOKEN_FIELD = 602
Const TOKEN_NEW = 603
Const TOKEN_DELETE = 604

Const TOKEN_AND = 700
Const TOKEN_OR = 701
Const TOKEN_NOT = 702
Const TOKEN_XOR = 703

Const TOKEN_SHL = 800
Const TOKEN_SHR = 801
Const TOKEN_FUNCTION = 900
Const TOKEN_ENDFUNCTION = 901
Const TOKEN_RETURN = 902
Const TOKEN_FUNCTIONNAME = 903
Const TOKEN_EXTFUNCTIONNAME = 904

Const TOKEN_WHILE = 1001
Const TOKEN_ENDWHILE = 1002
Const TOKEN_REPEAT = 1003
Const TOKEN_UNTIL = 1004
Const TOKEN_FOREVER = 1005
Const TOKEN_FOR = 1006
Const TOKEN_TO = 1007
Const TOKEN_STEP = 1008
Const TOKEN_NEXT = 1009
Const TOKEN_EXIT = 1010
Const TOKEN_SELECT = 1101
Const TOKEN_CASE = 1102
Const TOKEN_DEFAULT = 1103
Const TOKEN_ENDSELECT = 1104

;Const TOKEN_PRINT = 2000

Global GSC_ExpressionDataType
Global GSC_InputFile, GSC_OutputFile
Global GSC_Done, GSC_PrevFilePos
Global GSC_Token$, GSC_TokenType, GSC_PrevToken$, GSC_PrevTokenType
Global GSC_Ch$, GSC_PrevCh$



;This function compiles the given GameScript source code file (usually
;.GS) to a GameScript Executable file (default is .GSX). If ScriptFile$
;does not contain an extension, .GS will automatically appended. If
;CompiledFile (optional) does not contain a filename with an extension,
;.GSX will be automatically appended to the source code filename.
;The function returns True if the file was successfully compiled, False if not.
Function GSC_Compile(ScriptFile$, CompiledFile$ = "")
	Local var.Var, def.TypeDef, typ$

	;Clear old compile messages
	GSC_ClearMessages()

	;Process filenames
	ScriptFile = GSC_DefaultExtension(ScriptFile, "gs")
	If CompiledFile = "" Then
		CompiledFile = GSC_SetExtension(ScriptFile, "gsx")
	Else
		CompiledFile = GSC_DefaultExtension(CompiledFile, "gsx")
	End If
	If FileType(ScriptFile) <> 1 Then GSC_AddError("Source file does not exist!"): Return False
	
	;Check the date/time stamp
	script_datetime = GSC_FileModified(ScriptFile)
	If FileType(CompiledFile) = 1 Then
		tmpfile = ReadFile(CompiledFile)
		If tmpfile = 0 Then GSC_AddError("Error while reading existing executable: "+CompiledFile): Return False
		compiled_datetime = CreateBank(16)
		ReadBytes compiled_datetime, tmpfile, 0, 16
		RequireCompile = False
		For i = 0 To 15
			If PeekByte(compiled_datetime, i) <> PeekByte(script_datetime, i) Then RequireCompile = True: Exit
		Next
		If Not RequireCompile Then CloseFile tmpfile: FreeBank compiled_datetime: Return True	;Nothing to do - executable file is up-to-date
		FreeBank compiled_datetime
		CloseFile tmpfile
	End If
	
	;Open the files
	GSC_InputFile = ReadFile(ScriptFile)
	GSC_OutputFile = WriteFile(CompiledFile)
	If GSC_InputFile = 0 Then GSC_AddError("Could not read from source file: "+CompiledFile)
	If GSC_OutputFile = 0 Then GSC_AddError("Could not write to executable: "+CompiledFile)
	If GSC_CompileErrors() > 0 Then
		DeleteFile CompiledFile	;Delete the compiled file if errors occurred
		Return False
	End If
	
	;Create the virtual "main()"
	GSC_MainFunc = New Func
	GSC_MainFunc\Name = "main"
	GSC_MainFunc\ReturnType = DATATYPE_VOID
	GSC_MainFunc\Params = 0
	
	;Define all HARDCODED CONSTANTS such as True, False, etc.
	GSF_InitConstants()
	
	;Define registers (EAX, ECX, etc.)
	var = New Var
	var\Name = "eax"
	var\Owner = GSC_MainFunc
	var\DataType = DATATYPE_STRING
	var\Scope = SCOPE_GLOBAL
	var\TypeDef = Null
	var\CustomType = Null
	var\CodeNum = -1
	var\Value = 0
	REG_EAX = var

	var = New Var
	var\Name = "ebx"
	var\Owner = GSC_MainFunc
	var\DataType = DATATYPE_STRING
	var\Scope = SCOPE_GLOBAL
	var\TypeDef = Null
	var\CustomType = Null
	var\CodeNum = -2
	var\Value = 0
	REG_EBX = var

	var = New Var
	var\Name = "ecx"
	var\Owner = GSC_MainFunc
	var\DataType = DATATYPE_INTEGER
	var\Scope = SCOPE_GLOBAL
	var\TypeDef = Null
	var\CustomType = Null
	var\CodeNum = -3
	var\Value = 0
	REG_ECX = var

	;Write time/date stamp to file
	WriteBytes script_datetime, GSC_OutputFile, 0, 16
	
	;Write an identification header
	WriteString GSC_OutputFile, "gsx"
	
	;PASS 1 - Scan variable definitions and typedefs
	SeekFile GSC_InputFile, 0
	GSC_CurrentFunc = GSC_MainFunc
	GSC_Done = False
	GSC_Ch = " "
	GSC_NextToken()
	Repeat
		GSC_ScanStatement()
	Until GSC_Done

	;PASS 1B - Now that all typedefs are parsed, assign variable types to their proper TypeDefs
	For var = Each Var
		def = GSC_GetTypeDef(var\CustomTypeTemp)
		var\CustomType = def
		If def = Null And var\DataType = DATATYPE_CUSTOM Then
			GSC_PrevFilePos = var\DefinitionPos
			GSC_AddError("Undefined data type")
		End If
	Next
	
	;PASS 1C - Write typedef+variable declarations header
	For def.TypeDef=Each TypeDef
		WriteInt GSC_OutputFile, def\CodeNum	;Nothing to write but code numbers of typedefs since fields are inserted by the variable declaration
	Next
	WriteInt GSC_OutputFile, -1
	For var.Var=Each Var
		If var\CodeNum >= 0 Then
			WriteInt GSC_OutputFile, var\CodeNum
			If var\TypeDef <> Null Then WriteInt GSC_OutputFile, var\TypeDef\CodeNum Else WriteInt GSC_OutputFile, -1
			If var\CustomType <> Null Then WriteInt GSC_OutputFile, var\CustomType\CodeNum Else WriteInt GSC_OutputFile, -1
			WriteByte GSC_OutputFile, var\DataType
			If var\DataType = DATATYPE_INTEGER Then
				WriteInt GSC_OutputFile, Int(var\Value)
			End If
			If var\DataType = DATATYPE_FLOAT Then
				WriteFloat GSC_OutputFile, Float(var\Value)
			End If
			If var\DataType = DATATYPE_STRING Then
				WriteString GSC_OutputFile, Str(var\Value)
			End If
		End If
	Next
	WriteInt GSC_OutputFile, -1

	;Return with the errors if the first pass failed (the second pass cannot continue without the first)
	If GSC_Errors > 0 Then
		;Close the files
		CloseFile GSC_OutputFile
		CloseFile GSC_InputFile
		
		DeleteFile CompiledFile	;Delete the compiled file if errors occurred
		Return False
	End If
	
	;PASS 2 - Compile code
	SeekFile GSC_InputFile, 0
	GSC_CurrentFunc = GSC_MainFunc
	GSC_Done = False
	GSC_Ch = " "
	GSC_NextToken()
	Repeat
		GSC_ParseStatement()
	Until GSC_Done
	
	;FINAL PASS - Re-index compile message locations
	pos = FilePos(GSC_InputFile)
	SeekFile GSC_InputFile, 0
	lin = 1: msg = 1
	For i = 1 To GSC_MessageLin(GSC_Errors+GSC_Warnings)
		If ch = 13 Then lin = lin + 1
		ch = ReadByte(GSC_InputFile)
		If i = GSC_MessageLin(msg) Then
			GSC_MessageLin(msg) = lin
			msg = msg + 1
		End If
	Next
	SeekFile GSC_InputFile, pos
	
	;Close the files
	CloseFile GSC_OutputFile
	CloseFile GSC_InputFile
	
	;Clean up
	For var.Var = Each Var
		Delete var
	Next
	For def.TypeDef = Each TypeDef
		Delete def
	Next
	For func.Func = Each Func
		Delete func
	Next
	
	;Return
	If GSC_Errors = 0 Then
		Return True
	Else
		DeleteFile CompiledFile	;Delete the compiled file if errors occurred
		Return False
	End If
End Function

;This returns the number of errors and warnings from the last compile
Function GSC_CompileMessages()
	Return GSC_Errors + GSC_Warnings
End Function

;This returns the description for message #MessageIndex
Function GSC_CompileMessage$(MessageIndex)
	Return GSC_Message(MessageIndex)
End Function

;This returns the type of message #MessageIndex. Returns MESSAGE_WARNING or MESSAGE_ERROR
Function GSC_CompileMessageType(MessageIndex)
	Return GSC_MessageType(MessageIndex)
End Function

;This returns the line message #MessageIndex is at.
Function GSC_CompileMessageLine(MessageIndex)
	Return GSC_MessageLin(MessageIndex)
End Function

;This returns the character (from the left margin) that #MessageIndex is at.
;Function GSC_CompileMessageChar(MessageIndex)
;	Return GSC_MessageChar(MessageIndex)
;End Function

;This returns the number of errors from the last compile
Function GSC_CompileErrors()
	Return GSC_Errors
End Function

;This returns the number of warnings from the last compile
Function GSC_CompileWarnings()
	Return GSC_Warnings
End Function





;[INTERNAL] This function adds an error to the compile message list
Function GSC_AddError(Description$)
	If GSC_Errors+GSC_Warnings >= MAX_MESSAGES Then GSC_Done = True: Return 
	GSC_Errors = GSC_Errors + 1
	If GSC_Errors+GSC_Warnings >= MAX_MESSAGES-1 Then Description = "Too many error/warning messages. Compile operation aborted.": GSC_Done = True
	GSC_Message(GSC_Errors+GSC_Warnings) = Description
	GSC_MessageType(GSC_Errors+GSC_Warnings) = MESSAGE_ERROR
	
	GSC_MessageLin(GSC_Errors+GSC_Warnings) = GSC_PrevFilePos
End Function

;[INTERNAL] This function adds an warning to the compile message list
Function GSC_AddWarning(Description$)
	If GSC_Errors+GSC_Warnings >= MAX_MESSAGES Then GSC_Done = True: Return 
	GSC_Warnings = GSC_Warnings + 1
	If GSC_Errors+GSC_Warnings >= MAX_MESSAGES-1 Then Description = "Too many error/warning messages. Compile operation aborted.": GSC_Done = True
	GSC_Message(GSC_Errors+GSC_Warnings) = Description
	GSC_MessageType(GSC_Errors+GSC_Warnings) = MESSAGE_WARNING
	
	GSC_MessageLin(GSC_Errors+GSC_Warnings) = GSC_PrevFilePos
End Function

;[INTERNAL] This clears the message list from the last compile
Function GSC_ClearMessages()
	GSC_Warnings = 0
	GSC_Errors = 0
End Function

;[INTERNAL] This function is used internally by GameScript to add an extension when there is none.
;Example: GSC_DefaultExtension("data\document", "txt") = "data\document.txt"
;Example: GSC_DefaultExtension("data\document.doc", "txt") = "data\document.doc"
Function GSC_DefaultExtension$(File$, Extension$)
	Local length, p
	Local ch$, FileName$
	
	length = Len(File$)
	For p = length To 1 Step -1
		ch = Mid(File, p, 1)
		If ch = "\" Or ch = "/" Then Exit
		If ch = "." Then Return File
	Next
	FileName = File + "." + Extension
	
	Return FileName
End Function

;[INTERNAL] This function is used internally by GameScript to set/replace a file extension.
;Example: GSC_DefaultExtension("data\document", "txt") = "data\document.txt"
;Example: GSC_DefaultExtension("data\document.doc", "txt") = "data\document.txt"
Function GSC_SetExtension$(File$, Extension$)
	Local length, p
	Local ch$, FileName$
	
	length = Len(File$)
	For p = length To 1 Step -1
		ch = Mid(File, p, 1)
		If ch = "\" Or ch = "/" Then p = -1: Exit
		If ch = "." Then Exit
	Next
	If p = -1 Then
		FileName = FileName + "." + Extension	
	Else
		FileName = Left(File, p - 1)
		FileName = FileName + "." + Extension
	End If
	Return FileName
End Function

;[INTERNAL] This get the date/time stamp of the file (last modified time), returning a 16 byte bank
;NOTE: This will not compile without errors if you do not put "TimeStamp.decls" in your Userlibs\ folder
Function GSC_FileModified(File$)
	lpReOpenBuff = CreateBank(150)
	lpCreationTime = CreateBank(8)
	lpLastAccessTime = CreateBank(8)
	lpLastWriteTime = CreateBank(8)
	lpLocalFileTime = CreateBank(8)
	lpSystemTime = CreateBank(16)	
	
	hFile = api_OpenFile(File$, lpReOpenBuff, 0) ;*** IF YOU GET AN ERROR HERE, put "TimeStamp.decls" in your Userlibs\ folder ***

	If hFile <> -1 ;if hFile = -1 then error, can't open file
		result = api_GetFileTime(hFile, lpCreationTime, lpLastAccessTime, lpLastWriteTime)
		If result = 1 ;if result <> 1 then error, can't get file time			
			result = api_FileTimeToLocalFileTime(lpLastWriteTime,lpLocalFileTime)		
			If result = 1 ;if result <> 1 then error, can't convert to local time
				result = api_FileTimeToSystemTime (lpLocalFileTime,lpSystemTime)
				;note: if result <> 1 then function failed
			End If
		End If
		api_CloseHandle(hFile)				
	End If	

	;Clean up and end function	
	FreeBank lpReOpenBuff : FreeBank lpCreationTime : FreeBank lpLastAccessTime
	FreeBank lpLastWriteTime : FreeBank lpLocalFileTime
	
	Return lpSystemTime
End Function

;[INTERNAL] Returns a variable when given it's name and the current function
Function GSC_GetVariable.Var(Identifier$, Owner.Func)
	Local var.Var

	;Check if it already exists
	For var = Each Var
		If var\Scope <> SCOPE_TYPEFIELD And var\Name = Identifier And (var\Scope = SCOPE_GLOBAL Or var\Scope = SCOPE_CONSTANT Or var\Owner = Owner) Then Return var
	Next
	
	Return Null
End Function

;[INTERNAL] Returns a variable when given it's name
Function GSC_FindVariable.Var(Identifier$)
	Local var.Var

	;Check if it already exists
	For var = Each Var
		If var\Name = Identifier Then Return var
	Next
	
	Return Null
End Function

;[INTERNAL] Returns a typedef structure when given it's name
Function GSC_GetTypeDef.TypeDef(Identifier$)
	Local def.TypeDef
	
	;Check if it already exists
	For def = Each TypeDef
		If def\Name = Identifier Then Return def
	Next
	
	Return Null
End Function

;[INTERNAL] Returns a function structure when given it's name
Function GSC_GetFunc.Func(Identifier$)
	Local func.Func
	
	;Check if it already exists
	For func = Each Func
		If func\Name = Identifier Then Return func
	Next
	
	Return Null
End Function

;[INTERNAL] Parses a statement from the source file, and converts it to opcode. Termination can enable or disable
;whether line termination is required (False for statements inside an If statement, for example).
;ExitLine$ is a special parameter which is written to the file when it comes across the "Exit" keyword.
Function GSC_ParseStatement(Termination = True, ExitLine$="")
	Local ID.Var, var.Var, i, func.Func
	Local ParamCount, ParOpened;, ParamType[MAX_PARAMS]
	Local LabelElse, LabelEndif
	Local LabelCase, LabelNextCase, LabelExit, LabelTop
	Local TmpField.Var[MAX_SUBFIELDS], TmpFields, StepValue#
	
	Select GSC_TokenType
	
	;Case TOKEN_PRINT
	;	GSC_ParseExpression()
	;	GSC_WriteInstruction_V ASM_PRINT, REG_EAX
	;	DebugLog "    print eax"
	
	Case TOKEN_IDENTIFIER, TOKEN_LET
		;Get the variable
		If GSC_TokenType = TOKEN_LET Then GSC_NextToken()
		var = GSC_GetVariable(GSC_Token, GSC_CurrentFunc): ID = var
		If var = Null Then
			GSC_AddError("Undefined identifier/function")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		GSC_NextToken()
		If var\Scope = SCOPE_CONSTANT Then
			GSC_AddError("Constants may not be modified")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		While GSC_TokenType = TOKEN_PERIOD
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected custom type field name")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			;Check type field
			If var\DataType <> DATATYPE_CUSTOM Then
				GSC_AddError("Variable "+Chr(34)+var\Name+Chr(34)+" is not a custom type (expected assignment, found "+Chr(34)+"."+Chr(34)+")")
				GSC_NextToken()
				Return
			End If
			found = False
			For i = 1 To var\CustomType\VarFields
				If var\CustomType\VarField[i]\Name = GSC_Token Then found = True: Exit
			Next
			If found = False Then
				GSC_AddError("Type field not found")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			var = var\CustomType\VarField[i]
			TmpFields = TmpFields + 1
			TmpField[TmpFields] = var
			GSC_NextToken()
		Wend
		
		If GSC_TokenType <> TOKEN_EQ Then
			GSC_AddError("Expected equals symbol followed by assignment")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		GSC_ParseExpression()
		If TmpFields = 0 Then
			GSC_WriteInstruction_VV ASM_MOV, ID, REG_EAX
			DebugLog "    mov   ["+ID\Name+"], eax"
		Else
			GSC_WriteInstruction_VV ASM_MOV, REG_ECX, ID
			DebugLog "    mov   ecx, ["+ID\Name+"]"
			For i = 1 To TmpFields-1
				GSC_WriteInstruction_VV ASM_MFC, REG_ECX, TmpField[i]
				DebugLog "    mfc   ecx, <"+TmpField[i]\Name+">"	;mfc a,b = move type field b of custom type object ecx into a
			Next
			GSC_WriteInstruction_VV ASM_XFC, TmpField[TmpFields], REG_EAX
			DebugLog "    xfc   <"+TmpField[TmpFields]\Name+">, eax"	;mfc a,b = move b into type field a is custom type object ecx
		End If
		
	Case TOKEN_DELETE
		;Delete the custom type instance
		GSC_ParseExpression()
		GSC_WriteInstruction_V ASM_DEL, REG_EAX
		DebugLog "    del   eax"	;del a = delete custom type instance referenced by a
				
	Case TOKEN_GLOBAL, TOKEN_CONSTANT, TOKEN_LOCAL, TOKEN_STATIC, TOKEN_BEGINTYPE, TOKEN_ENDTYPE, TOKEN_FIELD
		;Skip
		Repeat
			GSC_NextToken()
		Until GSC_TokenType = TOKEN_TERMINATOR
		
	Case TOKEN_IF
		GSC_ParseExpression()
		If GSC_TokenType <> TOKEN_THEN Then
			GSC_AddError("Expected "+Chr(34)+"Then"+Chr(34))
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		LabelElse = GSC_UniqueLabel()
		GSC_WriteInstruction_L ASM_JZ, LabelElse
		DebugLog "    jz    _" + LabelElse
		
		GSC_NextToken()
		If GSC_TokenType = TOKEN_TERMINATOR And GSC_Token <> ";" Then
			;Multi-line IF statement
			Repeat
				GSC_ParseStatement(True, ExitLine)
				If GSC_Done Then
					GSC_AddError("Expected "+Chr(34)+"Else"+Chr(34)+" or "+Chr(34)+"EndIf"+Chr(34))
					Return
				End If
			Until GSC_TokenType = TOKEN_ELSE Or GSC_TokenType = TOKEN_ENDIF
			If GSC_TokenType = TOKEN_ELSE
				LabelEndif = GSC_UniqueLabel()
				GSC_WriteInstruction_L ASM_JMP, LabelEndif
				GSC_WriteInstruction_L ASM_LBL, LabelElse
				DebugLog "    jmp   _" + LabelEndif
				DebugLog "_" + LabelElse + ":"
				GSC_NextToken()
				If GSC_TokenType = TOKEN_IF Then GSC_AddWarning("Ambiguous coding style (may cause unexpected errors - see language manual)")
				Repeat
					GSC_ParseStatement(True, ExitLine)
					If GSC_Done Then
						GSC_AddError("Expected "+Chr(34)+"EndIf"+Chr(34))
						Return
					End If
				Until GSC_TokenType = TOKEN_ENDIF
				GSC_WriteInstruction_L ASM_LBL, LabelEndif
				DebugLog "_" + LabelEndif + ":"
				GSC_NextToken()
			Else ;EndIf
				GSC_WriteInstruction_L ASM_LBL, LabelElse
				DebugLog "_" + LabelElse + ":"
				GSC_NextToken()
			EndIf
		Else
			;Single-line IF statement
			Repeat
				GSC_ParseStatement(False, ExitLine)
				If GSC_Done Then
					GSC_AddError("Expected "+Chr(34)+"Else"+Chr(34)+" or "+Chr(34)+"EndIf"+Chr(34))
					Return
				End If
			Until GSC_TokenType = TOKEN_ELSE Or (GSC_TokenType = TOKEN_TERMINATOR And GSC_Token <> ";")
			If GSC_TokenType = TOKEN_ELSE
				LabelEndif = GSC_UniqueLabel()
				GSC_WriteInstruction_L ASM_JMP, LabelEndif
				GSC_WriteInstruction_L ASM_LBL, LabelElse
				DebugLog "    jmp   _" + LabelEndif
				DebugLog "_" + LabelElse + ":"
				GSC_NextToken()
				If GSC_TokenType = TOKEN_IF Then GSC_AddWarning("Ambiguous coding style (may cause unexpected errors - see language manual)")
				Repeat
					GSC_ParseStatement(False, ExitLine)
					If GSC_Done Then
						GSC_AddError("Expected statement terminator")
						Return
					End If
				Until GSC_TokenType = TOKEN_TERMINATOR And GSC_Token <> ";"
				GSC_WriteInstruction_L ASM_LBL, LabelEndif
				DebugLog "_" + LabelEndif + ":"
			Else ;Terminator
				GSC_WriteInstruction_L ASM_LBL, LabelElse
				DebugLog "_" + LabelElse + ":"
			EndIf
		End If
		
	Case TOKEN_ELSE, TOKEN_ENDIF
		GSC_AddError("Keyword is out of place (there are no open "+Chr(34)+"If"+Chr(34)+" statements)")
		GSC_NextToken()
		Return
		
	Case TOKEN_SELECT
		GSC_ParseExpression()
		GSC_WriteInstruction_V ASM_PUSH, REG_EAX
		DebugLog "    push  eax"	;Push eax, which is the Select expression
		If GSC_TokenType <> TOKEN_TERMINATOR Then
			GSC_AddError("Expected line-end or semicolon")
			GSC_SkipLine(): GSC_NextToken()
			Return	
		End If
		LabelExit = GSC_UniqueLabel()
		LabelNextCase = GSC_UniqueLabel()
		Defaulted = False
		Repeat
			GSC_NextToken()
			If GSC_Done Then
				GSC_AddError("Expected expression")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
		Until GSC_TokenType <> TOKEN_TERMINATOR
		Repeat
			LabelCase = LabelNextCase
			LabelNextCase = GSC_UniqueLabel()
			;Case
			If GSC_TokenType <> TOKEN_CASE And GSC_TokenType <> TOKEN_DEFAULT Then
				GSC_AddError("Expected "+Chr(34)+"Case"+Chr(34)+", "+Chr(34)+"Default"+Chr(34)+", or "+Chr(34)+"EndSelect"+Chr(34))
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			;Default after case?
			If Defaulted Then
				GSC_AddError("Expected "+Chr(34)+"EndSelect"+Chr(34)+" ("+Chr(34)+"Case"+Chr(34)+" may not proceed "+Chr(34)+"Default"+Chr(34)+")")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			If GSC_TokenType = TOKEN_CASE Then
				GSC_WriteInstruction_L ASM_LBL, LabelCase
				DebugLog "_" + LabelCase
				GSC_ParseExpression2()
				While GSC_TokenType = TOKEN_COMMA
					GSC_WriteInstruction_V ASM_PUSH, REG_EAX
					DebugLog "    push  eax"
					GSC_ParseExpression2()
					GSC_WriteInstruction_V ASM_POP, REG_ECX
					GSC_WriteInstruction_VV ASM_OR, REG_ECX, REG_EAX
					DebugLog "    pop   ecx"
					DebugLog "    or    ecx, eax"
				Wend
				GSC_WriteInstruction_V ASM_VPOP, REG_ECX
				GSC_WriteInstruction_VV ASM_cEQ, REG_ECX, REG_EAX
				GSC_WriteInstruction_L ASM_JZ, LabelNextCase
				DebugLog "    vpop ecx"
				DebugLog "    cEQ  ecx, eax"
				DebugLog "    jz   _" + LabelNextCase
			End If
			If GSC_TokenType = TOKEN_DEFAULT Then
				GSC_WriteInstruction_L ASM_LBL, LabelCase
				DebugLog "_" + LabelCase
				Defaulted = True
				GSC_NextToken()
			End If
			;Teminator
			If GSC_TokenType = TOKEN_TERMINATOR Then
				GSC_NextToken()
			End If
			;Parse statements
			While GSC_TokenType <> TOKEN_CASE And GSC_TokenType <> TOKEN_DEFAULT And GSC_TokenType <> TOKEN_ENDSELECT And GSC_Done = False
				GSC_ParseStatement(True, LabelExit)
			Wend
			GSC_WriteInstruction_L ASM_LBL, LabelExit
			DebugLog "    jmp _" + LabelExit
		Until GSC_TokenType = TOKEN_ENDSELECT Or GSC_Done
		If GSC_Done Then
			GSC_AddError("Expected "+Chr(34)+"EndSelect"+Chr(34))
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		GSC_WriteInstruction_L ASM_LBL, LabelNextCase
		GSC_WriteInstruction_L ASM_LBL, LabelExit
		GSC_WriteInstruction ASM_XPOP
		DebugLog "_" + LabelNextCase
		DebugLog "_" + LabelExit
		DebugLog "    xpop"
		GSC_NextToken()
		
	
	Case TOKEN_CASE, TOKEN_DEFAULT, TOKEN_ENDSELECT
		GSC_AddError("Keyword is out of place (there are no open "+Chr(34)+"select"+Chr(34)+" statements)")
		GSC_NextToken()
		Return
		
	Case TOKEN_WHILE
		LabelExit = GSC_UniqueLabel()
		LabelTop = GSC_UniqueLabel()
		GSC_WriteInstruction_L ASM_LBL, LabelTop
		DebugLog "_" + LabelTop
		GSC_ParseExpression()
		If GSC_TokenType <> TOKEN_TERMINATOR Then
			GSC_AddError("Expected line-end or semicolon")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		GSC_WriteInstruction_L ASM_JZ, LabelExit
		DebugLog "    jz  _" + LabelExit
		GSC_NextToken()
		;Parse statements
		While GSC_TokenType <> TOKEN_ENDWHILE And GSC_Done = False
			GSC_ParseStatement(True, LabelExit)
		Wend
		If GSC_Done Then
			GSC_AddError("Expected "+Chr(34)+"EndWhile"+Chr(34))
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		GSC_WriteInstruction_L ASM_JMP, LabelTop
		GSC_WriteInstruction_L ASM_LBL, LabelExit
		DebugLog "    jmp _" + LabelTop
		DebugLog "_" + LabelExit
		GSC_NextToken()
		
	Case TOKEN_ENDWHILE
		GSC_AddError("Keyword is out of place (there are no open "+Chr(34)+"While"+Chr(34)+" statements)")
		GSC_NextToken()
		Return
		
	Case TOKEN_FOR
		;For
		GSC_NextToken()
		;For x
		If GSC_TokenType <> TOKEN_IDENTIFIER Then
			GSC_AddError("Expected identifier or semicolon")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		;For x = 
		var = GSC_GetVariable(GSC_Token, GSC_CurrentFunc): ID = var
		GSC_NextToken()
		If var = Null Then
			GSC_AddError("Undefined identifier/function")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If	
		If var\Scope = SCOPE_CONSTANT Then
			GSC_AddError("Constants may not be modified")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		While GSC_TokenType = TOKEN_PERIOD
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected custom type field name")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			;Check type field
			If var\DataType <> DATATYPE_CUSTOM Then
				GSC_AddError("Variable "+Chr(34)+var\Name+Chr(34)+" is not a custom type (expected assignment, found "+Chr(34)+"."+Chr(34)+")")
				GSC_NextToken()
				Return
			End If
			found = False
			For i = 1 To var\CustomType\VarFields
				If var\CustomType\VarField[i]\Name = GSC_Token Then found = True: Exit
			Next
			If found = False Then
				GSC_AddError("Type field not found")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			var = var\CustomType\VarField[i]
			TmpFields = TmpFields + 1
			TmpField[TmpFields] = var
			GSC_NextToken()
		Wend
		If GSC_TokenType <> TOKEN_EQ Then
			GSC_AddError("Expected equals symbol followed by assignment")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		;For x = 1
		GSC_ParseExpression()
		If TmpFields = 0 Then
			GSC_WriteInstruction_VV ASM_MOV, ID, REG_EAX
			DebugLog "    mov   ["+ID\Name+"], eax"
		Else
			GSC_WriteInstruction_VV ASM_MOV, REG_ECX, ID
			DebugLog "    mov   ecx, ["+ID\Name+"]"
			For i = 1 To TmpFields-1
				GSC_WriteInstruction_VV ASM_MFC, REG_ECX, TmpField[i]
				DebugLog "    mfc   ecx, <"+TmpField[i]\Name+">"	;mfc a,b = move type field b of custom type object ecx into a
			Next
			GSC_WriteInstruction_VV ASM_XFC, TmpField[TmpFields], REG_EAX
			DebugLog "    xfc   <"+TmpField[TmpFields]\Name+">, eax"	;mfc a,b = move b into type field a is custom type object ecx
		End If
				
		LabelExit = GSC_UniqueLabel()
		LabelTop = GSC_UniqueLabel()
		GSC_WriteInstruction_L ASM_LBL, LabelTop
		DebugLog "_" + LabelTop
		
		;For x = 1 To 
		If GSC_TokenType <> TOKEN_TO Then
			GSC_AddError("Expected "+Chr(34)+"To"+Chr(34))
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;For x = 1 To 5
		GSC_WriteInstruction_VV ASM_MOV, REG_EAX, ID
		DebugLog "    mov   eax, ["+ID\Name+"]"
		For i = 1 To TmpFields-1
			GSC_WriteInstruction_VV ASM_MFA, REG_EAX, ID
			DebugLog "    mfa   eax,<"+TmpField[i]\Name+">"	;mfa a,b = move type field b of custom type object eax into a
		Next
		GSC_WriteInstruction_V ASM_PUSH, REG_EAX
		DebugLog "    push  eax"
		GSC_ParseExpression()
		GSC_WriteInstruction_V ASM_POP, REG_ECX
		DebugLog "    pop   ecx"
		GSC_WriteInstruction_VV ASM_cLE, REG_ECX, REG_EAX
		DebugLog "    cLE   ecx, eax"
		GSC_WriteInstruction_L ASM_JZ, LabelExit
		DebugLog "    jz    _" + LabelExit
		
		;For x = 1 To 5 Step 2
		If GSC_TokenType <> TOKEN_TERMINATOR And GSC_TokenType <> TOKEN_STEP Then
			GSC_AddError("Expected "+Chr(34)+"Step"+Chr(34)+" line-end or semicolon")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		StepValue# = 1
		If GSC_TokenType = TOKEN_STEP Then
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_NUMBER And GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected constant step value")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			If GSC_TokenType = TOKEN_IDENTIFIER Then
				var = GSC_GetVariable(GSC_Token, GSC_CurrentFunc)
				If var\Scope <> SCOPE_CONSTANT Then
					GSC_AddError("Expected constant step value")
					GSC_SkipLine(): GSC_NextToken()
					Return
				End If
				GSC_TokenType = TOKEN_NUMBER
				GSC_Token = var\Value
			End If
			StepValue# = GSC_Token
		End If
		GSC_NextToken()
		
		;Parse statements
		While GSC_TokenType <> TOKEN_NEXT And GSC_Done = False
			GSC_ParseStatement(True, LabelExit)
		Wend
		If GSC_Done Then
			GSC_AddError("Expected "+Chr(34)+"Next"+Chr(34))
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		;Incriment the variable
		GSC_WriteInstruction_VV ASM_MOV, REG_EAX, ID
		DebugLog "    mov   eax, ["+ID\Name+"]"
		For i = 1 To TmpFields-1
			GSC_WriteInstruction_VV ASM_MFA, REG_EAX, TmpField[i]
			DebugLog "    mfa   eax,<"+TmpField[i]\Name+">"	;mfa a,b = move type field b of custom type object eax into a
		Next
		GSC_WriteInstruction_VF ASM_MOV, REG_EBX, StepValue
		GSC_WriteInstruction_VV ASM_ADD, REG_EAX, REG_EBX
		DebugLog "    mov   ebx, "+StepValue
		DebugLog "    add   eax, ebx"
		If TmpFields = 0 Then
			GSC_WriteInstruction_VV ASM_MOV, ID, REG_EAX
			DebugLog "    mov   ["+ID\Name+"], eax"
		Else
			GSC_WriteInstruction_VV ASM_MOV, REG_ECX, ID
			DebugLog "    mov   ecx, ["+ID\Name+"]"
			For i = 1 To TmpFields-1
				GSC_WriteInstruction_VV ASM_MFC, REG_ECX, TmpField[i]
				DebugLog "    mfc   ecx, <"+TmpField[i]\Name+">"	;mfc a,b = move type field b of custom type object ecx into a
			Next
			GSC_WriteInstruction_VV ASM_XFC, TmpField[TmpFields], REG_EAX
			DebugLog "    xfc   <"+TmpField[TmpFields]\Name+">, eax"	;mfc a,b = move b into type field a is custom type object ecx
		End If		
		;Jump to the top
		GSC_WriteInstruction_L ASM_JMP, LabelTop
		GSC_WriteInstruction_L ASM_LBL, LabelExit
		DebugLog "    jmp _" + LabelTop
		DebugLog "_" + LabelExit
		GSC_NextToken()
		
	Case TOKEN_NEXT, TOKEN_TO, TOKEN_STEP
		GSC_AddError("Keyword is out of place (there are no open "+Chr(34)+"for"+Chr(34)+" statements)")
		GSC_NextToken()
		Return
		
	Case TOKEN_REPEAT
		LabelExit = GSC_UniqueLabel()
		LabelTop = GSC_UniqueLabel()
		GSC_WriteInstruction_L ASM_LBL, LabelTop
		DebugLog "_" + LabelTop
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_TERMINATOR Then
			GSC_AddError("Expected line-end or semicolon")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		Repeat
			GSC_NextToken()
		Until GSC_TokenType <> TOKEN_TERMINATOR
		;Parse statements
		While GSC_TokenType <> TOKEN_UNTIL And GSC_TokenType <> TOKEN_FOREVER And GSC_Done = False
			GSC_ParseStatement(True, LabelExit)
		Wend
		If GSC_Done Then
			GSC_AddError("Expected "+Chr(34)+"Until"+Chr(34)+" or "+Chr(34)+"Forever"+Chr(34))
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If GSC_TokenType = TOKEN_UNTIL Then
			GSC_ParseExpression()
			GSC_WriteInstruction_L ASM_JZ, LabelTop
			DebugLog "    jz  _" + LabelTop
		Else
			GSC_WriteInstruction_L ASM_JMP, LabelTop
			DebugLog "    jmp _" + LabelTop
		End If
		GSC_WriteInstruction_L ASM_LBL, LabelExit
		DebugLog "_" + LabelExit
		GSC_NextToken()
		
	Case TOKEN_UNTIL, TOKEN_FOREVER
		GSC_AddError("Keyword is out of place (there are no open "+Chr(34)+"Repeat"+Chr(34)+" statements)")
		GSC_NextToken()
		Return
		
	Case TOKEN_EXIT
		If ExitLine = "" Then
			GSC_AddError("Cannot exit here")
			GSC_NextToken()
			Return
		Else
			GSC_WriteInstruction_L ASM_JMP, ExitLine
			DebugLog "    jmp   _" + ExitLine
			GSC_NextToken()
		End If
		
	Case TOKEN_GOTO
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_IDENTIFIER And GSC_TokenType <> TOKEN_LABEL Then
			GSC_AddError("Expected label name")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		GSC_AddWarning("Usage of Goto is not recommended unless absolutely necessary")
		;TODO
		;GSC_WriteInstruction_L ASM_JMP, GSC_
		DebugLog "    jmp   _Label_" + GSC_Token
		GSC_NextToken()
	
	Case TOKEN_LABEL
		;TODO
		DebugLog "_Label_" + GSC_Token
		GSC_NextToken()
		
	Case TOKEN_FUNCTION	
		;Enter the function
		GSC_NextToken()
		GSC_PreviousFunction = GSC_CurrentFunc
		GSC_CurrentFunc = GSC_GetFunc(GSC_Token)
		Repeat
			GSC_NextToken()
		Until GSC_TokenType = TOKEN_TERMINATOR
		GSC_WriteInstruction_L ASM_JMP, GSC_CurrentFunc\EndLabel
		GSC_WriteInstruction_L ASM_LBL, GSC_CurrentFunc\Label
		DebugLog "    jmp _EndFunction_" + GSC_CurrentFunc\Name
		DebugLog "_Function_" + GSC_CurrentFunc\Name
		;Pop all the functions parameters off the stack
		For i = GSC_CurrentFunc\Params To 1 Step -1
			GSC_WriteInstruction_V ASM_POP, GSC_CurrentFunc\Param[i]
			DebugLog "    pop  ["+GSC_CurrentFunc\Param[i]\Name+"]"
		Next
		;Reset all local variables (not including function parameters)
		var = First Var
		Repeat
			If var\Scope = SCOPE_LOCAL And var\Owner = GSC_CurrentFunc Then
				param = False
				For i = 1 To GSC_CurrentFunc\Params
					If GSC_CUrrentFunc\Param[i] = var Then param = True
				Next
				If param = False Then
					DebugLog "    mov  [" + var\Name + "], "+var\Value
					Select var\DataType
					Case DATATYPE_INTEGER
						GSC_WriteInstruction_VI ASM_MOV, var, var\Value
					Case DATATYPE_FLOAT
						GSC_WriteInstruction_VF ASM_MOV, var, var\Value
					Case DATATYPE_STRING
						GSC_WriteInstruction_VS ASM_MOV, var, var\Value
					End Select
				End If
			End If
			var = After(Var)
		Until var = Null
		;Compile code inside the function
		Repeat
			GSC_ParseStatement(True, ExitLine)
			If GSC_TokenType = TOKEN_RETURN Then
				GSC_NextToken()
				If GSC_TokenType <> TOKEN_TERMINATOR Then
					GSC_PrevToken()
					GSC_ParseExpression()
				Else
					Select GSC_CurrentFunc\ReturnType
					Case DATATYPE_CUSTOM, DATATYPE_INTEGER
						GSC_WriteInstruction_VI ASM_MOV, REG_EAX, 0
						DebugLog "    mov eax, 0"
					Case DATATYPE_FLOAT
						GSC_WriteInstruction_VF ASM_MOV, REG_EAX, 0.0
						DebugLog "    mov eax, 0.0"
					Case DATATYPE_STRING
						GSC_WriteInstruction_VS ASM_MOV, REG_EAX, ""
						DebugLog "    mov eax, "+Chr(34)+Chr(34)
					End Select
				End If
				GSC_WriteInstruction ASM_CPOP
				DebugLog "    cpop"
				GSC_NextToken()
			End If
			If GSC_Done Then
				GSC_AddError("Expected "+Chr(34)+"EndFunction"+Chr(34))
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
		Until GSC_TokenType = TOKEN_ENDFUNCTION
		GSC_WriteInstruction ASM_CPOP
		GSC_WriteInstruction_L ASM_LBL, GSC_CurrentFunc\EndLabel
		DebugLog "    cpop"
		DebugLog "_EndFunction_" + GSC_CurrentFunc\Name	;End-of-function label used for skipping over function execution code (when not called)
		GSC_CurrentFunc = GSC_PreviousFunction
		GSC_NextToken()
		
	Case TOKEN_FUNCTIONNAME
		func = GSC_GetFunc(GSC_Token)
		If func = Null Then
			GSC_AddError("Call to undefined function")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;Push all of the function's local variables to the stack - to be popped off below (this enables recursion)
		var = First Var
		Repeat 
			If var\Scope = SCOPE_LOCAL And var\Owner = func Then
				GSC_WriteInstruction_V ASM_PUSH, var
				DebugLog "    push  [" + var\Name + "]"
			End If
			var = After(Var)
		Until var = Null
		;Push all the parameters to the stack - the function will read these off the stack into it's corrosponding local variables
		ParOpened = False: ParamCount = 0
		GSC_NextToken()
		If GSC_TokenType = TOKEN_OPENPAR Then
			Repeat
				GSC_NextToken()
				If GSC_TokenType = TOKEN_CLOSEPAR Then Exit
				GSC_PrevToken()
				GSC_ParseExpression()
				GSC_WriteInstruction_V ASM_PUSH, REG_EAX
				DebugLog "    push  eax"
				ParamCount = ParamCount + 1
				;ParamType[ParamCount] = GSC_ExpressionDataType
			Until GSC_TokenType <> TOKEN_COMMA
			If GSC_TokenType <> TOKEN_CLOSEPAR Then
				GSC_AddError("Expected closing parenthisis")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			GSC_NextToken()
		Else
			If GSC_TokenType <> TOKEN_TERMINATOR Then
				GSC_PrevToken()
				Repeat
					GSC_NextToken()
					If GSC_TokenType = TOKEN_CLOSEPAR Then Exit
					GSC_PrevToken()
					GSC_ParseExpression()
					GSC_WriteInstruction_V ASM_PUSH, REG_EAX
					DebugLog "    push  eax"
					ParamCount = ParamCount + 1
					;ParamType[ParamCount] = GSC_ExpressionDataType
				Until GSC_TokenType <> TOKEN_COMMA
				If GSC_TokenType = TOKEN_CLOSEPAR Then
					GSC_AddError("Closing parenthisis out of place")
					GSC_SkipLine(): GSC_NextToken()
					Return
				End If
			End If
		End If
		If ParamCount < func\Params Then
			GSC_AddError("Not enough function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If ParamCount > func\Params Then
			GSC_AddError("Too many function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		;Jump to the function, first pushing the execution pointer +2 (which is right after the jmp) to the call stack
		GSC_WriteInstruction_I ASM_CPUSH, 2
		GSC_WriteInstruction_L ASM_JMP, func\Label
		DebugLog "    cpush 2"
		DebugLog "    jmp   _Function_"+func\Name
		;Pop all of the function's local variables from the stack - restore the original variables before the function was called
		var = Last Var
		Repeat 
			If var\Scope = SCOPE_LOCAL And var\Owner = func Then
				GSC_WriteInstruction_V ASM_POP, var
				DebugLog "    pop   [" + var\Name + "]"
			End If
			var = Before(Var)
		Until var = Null
		;Done
		;GSC_NextToken()
	
		
	Case TOKEN_EXTFUNCTIONNAME
		FuncName$ = GSC_Token
		FuncID = GSF_FunctionID(FuncName)
		FuncParams = FUNC_PARAMS[FuncID]
		If FuncID = -1 Then
			GSC_AddError("Call to undefined function")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;Push all the parameters to the stack - the function will read these off the stack into it's corrosponding local variables
		ParOpened = False: ParamCount = 0
		GSC_NextToken()
		If GSC_TokenType = TOKEN_OPENPAR Then
			Repeat
				GSC_NextToken()
				If GSC_TokenType = TOKEN_CLOSEPAR Then Exit
				GSC_PrevToken()
				GSC_ParseExpression()
				GSC_WriteInstruction_V ASM_PUSH, REG_EAX
				DebugLog "    push  eax"
				ParamCount = ParamCount + 1
				;ParamType[ParamCount] = GSC_ExpressionDataType
			Until GSC_TokenType <> TOKEN_COMMA
			If GSC_TokenType <> TOKEN_CLOSEPAR Then
				GSC_AddError("Expected closing parenthisis")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			GSC_NextToken()
		Else
			If GSC_TokenType <> TOKEN_TERMINATOR Then
				GSC_PrevToken()
				Repeat
					GSC_NextToken()
					If GSC_TokenType = TOKEN_CLOSEPAR Then Exit
					GSC_PrevToken()
					GSC_ParseExpression()
					GSC_WriteInstruction_V ASM_PUSH, REG_EAX
					DebugLog "    push  eax"
					ParamCount = ParamCount + 1
					;ParamType[ParamCount] = GSC_ExpressionDataType
				Until GSC_TokenType <> TOKEN_COMMA
				If GSC_TokenType = TOKEN_CLOSEPAR Then
					GSC_AddError("Closing parenthisis out of place")
					GSC_SkipLine(): GSC_NextToken()
					Return
				End If
			End If
		End If
		If ParamCount < FuncParams Then
			GSC_AddError("Not enough function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If ParamCount > FuncParams Then
			GSC_AddError("Too many function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;Call the function
		GSC_WriteInstruction_C ASM_CALL, FuncID
		DebugLog "    call _ExtFunction_"+FuncName

		;Done
		;GSC_NextToken()
	
	Case TOKEN_ENDFUNCTION
		GSC_AddError("Keyword is out of place (all functions are already closed)")
		GSC_NextToken()
		Return
	
	Case TOKEN_RETURN
		GSC_AddError("Cannot return from non-function. Use "+Chr(34)+"End"+Chr(34)+" instead")
		GSC_NextToken()
		Return
		
	Case TOKEN_END
		GSC_WriteInstruction ASM_END
		DebugLog "    end"
		GSC_NextToken()
	
	Case TOKEN_EOF
		GSC_WriteInstruction ASM_END
		DebugLog "    end"
		GSC_Done = True
		Return
	
	Case TOKEN_UNKNOWN, TOKEN_TERMINATOR
		;Give the Unknown Symbol error
		If GSC_TokenType = TOKEN_UNKNOWN Then
			GSC_AddError("Syntax error: Unknown symbol")
		End If
		;Skip the unknown symbol or extra terminator
		GSC_NextToken()
		;This isn't really a statement, and has been skipped, so just return so it won't check for a statement terminator below
		Return
	
	Default
		GSC_AddError("Syntax error")
		GSC_SkipLine(): GSC_NextToken()
		Return
		
	End Select
	
	;Terminator check
	If Termination Then
		If GSC_TokenType = TOKEN_TERMINATOR Or GSC_TokenType = TOKEN_EOF Then
			GSC_NextToken()
		Else
			GSC_AddError("Expected line-end or semicolon")
			GSC_SkipLine(): GSC_NextToken()
			Return		
		End If
	End If
	
	Return
End Function

;[INTERNAL] Parses a statement from the source file, and reads all variable/type definitions
Function GSC_ScanStatement()
	Local ID$, var.Var, this.TypeDef, func.Func
	Local LabelElse$, LabelEndif$

	Select GSC_TokenType
	
	Case TOKEN_GLOBAL
		Repeat
			;Get variable identifier
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected identifier")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			ID = GSC_Token
			;Get variable type and create the variable accordingly
			GSC_NextToken()
			Select GSC_TokenType
			Case TOKEN_INTEGERTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_INTEGER
					var\Scope = SCOPE_GLOBAL
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = "0"
				End If
			Case TOKEN_FLOATTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_FLOAT
					var\Scope = SCOPE_GLOBAL
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = "0.0"
				End If
			Case TOKEN_STRINGTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_STRING
					var\Scope = SCOPE_GLOBAL
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = ""
				End If
			Case TOKEN_CUSTOMTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_CUSTOM
					var\Scope = SCOPE_GLOBAL
					var\TypeDef = Null
					var\CustomTypeTemp = GSC_Token
					var\DefinitionPos = GSC_PrevFilePos
					var\Value = "0"
				End If
			Default
				GSC_AddError("Expected data type")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End Select
			var\CodeNum = GSC_UniqueCode()
			
			GSC_NextToken()
			
			;Get initial value
			If GSC_TokenType = TOKEN_EQ Then
				GSC_NextToken()
				If GSC_TokenType = TOKEN_NUMBER Or GSC_TokenType = TOKEN_STRING Then
					var\Value = GSC_Token
				Else
					If GSC_TokenType = TOKEN_SUB Then
						GSC_NextToken()
						If GSC_TokenType <> TOKEN_NUMBER Then
							GSC_AddError("Expected numeric constant")
							GSC_SkipLine(): GSC_NextToken()
							Return
						End If
						var\Value = "-" + GSC_Token
					Else
						GSC_AddError("Expected numeric constant or string constant")
						GSC_SkipLine(): GSC_NextToken()
						Return
					End If
				End If
				GSC_NextToken()
			End If
		Until GSC_TokenType <> TOKEN_COMMA
		
	Case TOKEN_CONSTANT
		Repeat
			;Get variable identifier
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected identifier")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			ID = GSC_Token
			;Get variable type and create the variable accordingly
			GSC_NextToken()
			Select GSC_TokenType
			Case TOKEN_INTEGERTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate constant name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_INTEGER
					var\Scope = SCOPE_CONSTANT
					var\TypeDef = Null
					var\CustomType = Null
				End If
			Case TOKEN_FLOATTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate constant name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_FLOAT
					var\Scope = SCOPE_CONSTANT
					var\TypeDef = Null
					var\CustomType = Null
				End If
			Case TOKEN_STRINGTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate constant name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_STRING
					var\Scope = SCOPE_CONSTANT
					var\TypeDef = Null
					var\CustomType = Null
				End If
			Case TOKEN_CUSTOMTYPE
				If GSC_GetVariable(ID, GSC_MainFunc) <> Null Then
					GSC_AddError("Duplicate constant name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_MainFunc
					var\DataType = DATATYPE_CUSTOM
					var\Scope = SCOPE_CONSTANT
					var\TypeDef = Null
					var\CustomTypeTemp = GSC_Token
					var\DefinitionPos = GSC_PrevFilePos
				End If
			Default
				GSC_AddError("Expected data type")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End Select
			var\CodeNum = GSC_UniqueCode()
			
			GSC_NextToken()
			
			;Get value
			If GSC_TokenType <> TOKEN_EQ Then
				GSC_AddError("Constant must be assigned a value")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			GSC_NextToken()
			If GSC_TokenType = TOKEN_NUMBER Or GSC_TokenType = TOKEN_STRING Then
				var\Value = GSC_Token
			Else
				If GSC_TokenType = TOKEN_SUB Then
					GSC_NextToken()
					If GSC_TokenType <> TOKEN_NUMBER Then
						GSC_AddError("Expected numeric constant")
						GSC_SkipLine(): GSC_NextToken()
						Return
					End If
					var\Value = "-" + GSC_Token
				Else
					GSC_AddError("Expected numeric constant or string constant")
					GSC_SkipLine(): GSC_NextToken()
					Return
				End If
			End If
			GSC_NextToken()
		Until GSC_TokenType <> TOKEN_COMMA
		
	Case TOKEN_LOCAL
		Repeat
			;Get variable identifier
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected identifier")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			ID = GSC_Token
			;Get variable type and create the variable accordingly
			GSC_NextToken()
			Select GSC_TokenType
			Case TOKEN_INTEGERTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_INTEGER
					var\Scope = SCOPE_LOCAL
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = "0"
				End If
			Case TOKEN_FLOATTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_FLOAT
					var\Scope = SCOPE_LOCAL
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = "0.0"
				End If
			Case TOKEN_STRINGTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_STRING
					var\Scope = SCOPE_LOCAL
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = ""
				End If
			Case TOKEN_CUSTOMTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_CUSTOM
					var\Scope = SCOPE_LOCAL
					var\TypeDef = Null
					var\CustomTypeTemp = GSC_Token
					var\DefinitionPos = GSC_PreFilePos
					var\Value = "0"
				End If
			Default
				GSC_AddError("Expected data type")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End Select
			var\CodeNum = GSC_UniqueCode()
			
			GSC_NextToken()
			
			;Get initial value
			If GSC_TokenType = TOKEN_EQ Then
				GSC_NextToken()
				If GSC_TokenType = TOKEN_NUMBER Or GSC_TokenType = TOKEN_STRING Then
					var\Value = GSC_Token
				Else
					If GSC_TokenType = TOKEN_SUB Then
						GSC_NextToken()
						If GSC_TokenType <> TOKEN_NUMBER Then
							GSC_AddError("Expected numeric constant")
							GSC_SkipLine(): GSC_NextToken()
							Return
						End If
						var\Value = "-" + GSC_Token
					Else
						GSC_AddError("Expected numeric constant or string constant")
						GSC_SkipLine(): GSC_NextToken()
						Return
					End If
				End If
				GSC_NextToken()
			End If
		Until GSC_TokenType <> TOKEN_COMMA
		
	Case TOKEN_STATIC
		Repeat
			;Get variable identifier
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected identifier")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			ID = GSC_Token
			;Get variable type and create the variable accordingly
			GSC_NextToken()
			Select GSC_TokenType
			Case TOKEN_INTEGERTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_INTEGER
					var\Scope = SCOPE_STATIC
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = "0"
				End If
			Case TOKEN_FLOATTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_FLOAT
					var\Scope = SCOPE_STATIC
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = "0.0"
				End If
			Case TOKEN_STRINGTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_STRING
					var\Scope = SCOPE_STATIC
					var\TypeDef = Null
					var\CustomType = Null
					var\Value = ""
				End If
			Case TOKEN_CUSTOMTYPE
				If GSC_GetVariable(ID, GSC_CurrentFunc) <> Null Then
					GSC_AddError("Duplicate variable name")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					var = New Var
					var\Name = ID
					var\Owner = GSC_CurrentFunc
					var\DataType = DATATYPE_CUSTOM
					var\Scope = SCOPE_STATIC
					var\TypeDef = Null
					var\CustomTypeTemp = GSC_Token
					var\DefinitionPos = GSC_PrevFilePos
					var\Value = "0"
				End If
			Default
				GSC_AddError("Expected data type")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End Select
			var\CodeNum = GSC_UniqueCode()
			
			GSC_NextToken()
			
			;Get initial value
			If GSC_TokenType = TOKEN_EQ Then
				GSC_NextToken()
				If GSC_TokenType = TOKEN_NUMBER Or GSC_TokenType = TOKEN_STRING Then
					var\Value = GSC_Token
				Else
					If GSC_TokenType = TOKEN_SUB Then
						GSC_NextToken()
						If GSC_TokenType <> TOKEN_NUMBER Then
							GSC_AddError("Expected numeric constant")
							GSC_SkipLine(): GSC_NextToken()
							Return
						End If
						var\Value = "-" + GSC_Token
					Else
						GSC_AddError("Expected numeric constant or string constant")
						GSC_SkipLine(): GSC_NextToken()
						Return
					End If
				End If
				GSC_NextToken()
			End If
		Until GSC_TokenType <> TOKEN_COMMA
		
	Case TOKEN_BEGINTYPE
		;Get the type name
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_IDENTIFIER Then
			GSC_AddError("Expected identifier")
			GSC_SkipLine(): GSC_NextToken()
			Return		
		End If
		If GSC_GetTypeDef(GSC_Token) <> Null Then
			GSC_AddError("Duplicate type definition name")
			GSC_SkipLine(): GSC_NextToken()
			Return		
		End If
		;Create the type
		this = New TypeDef
		this\CodeNum = GSC_UniqueCode()
		this\Name = GSC_Token
		;Read fields
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_TERMINATOR Then
			GSC_AddError("Expected line-end or semicolon")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		Repeat
			GSC_NextToken()
		Until GSC_TokenType <> TOKEN_TERMINATOR
		Repeat
			If GSC_TokenType = TOKEN_ENDTYPE Then
				GSC_AddWarning("Empty type definition")
				GSC_NextToken()
				Return
			End If
			If GSC_TokenType <> TOKEN_FIELD Then
				GSC_AddError("Expected "+Chr(34)+"Field"+Chr(34))
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			;Read variables in Field keyword
			Repeat
				Repeat
					GSC_NextToken()
				Until GSC_TokenType <> TOKEN_TERMINATOR
				If GSC_TokenType <> TOKEN_IDENTIFIER Then
					GSC_AddError("Expected identifier")
					GSC_SkipLine(): GSC_NextToken()
					Return
				Else
					;Add variable to typdef list
					this\VarFields = this\VarFields + 1
					this\VarField[this\VarFields] = New Var
					var = this\VarField[this\VarFields]
					var\Name = GSC_Token
					var\TypeDef = this
					var\Owner = GSC_MainFunc
					var\Scope = SCOPE_TYPEFIELD
					var\CodeNum = GSC_UniqueCode()
					
					GSC_NextToken()
					Select GSC_TokenType
					Case TOKEN_INTEGERTYPE
						var\DataType = DATATYPE_INTEGER
						var\Value = "0"
					Case TOKEN_FLOATTYPE
						var\DataType = DATATYPE_FLOAT
						var\Value = "0.0"
					Case TOKEN_STRINGTYPE
						var\DataType = DATATYPE_STRING
						var\Value = ""
					Case TOKEN_CUSTOMTYPE
						var\DataType = DATATYPE_CUSTOM
						var\Value = "0"
						var\CustomTypeTemp = GSC_Token
						var\DefinitionPos = GSC_PrevFilePos
					Default
						GSC_AddError("Expected data type")
						GSC_SkipLine(): GSC_NextToken()
						Return
					End Select
				End If
				GSC_NextToken()
			Until GSC_TokenType <> TOKEN_COMMA
			
			If GSC_TokenType <> TOKEN_TERMINATOR Then
				GSC_AddError("Expected comma, semicolon, or line-end")
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
			
			Repeat
				GSC_NextToken()
			Until GSC_TokenType <> TOKEN_TERMINATOR
		Until GSC_TokenType <> TOKEN_FIELD
		;EndType
		If GSC_TokenType <> TOKEN_ENDTYPE Then
			GSC_AddError("Expected "+Chr(34)+"EndType"+Chr(34))
			GSC_SkipLine(): GSC_NextToken()
			Return		
		End If
		GSC_NextToken()
	
	Case TOKEN_ENDTYPE, TOKEN_FIELD
		GSC_AddError("Keyword is out of place (there are no open "+Chr(34)+"Type"+Chr(34)+" statements)")
		GSC_NextToken()
		Return		
			
	Case TOKEN_EOF
		GSC_Done = True
		Return
		
	Case TOKEN_TERMINATOR
		If GSC_Token = Chr(13) Or GSC_Token = Chr(10) Then
			;Skip ahead quickly until something processible comes up
			SeekFile GSC_InputFile, FilePos(GSC_InputFile)-1
			If GSC_ScanLines() = False Then
				GSC_Done = True
				Return
			End If
		End If
		GSC_NextToken()
		
	Case TOKEN_FUNCTION
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_IDENTIFIER And GSC_TokenType <> TOKEN_FUNCTIONNAME Then
			GSC_AddError("Expected function name")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If GSC_GetFunc(GSC_Token) <> Null Then
			GSC_AddError("Duplicate function name")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If GSC_FindVariable(GSC_Token) <> Null Then
			GSC_AddError("Duplicate identifier: function name already in use by variable")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If GSC_GetTypeDef(GSC_Token) <> Null Then
			GSC_AddError("Duplicate identifier: function name already in use by type definition")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		func = New Func
		func\Name = GSC_Token
		func\Label = GSC_UniqueLabel(): func\EndLabel = GSC_UniqueLabel()
		GSC_NextToken()

		Select GSC_TokenType
		Case TOKEN_INTEGERTYPE
			func\ReturnType = DATATYPE_INTEGER
			GSC_NextToken()
		Case TOKEN_FLOATTYPE
			func\ReturnType = DATATYPE_FLOAT
			GSC_NextToken()
		Case TOKEN_STRINGTYPE
			func\ReturnType = DATATYPE_STRING
			GSC_NextToken()
		Case TOKEN_CUSTOMTYPE
			func\ReturnType = DATATYPE_CUSTOM
			GSC_NextToken()
		Case TOKEN_VOIDTYPE
			func\ReturnType = DATATYPE_INTEGER
			GSC_NextToken()
		Default
			;GSC_AddError("Expected function data type")
			;GSC_SkipLine(): GSC_NextToken()
			;Return
			func\ReturnType = DATATYPE_INTEGER
		End Select
		If GSC_TokenType <> TOKEN_OPENPAR Then
			GSC_AddError("Expected opening parenthisis")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		;Parse function parameters
		Repeat
			Repeat
				GSC_NextToken()
				If GSC_Done Then
					GSC_AddError("Expected "+Chr(34)+"EndFunction"+Chr(34))
					GSC_SkipLine(): GSC_NextToken()
					Return
				End If
			Until GSC_TokenType <> TOKEN_TERMINATOR
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				Exit
			Else
				;Add variable to parameter list
				;If GSC_GetVariable(GSC_Token, func) <> Null Then
				;	GSC_AddError("Duplicate variable name")
				;	GSC_SkipLine(): GSC_NextToken()
				;	Return
				;End If
				func\Params = func\Params + 1
				func\Param[func\Params] = New Var
				var = func\Param[func\Params]
				var\Name = GSC_Token
				var\Owner = func
				var\Scope = SCOPE_LOCAL
				var\CodeNum = GSC_UniqueCode()
				
				GSC_NextToken()
				Select GSC_TokenType
				Case TOKEN_INTEGERTYPE
					var\DataType = DATATYPE_INTEGER
				Case TOKEN_FLOATTYPE
					var\DataType = DATATYPE_FLOAT
				Case TOKEN_STRINGTYPE
					var\DataType = DATATYPE_STRING
				Case TOKEN_CUSTOMTYPE
					var\DataType = DATATYPE_CUSTOM
					var\CustomTypeTemp = GSC_Token
					var\DefinitionPos = GSC_PrevFilePos
				Default
					GSC_AddError("Expected parameter data type")
					GSC_SkipLine(): GSC_NextToken()
					Return
				End Select
			End If
			GSC_NextToken()
		Until GSC_TokenType <> TOKEN_COMMA
		If GSC_TokenType <> TOKEN_CLOSEPAR Then
			GSC_AddError("Expected closing parenthisis")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		GSC_NextToken()
		GSC_PreviousFunction = GSC_CurrentFunc
		GSC_CurrentFunc = func
		Repeat
			GSC_ScanStatement()
			If GSC_Done
				GSC_AddError("Expected "+Chr(34)+"EndFunction"+Chr(34))
				GSC_SkipLine(): GSC_NextToken()
				Return
			End If
		Until GSC_TokenType = TOKEN_ENDFUNCTION
		GSC_CurrentFunc = GSC_PreviousFunction 
		
	Default
		;GSC_SkipLine()
		GSC_NextToken()
		Return
		
	End Select
	
	Return
End Function

;[INTERNAL]This compiles an expression. The expression result is stored in eax, and it's datatype indicated by GSC_ExpressionDataType
Function GSC_ParseExpression()
	GSC_ParseExpression2()
	Repeat
		Select GSC_TokenType
		Case TOKEN_AND
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseExpression2()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_AND, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    and   ecx, eax"
			
		Case TOKEN_OR
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseExpression2()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_OR, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    or    ecx, eax"
			
		Case TOKEN_XOR
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseExpression2()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_XOR, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    xor   ecx, eax"
			
		Default Return
		End Select
	Forever
End Function

;[INTERNAL]This compiles a subexpression
Function GSC_ParseExpression2()
	GSC_ParseMath1()
	Repeat
		Select GSC_TokenType
		
		Case TOKEN_LT
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath1()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_cLT, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    cLT   ecx,eax"
			
		Case TOKEN_GT
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath1()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_cGT, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    cGT   ecx,eax"
			
		Case TOKEN_LE
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath1()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_cLE, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    cLE   ecx,eax"
			
		Case TOKEN_GE
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath1()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_cGE, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    cGE   ecx,eax"
			
		Case TOKEN_EQ
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath1()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_cEQ, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    cEQ   ecx,eax"
			
		Case TOKEN_NE
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath1()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_cNE, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    cNE   ecx,eax"
			
		Default Return
		End Select
	Forever
End Function

;[INTERNAL]This compiles addition/subtraction operators
Function GSC_ParseMath1()
	GSC_ParseMath2()
	Repeat
		Select GSC_TokenType
		
		Case TOKEN_ADD
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath2()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_ADD, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    add   ecx,eax"
			
		Case TOKEN_SUB
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseMath2()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_SUB, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    sub   ecx,eax"
			
		Default
			Return
		End Select
	Forever
End Function

;[INTERNAL]This compiles multiplication/divition operators

Function GSC_ParseMath2()
	GSC_ParseBinary()
	Repeat
		Select GSC_TokenType
	
		Case TOKEN_MUL
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseBinary()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_MUL, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    mul   ecx,eax"
			
		Case TOKEN_DIV
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseBinary()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_DIV, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    div   ecx,eax"
			
		Case TOKEN_MOD
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseBinary()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_MOD, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    mod   ecx,eax"
			
		Default
			Return
		End Select
	Forever
End Function

;[INTERNAL]This compiles binary shift operators

Function GSC_ParseBinary()
	GSC_ParseLeaf()
	Repeat
		Select GSC_TokenType
	
		Case TOKEN_SHL
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseLeaf()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_SHL, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    shl   ecx,eax"
			
		Case TOKEN_SHR
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			GSC_ParseLeaf()
			GSC_WriteInstruction_V ASM_POP, REG_ECX
			GSC_WriteInstruction_VV ASM_SHR, REG_ECX, REG_EAX
			DebugLog "    pop   ecx"
			DebugLog "    shr   ecx,eax"

		Default
			Return			
		End Select
	Forever
End Function

;[INTERNAL]This compiles parenthisis/variables/strings/numbers
Function GSC_ParseLeaf()
	Local var.Var, def.TypeDef, i, found, func.Func, ID$

	GSC_NextToken()
	Select GSC_TokenType
		
	Case TOKEN_NEW
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_IDENTIFIER Then
			GSC_AddError("Expected custom data type")
			Return
		End If
		def = GSC_GetTypeDef(GSC_Token)
		If def = Null Then
			GSC_AddError("Undefined custom data type")
			Return
		End If
		GSC_WriteInstruction_VT ASM_NEW, REG_EAX, def
		DebugLog "    new   eax,{"+def\Name+"}"	;new a, b = copy the reference of a new instance of b into a
		GSC_NextToken()

	Case TOKEN_NOT
		GSC_ParseLeaf()
		GSC_WriteInstruction_V ASM_NOT, REG_EAX
		DebugLog "    not   eax"

	Case TOKEN_SUB
		GSC_ParseLeaf()
		GSC_WriteInstruction_V ASM_NEG, REG_EAX
		DebugLog "    neg   eax"
		
	Case TOKEN_OPENPAR
		GSC_ParseExpression()
		If GSC_TokenType <> TOKEN_CLOSEPAR Then
			GSC_AddError("Expected closing parenthisis")
			Return
		End If
		GSC_NextToken()
		
	Case TOKEN_FUNCTIONNAME
		ID = GSC_Token
		func = GSC_GetFunc(ID)
		If func = Null Then
			GSC_AddError("Call to undefined function")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;Push all of the current function's local variables to the stack - to be popped off below (this enables recursion)
		var = First Var
		Repeat
			If var\Scope = SCOPE_LOCAL And var\Owner = GSC_CurrentFunc Then
				DebugLog "    push  [" + var\Name + "]"
				GSC_WriteInstruction_V ASM_PUSH, var
			End If
			var = After(Var)
		Until var = Null
		
		;Push all the parameters to the stack - the function will read these off the stack into it's corrosponding local variables
		ParamCount = 0
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_OPENPAR Then
			GSC_AddError("Expected opening parenthisis")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		Repeat
			GSC_NextToken()
			If GSC_TokenType = TOKEN_CLOSEPAR Then Exit
			GSC_PrevToken()
			GSC_ParseExpression()
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			ParamCount = ParamCount + 1
		Until GSC_TokenType <> TOKEN_COMMA
		If GSC_TokenType <> TOKEN_CLOSEPAR Then
			GSC_AddError("Expected closing parenthisis")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If ParamCount < func\Params Then
			GSC_AddError("Not enough function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If ParamCount > func\Params Then
			GSC_AddError("Too many function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;Jump to the function, first pushing the execution pointer +2 (which is right after the jmp) to the call stack
		GSC_WriteInstruction_I ASM_CPUSH, 2
		GSC_WriteInstruction_L ASM_JMP, func\Label
		DebugLog "    cpush 2"
		DebugLog "    jmp   _Function_"+ID
		
		;Pop all of the current function's local variables from the stack - restore the original variables before the function was called
		var = Last Var
		Repeat 
			If var\Scope = SCOPE_LOCAL And var\Owner = GSC_CurrentFunc Then
				GSC_WriteInstruction_V ASM_POP, var
				DebugLog "    pop   [" + var\Name + "]"
			End If
			var = Before(Var)
		Until var = Null
		;Done
		If func\ReturnType > GSC_ExpressionDataType Then GSC_ExpressionDataType = func\ReturnType
		GSC_NextToken()
		
	Case TOKEN_EXTFUNCTIONNAME
		FuncName$ = GSC_Token
		FuncID = GSF_FunctionID(FuncName)
		FuncParams = FUNC_PARAMS[FuncID]
		If FuncID = -1 Then
			GSC_AddError("Call to undefined function")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;Push all the parameters to the stack - the function will read these off the stack into it's corrosponding local variables
		ParOpened = False: ParamCount = 0
		GSC_NextToken()
		If GSC_TokenType <> TOKEN_OPENPAR Then
			GSC_AddError("Expected opening parenthisis")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		Repeat
			GSC_NextToken()
			If GSC_TokenType = TOKEN_CLOSEPAR Then Exit
			GSC_PrevToken()
			GSC_ParseExpression()
			GSC_WriteInstruction_V ASM_PUSH, REG_EAX
			DebugLog "    push  eax"
			ParamCount = ParamCount + 1
			;ParamType[ParamCount] = GSC_ExpressionDataType
		Until GSC_TokenType <> TOKEN_COMMA
		If GSC_TokenType <> TOKEN_CLOSEPAR Then
			GSC_AddError("Expected closing parenthisis")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If ParamCount < FuncParams Then
			GSC_AddError("Not enough function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		If ParamCount > FuncParams Then
			GSC_AddError("Too many function parameters")
			GSC_SkipLine(): GSC_NextToken()
			Return
		End If
		
		;Call the function
		GSC_WriteInstruction_C ASM_CALL, FuncID
		DebugLog "    call _ExtFunction_"+FuncName

		;Done
		GSC_NextToken()

	Case TOKEN_IDENTIFIER
		var = GSC_GetVariable(GSC_Token, GSC_CurrentFunc)
		If var = Null Then
			GSC_AddError("Undefined identifier")
			GSC_NextToken()
			Return
		End If
		If var\Scope = SCOPE_CONSTANT Then
			If var\DataType = DATATYPE_INTEGER Then
				GSC_WriteInstruction_VI ASM_MOV, REG_EAX, var\Value
				DebugLog "    mov   eax,"+var\Value+""
			End If
			If var\DataType = DATATYPE_FLOAT Then
				GSC_WriteInstruction_VF ASM_MOV, REG_EAX, var\Value
				DebugLog "    mov   eax,"+var\Value+""
			End If
			If var\DataType = DATATYPE_STRING Then
				GSC_WriteInstruction_VS ASM_MOV, REG_EAX, var\Value
				DebugLog "    mov   eax,"+Chr(34)+var\Value+Chr(34)
			End If
		Else
			GSC_WriteInstruction_VV ASM_MOV, REG_EAX, var
			DebugLog "    mov   eax,["+GSC_Token+"]"
		End If
		GSC_NextToken()
		While GSC_TokenType = TOKEN_PERIOD
			GSC_NextToken()
			If GSC_TokenType <> TOKEN_IDENTIFIER Then
				GSC_AddError("Expected custom type field name")
				GSC_NextToken()
				Return
			End If
			;Check type field
			If var\DataType <> DATATYPE_CUSTOM Then
				GSC_AddError("Variable "+Chr(34)+var\Name+Chr(34)+" is not a custom type (expected expression, found "+Chr(34)+"."+Chr(34)+")")
				GSC_NextToken()
				Return
			End If
			found = False
			For i = 1 To var\CustomType\VarFields
				If var\CustomType\VarField[i]\Name = GSC_Token Then found = True: Exit
			Next
			If found = False Then
				GSC_AddError("Type field not found")
				GSC_NextToken()
				Return
			End If
			var = var\CustomType\VarField[i]
			GSC_WriteInstruction_VV ASM_MFA, REG_EAX, var
			DebugLog "    mfa   eax,<"+GSC_Token+">"	;mfa a,b = move type field b of custom type object eax into a
			GSC_NextToken()
		Wend
		If var\DataType > GSC_ExpressionDataType Then GSC_ExpressionDataType = var\DataType
	
	Case TOKEN_NUMBER
		DebugLog "    mov   eax," + GSC_Token
		If Instr(GSC_Token, ".") Then
			GSC_WriteInstruction_VF ASM_MOV, REG_EAX, Float(GSC_Token)
			If DATATYPE_FLOAT > GSC_ExpressionDataType Then GSC_ExpressionDataType = DATATYPE_FLOAT
		Else
			GSC_WriteInstruction_VI ASM_MOV, REG_EAX, Int(GSC_Token)
			If DATATYPE_INTEGER > GSC_ExpressionDataType Then GSC_ExpressionDataType = DATATYPE_INTEGER
		End If
		GSC_NextToken()
	
	Case TOKEN_STRING
		GSC_WriteInstruction_VS ASM_MOV, REG_EAX, GSC_Token
		DebugLog "    mov   eax," + Chr(34) + GSC_Token + Chr(34)
		If DATATYPE_STRING > GSC_ExpressionDataType Then GSC_ExpressionDataType = DATATYPE_STRING
		GSC_NextToken()
	
	Default
		GSC_AddError("Expected expression")
		Return
	End Select
	Return
End Function

;[INTERNAL] This function returns a un-used label name
Function GSC_UniqueLabel()
	GSC_LabelIndex = GSC_LabelIndex + 1
	Return GSC_LabelIndex
End Function

;[INTERNAL] This function returns a un-used variable ID code
Function GSC_UniqueCode()
	GSC_CodeIndex = GSC_CodeIndex + 1
	Return GSC_CodeIndex
End Function

;[INTERNAL] This function parses the previous "token" in the script file, putting it in GSC_Token$ and GSC_TokenType for later access.
;Note: This can be used only ONCE after each GSC_NextToken() command.
Function GSC_PrevToken()
	GSC_Token = GSC_PrevToken
	GSC_TokenType = GSC_PrevTokenType
	GSC_Ch = GSC_PrevCh
	SeekFile GSC_InputFile, GSC_PrevFilePos
End Function

;[INTERNAL] This function parses the next "token" in the script file, putting it in GSC_Token$ and GSC_TokenType for later access.
Function GSC_NextToken()
	;Save the old token data
	GSC_PrevToken = GSC_Token
	GSC_PrevTokenType = GSC_TokenType
	GSC_PrevCh = GSC_Ch
	
	;Save the old file "pointer" for error message location
	GSC_PrevFilePos = FilePos(GSC_InputFile)
	
	;Skip blank spaces
	Repeat
		GSC_Token = GSC_Ch
		GSC_NextCh()
	Until GSC_Token <> " " And GSC_Token <> Chr(9)
	
	;String
	If GSC_Token = Chr(34) Then
		GSC_Token = ""
		If GSC_Ch <> Chr(34) Then
			Repeat
				GSC_Token = GSC_Token + GSC_Ch
				GSC_NextCh(False)
			Until GSC_Ch = Chr(34) Or GSC_Ch = Chr(13) Or GSC_Ch = Chr(0)
		End If
		If GSC_Ch = Chr(34) Then GSC_NextCh()
		GSC_TokenType = TOKEN_STRING
		Return
	End If
	
	;Number
	If (GSC_Token >= "0" And GSC_Token <= "9") Or (GSC_Token = "." And GSC_Ch >= "0" And GSC_Ch <= "9")
		Repeat
			If ((GSC_Ch < "0" Or GSC_Ch > "9") And GSC_Ch <> ".") Or GSC_Ch = Chr(13) Or GSC_Ch = Chr(0) Then GSC_TokenType = TOKEN_NUMBER: Return
			GSC_Token = GSC_Token + GSC_Ch
			GSC_NextCh()
		Forever
	EndIf
	
	;Keyword/Function/Identifier
	If (GSC_Token >= "a" And GSC_Token <= "z") Or (GSC_Token >= "A" And GSC_Token <= "Z") Or GSC_Token = "_"
		Repeat
			If (GSC_Ch < "a" Or GSC_Ch > "z") And (GSC_Ch < "A" Or GSC_Ch > "Z") And (GSC_Ch < "0" Or GSC_Ch > "9") And GSC_Ch <> "_"
				;Change to lower case so it's not case sensative
				GSC_Token = Lower(GSC_Token)

				;Key data types
				;Select GSC_Token
				;Case "int", "integer"
				;	GSC_TokenType = TOKEN_INTEGERTYPE
				;	Return
				;Case "float"
				;	GSC_TokenType = TOKEN_FLOATTYPE
				;	Return
				;Case "str", "string"
				;	GSC_TokenType = TOKEN_STRINGTYPE
				;	Return
				;End Select

				;Keyword
				Select GSC_Token
				Case "new"
					GSC_TokenType = TOKEN_NEW
					Return
				Case "delete"
					GSC_TokenType = TOKEN_DELETE
					Return
				Case "and"
					GSC_TokenType = TOKEN_AND
					Return
				Case "or"
					GSC_TokenType = TOKEN_OR
					Return
				Case "xor"
					GSC_TokenType = TOKEN_XOR
					Return
				Case "not"
					GSC_TokenType = TOKEN_NOT
					Return
				Case "let"
					GSC_TokenType = TOKEN_LET
					Return
				Case "local"
					GSC_TokenType = TOKEN_LOCAL
					Return
				Case "static"
					GSC_TokenType = TOKEN_STATIC
					Return
				Case "global"
					GSC_TokenType = TOKEN_GLOBAL
					Return
				Case "const"
					GSC_TokenType = TOKEN_CONSTANT
					Return
				Case "if"
					GSC_TokenType = TOKEN_IF
					Return
				Case "then"
					GSC_TokenType = TOKEN_THEN
					Return
				Case "else"
					GSC_TokenType = TOKEN_ELSE
					Return
				Case "endif"
					GSC_TokenType = TOKEN_ENDIF
					Return
				Case "end"
					GSC_TokenType = TOKEN_END
					Return
				Case "type"
					GSC_TokenType = TOKEN_BEGINTYPE
					Return
				Case "endtype"
					GSC_TokenType = TOKEN_ENDTYPE
					Return
				Case "field"
					GSC_TokenType = TOKEN_FIELD
					Return
				Case "shr"
					GSC_TokenType = TOKEN_SHR
					Return
				Case "shl"
					GSC_TokenType = TOKEN_SHL
					Return
				Case "mod"
					GSC_TokenType = TOKEN_MOD
					Return
				Case "function"
					GSC_TokenType = TOKEN_FUNCTION
					Return
				Case "return"
					GSC_TokenType = TOKEN_RETURN
					Return
				Case "endfunction"
					GSC_TokenType = TOKEN_ENDFUNCTION
					Return
				Case "while"
					GSC_TokenType = TOKEN_WHILE
					Return
				Case "wend", "whileend", "endwhile"
					GSC_TokenType = TOKEN_ENDWHILE
					Return
				Case "repeat"
					GSC_TokenType = TOKEN_REPEAT
					Return
				Case "until"
					GSC_TokenType = TOKEN_UNTIL
					Return
				Case "forever"
					GSC_TokenType = TOKEN_FOREVER
					Return
				Case "for"
					GSC_TokenType = TOKEN_FOR
					Return
				Case "to"
					GSC_TokenType = TOKEN_TO
					Return
				Case "step"
					GSC_TokenType = TOKEN_STEP
					Return
				Case "next"
					GSC_TokenType = TOKEN_NEXT
					Return
				Case "exit"
					GSC_TokenType = TOKEN_EXIT
					Return
				Case "select"
					GSC_TokenType = TOKEN_SELECT
					Return
				Case "case"
					GSC_TokenType = TOKEN_CASE
					Return
				Case "default"
					GSC_TokenType = TOKEN_DEFAULT
					Return
				Case "endselect"
					GSC_TokenType = TOKEN_ENDSELECT
					Return
				Case "goto"
					GSC_TokenType = TOKEN_GOTO
					Return
				;Case "print"
				;	GSC_TokenType = TOKEN_PRINT
				;	Return
				End Select 
				
				;Function
				If GSC_GetFunc(GSC_Token) <> Null Then
					GSC_TokenType = TOKEN_FUNCTIONNAME
					Return
				ElseIf GSF_FunctionID(GSC_Token) <> -1
					GSC_TokenType = TOKEN_EXTFUNCTIONNAME
					Return
				End If
				
				;Identifier
				GSC_TokenType = TOKEN_IDENTIFIER
				Return
			EndIf
			GSC_Token = GSC_Token + GSC_Ch
			GSC_NextCh()
		Forever
	EndIf
	
	;Data types
	If GSC_Token = ":" Then
		GSC_Token = GSC_Ch
		GSC_NextCh()
		If (GSC_Token >= "a" And GSC_Token <= "z") Or (GSC_Token >= "A" And GSC_Token <= "Z") Or GSC_Token = "_"
			Repeat
				If (GSC_Ch < "a" Or GSC_Ch >= "z") And (GSC_Ch < "A" Or GSC_Ch >= "Z") And (GSC_Ch < "0" Or GSC_Ch > "9") And GSC_Ch <> "_"
					;Change to lower case so it's not case sensative
					GSC_Token = Lower(GSC_Token)
				
					;Key data types
					Select GSC_Token
					Case "int", "integer"
						GSC_TokenType = TOKEN_INTEGERTYPE
						Return
					Case "flt", "float"
						GSC_TokenType = TOKEN_FLOATTYPE
						Return
					Case "str", "string"
						GSC_TokenType = TOKEN_STRINGTYPE
						Return
					Case "void"
						GSC_TokenType = TOKEN_VOIDTYPE
						Return
					End Select
					
					;Custom data type
					GSC_TokenType = TOKEN_CUSTOMTYPE
					Return
				EndIf
				GSC_Token = GSC_Token + GSC_Ch
				GSC_NextCh()
			Forever
		EndIf
	End If
	
	;Labels
	If GSC_Token = "@" Then
		GSC_Token = GSC_Ch
		GSC_NextCh()
		If (GSC_Token >= "a" And GSC_Token <= "z") Or (GSC_Token >= "A" And GSC_Token <= "Z") Or GSC_Token = "_"
			Repeat
				If (GSC_Ch < "a" Or GSC_Ch >= "z") And (GSC_Ch < "A" Or GSC_Ch >= "Z") And (GSC_Ch < "0" Or GSC_Ch > "9") And GSC_Ch <> "_"
					GSC_Token = Lower(GSC_Token)
					GSC_TokenType = TOKEN_LABEL
					Return
				EndIf
				GSC_Token = GSC_Token + GSC_Ch
				GSC_NextCh()
			Forever
		EndIf
	End If
	
	;Operators
	Select GSC_Token
	Case "," GSC_TokenType = TOKEN_COMMA: Return
	Case "." GSC_TokenType = TOKEN_PERIOD: Return
	Case "+" GSC_TokenType = TOKEN_ADD: Return
	Case "-" GSC_TokenType = TOKEN_SUB: Return
	Case "*" GSC_TokenType = TOKEN_MUL: Return
	Case "/" GSC_TokenType = TOKEN_DIV: Return
	Case "(" GSC_TokenType = TOKEN_OPENPAR: Return
	Case ")" GSC_TokenType = TOKEN_CLOSEPAR: Return
	Case "%" GSC_TokenType = TOKEN_MOD: Return
	Case "="
		If GSC_Ch="<" GSC_NextCh(): GSC_TokenType = TOKEN_LE: Return
		If GSC_Ch=">" GSC_NextCh(): GSC_TokenType = TOKEN_GE: Return
		If GSC_Ch="=" GSC_NextCh(): GSC_TokenType = TOKEN_EQ: Return
		GSC_TokenType = TOKEN_EQ
		Return
	Case "<"
		If GSC_Ch="=" GSC_NextCh(): GSC_TokenType = TOKEN_LE: Return
		If GSC_Ch=">" GSC_NextCh(): GSC_TokenType = TOKEN_NE: Return
		If GSC_Ch="<" GSC_NextCh(): GSC_TokenType = TOKEN_SHL: Return
		GSC_TokenType = TOKEN_LT
		Return
	Case ">"
		If GSC_Ch="=" GSC_NextCh(): GSC_TokenType = TOKEN_GE: Return
		If GSC_Ch="<" GSC_NextCh(): GSC_TokenType = TOKEN_NE: Return
		If GSC_Ch=">" GSC_NextCh(): GSC_TokenType = TOKEN_SHR: Return
		GSC_TokenType = TOKEN_GT
		Return
	Case "!"
		If GSC_Ch="=" GSC_NextCh(): GSC_TokenType = TOKEN_NE: Return
	End Select
	
	;Special
	Select GSC_Token
	Case Chr(0)
		GSC_Token = ""
		GSC_TokenType = TOKEN_EOF
		Return
	Case ";", Chr(13), Chr(10)
		GSC_TokenType = TOKEN_TERMINATOR
		Return	
	End Select
	
	;If all else fails, it's a syntax error
	GSC_TokenType = TOKEN_UNKNOWN
End Function

;[INTERNAL] This function reads the next character in the script file, putting it in GSC_Ch$ for later access.
Function GSC_NextCh(SkipComments = True)
	;(Skip comments)
	If Eof(GSC_InputFile) Then GSC_Ch = Chr(0): Return
	GSC_Ch = Chr(ReadByte(GSC_InputFile))
	If SkipComments And GSC_Ch = "'" Then
		While GSC_Ch <> Chr(13)
			If Eof(GSC_InputFile) Then GSC_Ch = Chr(0): Return
			GSC_Ch = Chr(ReadByte(GSC_InputFile))
		Wend
		If Eof(GSC_InputFile) Then GSC_Ch = Chr(0): Return
		GSC_Ch = Chr(ReadByte(GSC_InputFile))
	End If
End Function

;[INTERNAL] Skips the contents of a line (or to the next terminator) (usually used when a line contains errors)
Function GSC_SkipLine()
	Repeat
		GSC_NextCh()
	Until GSC_Ch = ";" Or GSC_Ch = Chr(13) Or GSC_Ch = Chr(10) Or GSC_Ch = Chr(0)
End Function

;[INTERNAL] Skips through the contents of the input file until something processible by GSC_ScanStatement() is found.
;If nothing is found False is returned, else True is returned.
Function GSC_ScanLines()
	Local lin$, pos, ok
	
	;Scan
	ok = False
	Repeat
		;Read
		pos = FilePos(GSC_InputFile)
		lin = Lower(Trim(ReadLine(GSC_InputFile)))
		;Ignore comments
		;c = Instr(lin, "'")
		;If c <> 0 Then lin = Left(lin, c)
		;Check
		If lin <> "" Then
			If Instr(lin, "local") Then ok = True: Exit
			If Instr(lin, "global") Then ok = True: Exit
			If Instr(lin, "static") Then ok = True: Exit
			If Instr(lin, "const") Then ok = True: Exit
			If Instr(lin, "type") Then ok = True: Exit
			If Instr(lin, "field") Then ok = True: Exit
			If Instr(lin, "endtype") Then ok = True: Exit
			If Instr(lin, "function") Then ok = True: Exit
		End If
	Until Eof(GSC_InputFile)
	
	If ok = False Then
		;Nothing more
		Return False
	Else
		SeekFile GSC_InputFile, pos
		GSC_NextCh()
		Return True
	End If
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction(Instruction)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	WriteByte GSC_OutputFile, FILE_NULL
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_VV(Instruction, a.Var, b.Var)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_VAR_VAR	;Data header
	WriteInt GSC_OutputFile, a\CodeNum
	WriteInt GSC_OutputFile, b\CodeNum
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_V(Instruction, a.Var)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_VAR		;Data header
	WriteInt GSC_OutputFile, a\CodeNum
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_L(Instruction, a)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_LBL		;Data header
	WriteInt GSC_OutputFile, a
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_I(Instruction, a)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_INT		;Data header
	WriteInt GSC_OutputFile, a
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_VF(Instruction, a.Var, b#)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_VAR_FLT	;Data header
	WriteInt GSC_OutputFile, a\CodeNum
	WriteFloat GSC_OutputFile, b
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_VI(Instruction, a.Var, b)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_VAR_INT	;Data header
	WriteInt GSC_OutputFile, a\CodeNum
	WriteInt GSC_OutputFile, b
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_VS(Instruction, a.Var, b$)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_VAR_STR	;Data header
	WriteInt GSC_OutputFile, a\CodeNum
	WriteString GSC_OutputFile, b
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_VT(Instruction, a.Var, b.TypeDef)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_VAR_TYP	;Data header
	WriteInt GSC_OutputFile, a\CodeNum
	WriteInt GSC_OutputFile, b\CodeNum
End Function

;[INTERNAL] Writes instruction data to the file. Instruction should be ASM_<instruction>.
Function GSC_WriteInstruction_C(Instruction, FunctionID)
	;Write instuction code
	WriteByte GSC_OutputFile, Instruction
	
	;Write the data
	WriteByte GSC_OutputFile, FILE_CALL	;Data header
	WriteInt GSC_OutputFile, FunctionID
End Function