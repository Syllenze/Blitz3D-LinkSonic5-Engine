;__ GameScript [Virtual Machine] _________________________________________
;Copyright (C) 2005			John Judnich
Global tel

Global GSV_Message$, GSV_MessageFile$, GSV_MessageInstr, GSV_MessageThread.GSV_Thread

;Execution Thread
Const MAX_VARS = 1024
Const MAX_INSTRUCTIONS = 4096
Const CALLSTACK_SIZE = 255
Const DATASTACK_SIZE = 255

Type GSV_Thread
	Field Name$
	Field Paused
	Field ResumeTime	;Used when thread execution is delayed by script
	Field Variable.GSV_Var[MAX_VARS]
	Field TypeDef.GSV_TypeDef[MAX_VARS]
	Field Instruction.GSV_InstrCode[MAX_INSTRUCTIONS]
	Field Instructions, InstructionPointer
	Field EAX.GSV_Var, EBX.GSV_Var, ECX.GSV_Var
	Field DataStack.GSV_Var[DATASTACK_SIZE], DataStackLevel
	Field CallStack[CALLSTACK_SIZE], CallStackLevel
End Type

;VM Variable Memory
Const VAR_INTEGER = 0, VAR_FLOAT = 1, VAR_STRING = 2, VAR_CUSTOM = 3	;Numbers in order of precedence, 0 being lowest
Const VAR_NONE = -1	;Used to specify no return value for external functions
Type GSV_Var
	Field I%, F#, S$, C.GSV_Type
	Field DataType, TypeDef.GSV_TypeDef
	Field Auto
	Field Thread.GSV_Thread
End Type
Const MAX_FIELDS = 64
Type GSV_TypeDef
	Field VarFields
	Field VarField_DataType[MAX_FIELDS]
	Field VarField_TypeDef.GSV_TypeDef[MAX_FIELDS]
	Field Thread.GSV_Thread
End Type
Type GSV_Type
	Field VarFields
	Field VarField.GSV_Var[MAX_FIELDS]
	Field Thread.GSV_Thread
End Type

;VM Instruction Memory
Type GSV_InstrCode
	Field Pcode, Args
	Field Arg1_I%, Arg1_F#, Arg1_S$, Arg1_V.GSV_Var, Arg1_T.GSV_TypeDef, Arg1_L%
	Field Arg2_I%, Arg2_F#, Arg2_S$, Arg2_V.GSV_Var, Arg2_T.GSV_TypeDef, Arg2_L%
End Type

;Pcodes (listed in order or precedance, for fastest possible execution)
Const OP_MOV = 1
Const OP_MFC = 2
Const OP_MFA = 3
Const OP_XFA = 4
Const OP_XFC = 39
Const OP_PUSH = 10
Const OP_POP  = 11
Const OP_VPOP  = 32
Const OP_XPOP  = 33
Const OP_JMP = 7
Const OP_JZ  = 8
Const OP_ADD = 23
Const OP_SUB = 24
Const OP_MUL = 25
Const OP_DIV = 26
Const OP_MOD   = 35
Const OP_CPUSH = 30
Const OP_CPOP  = 31
Const OP_cLT = 17
Const OP_cGT = 18
Const OP_cLE = 19
Const OP_cGE = 20
Const OP_cEQ = 21
Const OP_cNE = 22
Const OP_CALL = 36
Const OP_NEW = 5
Const OP_DEL = 6
Const OP_AND = 12
Const OP_OR  = 13
Const OP_XOR = 14
Const OP_NOT = 15
Const OP_NEG = 16
Const OP_SHL = 27
Const OP_SHR = 28
Const OP_LBL = 34
Const OP_END = 9
Const OP_PRINT = 100

;File codes (used when saving binary execution data)
Const READ_NULL =		0
Const READ_VAR_VAR =	1
Const READ_VAR =		2
Const READ_LBL =		3
Const READ_INT =		4
Const READ_VAR_FLT =	5
Const READ_VAR_INTEGER =6
Const READ_VAR_STRING =	7
Const READ_VAR_TYP =	8
Const READ_CALL =	9

