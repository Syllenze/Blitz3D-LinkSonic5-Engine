;__ GameScript [Functions] _________________________________________
;Copyright (C) 2005			John Judnich

;This file includes the identification and execution properties of external GameScript
;functions. To add you own functions to GameScript for your specific needs, modify this
;file.

;These constant values represent the numeric IDs for all functions
Const FUNC_ABS = 1
Const FUNC_RND = 2
Const FUNC_RAND = 3
Const FUNC_SQR = 4
Const FUNC_EXP = 5
Const FUNC_LOG = 6
Const FUNC_LOG10 = 7
Const FUNC_COS = 8
Const FUNC_SIN = 9
Const FUNC_TAN = 10
Const FUNC_ACOS = 11
Const FUNC_ASIN = 12
Const FUNC_ATAN = 13
Const FUNC_DELAY = 14
Const FUNC_PRINT = 15

;This array conatins the number of parameters for each function
Global FUNC_PARAMS[49]
FUNC_PARAMS[FUNC_ABS] = 1
FUNC_PARAMS[FUNC_RND] = 2
FUNC_PARAMS[FUNC_RAND] = 2
FUNC_PARAMS[FUNC_SQR] = 1
FUNC_PARAMS[FUNC_EXP] = 1
FUNC_PARAMS[FUNC_LOG] = 1
FUNC_PARAMS[FUNC_LOG10] = 1
FUNC_PARAMS[FUNC_COS] = 1
FUNC_PARAMS[FUNC_SIN] = 1
FUNC_PARAMS[FUNC_TAN] = 1
FUNC_PARAMS[FUNC_ACOS] = 1
FUNC_PARAMS[FUNC_ASIN] = 1
FUNC_PARAMS[FUNC_ATAN] = 1
FUNC_PARAMS[FUNC_DELAY] = 1
FUNC_PARAMS[FUNC_PRINT] = 1

;This function must return a numeric ID for a function called "Name$" if it exists, or
;-1 if it does not exist. Note: Name$ will be lower case.
Function GSF_FunctionID(Name$)
	Select Name
		Case "delay": Return FUNC_DELAY
		Case "abs": Return FUNC_ABS
		Case "rnd": Return FUNC_RND
		Case "rand": Return FUNC_RAND
		Case "sqr": Return FUNC_SQR
		Case "exp": Return FUNC_EXP
		Case "log": Return FUNC_LOG
		Case "log10": Return FUNC_LOG10
		Case "cos": Return FUNC_COS
		Case "sin": Return FUNC_SIN
		Case "tan": Return FUNC_TAN
		Case "acos": Return FUNC_ACOS
		Case "asin": Return FUNC_ASIN
		Case "atan": Return FUNC_ATAN	
		Case "print": Return FUNC_PRINT
		
		Default: Return -1
	End Select
End Function

;This function must execute the action of a specific function ID being called.
;To return a value, set GSF_ReturnVal$ to the value, and GSF_ReturnType to VAR_INT,
;VAR_FLOAT, VAR_STRING, or VAR_NONE, depending on what the return value.
;To get the function parameters, call GSF_GetParamF, GSF_GetParamI, or GSF_GetParamS
;(depending on the data type) to retreive the parameters one by one. Note: The parameters
;will be read from right to left from the funtion's call.
Global GSF_ReturnVal$, GSF_ReturnType
Function GSF_ExecuteFunction(Thread.GSV_Thread, FuncID)
	GSF_ReturnType = VAR_NONE
	
	Select FuncID
		;Basic System Functions
		Case FUNC_DELAY:
			;Thread\Paused = True
			;Thread\ResumeTime = MilliSecs() + GSF_GetParamI(Thread)
			Delay GSF_GetParamI(Thread)	;comment this out and uncomment the above two lines for multi-threaded delays
		
		;Basic Math Functions
		Case FUNC_RND:
			GSF_ReturnVal = Rnd(GSF_GetParamF(Thread), GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
			
		Case FUNC_RAND:
			GSF_ReturnVal = Rand(GSF_GetParamI(Thread), GSF_GetParamI(Thread))
			GSF_ReturnType = VAR_INT		
		
		Case FUNC_ABS:
			GSF_ReturnVal = Abs(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
			
		Case FUNC_SQR:
			GSF_ReturnVal = Sqr(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_EXP:
			GSF_ReturnVal = Exp(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_LOG:
			GSF_ReturnVal = Log(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_LOG10:
			GSF_ReturnVal = Log10(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_COS:
			GSF_ReturnVal = Cos(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_SIN:
			GSF_ReturnVal = Sin(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_TAN:
			GSF_ReturnVal = Tan(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_ACOS:
			GSF_ReturnVal = ACos(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_ASIN:
			GSF_ReturnVal = ASin(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_ATAN:
			GSF_ReturnVal = ATan(GSF_GetParamF(Thread))
			GSF_ReturnType = VAR_FLOAT
		
		Case FUNC_PRINT:
			Print GSF_GetParamS(Thread)
			
		Default:
			Return False
	End Select
	
	Return True
End Function

;This function should initialize all hard-coded constants. Use GSF_AddConstant() to
;add constants.
Function GSF_InitConstants()
	GSF_AddConstant "true", DATATYPE_INTEGER, 1
	GSF_AddConstant "false", DATATYPE_INTEGER, 0
	GSF_AddConstant "null", DATATYPE_STRING, "0"
End Function



Function GSF_GetParamF#(Thread.GSV_Thread)
	Return GSF_GetParamS(Thread)
End Function

Function GSF_GetParamI%(Thread.GSV_Thread)
	Return GSF_GetParamS(Thread)
End Function

Function GSF_GetParamS$(Thread.GSV_Thread)
		;Read the top stack item
		Var.GSV_Var = Thread\DataStack[Thread\DataStackLevel]
		Select Var\DataType
			Case VAR_INTEGER
				Ret$ = Var\I
			Case VAR_FLOAT
				Ret$ = Var\F
			Case VAR_STRING
				Ret$ = Var\S
		End Select
		
		;Now, remove the top stack item
		Delete Thread\DataStack[Thread\DataStackLevel]
		Thread\DataStackLevel = Thread\DataStackLevel - 1
		If Thread\DataStackLevel < 0 Then GSV_RuntimeError(Thread, "GSF_GetParam() Error: No remaining parameters.")
		
		;Return the value
		Return Ret
End Function

;Adds a constant variable to the compiler. Name$ should specify the name of the constant,
;and DataType should specify either DATATYPE_INTEGER, DATATYPE_STRING, or DATATYPE_FLOAT.
;Value$ should specify the constant value that the variable is given.
Function GSF_AddConstant(Name$, DataType, Value$)
	var.Var = New Var
	var\Name = Lower(Name)
	var\Owner = GSC_MainFunc
	var\DataType = DataType
	var\Scope = SCOPE_CONSTANT
	var\TypeDef = Null
	var\CustomType = Null
	var\Value = Value
	var\CodeNum = -100
End Function