;Loads a GameScript Executable (.gsx) from a file and prepares it for execution.
;Returns the handle of the new thread if successful, false if not. The thread returned
;is by default in a paused state, so you'll have to call GSV_ThreadExecute to run it.
Function GSV_ThreadLoad(File$)
	;Automatically compile gs->gsx when necessary or load directly from executable
	File = GSC_DefaultExtension(File, "gs")
	If FileType(File) = 0 Then
		File = GSC_SetExtension(File, "gsx")
	Else
		If File <> GSC_SetExtension(File, "gsx") Then
			If GSC_Compile(File) = False Then
				GSV_MessageFile = File: GSV_Message = "Load error: Compile .gs failed"
				Return 0
			End If
			File = GSC_SetExtension(File, "gsx")
		End If
	End If
	If FileType(File) = 0 Then
		Return 0
	End If
	
	;Open the file
	If FileType(File) = 0 Then GSV_MessageFile = File: GSV_Message = "Load error: Executable file does not exist": Return 0
	InputFile = ReadFile(File)
	If InputFile = 0 Then GSV_MessageFile = File: GSV_Message = "Load error: Could not read executable file": Return 0
	
	;Read and discard the date/time stamp
	tmp = CreateBank(16)
	ReadBytes tmp, InputFile, 0, 16
	FreeBank tmp
	
	;Check the ID header
	ID$ = ReadString(InputFile)
	If ID$ <> "gsx" Then GSV_MessageFile = File: GSV_Message = "Load error: Executable file is either corrupted or is not a GameScript Executable": Return 0
	
	;Create the thread
	thread.GSV_Thread = New GSV_Thread
	thread\Paused = True
	thread\Name = File
	
	;Create registers
	thread\EAX = New GSV_Var
	thread\EAX\Auto = True
	thread\EAX\Thread = thread
	
	thread\EBX = New GSV_Var
	thread\EBX\Auto = True
	thread\EBX\Thread = thread
	
	thread\ECX = New GSV_Var
	thread\ECX\Auto = True
	thread\ECX\Thread = thread
	
	;Read type definitions
	code = ReadInt(InputFile)
	While code <> -1
		typedef.GSV_TypeDef = New GSV_TypeDef
		typedef\Thread = thread
		thread\TypeDef[code] = typedef
		code = ReadInt(InputFile)
		If Eof(InputFile) Then GSV_MessageFile = File: GSV_Message = "Load error: Executable file is either corrupted or is not a GameScript Executable (unexpected EOF)": Return 0
	Wend
	
	;Read variable/typefield definitions
	code = ReadInt(InputFile)
	While code <> -1
		typecode = ReadInt(InputFile)
		If typecode <> -1 Then
			typedef = thread\TypeDef[typecode]
			typedef\VarFields = typedef\VarFields + 1
			typecode = ReadInt(InputFile)
			If typecode <> -1 Then typedef\VarField_TypeDef[typedef\VarFields] = thread\TypeDef[typecode]
			datatype = ReadByte(InputFile)
			typedef\VarField_DataType[typedef\VarFields] = datatype
			If typedef\VarField_DataType[typedef\VarFields] = VAR_INTEGER Then
				ReadInt(InputFile)
			End If
			If typedef\VarField_DataType[typedef\VarFields] = VAR_FLOAT Then
				ReadFloat(InputFile)
			End If
			If typedef\VarField_DataType[typedef\VarFields] = VAR_STRING Then
				ReadString(InputFile)
			End If
			
			var.GSV_Var = New GSV_Var ;Create a new var for the typefield, simply to specify information for MFC, MFA, etc. operations, which use VARIABLES to specify type fields
			thread\Variable[code] = var
			var\I = typedef\VarFields	;Store the varfield index in i%
			var\Thread = thread
		Else
			var.GSV_Var = New GSV_Var
			thread\Variable[code] = var
			typecode = ReadInt(InputFile)
			If typecode <> -1 Then var\TypeDef = thread\TypeDef[typecode]
			datatype = ReadByte(InputFile)
			var\DataType = datatype
			If var\DataType = VAR_INTEGER Then
				var\I = ReadInt(InputFile)
			End If
			If var\DataType = VAR_FLOAT Then
				var\F = ReadFloat(InputFile)
			End If
			If var\DataType = VAR_STRING Then
				var\S = ReadString(InputFile)
			End If
			var\Thread = thread
		End If
		code = ReadInt(InputFile)
		If Eof(InputFile) Then GSV_MessageFile = File: GSV_Message = "Load error: Executable file is either corrupted or is not a GameScript Executable (unexpected EOF)": Return 0
	Wend
	
	;PASS 1 - Read instruction codes
	pos = FilePos(InputFile)
	thread\InstructionPointer = 1
	Repeat
		op.GSV_InstrCode = New GSV_InstrCode
		thread\Instructions = thread\Instructions + 1
		thread\Instruction[thread\Instructions] = op
		op\Pcode = ReadByte(InputFile)
		nextdata = ReadByte(InputFile)
		op\Args = nextdata
		Select nextdata
		Case READ_NULL
		Case READ_VAR_VAR
			varID = ReadInt(InputFile)
			Select varID
			Case -1
				op\Arg1_V = thread\EAX
			Case -2
				op\Arg1_V = thread\EBX
			Case -3
				op\Arg1_V = thread\ECX
			Default
				op\Arg1_V = thread\Variable[varID]
			End Select
			varID = ReadInt(InputFile)
			Select varID
			Case -1
				op\Arg2_V = thread\EAX
			Case -2
				op\Arg2_V = thread\EBX
			Case -3
				op\Arg2_V = thread\ECX
			Default
				op\Arg2_V = thread\Variable[varID]
			End Select
		Case READ_VAR 
			varID = ReadInt(InputFile)
			Select varID
			Case -1
				op\Arg1_V = thread\EAX
			Case -2
				op\Arg1_V = thread\EBX
			Case -3
				op\Arg1_V = thread\ECX
			Default
				op\Arg1_V = thread\Variable[varID]
			End Select
		Case READ_LBL
			op\Arg1_L = -ReadInt(InputFile)
		Case READ_INT
			op\Arg1_I = ReadInt(InputFile)
		Case READ_VAR_FLT
			varID = ReadInt(InputFile)
			Select varID
			Case -1
				op\Arg1_V = thread\EAX
			Case -2
				op\Arg1_V = thread\EBX
			Case -3
				op\Arg1_V = thread\ECX
			Default
				op\Arg1_V = thread\Variable[varID]
			End Select
			op\Arg2_F = ReadFloat(InputFile)
		Case READ_VAR_INTEGER
			varID = ReadInt(InputFile)
			Select varID
			Case -1
				op\Arg1_V = thread\EAX
			Case -2
				op\Arg1_V = thread\EBX
			Case -3
				op\Arg1_V = thread\ECX
			Default
				op\Arg1_V = thread\Variable[varID]
			End Select
			op\Arg2_I = ReadInt(InputFile)
		Case READ_VAR_STRING
			varID = ReadInt(InputFile)
			Select varID
			Case -1
				op\Arg1_V = thread\EAX
			Case -2
				op\Arg1_V = thread\EBX
			Case -3
				op\Arg1_V = thread\ECX
			Default
				op\Arg1_V = thread\Variable[varID]
			End Select
			op\Arg2_S = ReadString(InputFile)
		Case READ_VAR_TYP
			varID = ReadInt(InputFile)
			Select varID
			Case -1
				op\Arg1_V = thread\EAX
			Case -2
				op\Arg1_V = thread\EBX
			Case -3
				op\Arg1_V = thread\ECX
			Default
				op\Arg1_V = thread\Variable[varID]
			End Select
			op\Arg2_T = thread\TypeDef[ReadInt(InputFile)]
		Case READ_CALL
			op\Arg1_I = ReadInt(InputFile)
		Default
			GSV_MessageFile = File: GSV_Message = "Load error: Executable file is either corrupted or is not a GameScript Executable (unknown parameter ID)": Return 0
		End Select
	Until Eof(InputFile)
	
	;PASS 2 - Re-index labels
	SeekFile InputFile, pos
	Repeat
		cnt = cnt + 1
		code = ReadByte(InputFile)
		nextdata = ReadByte(InputFile)
		
		Select nextdata
		Case READ_NULL
		Case READ_VAR_VAR
			ReadInt(InputFile)
			ReadInt(InputFile)
		Case READ_VAR
			ReadInt(InputFile)
		Case READ_LBL
			label = ReadInt(InputFile)
			If code = OP_LBL Then
				For tmpop.GSV_InstrCode = Each GSV_InstrCode
					If (tmpop\Pcode = OP_JMP Or tmpop\Pcode = OP_JZ) And tmpop\Arg1_L = -label Then tmpop\Arg1_L = cnt
				Next
			End If
		Case READ_INT
			ReadInt(InputFile)
		Case READ_VAR_FLT
			ReadInt(InputFile)
			ReadFloat(InputFile)
		Case READ_VAR_INTEGER
			ReadInt(InputFile)
			ReadInt(InputFile)
		Case READ_VAR_STRING
			ReadInt(InputFile)
			ReadString(InputFile)
		Case READ_VAR_TYP
			ReadInt(InputFile)
			ReadInt(InputFile)
		Case READ_CALL
			ReadInt(InputFile)
		End Select
	Until Eof(InputFile)
	
	
	;Done
	CloseFile InputFile
	Return Handle(thread)
End Function

;Sets the given thread to be executed at the next GSV_ThreadUpdate() call. This basically "unpauses" the thread.
Function GSV_ThreadExecute(ThreadHandle)
	Thread.GSV_Thread = Object.GSV_Thread(ThreadHandle)
	
	Thread\Paused = False
End Function

;Pauses the execution of the given thread. You can resume execution later with GSV_ThreadExecute().
Function GSV_ThreadPause(ThreadHandle)
	Thread.GSV_Thread = Object.GSV_Thread(ThreadHandle)
	
	Thread\Paused = True
	Thread\ResumeTime = 0
End Function

;Returns whether or not the thread given is paused/stopped or not
Function GSV_ThreadActive(ThreadHandle)
	Thread.GSV_Thread = Object.GSV_Thread(ThreadHandle)
	
	If Thread\ResumeTime <> 0 Then Return True
	Return 1-Thread\Paused
End Function

;Terminates the given thread, deleting all memory used by it. If you intend to execute the same script again
;after this point, use GSV_ThreadStop() instead, which does not delete, but simply halts and resets the
;thread.
Function GSV_ThreadDelete(ThreadHandle)
	Thread.GSV_Thread = Object.GSV_Thread(ThreadHandle)
	
	;Delete variables/data stack/registers/types/type definitions
	For var.GSV_Var = Each GSV_Var
		If var\Thread = Thread Then Delete var
	Next
	For td.GSV_TypeDef = Each GSV_TypeDef
		If td\Thread = Thread Then Delete td
	Next
	For t.GSV_Type = Each GSV_Type
		If t\Thread = Thread Then Delete t
	Next
	
	;Delete instruction codes
	For i = 1 To Thread\Instructions
		Delete Thread\Instruction[i]
	Next
	
	;Delete thread
	Delete Thread
End Function

;Terminates the given thread, reseting a variables and execution data. After a thread is terminated, it can
;be run again with GSV_ThreadExecute(), since it's not actually deleted.
Function GSV_ThreadStop(ThreadHandle)
	Thread.GSV_Thread = Object.GSV_Thread(ThreadHandle)
	
	Thread\Paused = True
	Thread\ResumeTime = 0
	Thread\InstructionPointer = 1	
End Function

;Updates a single thread (executes one opcode of the given thread). Returns false if a runtime error has occurred.
Function GSV_ThreadUpdate(ThreadHandle)
	Thread.GSV_Thread = Object.GSV_Thread(ThreadHandle)
	
	If Thread\ResumeTime <> 0 Then
		If MilliSecs() >= Thread\ResumeTime Then Thread\Paused = False: Thread\ResumeTime = 0
	EndIf
	
	If Thread\Paused Then Return True
	InstrCode.GSV_InstrCode = Thread\Instruction[Thread\InstructionPointer]
	
	olderror = GSV_MessageInstr
	
	Select InstrCode\Pcode
	Case OP_MOV
		Select InstrCode\Args
		Case READ_VAR_VAR
			GSV_MOV(Thread, InstrCode\Arg1_V, InstrCode\Arg2_V)	;VAR_VAR MOV operations can use the GSV_MOV function, since this basic operation is so commonly used when pushing, popping, and ANY data manipulation
		Case READ_VAR_FLT
			If InstrCode\Arg1_V\Auto = False Then
				Select InstrCode\Arg1_V\DataType
				Case VAR_INTEGER
					InstrCode\Arg1_V\I = InstrCode\Arg2_F
				Case VAR_FLOAT
					InstrCode\Arg1_V\F = InstrCode\Arg2_F
				Case VAR_STRING
					InstrCode\Arg1_V\S = InstrCode\Arg2_F
				Case VAR_CUSTOM
					GSV_RuntimeError(Thread, "Cannot convert float to custom type")
				End Select
			Else
				InstrCode\Arg1_V\DataType = VAR_FLOAT
				InstrCode\Arg1_V\F = InstrCode\Arg2_F
			End If
		Case READ_VAR_INTEGER
			If InstrCode\Arg1_V\Auto = False Then
				Select InstrCode\Arg1_V\DataType
				Case VAR_INTEGER
					InstrCode\Arg1_V\I = InstrCode\Arg2_I
				Case VAR_FLOAT
					InstrCode\Arg1_V\F = InstrCode\Arg2_I
				Case VAR_STRING
					InstrCode\Arg1_V\S = InstrCode\Arg2_I
				Case VAR_CUSTOM
					GSV_RuntimeError(Thread, "Cannot convert integer to custom type")
				End Select
			Else
				InstrCode\Arg1_V\DataType = VAR_INTEGER
				InstrCode\Arg1_V\I = InstrCode\Arg2_I
			End If
		Case READ_VAR_STRING
			If InstrCode\Arg1_V\Auto = False Then
				Select InstrCode\Arg1_V\DataType
				Case VAR_INTEGER
					InstrCode\Arg1_V\I = InstrCode\Arg2_S
				Case VAR_FLOAT
					InstrCode\Arg1_V\F = InstrCode\Arg2_S
				Case VAR_STRING
					InstrCode\Arg1_V\S = InstrCode\Arg2_S
				Case VAR_CUSTOM
					GSV_RuntimeError(Thread, "Cannot convert string to custom type")
				End Select
			Else
				InstrCode\Arg1_V\DataType = VAR_STRING
				InstrCode\Arg1_V\S = InstrCode\Arg2_S
			End If
		Case READ_VAR_TYP
			GSV_RuntimeError(Thread, "Cannot convert a type declaration to a type object")
		Default
			GSV_RuntimeError(Thread, "Cannot MOV into a constant value")
		End Select
		
	Case OP_MFC
		If thread\ECX\DataType <> VAR_CUSTOM Or thread\ECX\C = Null Then
			If thread\ECX\C = Null Then
				GSV_RuntimeError(Thread, "Attemped to access data from a NULL custom type")
			Else
				GSV_RuntimeError(Thread, "Attemped to access a field of a non custom type")
			End If
		Else
			;The type field is specified in arg2 (var) - the var simply contains an int value of the field index.
			;^^ After this line, arg2 will now be pointing to the actual variable contained
			;in the type field, specific to the custom type object ECX is pointing to currently
			;And finially, the below code operates just like a simple var-to-var MOV instruction
			GSV_MOV(Thread, InstrCode\Arg1_V, thread\ECX\C\VarField[InstrCode\Arg2_V\i])
		End If
		
	Case OP_MFA
		If thread\EAX\DataType <> VAR_CUSTOM Or thread\EAX\C = Null Then
			If thread\EAX\C = Null Then
				GSV_RuntimeError(Thread, "Attemped to access data from a NULL custom type")
			Else
				GSV_RuntimeError(Thread, "Attemped to access a field of a non custom type")
			End If
		Else
			 ;The type field is specified in arg2 (var) - the var simply contains an int value of the field index.
			;^^ After this line, arg2 will now be pointing to the actual variable contained
			;in the type field, specific to the custom type object EAX is pointing to currently
			;And finially, the below code operates just like a simple var-to-var MOV instruction
			GSV_MOV(Thread, InstrCode\Arg1_V, thread\EAX\C\VarField[InstrCode\Arg2_V\i])
		End If
		
	Case OP_XFA
		If thread\EAX\DataType <> VAR_CUSTOM Or thread\EAX\C = Null Then
			If thread\EAX\C = Null Then
				GSV_RuntimeError(Thread, "Attemped to access data from a NULL custom type")
			Else
				GSV_RuntimeError(Thread, "Attemped to access a field of a non custom type")
			End If
		Else
			;The type field is specified in arg1 (var) - the var simply contains an int value of the field index.
			;^^ After this line, arg1 will now be pointing to the actual variable contained
			;in the type field, specific to the custom type object EAX is pointing to currently
			;And finially, the below code operates just like a simple var-to-var MOV instruction
			GSV_MOV(Thread, thread\EAX\C\VarField[InstrCode\Arg1_V\i], InstrCode\Arg2_V)
		End If

	Case OP_XFC
		If thread\ECX\DataType <> VAR_CUSTOM Or thread\ECX\C = Null Then
			If thread\ECX\C = Null Then
				GSV_RuntimeError(Thread, "Attemped to access data from a NULL custom type")
			Else
				GSV_RuntimeError(Thread, "Attemped to access a field of a non custom type")
			End If
		Else
			;The type field is specified in arg1 (var) - the var simply contains an int value of the field index.
			;^^ After this line, arg1 will now be pointing to the actual variable contained
			;in the type field, specific to the custom type object ECX is pointing to currently
			;And finially, the below code operates just like a simple var-to-var MOV instruction
			GSV_MOV(Thread, thread\ECX\C\VarField[InstrCode\Arg1_V\i], InstrCode\Arg2_V)
		End If
		
		
	Case OP_PUSH
		;Make a new stack item
		var.GSV_Var = New GSV_Var
		var\I = InstrCode\Arg1_V\I
		var\F = InstrCode\Arg1_V\F
		var\S = InstrCode\Arg1_V\S
		var\C = InstrCode\Arg1_V\C
		var\DataType = InstrCode\Arg1_V\DataType
		var\TypeDef = InstrCode\Arg1_V\TypeDef
		var\Auto = InstrCode\Arg1_V\Auto
		
		Thread\DataStackLevel = Thread\DataStackLevel + 1
		Thread\DataStack[Thread\DataStackLevel] = var
		var\Thread = Thread
		
		If Thread\DataStackLevel >= DATASTACK_SIZE Then GSV_RuntimeError(Thread, "Expression too complex (data stack overflow)")
		
	Case OP_POP
		InstrCode\Arg2_V = Thread\DataStack[Thread\DataStackLevel]
		;^^ After this line, arg2 will now be pointing to the variable in the stack.
		;Now, the below code can operate just like a simple var-to-var MOV instruction
		GSV_MOV(Thread, InstrCode\Arg1_V, InstrCode\Arg2_V)
		
		;Now, remove the top item
		Delete Thread\DataStack[Thread\DataStackLevel]
		Thread\DataStackLevel = Thread\DataStackLevel - 1
		
		If Thread\DataStackLevel < 0 Then GSV_RuntimeError(Thread, "Atempted to POP from an empty data stack")
		
	Case OP_VPOP
		InstrCode\Arg2_V = Thread\DataStack[Thread\DataStackLevel]
		;^^ After this line, arg2 will now be pointing to the variable in the stack.
		;Now, the below code can operate just like a simple var-to-var MOV instruction
		GSV_MOV(Thread, InstrCode\Arg1_V, InstrCode\Arg2_V)

		If Thread\DataStackLevel = 0 Then GSV_RuntimeError(Thread, "Atempted to VPOP from an empty data stack")
		
	Case OP_XPOP
		;Remove the top stack item
		Delete Thread\DataStack[Thread\DataStackLevel]
		Thread\DataStackLevel = Thread\DataStackLevel - 1

		If Thread\DataStackLevel < 0 Then GSV_RuntimeError(Thread, "Atempted to XPOP from an empty data stack")
	
	Case OP_JMP
		Thread\InstructionPointer = InstrCode\Arg1_L - 1	;-1 since instruction pointer will be additionally incrimented
		
	Case OP_JZ
		Select Thread\EAX\DataType
		Case VAR_INTEGER
			If Thread\EAX\I = 0 Then Thread\InstructionPointer = InstrCode\Arg1_L
		Case VAR_FLOAT
			If Thread\EAX\F = 0 Then Thread\InstructionPointer = InstrCode\Arg1_L
		Case VAR_STRING
			GSV_RuntimeError(Thread, "Strings may not be used as boolean true/false values")
		Case VAR_CUSTOM
			GSV_RuntimeError(Thread, "Custom types may not be used as boolean true/false values")
		End Select
		
	Case OP_LBL
	
	Case OP_ADD
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = InstrCode\Arg1_V\I + InstrCode\Arg2_V\I
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = Float(InstrCode\Arg1_V\I) + InstrCode\Arg2_V\F
			Case VAR_STRING
				Thread\EAX\DataType = VAR_STRING: Thread\EAX\S = Str(InstrCode\Arg1_V\I) + InstrCode\Arg2_V\S
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot add two custom type objects together (missing .field possibly)")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F + Float(InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F + InstrCode\Arg2_V\F
			Case VAR_STRING
				Thread\EAX\DataType = VAR_STRING: Thread\EAX\S = Str(InstrCode\Arg1_V\F) + InstrCode\Arg2_V\S
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot add two custom type objects together (missing .field possibly)")
			End Select
		Case VAR_STRING
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_STRING: Thread\EAX\S = InstrCode\Arg1_V\S + Str(InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_STRING: Thread\EAX\S = InstrCode\Arg1_V\S + Str(InstrCode\Arg2_V\F)
			Case VAR_STRING
				Thread\EAX\DataType = VAR_STRING: Thread\EAX\S = InstrCode\Arg1_V\S + InstrCode\Arg2_V\S
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot add two custom type objects together (missing .field possibly)")
			End Select
		Case VAR_CUSTOM
			GSV_RuntimeError(Thread, "Cannot add two custom type objects together (missing .field possibly)")
		End Select
	
	Case OP_SUB
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = InstrCode\Arg1_V\I - InstrCode\Arg2_V\I
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = Float(InstrCode\Arg1_V\I) - InstrCode\Arg2_V\F
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform subtraction with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot subtract two custom type objects (missing .field possibly)")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F - Float(InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F - InstrCode\Arg2_V\F
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform subtraction with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot subtract two custom type objects (missing .field possibly)")
			End Select
		Case VAR_STRING
			GSV_RuntimeError(Thread, "Cannot perform subtraction with string variables")
		Case VAR_CUSTOM
			GSV_RuntimeError(Thread, "Cannot subtract two custom type objects (missing .field possibly)")
		End Select
	
	Case OP_MUL
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = InstrCode\Arg1_V\I * InstrCode\Arg2_V\I
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = Float(InstrCode\Arg1_V\I) * InstrCode\Arg2_V\F
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform multiplication with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot multiply two custom type objects (missing .field possibly)")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F * Float(InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F * InstrCode\Arg2_V\F
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform multiplication with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot multiply two custom type objects (missing .field possibly)")
			End Select
		Case VAR_STRING
			GSV_RuntimeError(Thread, "Cannot perform multiplication with string variables")
		Case VAR_CUSTOM
			GSV_RuntimeError(Thread, "Cannot multiply two custom type objects (missing .field possibly)")
		End Select
	
	Case OP_DIV
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = InstrCode\Arg1_V\I / InstrCode\Arg2_V\I
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = Float(InstrCode\Arg1_V\I) / InstrCode\Arg2_V\F
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform division with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot divide two custom type objects (missing .field possibly)")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F / Float(InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_FLOAT: Thread\EAX\F = InstrCode\Arg1_V\F / InstrCode\Arg2_V\F
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform division with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot divide two custom type objects (missing .field possibly)")
			End Select
		Case VAR_STRING
			GSV_RuntimeError(Thread, "Cannot perform division with string variables")
		Case VAR_CUSTOM
			GSV_RuntimeError(Thread, "Cannot divide two custom type objects (missing .field possibly)")
		End Select
	
	Case OP_MOD
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = InstrCode\Arg1_V\I Mod InstrCode\Arg2_V\I
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = InstrCode\Arg1_V\I Mod Int(InstrCode\Arg2_V\F)
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform modulus division with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot divide two custom type objects (missing .field possibly)")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = Int(InstrCode\Arg1_V\F) Mod InstrCode\Arg2_V\I
			Case VAR_FLOAT
				Thread\EAX\DataType = VAR_INTEGER: Thread\EAX\I = Int(InstrCode\Arg1_V\F) Mod Int(InstrCode\Arg2_V\F)
			Case VAR_STRING
				GSV_RuntimeError(Thread, "Cannot perform modulus division with string variables")
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Cannot divide two custom type objects (missing .field possibly)")
			End Select
		Case VAR_STRING
			GSV_RuntimeError(Thread, "Cannot perform division with string variables")
		Case VAR_CUSTOM
			GSV_RuntimeError(Thread, "Cannot divide two custom type objects (missing .field possibly)")
		End Select
	
	Case OP_CPUSH
		Thread\CallStackLevel = Thread\CallStackLevel + 1
		Thread\CallStack[Thread\CallStackLevel] = Thread\InstructionPointer + InstrCode\Arg1_I
		
	Case OP_CPOP
		Thread\InstructionPointer = Thread\CallStack[Thread\CallStackLevel] - 1 ;-1 since instruction pointer will be additionally incrimented
		Thread\CallStackLevel = Thread\CallStackLevel - 1
	
	Case OP_cLT
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\I < InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\I < InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\F < InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\F < InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_STRING
			Select InstrCode\Arg2_V\DataType
			Case VAR_STRING
				Thread\EAX\I = (InstrCode\Arg1_V\S < InstrCode\Arg2_V\S)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_CUSTOM
			Select InstrCode\Arg2_V\DataType
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Illegal operator for custom type objects")
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		End Select
		Thread\EAX\DataType = VAR_INTEGER	;Integer is used for boolean output of boolean expression

	Case OP_cGT
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\I > InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\I > InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\F > InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\F > InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_STRING
			Select InstrCode\Arg2_V\DataType
			Case VAR_STRING
				Thread\EAX\I = (InstrCode\Arg1_V\S > InstrCode\Arg2_V\S)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_CUSTOM
			Select InstrCode\Arg2_V\DataType
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Illegal operator for custom type objects")
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		End Select
		Thread\EAX\DataType = VAR_INTEGER	;Integer is used for boolean output of boolean expression

	Case OP_cLE
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\I <= InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\I <= InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\F <= InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\F <= InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_STRING
			Select InstrCode\Arg2_V\DataType
			Case VAR_STRING
				Thread\EAX\I = (InstrCode\Arg1_V\S <= InstrCode\Arg2_V\S)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_CUSTOM
			Select InstrCode\Arg2_V\DataType
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Illegal operator for custom type objects")
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		End Select
		Thread\EAX\DataType = VAR_INTEGER	;Integer is used for boolean output of boolean expression

	Case OP_cGE
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\I >= InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\I >= InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\F >= InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\F >= InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_STRING
			Select InstrCode\Arg2_V\DataType
			Case VAR_STRING
				Thread\EAX\I = (InstrCode\Arg1_V\S >= InstrCode\Arg2_V\S)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_CUSTOM
			Select InstrCode\Arg2_V\DataType
			Case VAR_CUSTOM
				GSV_RuntimeError(Thread, "Illegal operator for custom type objects")
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		End Select
		Thread\EAX\DataType = VAR_INTEGER	;Integer is used for boolean output of boolean expression

	Case OP_cEQ
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\I = InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\I = InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\F = InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\F = InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_STRING
			Select InstrCode\Arg2_V\DataType
			Case VAR_STRING
				Thread\EAX\I = (InstrCode\Arg1_V\S = InstrCode\Arg2_V\S)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_CUSTOM
			Select InstrCode\Arg2_V\DataType
			Case VAR_CUSTOM
				Thread\EAX\I = (InstrCode\Arg1_V\C = InstrCode\Arg2_V\C)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		End Select
		Thread\EAX\DataType = VAR_INTEGER	;Integer is used for boolean output of boolean expression

	Case OP_cNE
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\I <> InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\I <> InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_FLOAT
			Select InstrCode\Arg2_V\DataType
			Case VAR_INTEGER
				Thread\EAX\I = (InstrCode\Arg1_V\F <> InstrCode\Arg2_V\I)
			Case VAR_FLOAT
				Thread\EAX\I = (InstrCode\Arg1_V\F <> InstrCode\Arg2_V\F)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_STRING
			Select InstrCode\Arg2_V\DataType
			Case VAR_STRING
				Thread\EAX\I = (InstrCode\Arg1_V\S <> InstrCode\Arg2_V\S)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		Case VAR_CUSTOM
			Select InstrCode\Arg2_V\DataType
			Case VAR_CUSTOM
				Thread\EAX\I = (InstrCode\Arg1_V\C <> InstrCode\Arg2_V\C)
			Default
				GSV_RuntimeError(Thread, "Cannot compare incompatible variable types")
			End Select
		End Select
		Thread\EAX\DataType = VAR_INTEGER	;Integer is used for boolean output of boolean expression
		
	Case OP_CALL
		If GSF_ExecuteFunction(Thread, InstrCode\Arg1_I) = False
			GSV_RuntimeError(Thread, "External function not found")
		Else
			Thread\EAX\DataType = GSF_ReturnType
			Select GSF_ReturnType
			Case VAR_INTEGER
				Thread\EAX\I = GSF_ReturnVal
			Case VAR_FLOAT
				Thread\EAX\F = GSF_ReturnVal
			Case VAR_STRING
				Thread\EAX\S = GSF_ReturnVal
			End Select
		EndIf
		
	Case OP_NEW
		obj.GSV_Type = New GSV_Type
		obj\Thread = Thread
		obj\VarFields = InstrCode\Arg2_T\VarFields
		For i = 1 To obj\VarFields
			obj\VarField[i] = New GSV_Var
			obj\VarField[i]\DataType = InstrCode\Arg2_T\VarField_DataType[i]
			obj\VarField[i]\TypeDef = InstrCode\Arg2_T\VarField_TypeDef[i]
			obj\VarField[i]\I = i
			obj\VarField[i]\Thread = Thread
		Next
		If InstrCode\Arg1_V\TypeDef <> InstrCode\Arg2_T And InstrCode\Arg1_V\Auto = False Then
			GSV_RuntimeError(Thread, "NEW type definition does not match variable declaration")
		Else
			InstrCode\Arg1_V\DataType = VAR_CUSTOM
			InstrCode\Arg1_V\C = obj
		End If
	
	Case OP_DEL
		For i = 1 To InstrCode\Arg1_V\C\VarFields
			Delete InstrCode\Arg1_V\C\VarField[i]
		Next
		Delete InstrCode\Arg1_V\C
	
	Case OP_AND
		If InstrCode\Arg1_V\DataType <> VAR_INTEGER Or InstrCode\Arg2_V\DataType <> VAR_INTEGER Then
			GSV_RuntimeError(Thread, "Binary AND keyword may only be used with integer values")
		End If
		Thread\EAX\I = (InstrCode\Arg1_V\I And InstrCode\Arg2_V\I)

	Case OP_OR
		If InstrCode\Arg1_V\DataType <> VAR_INTEGER Or InstrCode\Arg2_V\DataType <> VAR_INTEGER Then
			GSV_RuntimeError(Thread, "Binary OR keyword may only be used with integer values")
		End If
		Thread\EAX\I = (InstrCode\Arg1_V\I Or InstrCode\Arg2_V\I)

	Case OP_XOR
		If InstrCode\Arg1_V\DataType <> VAR_INTEGER Or InstrCode\Arg2_V\DataType <> VAR_INTEGER Then
			GSV_RuntimeError(Thread, "Binary XOR keyword may only be used with integer values")
		End If
		Thread\EAX\I = (InstrCode\Arg1_V\I Xor InstrCode\Arg2_V\I)

	Case OP_NOT
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			InstrCode\Arg1_V\I = Not InstrCode\Arg1_V\I
		Case VAR_FLOAT
			InstrCode\Arg1_V\F = Not InstrCode\Arg1_V\F
		Default
			GSV_RuntimeError(Thread, "NOT keyword is only compatible with integer and float values")
		End Select
		
	Case OP_NEG
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			InstrCode\Arg1_V\I = -InstrCode\Arg1_V\I
		Case VAR_FLOAT
			InstrCode\Arg1_V\F = -InstrCode\Arg1_V\F
		Default
			GSV_RuntimeError(Thread, "NOT keyword is only compatible with integer and float values")
		End Select
		
	Case OP_SHL
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			InstrCode\Arg1_V\I = InstrCode\Arg1_V\I Shl InstrCode\Arg2_V\I
		Case VAR_FLOAT
			InstrCode\Arg1_V\F = -InstrCode\Arg1_V\F Shl InstrCode\Arg2_V\I
		Default
			GSV_RuntimeError(Thread, "NOT keyword is only compatible with integer and float values")
		End Select
		
	Case OP_SHR
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			InstrCode\Arg1_V\I = -InstrCode\Arg1_V\I Shr InstrCode\Arg2_V\I
		Case VAR_FLOAT
			InstrCode\Arg1_V\F = -InstrCode\Arg1_V\F Shr InstrCode\Arg2_V\I
		Default
			GSV_RuntimeError(Thread, "NOT keyword is only compatible with integer and float values")
		End Select

	Case OP_END
		GSV_ThreadStop(ThreadHandle)
		
	Case OP_PRINT
		Select InstrCode\Arg1_V\DataType
		Case VAR_INTEGER
			out$ = InstrCode\Arg1_V\I
		Case VAR_FLOAT
			out$ = InstrCode\Arg1_V\F
		Case VAR_STRING
			out$ = InstrCode\Arg1_V\S
		End Select
		Print out
		DebugLog "Script Output: "+out
		
	Default
		GSV_RuntimeError(Thread, "Unknown instruction code ("+InstrCode\PCode+")! Possible corrupted GSX executable.")
	
	End Select
	
	Thread\InstructionPointer = Thread\InstructionPointer + 1
	If Thread\InstructionPointer > Thread\Instructions Then GSV_ThreadStop(ThreadHandle)
	
	If GSV_MessageInstr <> olderror Then Return 0 Else Return 1
End Function

;This updates all running threads. This must be called periodically in order for execution threads to be executed.
;Set Steps to the number of instructions to be executed in each Thread, if possible.
;If ErrorRecovery is set to False, any runtime error totally crashes the program with an error message and writes a debuglog. This is good for a debug version of your program.
;If ErrorRecovery is set to True, any runtime error will silently restart the thread. This is good for the public release version of your program.
;The default ErrorRecovery setting is False
Function GSV_Update(Steps = 1, ErrorRecovery = False)
	For thread.GSV_Thread = Each GSV_Thread
		For i = 1 To Steps
			;Update the thread
			If GSV_ThreadUpdate(Handle(thread)) = 0 Then
				If ErrorRecovery Then
					;Restart the thread
					GSV_ThreadStop(Handle(thread))
					GSV_ThreadExecute(Handle(thread))
				Else
					;Write a crash log that the user can send to the programmer when reporting a bug in a script
					errlog = WriteFile("GameScript Crash Log.txt")
					;WriteLine errlog, "_________________________________________________"
					WriteLine errlog, "_______" + CurrentDate()+", "+CurrentTime()+" - "+GSV_ErrorFile()+":" + "_________________"
					WriteLine errlog, "Runtime error at instruction #"+GSV_ErrorLocation()+": "
					top = GSV_MessageInstr - 4: If top < 1 Then top = 1
					bottom = GSV_MessageInstr + 3: If bottom > GSV_MessageThread\Instructions Then bottom = GSV_MessageInstr
					If top <> 1 Then WriteLine errlog, "..."
					For i = top To bottom
						lin$ = GSV_Disassemble(GSV_MessageThread, GSV_MessageThread\Instruction[i])
						If i = GSV_MessageInstr Then
							WriteLine errlog, lin$ + "   <-- " + GSV_Error()
						Else
							WriteLine errlog, lin$
						End If
					Next
					If bottom <> GSV_MessageThread\Instructions Then WriteLine errlog, "..."
					WriteLine errlog, ""
					CloseFile errlog
				
					;Report the error and exit
					RuntimeError "Runtime error in GameScript executable at instruction "+GSV_ErrorLocation()+": "+GSV_Error()+Chr$(13)+"A log file has been created containing more technical information about this error."+Chr(13)+"Click OK to close this application."
					End
				End If
			End If
			
			;Exit from this loop if the program isn't running right now
			If thread\Paused Then Exit
		Next
	Next
End Function

;Returns a message/error description from GameScript VM, if any
Function GSV_Error$()
	Return GSV_Message
End Function

;Returns the file with a message/error from GameScript VM, if any
Function GSV_ErrorFile$()
	Return GSV_MessageFile
End Function

;Returns the location (instruction num.) of the message/error from GameScript VM, if any
Function GSV_ErrorLocation()
	Return GSV_MessageInstr
End Function

;[INTERNAL] Sets the current error
Function GSV_RuntimeError(Thread.GSV_Thread, Desc$)
	GSV_Message = Desc
	GSV_MessageFile = Thread\Name
	GSV_MessageThread = Thread
	GSV_MessageInstr = Thread\InstructionPointer
	GSV_ThreadStop(Handle(Thread))
End Function


;[INTERNAL] Moves contents from Var2 into Var1. This also takes care of variable types, so
;data type conversion is handled also.
Function GSV_MOV(Thread.GSV_Thread, Var1.GSV_Var, Var2.GSV_Var)

	If var1 = Null Then GSV_RunTimeError(Thread, "Var1 does not exist"): Return
	If var2 = Null Then GSV_RunTimeError(Thread, "Var2 does not exist"): Return

	If Var1\Auto = False Then	;Auto variables (like registers) are typeless, actually changing their type with each different assignment
		Select Var1\DataType
		Case VAR_INTEGER
			Select Var2\DataType
			Case VAR_INTEGER
				Var1\I = Var2\I
			Case VAR_FLOAT
				Var1\I = Var2\F
			Case VAR_STRING
				Var1\I = Var2\S
			Case VAR_CUSTOM
				;Var1\I = Var2\C
				GSV_RuntimeError(Thread, "Cannot convert custom type to int")
			End Select
		Case VAR_FLOAT
			Select Var2\DataType
			Case VAR_INTEGER
				Var1\F = Var2\I
			Case VAR_FLOAT
				Var1\F = Var2\F
			Case VAR_STRING
				Var1\F = Var2\S
			Case VAR_CUSTOM
				;Var1\F = Var2\C
				GSV_RuntimeError(Thread, "Cannot convert custom type to float")
			End Select
		Case VAR_STRING
			Select Var2\DataType
			Case VAR_INTEGER
				Var1\S = Var2\I
			Case VAR_FLOAT
				Var1\S = Var2\F
			Case VAR_STRING
				Var1\S = Var2\S
			Case VAR_CUSTOM
				;Var1\S = Var2\C
				GSV_RuntimeError(Thread, "Cannot convert custom type to string")
			End Select
		Case VAR_CUSTOM
			Select Var2\DataType
			Case VAR_INTEGER
				;Var1\C = Var2\I
				GSV_RuntimeError(Thread, "Cannot convert int to custom type")
			Case VAR_FLOAT
				;Var1\C = Var2\F
				GSV_RuntimeError(Thread, "Cannot convert float to custom type")
			Case VAR_STRING
				;Var1\C = Var2\S
				GSV_RuntimeError(Thread, "Cannot convert string to custom type")
			Case VAR_CUSTOM
				Var1\C = Var2\C
			End Select
		End Select
	Else
		Var1\DataType = Var2\DataType
		Select Var1\DataType
		Case VAR_INTEGER
			Var1\I = Var2\I
		Case VAR_FLOAT
			Var1\F = Var2\F
		Case VAR_STRING
			Var1\S = Var2\S
		Case VAR_CUSTOM
			Var1\C = Var2\C
		End Select
	End If
End Function


;[INTERNAL] This returns a string of assembly language representing the given instruction codes structure
Function GSV_Disassemble$(Thread.GSV_Thread, InstrCode.GSV_InstrCode)
	Select InstrCode\Pcode
	Case OP_MOV
		lin$ = "    mov   "
	Case OP_MFC
		lin$ = "    mfc   "
	Case OP_MFA
		lin$ = "    mfa   "
	Case OP_XFA
		lin$ = "    xfa   "
	Case OP_XFC
		lin$ = "    xfc   "
	Case OP_PUSH
		lin$ = "    push  "
	Case OP_POP
		lin$ = "    pop   "
	Case OP_VPOP
		lin$ = "    vpop  "
	Case OP_XPOP
		lin$ = "    xpop  "
	Case OP_JMP
		lin$ = "    jmp   "
	Case OP_JZ
		lin$ = "    jz    "
	Case OP_ADD
		lin$ = "    add   "
	Case OP_SUB
		lin$ = "    sub   "
	Case OP_MUL
		lin$ = "    mul   "
	Case OP_DIV
		lin$ = "    div   "
	Case OP_CPUSH
		lin$ = "    cpush "
	Case OP_CPOP
		lin$ = "    cpop  "
	Case OP_cLT
		lin$ = "    cLT   "
	Case OP_cGT
		lin$ = "    cGE   "
	Case OP_cLE
		lin$ = "    cLE   "
	Case OP_cGE
		lin$ = "    cGE   "
	Case OP_cEQ
		lin$ = "    cEQ   "
	Case OP_cNE
		lin$ = "    cNE   "
	Case OP_NEW
		lin$ = "    new   "
	Case OP_DEL
		lin$ = "    del   "
	Case OP_AND
		lin$ = "    and   "
	Case OP_OR
		lin$ = "    or    "
	Case OP_XOR
		lin$ = "    xor   "
	Case OP_NOT
		lin$ = "    not   "
	Case OP_NEG
		lin$ = "    neg   "
	Case OP_SHL
		lin$ = "    shl   "
	Case OP_SHR
		lin$ = "    shr   "
	Case OP_LBL
		lin$ = ""
	Case OP_END
		lin$ = "    end   "
	Case OP_CALL
		lin$ = "    call  "
	Case OP_PRINT
		lin$ = "    print "
	End Select
	
	Select InstrCode\Args
		Case READ_NULL
		Case READ_VAR_VAR
			If InstrCode\Arg1_V = Thread\EAX Then
				lin = lin + "eax"
			ElseIf InstrCode\Arg1_V = Thread\EBX Then
				lin = lin + "ebx"
			ElseIf InstrCode\Arg1_V = Thread\ECX Then
				lin = lin + "ecx"
			Else
				lin = lin + "[var]" ;TODO
			End If
			lin = lin + ", "
			If InstrCode\Arg2_V = Thread\EAX Then
				lin = lin + "eax"
			ElseIf InstrCode\Arg2_V = Thread\EBX Then
				lin = lin + "ebx"
			ElseIf InstrCode\Arg2_V = Thread\ECX Then
				lin = lin + "ecx"
			Else
				lin = lin + "[var]" ;TODO
			End If
		Case READ_VAR
			If InstrCode\Arg1_V = Thread\EAX Then
				lin = lin + "eax"
			ElseIf InstrCode\Arg1_V = Thread\EBX Then
				lin = lin + "ebx"
			ElseIf InstrCode\Arg1_V = Thread\ECX Then
				lin = lin + "ecx"
			Else
				lin = lin + "[var]" ;TODO
			End If
		Case READ_LBL
			lin = lin + "_Label" + InstrCode\Arg1_L
		Case READ_INT
			lin = lin + InstrCode\Arg1_I
		Case READ_VAR_FLT
			If InstrCode\Arg1_V = Thread\EAX Then
				lin = lin + "eax"
			ElseIf InstrCode\Arg1_V = Thread\EBX Then
				lin = lin + "ebx"
			ElseIf InstrCode\Arg1_V = Thread\ECX Then
				lin = lin + "ecx"
			Else
				lin = lin + "[var]" ;TODO
			End If
			lin = lin + ", "
			lin = lin + InstrCode\Arg1_F
		Case READ_VAR_INTEGER
			If InstrCode\Arg1_V = Thread\EAX Then
				lin = lin + "eax"
			ElseIf InstrCode\Arg1_V = Thread\EBX Then
				lin = lin + "ebx"
			ElseIf InstrCode\Arg1_V = Thread\ECX Then
				lin = lin + "ecx"
			Else
				lin = lin + "[var]" ;TODO
			End If
			lin = lin + ", "
			lin = lin + InstrCode\Arg1_I
		Case READ_VAR_STRING
			If InstrCode\Arg1_V = Thread\EAX Then
				lin = lin + "eax"
			ElseIf InstrCode\Arg1_V = Thread\EBX Then
				lin = lin + "ebx"
			ElseIf InstrCode\Arg1_V = Thread\ECX Then
				lin = lin + "ecx"
			Else
				lin = lin + "[var]" ;TODO
			End If
			lin = lin + ", "
			lin = lin + Chr(34) + InstrCode\Arg1_S + Chr(34)
		Case READ_VAR_TYP
			If InstrCode\Arg1_V = Thread\EAX Then
				lin = lin + "eax"
			ElseIf InstrCode\Arg1_V = Thread\EBX Then
				lin = lin + "ebx"
			ElseIf InstrCode\Arg1_V = Thread\ECX Then
				lin = lin + "ecx"
			Else
				lin = lin + "[var]" ;TODO
			End If
			lin = lin + ", "
			lin = lin + "{type}"
		Case READ_CALL
			lin = lin + InstrCode\Arg1_I
			
	End Select

	Return lin
End Function