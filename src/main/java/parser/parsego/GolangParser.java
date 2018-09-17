// Generated from /Users/jwx/Documents/antlr/goparser/src/main/java/multiParser.goextractor.parsego/Golang.g4 by ANTLR 4.7
package parser.parsego;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GolangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, IDENTIFIER=62, KEYWORD=63, BINARY_OP=64, INT_LIT=65, 
		FLOAT_LIT=66, IMAGINARY_LIT=67, RUNE_LIT=68, LITTLE_U_VALUE=69, BIG_U_VALUE=70, 
		STRING_LIT=71, WS=72, COMMENT=73, TERMINATOR=74, LINE_COMMENT=75;
	public static final int
		RULE_sourceFile = 0, RULE_packageClause = 1, RULE_importDecl = 2, RULE_importSpec = 3, 
		RULE_importPath = 4, RULE_topLevelDecl = 5, RULE_declaration = 6, RULE_constDecl = 7, 
		RULE_constSpec = 8, RULE_identifierList = 9, RULE_expressionList = 10, 
		RULE_typeDecl = 11, RULE_typeSpec = 12, RULE_functionDecl = 13, RULE_function = 14, 
		RULE_methodDecl = 15, RULE_receiver = 16, RULE_varDecl = 17, RULE_varSpec = 18, 
		RULE_block = 19, RULE_statementList = 20, RULE_statement = 21, RULE_simpleStmt = 22, 
		RULE_expressionStmt = 23, RULE_sendStmt = 24, RULE_incDecStmt = 25, RULE_assignment = 26, 
		RULE_leftAssignment = 27, RULE_rightAssignment = 28, RULE_assign_op = 29, 
		RULE_shortVarDecl = 30, RULE_leftShortVarDecl = 31, RULE_rightShortVarDecl = 32, 
		RULE_emptyStmt = 33, RULE_labeledStmt = 34, RULE_returnStmt = 35, RULE_breakStmt = 36, 
		RULE_continueStmt = 37, RULE_gotoStmt = 38, RULE_fallthroughStmt = 39, 
		RULE_deferStmt = 40, RULE_ifStmt = 41, RULE_ifStmtIf = 42, RULE_ifStmtElse = 43, 
		RULE_switchStmt = 44, RULE_exprSwitchStmt = 45, RULE_exprCaseClause = 46, 
		RULE_exprSwitchCase = 47, RULE_typeSwitchStmt = 48, RULE_typeSwitchGuard = 49, 
		RULE_typeCaseClause = 50, RULE_typeSwitchCase = 51, RULE_typeList = 52, 
		RULE_selectStmt = 53, RULE_commClause = 54, RULE_commCase = 55, RULE_recvStmt = 56, 
		RULE_forStmt = 57, RULE_forClause = 58, RULE_rangeClause = 59, RULE_goStmt = 60, 
		RULE_type = 61, RULE_typeName = 62, RULE_typeLit = 63, RULE_arrayType = 64, 
		RULE_arrayLength = 65, RULE_elementType = 66, RULE_pointerType = 67, RULE_interfaceType = 68, 
		RULE_sliceType = 69, RULE_mapType = 70, RULE_channelType = 71, RULE_methodSpec = 72, 
		RULE_functionType = 73, RULE_signature = 74, RULE_result = 75, RULE_parameters = 76, 
		RULE_parameterList = 77, RULE_parameterDecl = 78, RULE_operand = 79, RULE_literal = 80, 
		RULE_basicLit = 81, RULE_operandName = 82, RULE_qualifiedIdent = 83, RULE_compositeLit = 84, 
		RULE_literalType = 85, RULE_literalValue = 86, RULE_elementList = 87, 
		RULE_keyedElement = 88, RULE_key = 89, RULE_element = 90, RULE_structType = 91, 
		RULE_fieldDecl = 92, RULE_anonymousField = 93, RULE_functionLit = 94, 
		RULE_primaryExpr = 95, RULE_selector = 96, RULE_index = 97, RULE_slice = 98, 
		RULE_typeAssertion = 99, RULE_arguments = 100, RULE_methodExpr = 101, 
		RULE_receiverType = 102, RULE_expression = 103, RULE_unaryExpr = 104, 
		RULE_conversion = 105, RULE_eos = 106;
	public static final String[] ruleNames = {
		"sourceFile", "packageClause", "importDecl", "importSpec", "importPath", 
		"topLevelDecl", "declaration", "constDecl", "constSpec", "identifierList", 
		"expressionList", "typeDecl", "typeSpec", "functionDecl", "function", 
		"methodDecl", "receiver", "varDecl", "varSpec", "block", "statementList", 
		"statement", "simpleStmt", "expressionStmt", "sendStmt", "incDecStmt", 
		"assignment", "leftAssignment", "rightAssignment", "assign_op", "shortVarDecl", 
		"leftShortVarDecl", "rightShortVarDecl", "emptyStmt", "labeledStmt", "returnStmt", 
		"breakStmt", "continueStmt", "gotoStmt", "fallthroughStmt", "deferStmt", 
		"ifStmt", "ifStmtIf", "ifStmtElse", "switchStmt", "exprSwitchStmt", "exprCaseClause", 
		"exprSwitchCase", "typeSwitchStmt", "typeSwitchGuard", "typeCaseClause", 
		"typeSwitchCase", "typeList", "selectStmt", "commClause", "commCase", 
		"recvStmt", "forStmt", "forClause", "rangeClause", "goStmt", "type", "typeName", 
		"typeLit", "arrayType", "arrayLength", "elementType", "pointerType", "interfaceType", 
		"sliceType", "mapType", "channelType", "methodSpec", "functionType", "signature", 
		"result", "parameters", "parameterList", "parameterDecl", "operand", "literal", 
		"basicLit", "operandName", "qualifiedIdent", "compositeLit", "literalType", 
		"literalValue", "elementList", "keyedElement", "key", "element", "structType", 
		"fieldDecl", "anonymousField", "functionLit", "primaryExpr", "selector", 
		"index", "slice", "typeAssertion", "arguments", "methodExpr", "receiverType", 
		"expression", "unaryExpr", "conversion", "eos"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'package'", "'import'", "'('", "')'", "'.'", "'const'", "'='", 
		"','", "'type'", "';'", "'func'", "'var'", "'{'", "'}'", "'<-'", "'++'", 
		"'--'", "'+'", "'-'", "'|'", "'^'", "'*'", "'/'", "'%'", "'<<'", "'>>'", 
		"'&'", "'&^'", "':='", "':'", "'return'", "'break'", "'continue'", "'goto'", 
		"'fallthrough'", "'defer'", "'if'", "'else'", "'switch'", "'case'", "'default'", 
		"'select'", "'for'", "'range'", "'go'", "'['", "']'", "'interface'", "'map'", 
		"'chan'", "'...'", "'struct'", "'||'", "'&&'", "'=='", "'!='", "'<'", 
		"'<='", "'>'", "'>='", "'!'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "IDENTIFIER", "KEYWORD", "BINARY_OP", "INT_LIT", "FLOAT_LIT", 
		"IMAGINARY_LIT", "RUNE_LIT", "LITTLE_U_VALUE", "BIG_U_VALUE", "STRING_LIT", 
		"WS", "COMMENT", "TERMINATOR", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Golang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	/**
	 * Returns {@code true} iff on the current index of the parser's
	 * token stream a token exists on the {@code HIDDEN} channel which
	 * either is a line terminator, or is a multi line comment that
	 * contains a line terminator.
	 *
	 * @return {@code true} iff on the current index of the parser's
	 * token stream a token exists on the {@code HIDDEN} channel which
	 * either is a line terminator, or is a multi line comment that
	 * contains a line terminator.
	 */
	private boolean lineTerminatorAhead() {
		// Get the token ahead of the current index.
		int possibleIndexEosToken = this.getCurrentToken().getTokenIndex() - 1;
		Token ahead = _input.get(possibleIndexEosToken);
		if (ahead.getChannel() != Lexer.HIDDEN) {
			// We're only interested in tokens on the HIDDEN channel.
			return false;
		}

		if (ahead.getType() == TERMINATOR) {
			// There is definitely a line terminator ahead.
			return true;
		}

		if (ahead.getType() == WS) {
			// Get the token ahead of the current whitespaces.
			possibleIndexEosToken = this.getCurrentToken().getTokenIndex() - 2;
			ahead = _input.get(possibleIndexEosToken);
		}

		// Get the token's text and type.
		String text = ahead.getText();
		int type = ahead.getType();

		// Check if the token is, or contains a line terminator.
		return (type == COMMENT && (text.contains("\r") || text.contains("\n"))) ||
				(type == TERMINATOR);
	}


	public GolangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SourceFileContext extends ParserRuleContext {
		public PackageClauseContext packageClause() {
			return getRuleContext(PackageClauseContext.class,0);
		}
		public List<EosContext> eos() {
			return getRuleContexts(EosContext.class);
		}
		public EosContext eos(int i) {
			return getRuleContext(EosContext.class,i);
		}
		public List<ImportDeclContext> importDecl() {
			return getRuleContexts(ImportDeclContext.class);
		}
		public ImportDeclContext importDecl(int i) {
			return getRuleContext(ImportDeclContext.class,i);
		}
		public List<TopLevelDeclContext> topLevelDecl() {
			return getRuleContexts(TopLevelDeclContext.class);
		}
		public TopLevelDeclContext topLevelDecl(int i) {
			return getRuleContext(TopLevelDeclContext.class,i);
		}
		public SourceFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sourceFile; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSourceFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SourceFileContext sourceFile() throws RecognitionException {
		SourceFileContext _localctx = new SourceFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_sourceFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			packageClause();
			setState(215);
			eos();
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(216);
				importDecl();
				setState(217);
				eos();
				}
				}
				setState(223);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__10) | (1L << T__11))) != 0)) {
				{
				{
				setState(224);
				topLevelDecl();
				setState(225);
				eos();
				}
				}
				setState(231);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PackageClauseContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public PackageClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitPackageClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageClauseContext packageClause() throws RecognitionException {
		PackageClauseContext _localctx = new PackageClauseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_packageClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(T__0);
			setState(233);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDeclContext extends ParserRuleContext {
		public List<ImportSpecContext> importSpec() {
			return getRuleContexts(ImportSpecContext.class);
		}
		public ImportSpecContext importSpec(int i) {
			return getRuleContext(ImportSpecContext.class,i);
		}
		public List<EosContext> eos() {
			return getRuleContexts(EosContext.class);
		}
		public EosContext eos(int i) {
			return getRuleContext(EosContext.class,i);
		}
		public ImportDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitImportDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclContext importDecl() throws RecognitionException {
		ImportDeclContext _localctx = new ImportDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(T__1);
			setState(247);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
			case IDENTIFIER:
			case STRING_LIT:
				{
				setState(236);
				importSpec();
				}
				break;
			case T__2:
				{
				setState(237);
				match(T__2);
				setState(243);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4 || _la==IDENTIFIER || _la==STRING_LIT) {
					{
					{
					setState(238);
					importSpec();
					setState(239);
					eos();
					}
					}
					setState(245);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(246);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportSpecContext extends ParserRuleContext {
		public ImportPathContext importPath() {
			return getRuleContext(ImportPathContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public ImportSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitImportSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportSpecContext importSpec() throws RecognitionException {
		ImportSpecContext _localctx = new ImportSpecContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_importSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4 || _la==IDENTIFIER) {
				{
				setState(249);
				_la = _input.LA(1);
				if ( !(_la==T__4 || _la==IDENTIFIER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(252);
			importPath();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportPathContext extends ParserRuleContext {
		public TerminalNode STRING_LIT() { return getToken(GolangParser.STRING_LIT, 0); }
		public ImportPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importPath; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitImportPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportPathContext importPath() throws RecognitionException {
		ImportPathContext _localctx = new ImportPathContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_importPath);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(STRING_LIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TopLevelDeclContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public FunctionDeclContext functionDecl() {
			return getRuleContext(FunctionDeclContext.class,0);
		}
		public MethodDeclContext methodDecl() {
			return getRuleContext(MethodDeclContext.class,0);
		}
		public TopLevelDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_topLevelDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTopLevelDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TopLevelDeclContext topLevelDecl() throws RecognitionException {
		TopLevelDeclContext _localctx = new TopLevelDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_topLevelDecl);
		try {
			setState(259);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(256);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(257);
				functionDecl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(258);
				methodDecl();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public ConstDeclContext constDecl() {
			return getRuleContext(ConstDeclContext.class,0);
		}
		public TypeDeclContext typeDecl() {
			return getRuleContext(TypeDeclContext.class,0);
		}
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_declaration);
		try {
			setState(264);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				constDecl();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				typeDecl();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(263);
				varDecl();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstDeclContext extends ParserRuleContext {
		public List<ConstSpecContext> constSpec() {
			return getRuleContexts(ConstSpecContext.class);
		}
		public ConstSpecContext constSpec(int i) {
			return getRuleContext(ConstSpecContext.class,i);
		}
		public List<EosContext> eos() {
			return getRuleContexts(EosContext.class);
		}
		public EosContext eos(int i) {
			return getRuleContext(EosContext.class,i);
		}
		public ConstDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitConstDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstDeclContext constDecl() throws RecognitionException {
		ConstDeclContext _localctx = new ConstDeclContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_constDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			match(T__5);
			setState(278);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(267);
				constSpec();
				}
				break;
			case T__2:
				{
				setState(268);
				match(T__2);
				setState(274);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==IDENTIFIER) {
					{
					{
					setState(269);
					constSpec();
					setState(270);
					eos();
					}
					}
					setState(276);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(277);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstSpecContext extends ParserRuleContext {
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ConstSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitConstSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstSpecContext constSpec() throws RecognitionException {
		ConstSpecContext _localctx = new ConstSpecContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			identifierList();
			setState(286);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(282);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__21) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << IDENTIFIER))) != 0)) {
					{
					setState(281);
					type();
					}
				}

				setState(284);
				match(T__6);
				setState(285);
				expressionList();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierListContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(GolangParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GolangParser.IDENTIFIER, i);
		}
		public IdentifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitIdentifierList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierListContext identifierList() throws RecognitionException {
		IdentifierListContext _localctx = new IdentifierListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_identifierList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(IDENTIFIER);
			setState(293);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(289);
					match(T__7);
					setState(290);
					match(IDENTIFIER);
					}
					} 
				}
				setState(295);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expressionList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			expression(0);
			setState(301);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(297);
					match(T__7);
					setState(298);
					expression(0);
					}
					} 
				}
				setState(303);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDeclContext extends ParserRuleContext {
		public List<TypeSpecContext> typeSpec() {
			return getRuleContexts(TypeSpecContext.class);
		}
		public TypeSpecContext typeSpec(int i) {
			return getRuleContext(TypeSpecContext.class,i);
		}
		public TypeDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDeclContext typeDecl() throws RecognitionException {
		TypeDeclContext _localctx = new TypeDeclContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_typeDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			match(T__8);
			setState(316);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(305);
				typeSpec();
				}
				break;
			case T__2:
				{
				setState(306);
				match(T__2);
				setState(312);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==IDENTIFIER) {
					{
					{
					setState(307);
					typeSpec();
					setState(308);
					match(T__9);
					}
					}
					setState(314);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(315);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSpecContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeSpecContext typeSpec() throws RecognitionException {
		TypeSpecContext _localctx = new TypeSpecContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_typeSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			match(IDENTIFIER);
			setState(319);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public FunctionDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitFunctionDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclContext functionDecl() throws RecognitionException {
		FunctionDeclContext _localctx = new FunctionDeclContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_functionDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(321);
			match(T__10);
			setState(322);
			match(IDENTIFIER);
			setState(325);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(323);
				function();
				}
				break;
			case 2:
				{
				setState(324);
				signature();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			signature();
			setState(328);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclContext extends ParserRuleContext {
		public ReceiverContext receiver() {
			return getRuleContext(ReceiverContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public MethodDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitMethodDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclContext methodDecl() throws RecognitionException {
		MethodDeclContext _localctx = new MethodDeclContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_methodDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			match(T__10);
			setState(331);
			receiver();
			setState(332);
			match(IDENTIFIER);
			setState(335);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(333);
				function();
				}
				break;
			case 2:
				{
				setState(334);
				signature();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReceiverContext extends ParserRuleContext {
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public ReceiverContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_receiver; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitReceiver(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReceiverContext receiver() throws RecognitionException {
		ReceiverContext _localctx = new ReceiverContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_receiver);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			parameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclContext extends ParserRuleContext {
		public List<VarSpecContext> varSpec() {
			return getRuleContexts(VarSpecContext.class);
		}
		public VarSpecContext varSpec(int i) {
			return getRuleContext(VarSpecContext.class,i);
		}
		public List<EosContext> eos() {
			return getRuleContexts(EosContext.class);
		}
		public EosContext eos(int i) {
			return getRuleContext(EosContext.class,i);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			match(T__11);
			setState(351);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(340);
				varSpec();
				}
				break;
			case T__2:
				{
				setState(341);
				match(T__2);
				setState(347);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==IDENTIFIER) {
					{
					{
					setState(342);
					varSpec();
					setState(343);
					eos();
					}
					}
					setState(349);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(350);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarSpecContext extends ParserRuleContext {
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public VarSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitVarSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarSpecContext varSpec() throws RecognitionException {
		VarSpecContext _localctx = new VarSpecContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_varSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			identifierList();
			setState(361);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__10:
			case T__14:
			case T__21:
			case T__45:
			case T__47:
			case T__48:
			case T__49:
			case T__51:
			case IDENTIFIER:
				{
				setState(354);
				type();
				setState(357);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(355);
					match(T__6);
					setState(356);
					expressionList();
					}
					break;
				}
				}
				break;
			case T__6:
				{
				setState(359);
				match(T__6);
				setState(360);
				expressionList();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			match(T__12);
			setState(364);
			statementList();
			setState(365);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementListContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<EosContext> eos() {
			return getRuleContexts(EosContext.class);
		}
		public EosContext eos(int i) {
			return getRuleContext(EosContext.class,i);
		}
		public StatementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitStatementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementListContext statementList() throws RecognitionException {
		StatementListContext _localctx = new StatementListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_statementList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(372);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__5) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__38) | (1L << T__41) | (1L << T__42) | (1L << T__44) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
				{
				{
				setState(367);
				statement();
				setState(368);
				eos();
				}
				}
				setState(374);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public LabeledStmtContext labeledStmt() {
			return getRuleContext(LabeledStmtContext.class,0);
		}
		public SimpleStmtContext simpleStmt() {
			return getRuleContext(SimpleStmtContext.class,0);
		}
		public GoStmtContext goStmt() {
			return getRuleContext(GoStmtContext.class,0);
		}
		public ReturnStmtContext returnStmt() {
			return getRuleContext(ReturnStmtContext.class,0);
		}
		public BreakStmtContext breakStmt() {
			return getRuleContext(BreakStmtContext.class,0);
		}
		public ContinueStmtContext continueStmt() {
			return getRuleContext(ContinueStmtContext.class,0);
		}
		public GotoStmtContext gotoStmt() {
			return getRuleContext(GotoStmtContext.class,0);
		}
		public FallthroughStmtContext fallthroughStmt() {
			return getRuleContext(FallthroughStmtContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public SwitchStmtContext switchStmt() {
			return getRuleContext(SwitchStmtContext.class,0);
		}
		public SelectStmtContext selectStmt() {
			return getRuleContext(SelectStmtContext.class,0);
		}
		public ForStmtContext forStmt() {
			return getRuleContext(ForStmtContext.class,0);
		}
		public DeferStmtContext deferStmt() {
			return getRuleContext(DeferStmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_statement);
		try {
			setState(390);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(375);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(376);
				labeledStmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(377);
				simpleStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(378);
				goStmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(379);
				returnStmt();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(380);
				breakStmt();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(381);
				continueStmt();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(382);
				gotoStmt();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(383);
				fallthroughStmt();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(384);
				block();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(385);
				ifStmt();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(386);
				switchStmt();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(387);
				selectStmt();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(388);
				forStmt();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(389);
				deferStmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleStmtContext extends ParserRuleContext {
		public SendStmtContext sendStmt() {
			return getRuleContext(SendStmtContext.class,0);
		}
		public ExpressionStmtContext expressionStmt() {
			return getRuleContext(ExpressionStmtContext.class,0);
		}
		public IncDecStmtContext incDecStmt() {
			return getRuleContext(IncDecStmtContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ShortVarDeclContext shortVarDecl() {
			return getRuleContext(ShortVarDeclContext.class,0);
		}
		public EmptyStmtContext emptyStmt() {
			return getRuleContext(EmptyStmtContext.class,0);
		}
		public SimpleStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSimpleStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleStmtContext simpleStmt() throws RecognitionException {
		SimpleStmtContext _localctx = new SimpleStmtContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_simpleStmt);
		try {
			setState(398);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(392);
				sendStmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(393);
				expressionStmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(394);
				incDecStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(395);
				assignment();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(396);
				shortVarDecl();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(397);
				emptyStmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStmtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitExpressionStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStmtContext expressionStmt() throws RecognitionException {
		ExpressionStmtContext _localctx = new ExpressionStmtContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_expressionStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SendStmtContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SendStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sendStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSendStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SendStmtContext sendStmt() throws RecognitionException {
		SendStmtContext _localctx = new SendStmtContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_sendStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			expression(0);
			setState(403);
			match(T__14);
			setState(404);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IncDecStmtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IncDecStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incDecStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitIncDecStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncDecStmtContext incDecStmt() throws RecognitionException {
		IncDecStmtContext _localctx = new IncDecStmtContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_incDecStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406);
			expression(0);
			setState(407);
			_la = _input.LA(1);
			if ( !(_la==T__15 || _la==T__16) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public LeftAssignmentContext leftAssignment() {
			return getRuleContext(LeftAssignmentContext.class,0);
		}
		public Assign_opContext assign_op() {
			return getRuleContext(Assign_opContext.class,0);
		}
		public RightAssignmentContext rightAssignment() {
			return getRuleContext(RightAssignmentContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			leftAssignment();
			setState(410);
			assign_op();
			setState(411);
			rightAssignment();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LeftAssignmentContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public LeftAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_leftAssignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitLeftAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LeftAssignmentContext leftAssignment() throws RecognitionException {
		LeftAssignmentContext _localctx = new LeftAssignmentContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_leftAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(413);
			expressionList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RightAssignmentContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public RightAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rightAssignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitRightAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RightAssignmentContext rightAssignment() throws RecognitionException {
		RightAssignmentContext _localctx = new RightAssignmentContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_rightAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(415);
			expressionList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_opContext extends ParserRuleContext {
		public Assign_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_op; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitAssign_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_opContext assign_op() throws RecognitionException {
		Assign_opContext _localctx = new Assign_opContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_assign_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27))) != 0)) {
				{
				setState(417);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(420);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShortVarDeclContext extends ParserRuleContext {
		public LeftShortVarDeclContext leftShortVarDecl() {
			return getRuleContext(LeftShortVarDeclContext.class,0);
		}
		public RightShortVarDeclContext rightShortVarDecl() {
			return getRuleContext(RightShortVarDeclContext.class,0);
		}
		public ShortVarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shortVarDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitShortVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShortVarDeclContext shortVarDecl() throws RecognitionException {
		ShortVarDeclContext _localctx = new ShortVarDeclContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_shortVarDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			leftShortVarDecl();
			setState(423);
			match(T__28);
			setState(424);
			rightShortVarDecl();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LeftShortVarDeclContext extends ParserRuleContext {
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public LeftShortVarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_leftShortVarDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitLeftShortVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LeftShortVarDeclContext leftShortVarDecl() throws RecognitionException {
		LeftShortVarDeclContext _localctx = new LeftShortVarDeclContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_leftShortVarDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			identifierList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RightShortVarDeclContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public RightShortVarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rightShortVarDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitRightShortVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RightShortVarDeclContext rightShortVarDecl() throws RecognitionException {
		RightShortVarDeclContext _localctx = new RightShortVarDeclContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_rightShortVarDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(428);
			expressionList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyStmtContext extends ParserRuleContext {
		public EmptyStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitEmptyStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyStmtContext emptyStmt() throws RecognitionException {
		EmptyStmtContext _localctx = new EmptyStmtContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_emptyStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabeledStmtContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public LabeledStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labeledStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitLabeledStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabeledStmtContext labeledStmt() throws RecognitionException {
		LabeledStmtContext _localctx = new LabeledStmtContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_labeledStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(432);
			match(IDENTIFIER);
			setState(433);
			match(T__29);
			setState(434);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStmtContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStmtContext returnStmt() throws RecognitionException {
		ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_returnStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(436);
			match(T__30);
			setState(438);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(437);
				expressionList();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStmtContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public BreakStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitBreakStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStmtContext breakStmt() throws RecognitionException {
		BreakStmtContext _localctx = new BreakStmtContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_breakStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(440);
			match(T__31);
			setState(442);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(441);
				match(IDENTIFIER);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStmtContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public ContinueStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitContinueStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStmtContext continueStmt() throws RecognitionException {
		ContinueStmtContext _localctx = new ContinueStmtContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_continueStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			match(T__32);
			setState(446);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(445);
				match(IDENTIFIER);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GotoStmtContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public GotoStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gotoStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitGotoStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GotoStmtContext gotoStmt() throws RecognitionException {
		GotoStmtContext _localctx = new GotoStmtContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_gotoStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(448);
			match(T__33);
			setState(449);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FallthroughStmtContext extends ParserRuleContext {
		public FallthroughStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fallthroughStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitFallthroughStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FallthroughStmtContext fallthroughStmt() throws RecognitionException {
		FallthroughStmtContext _localctx = new FallthroughStmtContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_fallthroughStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(451);
			match(T__34);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeferStmtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeferStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deferStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitDeferStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeferStmtContext deferStmt() throws RecognitionException {
		DeferStmtContext _localctx = new DeferStmtContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_deferStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(453);
			match(T__35);
			setState(454);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStmtContext extends ParserRuleContext {
		public IfStmtIfContext ifStmtIf() {
			return getRuleContext(IfStmtIfContext.class,0);
		}
		public IfStmtElseContext ifStmtElse() {
			return getRuleContext(IfStmtElseContext.class,0);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_ifStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(456);
			ifStmtIf();
			setState(458);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(457);
				ifStmtElse();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStmtIfContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SimpleStmtContext simpleStmt() {
			return getRuleContext(SimpleStmtContext.class,0);
		}
		public IfStmtIfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmtIf; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitIfStmtIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtIfContext ifStmtIf() throws RecognitionException {
		IfStmtIfContext _localctx = new IfStmtIfContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_ifStmtIf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(460);
			match(T__36);
			setState(464);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(461);
				simpleStmt();
				setState(462);
				match(T__9);
				}
				break;
			}
			setState(466);
			expression(0);
			setState(467);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStmtElseContext extends ParserRuleContext {
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfStmtElseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmtElse; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitIfStmtElse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtElseContext ifStmtElse() throws RecognitionException {
		IfStmtElseContext _localctx = new IfStmtElseContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_ifStmtElse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(469);
			match(T__37);
			setState(472);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__36:
				{
				setState(470);
				ifStmt();
				}
				break;
			case T__12:
				{
				setState(471);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchStmtContext extends ParserRuleContext {
		public ExprSwitchStmtContext exprSwitchStmt() {
			return getRuleContext(ExprSwitchStmtContext.class,0);
		}
		public TypeSwitchStmtContext typeSwitchStmt() {
			return getRuleContext(TypeSwitchStmtContext.class,0);
		}
		public SwitchStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSwitchStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStmtContext switchStmt() throws RecognitionException {
		SwitchStmtContext _localctx = new SwitchStmtContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_switchStmt);
		try {
			setState(476);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(474);
				exprSwitchStmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(475);
				typeSwitchStmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprSwitchStmtContext extends ParserRuleContext {
		public SimpleStmtContext simpleStmt() {
			return getRuleContext(SimpleStmtContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<ExprCaseClauseContext> exprCaseClause() {
			return getRuleContexts(ExprCaseClauseContext.class);
		}
		public ExprCaseClauseContext exprCaseClause(int i) {
			return getRuleContext(ExprCaseClauseContext.class,i);
		}
		public ExprSwitchStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprSwitchStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitExprSwitchStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprSwitchStmtContext exprSwitchStmt() throws RecognitionException {
		ExprSwitchStmtContext _localctx = new ExprSwitchStmtContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_exprSwitchStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
			match(T__38);
			setState(482);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(479);
				simpleStmt();
				setState(480);
				match(T__9);
				}
				break;
			}
			setState(485);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
				{
				setState(484);
				expression(0);
				}
			}

			setState(487);
			match(T__12);
			setState(491);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__39 || _la==T__40) {
				{
				{
				setState(488);
				exprCaseClause();
				}
				}
				setState(493);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(494);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprCaseClauseContext extends ParserRuleContext {
		public ExprSwitchCaseContext exprSwitchCase() {
			return getRuleContext(ExprSwitchCaseContext.class,0);
		}
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public ExprCaseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprCaseClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitExprCaseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprCaseClauseContext exprCaseClause() throws RecognitionException {
		ExprCaseClauseContext _localctx = new ExprCaseClauseContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_exprCaseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(496);
			exprSwitchCase();
			setState(497);
			match(T__29);
			setState(498);
			statementList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprSwitchCaseContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ExprSwitchCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprSwitchCase; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitExprSwitchCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprSwitchCaseContext exprSwitchCase() throws RecognitionException {
		ExprSwitchCaseContext _localctx = new ExprSwitchCaseContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_exprSwitchCase);
		try {
			setState(503);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__39:
				enterOuterAlt(_localctx, 1);
				{
				setState(500);
				match(T__39);
				setState(501);
				expressionList();
				}
				break;
			case T__40:
				enterOuterAlt(_localctx, 2);
				{
				setState(502);
				match(T__40);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSwitchStmtContext extends ParserRuleContext {
		public TypeSwitchGuardContext typeSwitchGuard() {
			return getRuleContext(TypeSwitchGuardContext.class,0);
		}
		public SimpleStmtContext simpleStmt() {
			return getRuleContext(SimpleStmtContext.class,0);
		}
		public List<TypeCaseClauseContext> typeCaseClause() {
			return getRuleContexts(TypeCaseClauseContext.class);
		}
		public TypeCaseClauseContext typeCaseClause(int i) {
			return getRuleContext(TypeCaseClauseContext.class,i);
		}
		public TypeSwitchStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSwitchStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeSwitchStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeSwitchStmtContext typeSwitchStmt() throws RecognitionException {
		TypeSwitchStmtContext _localctx = new TypeSwitchStmtContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_typeSwitchStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(505);
			match(T__38);
			setState(509);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(506);
				simpleStmt();
				setState(507);
				match(T__9);
				}
				break;
			}
			setState(511);
			typeSwitchGuard();
			setState(512);
			match(T__12);
			setState(516);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__39 || _la==T__40) {
				{
				{
				setState(513);
				typeCaseClause();
				}
				}
				setState(518);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(519);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSwitchGuardContext extends ParserRuleContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public TypeSwitchGuardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSwitchGuard; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeSwitchGuard(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeSwitchGuardContext typeSwitchGuard() throws RecognitionException {
		TypeSwitchGuardContext _localctx = new TypeSwitchGuardContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_typeSwitchGuard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(523);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(521);
				match(IDENTIFIER);
				setState(522);
				match(T__28);
				}
				break;
			}
			setState(525);
			primaryExpr(0);
			setState(526);
			match(T__4);
			setState(527);
			match(T__2);
			setState(528);
			match(T__8);
			setState(529);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeCaseClauseContext extends ParserRuleContext {
		public TypeSwitchCaseContext typeSwitchCase() {
			return getRuleContext(TypeSwitchCaseContext.class,0);
		}
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public TypeCaseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeCaseClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeCaseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeCaseClauseContext typeCaseClause() throws RecognitionException {
		TypeCaseClauseContext _localctx = new TypeCaseClauseContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_typeCaseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(531);
			typeSwitchCase();
			setState(532);
			match(T__29);
			setState(533);
			statementList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSwitchCaseContext extends ParserRuleContext {
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public TypeSwitchCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSwitchCase; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeSwitchCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeSwitchCaseContext typeSwitchCase() throws RecognitionException {
		TypeSwitchCaseContext _localctx = new TypeSwitchCaseContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_typeSwitchCase);
		try {
			setState(538);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__39:
				enterOuterAlt(_localctx, 1);
				{
				setState(535);
				match(T__39);
				setState(536);
				typeList();
				}
				break;
			case T__40:
				enterOuterAlt(_localctx, 2);
				{
				setState(537);
				match(T__40);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeListContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(540);
			type();
			setState(545);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(541);
				match(T__7);
				setState(542);
				type();
				}
				}
				setState(547);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectStmtContext extends ParserRuleContext {
		public List<CommClauseContext> commClause() {
			return getRuleContexts(CommClauseContext.class);
		}
		public CommClauseContext commClause(int i) {
			return getRuleContext(CommClauseContext.class,i);
		}
		public SelectStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSelectStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectStmtContext selectStmt() throws RecognitionException {
		SelectStmtContext _localctx = new SelectStmtContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_selectStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(548);
			match(T__41);
			setState(549);
			match(T__12);
			setState(553);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__39 || _la==T__40) {
				{
				{
				setState(550);
				commClause();
				}
				}
				setState(555);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(556);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommClauseContext extends ParserRuleContext {
		public CommCaseContext commCase() {
			return getRuleContext(CommCaseContext.class,0);
		}
		public StatementListContext statementList() {
			return getRuleContext(StatementListContext.class,0);
		}
		public CommClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitCommClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommClauseContext commClause() throws RecognitionException {
		CommClauseContext _localctx = new CommClauseContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_commClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(558);
			commCase();
			setState(559);
			match(T__29);
			setState(560);
			statementList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommCaseContext extends ParserRuleContext {
		public SendStmtContext sendStmt() {
			return getRuleContext(SendStmtContext.class,0);
		}
		public RecvStmtContext recvStmt() {
			return getRuleContext(RecvStmtContext.class,0);
		}
		public CommCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commCase; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitCommCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommCaseContext commCase() throws RecognitionException {
		CommCaseContext _localctx = new CommCaseContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_commCase);
		try {
			setState(568);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__39:
				enterOuterAlt(_localctx, 1);
				{
				setState(562);
				match(T__39);
				setState(565);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					{
					setState(563);
					sendStmt();
					}
					break;
				case 2:
					{
					setState(564);
					recvStmt();
					}
					break;
				}
				}
				break;
			case T__40:
				enterOuterAlt(_localctx, 2);
				{
				setState(567);
				match(T__40);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RecvStmtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public RecvStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recvStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitRecvStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RecvStmtContext recvStmt() throws RecognitionException {
		RecvStmtContext _localctx = new RecvStmtContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_recvStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(576);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				{
				setState(570);
				expressionList();
				setState(571);
				match(T__6);
				}
				break;
			case 2:
				{
				setState(573);
				identifierList();
				setState(574);
				match(T__28);
				}
				break;
			}
			setState(578);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStmtContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForClauseContext forClause() {
			return getRuleContext(ForClauseContext.class,0);
		}
		public RangeClauseContext rangeClause() {
			return getRuleContext(RangeClauseContext.class,0);
		}
		public ForStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStmtContext forStmt() throws RecognitionException {
		ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_forStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(580);
			match(T__42);
			setState(584);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				{
				setState(581);
				expression(0);
				}
				break;
			case 2:
				{
				setState(582);
				forClause();
				}
				break;
			case 3:
				{
				setState(583);
				rangeClause();
				}
				break;
			}
			setState(586);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForClauseContext extends ParserRuleContext {
		public List<SimpleStmtContext> simpleStmt() {
			return getRuleContexts(SimpleStmtContext.class);
		}
		public SimpleStmtContext simpleStmt(int i) {
			return getRuleContext(SimpleStmtContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitForClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForClauseContext forClause() throws RecognitionException {
		ForClauseContext _localctx = new ForClauseContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_forClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(589);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				{
				setState(588);
				simpleStmt();
				}
				break;
			}
			setState(591);
			match(T__9);
			setState(593);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
				{
				setState(592);
				expression(0);
				}
			}

			setState(595);
			match(T__9);
			setState(597);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__9) | (1L << T__10) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
				{
				setState(596);
				simpleStmt();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RangeClauseContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public RangeClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rangeClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitRangeClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RangeClauseContext rangeClause() throws RecognitionException {
		RangeClauseContext _localctx = new RangeClauseContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_rangeClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(605);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				{
				setState(599);
				expressionList();
				setState(600);
				match(T__6);
				}
				break;
			case 2:
				{
				setState(602);
				identifierList();
				setState(603);
				match(T__28);
				}
				break;
			}
			setState(607);
			match(T__43);
			setState(608);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoStmtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public GoStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitGoStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GoStmtContext goStmt() throws RecognitionException {
		GoStmtContext _localctx = new GoStmtContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_goStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(610);
			match(T__44);
			setState(611);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TypeLitContext typeLit() {
			return getRuleContext(TypeLitContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_type);
		try {
			setState(619);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(613);
				typeName();
				}
				break;
			case T__10:
			case T__14:
			case T__21:
			case T__45:
			case T__47:
			case T__48:
			case T__49:
			case T__51:
				enterOuterAlt(_localctx, 2);
				{
				setState(614);
				typeLit();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(615);
				match(T__2);
				setState(616);
				type();
				setState(617);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public QualifiedIdentContext qualifiedIdent() {
			return getRuleContext(QualifiedIdentContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_typeName);
		try {
			setState(623);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(621);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(622);
				qualifiedIdent();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeLitContext extends ParserRuleContext {
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public StructTypeContext structType() {
			return getRuleContext(StructTypeContext.class,0);
		}
		public PointerTypeContext pointerType() {
			return getRuleContext(PointerTypeContext.class,0);
		}
		public FunctionTypeContext functionType() {
			return getRuleContext(FunctionTypeContext.class,0);
		}
		public InterfaceTypeContext interfaceType() {
			return getRuleContext(InterfaceTypeContext.class,0);
		}
		public SliceTypeContext sliceType() {
			return getRuleContext(SliceTypeContext.class,0);
		}
		public MapTypeContext mapType() {
			return getRuleContext(MapTypeContext.class,0);
		}
		public ChannelTypeContext channelType() {
			return getRuleContext(ChannelTypeContext.class,0);
		}
		public TypeLitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeLit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeLit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeLitContext typeLit() throws RecognitionException {
		TypeLitContext _localctx = new TypeLitContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_typeLit);
		try {
			setState(633);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(625);
				arrayType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(626);
				structType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(627);
				pointerType();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(628);
				functionType();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(629);
				interfaceType();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(630);
				sliceType();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(631);
				mapType();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(632);
				channelType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public ArrayLengthContext arrayLength() {
			return getRuleContext(ArrayLengthContext.class,0);
		}
		public ElementTypeContext elementType() {
			return getRuleContext(ElementTypeContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_arrayType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(635);
			match(T__45);
			setState(636);
			arrayLength();
			setState(637);
			match(T__46);
			setState(638);
			elementType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayLengthContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayLengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLength; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitArrayLength(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLengthContext arrayLength() throws RecognitionException {
		ArrayLengthContext _localctx = new ArrayLengthContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_arrayLength);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(640);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ElementTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitElementType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementTypeContext elementType() throws RecognitionException {
		ElementTypeContext _localctx = new ElementTypeContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_elementType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(642);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PointerTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public PointerTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pointerType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitPointerType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PointerTypeContext pointerType() throws RecognitionException {
		PointerTypeContext _localctx = new PointerTypeContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_pointerType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(644);
			match(T__21);
			setState(645);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceTypeContext extends ParserRuleContext {
		public List<MethodSpecContext> methodSpec() {
			return getRuleContexts(MethodSpecContext.class);
		}
		public MethodSpecContext methodSpec(int i) {
			return getRuleContext(MethodSpecContext.class,i);
		}
		public List<EosContext> eos() {
			return getRuleContexts(EosContext.class);
		}
		public EosContext eos(int i) {
			return getRuleContext(EosContext.class,i);
		}
		public InterfaceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitInterfaceType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceTypeContext interfaceType() throws RecognitionException {
		InterfaceTypeContext _localctx = new InterfaceTypeContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_interfaceType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
			match(T__47);
			setState(648);
			match(T__12);
			setState(654);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(649);
				methodSpec();
				setState(650);
				eos();
				}
				}
				setState(656);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(657);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SliceTypeContext extends ParserRuleContext {
		public ElementTypeContext elementType() {
			return getRuleContext(ElementTypeContext.class,0);
		}
		public SliceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sliceType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSliceType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SliceTypeContext sliceType() throws RecognitionException {
		SliceTypeContext _localctx = new SliceTypeContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_sliceType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(659);
			match(T__45);
			setState(660);
			match(T__46);
			setState(661);
			elementType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MapTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ElementTypeContext elementType() {
			return getRuleContext(ElementTypeContext.class,0);
		}
		public MapTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitMapType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapTypeContext mapType() throws RecognitionException {
		MapTypeContext _localctx = new MapTypeContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_mapType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(663);
			match(T__48);
			setState(664);
			match(T__45);
			setState(665);
			type();
			setState(666);
			match(T__46);
			setState(667);
			elementType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ChannelTypeContext extends ParserRuleContext {
		public ElementTypeContext elementType() {
			return getRuleContext(ElementTypeContext.class,0);
		}
		public ChannelTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_channelType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitChannelType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChannelTypeContext channelType() throws RecognitionException {
		ChannelTypeContext _localctx = new ChannelTypeContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_channelType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				setState(669);
				match(T__49);
				}
				break;
			case 2:
				{
				setState(670);
				match(T__49);
				setState(671);
				match(T__14);
				}
				break;
			case 3:
				{
				setState(672);
				match(T__14);
				setState(673);
				match(T__49);
				}
				break;
			}
			setState(676);
			elementType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodSpecContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public MethodSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodSpec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitMethodSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodSpecContext methodSpec() throws RecognitionException {
		MethodSpecContext _localctx = new MethodSpecContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_methodSpec);
		try {
			setState(681);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(678);
				match(IDENTIFIER);
				setState(679);
				signature();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(680);
				typeName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTypeContext extends ParserRuleContext {
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public FunctionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitFunctionType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionTypeContext functionType() throws RecognitionException {
		FunctionTypeContext _localctx = new FunctionTypeContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_functionType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(683);
			match(T__10);
			setState(684);
			signature();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SignatureContext extends ParserRuleContext {
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public ResultContext result() {
			return getRuleContext(ResultContext.class,0);
		}
		public SignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signature; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignatureContext signature() throws RecognitionException {
		SignatureContext _localctx = new SignatureContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_signature);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(686);
			parameters();
			setState(688);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(687);
				result();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResultContext extends ParserRuleContext {
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_result; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitResult(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResultContext result() throws RecognitionException {
		ResultContext _localctx = new ResultContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_result);
		try {
			setState(692);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(690);
				parameters();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(691);
				type();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametersContext extends ParserRuleContext {
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(694);
			match(T__2);
			setState(699);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__21) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(695);
				parameterList();
				setState(697);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(696);
					match(T__7);
					}
				}

				}
			}

			setState(701);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterDeclContext> parameterDecl() {
			return getRuleContexts(ParameterDeclContext.class);
		}
		public ParameterDeclContext parameterDecl(int i) {
			return getRuleContext(ParameterDeclContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_parameterList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(703);
			parameterDecl();
			setState(708);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(704);
					match(T__7);
					setState(705);
					parameterDecl();
					}
					} 
				}
				setState(710);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public ParameterDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitParameterDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterDeclContext parameterDecl() throws RecognitionException {
		ParameterDeclContext _localctx = new ParameterDeclContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_parameterDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(712);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				{
				setState(711);
				identifierList();
				}
				break;
			}
			setState(715);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__50) {
				{
				setState(714);
				match(T__50);
				}
			}

			setState(717);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperandContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public OperandNameContext operandName() {
			return getRuleContext(OperandNameContext.class,0);
		}
		public MethodExprContext methodExpr() {
			return getRuleContext(MethodExprContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public OperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operand; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitOperand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperandContext operand() throws RecognitionException {
		OperandContext _localctx = new OperandContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_operand);
		try {
			setState(726);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(719);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(720);
				operandName();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(721);
				methodExpr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(722);
				match(T__2);
				setState(723);
				expression(0);
				setState(724);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public BasicLitContext basicLit() {
			return getRuleContext(BasicLitContext.class,0);
		}
		public CompositeLitContext compositeLit() {
			return getRuleContext(CompositeLitContext.class,0);
		}
		public FunctionLitContext functionLit() {
			return getRuleContext(FunctionLitContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_literal);
		try {
			setState(731);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_LIT:
			case FLOAT_LIT:
			case IMAGINARY_LIT:
			case RUNE_LIT:
			case STRING_LIT:
				enterOuterAlt(_localctx, 1);
				{
				setState(728);
				basicLit();
				}
				break;
			case T__45:
			case T__48:
			case T__51:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(729);
				compositeLit();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 3);
				{
				setState(730);
				functionLit();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BasicLitContext extends ParserRuleContext {
		public TerminalNode INT_LIT() { return getToken(GolangParser.INT_LIT, 0); }
		public TerminalNode FLOAT_LIT() { return getToken(GolangParser.FLOAT_LIT, 0); }
		public TerminalNode IMAGINARY_LIT() { return getToken(GolangParser.IMAGINARY_LIT, 0); }
		public TerminalNode RUNE_LIT() { return getToken(GolangParser.RUNE_LIT, 0); }
		public TerminalNode STRING_LIT() { return getToken(GolangParser.STRING_LIT, 0); }
		public BasicLitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicLit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitBasicLit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BasicLitContext basicLit() throws RecognitionException {
		BasicLitContext _localctx = new BasicLitContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_basicLit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(733);
			_la = _input.LA(1);
			if ( !(((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperandNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public QualifiedIdentContext qualifiedIdent() {
			return getRuleContext(QualifiedIdentContext.class,0);
		}
		public OperandNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operandName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitOperandName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperandNameContext operandName() throws RecognitionException {
		OperandNameContext _localctx = new OperandNameContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_operandName);
		try {
			setState(737);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(735);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(736);
				qualifiedIdent();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedIdentContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(GolangParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(GolangParser.IDENTIFIER, i);
		}
		public QualifiedIdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedIdent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitQualifiedIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedIdentContext qualifiedIdent() throws RecognitionException {
		QualifiedIdentContext _localctx = new QualifiedIdentContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_qualifiedIdent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(739);
			match(IDENTIFIER);
			setState(740);
			match(T__4);
			setState(741);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompositeLitContext extends ParserRuleContext {
		public LiteralTypeContext literalType() {
			return getRuleContext(LiteralTypeContext.class,0);
		}
		public LiteralValueContext literalValue() {
			return getRuleContext(LiteralValueContext.class,0);
		}
		public CompositeLitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compositeLit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitCompositeLit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompositeLitContext compositeLit() throws RecognitionException {
		CompositeLitContext _localctx = new CompositeLitContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_compositeLit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(743);
			literalType();
			setState(744);
			literalValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralTypeContext extends ParserRuleContext {
		public StructTypeContext structType() {
			return getRuleContext(StructTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public ElementTypeContext elementType() {
			return getRuleContext(ElementTypeContext.class,0);
		}
		public SliceTypeContext sliceType() {
			return getRuleContext(SliceTypeContext.class,0);
		}
		public MapTypeContext mapType() {
			return getRuleContext(MapTypeContext.class,0);
		}
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public LiteralTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitLiteralType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralTypeContext literalType() throws RecognitionException {
		LiteralTypeContext _localctx = new LiteralTypeContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_literalType);
		try {
			setState(755);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(746);
				structType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(747);
				arrayType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(748);
				match(T__45);
				setState(749);
				match(T__50);
				setState(750);
				match(T__46);
				setState(751);
				elementType();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(752);
				sliceType();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(753);
				mapType();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(754);
				typeName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralValueContext extends ParserRuleContext {
		public ElementListContext elementList() {
			return getRuleContext(ElementListContext.class,0);
		}
		public LiteralValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitLiteralValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralValueContext literalValue() throws RecognitionException {
		LiteralValueContext _localctx = new LiteralValueContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_literalValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(757);
			match(T__12);
			setState(762);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__12) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
				{
				setState(758);
				elementList();
				setState(760);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(759);
					match(T__7);
					}
				}

				}
			}

			setState(764);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementListContext extends ParserRuleContext {
		public List<KeyedElementContext> keyedElement() {
			return getRuleContexts(KeyedElementContext.class);
		}
		public KeyedElementContext keyedElement(int i) {
			return getRuleContext(KeyedElementContext.class,i);
		}
		public ElementListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitElementList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementListContext elementList() throws RecognitionException {
		ElementListContext _localctx = new ElementListContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_elementList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(766);
			keyedElement();
			setState(771);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(767);
					match(T__7);
					setState(768);
					keyedElement();
					}
					} 
				}
				setState(773);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyedElementContext extends ParserRuleContext {
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public KeyedElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyedElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitKeyedElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyedElementContext keyedElement() throws RecognitionException {
		KeyedElementContext _localctx = new KeyedElementContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_keyedElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(777);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				{
				setState(774);
				key();
				setState(775);
				match(T__29);
				}
				break;
			}
			setState(779);
			element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LiteralValueContext literalValue() {
			return getRuleContext(LiteralValueContext.class,0);
		}
		public KeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyContext key() throws RecognitionException {
		KeyContext _localctx = new KeyContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_key);
		try {
			setState(784);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(781);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(782);
				expression(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(783);
				literalValue();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LiteralValueContext literalValue() {
			return getRuleContext(LiteralValueContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_element);
		try {
			setState(788);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__10:
			case T__14:
			case T__17:
			case T__18:
			case T__20:
			case T__21:
			case T__26:
			case T__45:
			case T__47:
			case T__48:
			case T__49:
			case T__51:
			case T__60:
			case IDENTIFIER:
			case INT_LIT:
			case FLOAT_LIT:
			case IMAGINARY_LIT:
			case RUNE_LIT:
			case STRING_LIT:
				enterOuterAlt(_localctx, 1);
				{
				setState(786);
				expression(0);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(787);
				literalValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructTypeContext extends ParserRuleContext {
		public List<FieldDeclContext> fieldDecl() {
			return getRuleContexts(FieldDeclContext.class);
		}
		public FieldDeclContext fieldDecl(int i) {
			return getRuleContext(FieldDeclContext.class,i);
		}
		public List<EosContext> eos() {
			return getRuleContexts(EosContext.class);
		}
		public EosContext eos(int i) {
			return getRuleContext(EosContext.class,i);
		}
		public StructTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitStructType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructTypeContext structType() throws RecognitionException {
		StructTypeContext _localctx = new StructTypeContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_structType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(790);
			match(T__51);
			setState(791);
			match(T__12);
			setState(797);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21 || _la==IDENTIFIER) {
				{
				{
				setState(792);
				fieldDecl();
				setState(793);
				eos();
				}
				}
				setState(799);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(800);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldDeclContext extends ParserRuleContext {
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AnonymousFieldContext anonymousField() {
			return getRuleContext(AnonymousFieldContext.class,0);
		}
		public TerminalNode STRING_LIT() { return getToken(GolangParser.STRING_LIT, 0); }
		public FieldDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitFieldDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclContext fieldDecl() throws RecognitionException {
		FieldDeclContext _localctx = new FieldDeclContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_fieldDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(806);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(802);
				identifierList();
				setState(803);
				type();
				}
				break;
			case 2:
				{
				setState(805);
				anonymousField();
				}
				break;
			}
			setState(809);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				{
				setState(808);
				match(STRING_LIT);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnonymousFieldContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public AnonymousFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anonymousField; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitAnonymousField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnonymousFieldContext anonymousField() throws RecognitionException {
		AnonymousFieldContext _localctx = new AnonymousFieldContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_anonymousField);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(812);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__21) {
				{
				setState(811);
				match(T__21);
				}
			}

			setState(814);
			typeName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionLitContext extends ParserRuleContext {
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public FunctionLitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionLit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitFunctionLit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionLitContext functionLit() throws RecognitionException {
		FunctionLitContext _localctx = new FunctionLitContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_functionLit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(816);
			match(T__10);
			setState(817);
			function();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExprContext extends ParserRuleContext {
		public PrimaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpr; }
	 
		public PrimaryExprContext() { }
		public void copyFrom(PrimaryExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OperandPrimaryExprContext extends PrimaryExprContext {
		public OperandContext operand() {
			return getRuleContext(OperandContext.class,0);
		}
		public OperandPrimaryExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitOperandPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TypeAssertionPrimaryExprContext extends PrimaryExprContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public TypeAssertionContext typeAssertion() {
			return getRuleContext(TypeAssertionContext.class,0);
		}
		public TypeAssertionPrimaryExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeAssertionPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IndexPrimaryExprContext extends PrimaryExprContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public IndexPrimaryExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitIndexPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MethodCallPrimaryExprContext extends PrimaryExprContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public MethodCallPrimaryExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitMethodCallPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SelectorPrimaryExprContext extends PrimaryExprContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public SelectorContext selector() {
			return getRuleContext(SelectorContext.class,0);
		}
		public SelectorPrimaryExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSelectorPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConversionPrimaryExprContext extends PrimaryExprContext {
		public ConversionContext conversion() {
			return getRuleContext(ConversionContext.class,0);
		}
		public ConversionPrimaryExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitConversionPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SlicePrimaryExprContext extends PrimaryExprContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public SliceContext slice() {
			return getRuleContext(SliceContext.class,0);
		}
		public SlicePrimaryExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSlicePrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExprContext primaryExpr() throws RecognitionException {
		return primaryExpr(0);
	}

	private PrimaryExprContext primaryExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PrimaryExprContext _localctx = new PrimaryExprContext(_ctx, _parentState);
		PrimaryExprContext _prevctx = _localctx;
		int _startState = 190;
		enterRecursionRule(_localctx, 190, RULE_primaryExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(822);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				{
				_localctx = new OperandPrimaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(820);
				operand();
				}
				break;
			case 2:
				{
				_localctx = new ConversionPrimaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(821);
				conversion();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(836);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(834);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
					case 1:
						{
						_localctx = new SelectorPrimaryExprContext(new PrimaryExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpr);
						setState(824);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(825);
						selector();
						}
						break;
					case 2:
						{
						_localctx = new IndexPrimaryExprContext(new PrimaryExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpr);
						setState(826);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(827);
						index();
						}
						break;
					case 3:
						{
						_localctx = new SlicePrimaryExprContext(new PrimaryExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpr);
						setState(828);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(829);
						slice();
						}
						break;
					case 4:
						{
						_localctx = new TypeAssertionPrimaryExprContext(new PrimaryExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpr);
						setState(830);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(831);
						typeAssertion();
						}
						break;
					case 5:
						{
						_localctx = new MethodCallPrimaryExprContext(new PrimaryExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpr);
						setState(832);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(833);
						arguments();
						}
						break;
					}
					} 
				}
				setState(838);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SelectorContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public SelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selector; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectorContext selector() throws RecognitionException {
		SelectorContext _localctx = new SelectorContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_selector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(839);
			match(T__4);
			setState(840);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(842);
			match(T__45);
			setState(843);
			expression(0);
			setState(844);
			match(T__46);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SliceContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SliceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_slice; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitSlice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SliceContext slice() throws RecognitionException {
		SliceContext _localctx = new SliceContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_slice);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(846);
			match(T__45);
			setState(862);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				{
				{
				setState(848);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
					{
					setState(847);
					expression(0);
					}
				}

				setState(850);
				match(T__29);
				setState(852);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
					{
					setState(851);
					expression(0);
					}
				}

				}
				}
				break;
			case 2:
				{
				{
				setState(855);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
					{
					setState(854);
					expression(0);
					}
				}

				setState(857);
				match(T__29);
				setState(858);
				expression(0);
				setState(859);
				match(T__29);
				setState(860);
				expression(0);
				}
				}
				break;
			}
			setState(864);
			match(T__46);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeAssertionContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeAssertionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeAssertion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitTypeAssertion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeAssertionContext typeAssertion() throws RecognitionException {
		TypeAssertionContext _localctx = new TypeAssertionContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_typeAssertion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(866);
			match(T__4);
			setState(867);
			match(T__2);
			setState(868);
			type();
			setState(869);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(871);
			match(T__2);
			setState(886);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__51) | (1L << T__60) | (1L << IDENTIFIER))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (INT_LIT - 65)) | (1L << (FLOAT_LIT - 65)) | (1L << (IMAGINARY_LIT - 65)) | (1L << (RUNE_LIT - 65)) | (1L << (STRING_LIT - 65)))) != 0)) {
				{
				setState(878);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
				case 1:
					{
					setState(872);
					expressionList();
					}
					break;
				case 2:
					{
					setState(873);
					type();
					setState(876);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
					case 1:
						{
						setState(874);
						match(T__7);
						setState(875);
						expressionList();
						}
						break;
					}
					}
					break;
				}
				setState(881);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__50) {
					{
					setState(880);
					match(T__50);
					}
				}

				setState(884);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(883);
					match(T__7);
					}
				}

				}
			}

			setState(888);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodExprContext extends ParserRuleContext {
		public ReceiverTypeContext receiverType() {
			return getRuleContext(ReceiverTypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(GolangParser.IDENTIFIER, 0); }
		public MethodExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitMethodExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodExprContext methodExpr() throws RecognitionException {
		MethodExprContext _localctx = new MethodExprContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_methodExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(890);
			receiverType();
			setState(891);
			match(T__4);
			setState(892);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReceiverTypeContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public ReceiverTypeContext receiverType() {
			return getRuleContext(ReceiverTypeContext.class,0);
		}
		public ReceiverTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_receiverType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitReceiverType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReceiverTypeContext receiverType() throws RecognitionException {
		ReceiverTypeContext _localctx = new ReceiverTypeContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_receiverType);
		try {
			setState(904);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(894);
				typeName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(895);
				match(T__2);
				setState(896);
				match(T__21);
				setState(897);
				typeName();
				setState(898);
				match(T__3);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(900);
				match(T__2);
				setState(901);
				receiverType();
				setState(902);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 206;
		enterRecursionRule(_localctx, 206, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(907);
			unaryExpr();
			}
			_ctx.stop = _input.LT(-1);
			setState(914);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,90,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_expression);
					setState(909);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(910);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57) | (1L << T__58) | (1L << T__59))) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(911);
					expression(2);
					}
					} 
				}
				setState(916);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,90,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnaryExprContext extends ParserRuleContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_unaryExpr);
		int _la;
		try {
			setState(920);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,91,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(917);
				primaryExpr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(918);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__26) | (1L << T__60))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(919);
				unaryExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConversionContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConversionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conversion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitConversion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConversionContext conversion() throws RecognitionException {
		ConversionContext _localctx = new ConversionContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_conversion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(922);
			type();
			setState(923);
			match(T__2);
			setState(924);
			expression(0);
			setState(926);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(925);
				match(T__7);
				}
			}

			setState(928);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EosContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(GolangParser.EOF, 0); }
		public EosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eos; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GolangVisitor ) return ((GolangVisitor<? extends T>)visitor).visitEos(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EosContext eos() throws RecognitionException {
		EosContext _localctx = new EosContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_eos);
		try {
			setState(934);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,93,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(930);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(931);
				match(EOF);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(932);
				if (!(lineTerminatorAhead())) throw new FailedPredicateException(this, "lineTerminatorAhead()");
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(933);
				if (!(_input.LT(1).getText().equals("}") )) throw new FailedPredicateException(this, "_input.LT(1).getText().equals(\"}\") ");
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 95:
			return primaryExpr_sempred((PrimaryExprContext)_localctx, predIndex);
		case 103:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 106:
			return eos_sempred((EosContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean primaryExpr_sempred(PrimaryExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 2);
		case 4:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean eos_sempred(EosContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return lineTerminatorAhead();
		case 7:
			return _input.LT(1).getText().equals("}") ;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3M\u03ab\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\3\2\3\2\3\2\3\2\3\2\7\2\u00de\n\2\f\2\16\2\u00e1\13\2\3\2\3\2"+
		"\3\2\7\2\u00e6\n\2\f\2\16\2\u00e9\13\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\7\4\u00f4\n\4\f\4\16\4\u00f7\13\4\3\4\5\4\u00fa\n\4\3\5\5\5\u00fd"+
		"\n\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\5\7\u0106\n\7\3\b\3\b\3\b\5\b\u010b\n"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0113\n\t\f\t\16\t\u0116\13\t\3\t\5\t\u0119"+
		"\n\t\3\n\3\n\5\n\u011d\n\n\3\n\3\n\5\n\u0121\n\n\3\13\3\13\3\13\7\13\u0126"+
		"\n\13\f\13\16\13\u0129\13\13\3\f\3\f\3\f\7\f\u012e\n\f\f\f\16\f\u0131"+
		"\13\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0139\n\r\f\r\16\r\u013c\13\r\3\r\5"+
		"\r\u013f\n\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\5\17\u0148\n\17\3\20\3"+
		"\20\3\20\3\21\3\21\3\21\3\21\3\21\5\21\u0152\n\21\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\7\23\u015c\n\23\f\23\16\23\u015f\13\23\3\23\5\23"+
		"\u0162\n\23\3\24\3\24\3\24\3\24\5\24\u0168\n\24\3\24\3\24\5\24\u016c\n"+
		"\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\7\26\u0175\n\26\f\26\16\26\u0178"+
		"\13\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\5\27\u0189\n\27\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0191\n"+
		"\30\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3"+
		"\35\3\35\3\36\3\36\3\37\5\37\u01a5\n\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3"+
		"\"\3\"\3#\3#\3$\3$\3$\3$\3%\3%\5%\u01b9\n%\3&\3&\5&\u01bd\n&\3\'\3\'\5"+
		"\'\u01c1\n\'\3(\3(\3(\3)\3)\3*\3*\3*\3+\3+\5+\u01cd\n+\3,\3,\3,\3,\5,"+
		"\u01d3\n,\3,\3,\3,\3-\3-\3-\5-\u01db\n-\3.\3.\5.\u01df\n.\3/\3/\3/\3/"+
		"\5/\u01e5\n/\3/\5/\u01e8\n/\3/\3/\7/\u01ec\n/\f/\16/\u01ef\13/\3/\3/\3"+
		"\60\3\60\3\60\3\60\3\61\3\61\3\61\5\61\u01fa\n\61\3\62\3\62\3\62\3\62"+
		"\5\62\u0200\n\62\3\62\3\62\3\62\7\62\u0205\n\62\f\62\16\62\u0208\13\62"+
		"\3\62\3\62\3\63\3\63\5\63\u020e\n\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64"+
		"\3\64\3\64\3\64\3\65\3\65\3\65\5\65\u021d\n\65\3\66\3\66\3\66\7\66\u0222"+
		"\n\66\f\66\16\66\u0225\13\66\3\67\3\67\3\67\7\67\u022a\n\67\f\67\16\67"+
		"\u022d\13\67\3\67\3\67\38\38\38\38\39\39\39\59\u0238\n9\39\59\u023b\n"+
		"9\3:\3:\3:\3:\3:\3:\5:\u0243\n:\3:\3:\3;\3;\3;\3;\5;\u024b\n;\3;\3;\3"+
		"<\5<\u0250\n<\3<\3<\5<\u0254\n<\3<\3<\5<\u0258\n<\3=\3=\3=\3=\3=\3=\5"+
		"=\u0260\n=\3=\3=\3=\3>\3>\3>\3?\3?\3?\3?\3?\3?\5?\u026e\n?\3@\3@\5@\u0272"+
		"\n@\3A\3A\3A\3A\3A\3A\3A\3A\5A\u027c\nA\3B\3B\3B\3B\3B\3C\3C\3D\3D\3E"+
		"\3E\3E\3F\3F\3F\3F\3F\7F\u028f\nF\fF\16F\u0292\13F\3F\3F\3G\3G\3G\3G\3"+
		"H\3H\3H\3H\3H\3H\3I\3I\3I\3I\3I\5I\u02a5\nI\3I\3I\3J\3J\3J\5J\u02ac\n"+
		"J\3K\3K\3K\3L\3L\5L\u02b3\nL\3M\3M\5M\u02b7\nM\3N\3N\3N\5N\u02bc\nN\5"+
		"N\u02be\nN\3N\3N\3O\3O\3O\7O\u02c5\nO\fO\16O\u02c8\13O\3P\5P\u02cb\nP"+
		"\3P\5P\u02ce\nP\3P\3P\3Q\3Q\3Q\3Q\3Q\3Q\3Q\5Q\u02d9\nQ\3R\3R\3R\5R\u02de"+
		"\nR\3S\3S\3T\3T\5T\u02e4\nT\3U\3U\3U\3U\3V\3V\3V\3W\3W\3W\3W\3W\3W\3W"+
		"\3W\3W\5W\u02f6\nW\3X\3X\3X\5X\u02fb\nX\5X\u02fd\nX\3X\3X\3Y\3Y\3Y\7Y"+
		"\u0304\nY\fY\16Y\u0307\13Y\3Z\3Z\3Z\5Z\u030c\nZ\3Z\3Z\3[\3[\3[\5[\u0313"+
		"\n[\3\\\3\\\5\\\u0317\n\\\3]\3]\3]\3]\3]\7]\u031e\n]\f]\16]\u0321\13]"+
		"\3]\3]\3^\3^\3^\3^\5^\u0329\n^\3^\5^\u032c\n^\3_\5_\u032f\n_\3_\3_\3`"+
		"\3`\3`\3a\3a\3a\5a\u0339\na\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\7a\u0345\na"+
		"\fa\16a\u0348\13a\3b\3b\3b\3c\3c\3c\3c\3d\3d\5d\u0353\nd\3d\3d\5d\u0357"+
		"\nd\3d\5d\u035a\nd\3d\3d\3d\3d\3d\5d\u0361\nd\3d\3d\3e\3e\3e\3e\3e\3f"+
		"\3f\3f\3f\3f\5f\u036f\nf\5f\u0371\nf\3f\5f\u0374\nf\3f\5f\u0377\nf\5f"+
		"\u0379\nf\3f\3f\3g\3g\3g\3g\3h\3h\3h\3h\3h\3h\3h\3h\3h\3h\5h\u038b\nh"+
		"\3i\3i\3i\3i\3i\3i\7i\u0393\ni\fi\16i\u0396\13i\3j\3j\3j\5j\u039b\nj\3"+
		"k\3k\3k\3k\5k\u03a1\nk\3k\3k\3l\3l\3l\3l\5l\u03a9\nl\3l\2\4\u00c0\u00d0"+
		"m\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDF"+
		"HJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c"+
		"\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4"+
		"\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc"+
		"\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4"+
		"\u00d6\2\b\4\2\7\7@@\3\2\22\23\3\2\24\36\4\2CFII\4\2\24\36\67>\7\2\21"+
		"\21\24\25\27\30\35\35??\2\u03ca\2\u00d8\3\2\2\2\4\u00ea\3\2\2\2\6\u00ed"+
		"\3\2\2\2\b\u00fc\3\2\2\2\n\u0100\3\2\2\2\f\u0105\3\2\2\2\16\u010a\3\2"+
		"\2\2\20\u010c\3\2\2\2\22\u011a\3\2\2\2\24\u0122\3\2\2\2\26\u012a\3\2\2"+
		"\2\30\u0132\3\2\2\2\32\u0140\3\2\2\2\34\u0143\3\2\2\2\36\u0149\3\2\2\2"+
		" \u014c\3\2\2\2\"\u0153\3\2\2\2$\u0155\3\2\2\2&\u0163\3\2\2\2(\u016d\3"+
		"\2\2\2*\u0176\3\2\2\2,\u0188\3\2\2\2.\u0190\3\2\2\2\60\u0192\3\2\2\2\62"+
		"\u0194\3\2\2\2\64\u0198\3\2\2\2\66\u019b\3\2\2\28\u019f\3\2\2\2:\u01a1"+
		"\3\2\2\2<\u01a4\3\2\2\2>\u01a8\3\2\2\2@\u01ac\3\2\2\2B\u01ae\3\2\2\2D"+
		"\u01b0\3\2\2\2F\u01b2\3\2\2\2H\u01b6\3\2\2\2J\u01ba\3\2\2\2L\u01be\3\2"+
		"\2\2N\u01c2\3\2\2\2P\u01c5\3\2\2\2R\u01c7\3\2\2\2T\u01ca\3\2\2\2V\u01ce"+
		"\3\2\2\2X\u01d7\3\2\2\2Z\u01de\3\2\2\2\\\u01e0\3\2\2\2^\u01f2\3\2\2\2"+
		"`\u01f9\3\2\2\2b\u01fb\3\2\2\2d\u020d\3\2\2\2f\u0215\3\2\2\2h\u021c\3"+
		"\2\2\2j\u021e\3\2\2\2l\u0226\3\2\2\2n\u0230\3\2\2\2p\u023a\3\2\2\2r\u0242"+
		"\3\2\2\2t\u0246\3\2\2\2v\u024f\3\2\2\2x\u025f\3\2\2\2z\u0264\3\2\2\2|"+
		"\u026d\3\2\2\2~\u0271\3\2\2\2\u0080\u027b\3\2\2\2\u0082\u027d\3\2\2\2"+
		"\u0084\u0282\3\2\2\2\u0086\u0284\3\2\2\2\u0088\u0286\3\2\2\2\u008a\u0289"+
		"\3\2\2\2\u008c\u0295\3\2\2\2\u008e\u0299\3\2\2\2\u0090\u02a4\3\2\2\2\u0092"+
		"\u02ab\3\2\2\2\u0094\u02ad\3\2\2\2\u0096\u02b0\3\2\2\2\u0098\u02b6\3\2"+
		"\2\2\u009a\u02b8\3\2\2\2\u009c\u02c1\3\2\2\2\u009e\u02ca\3\2\2\2\u00a0"+
		"\u02d8\3\2\2\2\u00a2\u02dd\3\2\2\2\u00a4\u02df\3\2\2\2\u00a6\u02e3\3\2"+
		"\2\2\u00a8\u02e5\3\2\2\2\u00aa\u02e9\3\2\2\2\u00ac\u02f5\3\2\2\2\u00ae"+
		"\u02f7\3\2\2\2\u00b0\u0300\3\2\2\2\u00b2\u030b\3\2\2\2\u00b4\u0312\3\2"+
		"\2\2\u00b6\u0316\3\2\2\2\u00b8\u0318\3\2\2\2\u00ba\u0328\3\2\2\2\u00bc"+
		"\u032e\3\2\2\2\u00be\u0332\3\2\2\2\u00c0\u0338\3\2\2\2\u00c2\u0349\3\2"+
		"\2\2\u00c4\u034c\3\2\2\2\u00c6\u0350\3\2\2\2\u00c8\u0364\3\2\2\2\u00ca"+
		"\u0369\3\2\2\2\u00cc\u037c\3\2\2\2\u00ce\u038a\3\2\2\2\u00d0\u038c\3\2"+
		"\2\2\u00d2\u039a\3\2\2\2\u00d4\u039c\3\2\2\2\u00d6\u03a8\3\2\2\2\u00d8"+
		"\u00d9\5\4\3\2\u00d9\u00df\5\u00d6l\2\u00da\u00db\5\6\4\2\u00db\u00dc"+
		"\5\u00d6l\2\u00dc\u00de\3\2\2\2\u00dd\u00da\3\2\2\2\u00de\u00e1\3\2\2"+
		"\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e7\3\2\2\2\u00e1\u00df"+
		"\3\2\2\2\u00e2\u00e3\5\f\7\2\u00e3\u00e4\5\u00d6l\2\u00e4\u00e6\3\2\2"+
		"\2\u00e5\u00e2\3\2\2\2\u00e6\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8"+
		"\3\2\2\2\u00e8\3\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00eb\7\3\2\2\u00eb"+
		"\u00ec\7@\2\2\u00ec\5\3\2\2\2\u00ed\u00f9\7\4\2\2\u00ee\u00fa\5\b\5\2"+
		"\u00ef\u00f5\7\5\2\2\u00f0\u00f1\5\b\5\2\u00f1\u00f2\5\u00d6l\2\u00f2"+
		"\u00f4\3\2\2\2\u00f3\u00f0\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3\3\2"+
		"\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f8\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8"+
		"\u00fa\7\6\2\2\u00f9\u00ee\3\2\2\2\u00f9\u00ef\3\2\2\2\u00fa\7\3\2\2\2"+
		"\u00fb\u00fd\t\2\2\2\u00fc\u00fb\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe"+
		"\3\2\2\2\u00fe\u00ff\5\n\6\2\u00ff\t\3\2\2\2\u0100\u0101\7I\2\2\u0101"+
		"\13\3\2\2\2\u0102\u0106\5\16\b\2\u0103\u0106\5\34\17\2\u0104\u0106\5 "+
		"\21\2\u0105\u0102\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0104\3\2\2\2\u0106"+
		"\r\3\2\2\2\u0107\u010b\5\20\t\2\u0108\u010b\5\30\r\2\u0109\u010b\5$\23"+
		"\2\u010a\u0107\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u0109\3\2\2\2\u010b\17"+
		"\3\2\2\2\u010c\u0118\7\b\2\2\u010d\u0119\5\22\n\2\u010e\u0114\7\5\2\2"+
		"\u010f\u0110\5\22\n\2\u0110\u0111\5\u00d6l\2\u0111\u0113\3\2\2\2\u0112"+
		"\u010f\3\2\2\2\u0113\u0116\3\2\2\2\u0114\u0112\3\2\2\2\u0114\u0115\3\2"+
		"\2\2\u0115\u0117\3\2\2\2\u0116\u0114\3\2\2\2\u0117\u0119\7\6\2\2\u0118"+
		"\u010d\3\2\2\2\u0118\u010e\3\2\2\2\u0119\21\3\2\2\2\u011a\u0120\5\24\13"+
		"\2\u011b\u011d\5|?\2\u011c\u011b\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011e"+
		"\3\2\2\2\u011e\u011f\7\t\2\2\u011f\u0121\5\26\f\2\u0120\u011c\3\2\2\2"+
		"\u0120\u0121\3\2\2\2\u0121\23\3\2\2\2\u0122\u0127\7@\2\2\u0123\u0124\7"+
		"\n\2\2\u0124\u0126\7@\2\2\u0125\u0123\3\2\2\2\u0126\u0129\3\2\2\2\u0127"+
		"\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128\25\3\2\2\2\u0129\u0127\3\2\2"+
		"\2\u012a\u012f\5\u00d0i\2\u012b\u012c\7\n\2\2\u012c\u012e\5\u00d0i\2\u012d"+
		"\u012b\3\2\2\2\u012e\u0131\3\2\2\2\u012f\u012d\3\2\2\2\u012f\u0130\3\2"+
		"\2\2\u0130\27\3\2\2\2\u0131\u012f\3\2\2\2\u0132\u013e\7\13\2\2\u0133\u013f"+
		"\5\32\16\2\u0134\u013a\7\5\2\2\u0135\u0136\5\32\16\2\u0136\u0137\7\f\2"+
		"\2\u0137\u0139\3\2\2\2\u0138\u0135\3\2\2\2\u0139\u013c\3\2\2\2\u013a\u0138"+
		"\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013d\3\2\2\2\u013c\u013a\3\2\2\2\u013d"+
		"\u013f\7\6\2\2\u013e\u0133\3\2\2\2\u013e\u0134\3\2\2\2\u013f\31\3\2\2"+
		"\2\u0140\u0141\7@\2\2\u0141\u0142\5|?\2\u0142\33\3\2\2\2\u0143\u0144\7"+
		"\r\2\2\u0144\u0147\7@\2\2\u0145\u0148\5\36\20\2\u0146\u0148\5\u0096L\2"+
		"\u0147\u0145\3\2\2\2\u0147\u0146\3\2\2\2\u0148\35\3\2\2\2\u0149\u014a"+
		"\5\u0096L\2\u014a\u014b\5(\25\2\u014b\37\3\2\2\2\u014c\u014d\7\r\2\2\u014d"+
		"\u014e\5\"\22\2\u014e\u0151\7@\2\2\u014f\u0152\5\36\20\2\u0150\u0152\5"+
		"\u0096L\2\u0151\u014f\3\2\2\2\u0151\u0150\3\2\2\2\u0152!\3\2\2\2\u0153"+
		"\u0154\5\u009aN\2\u0154#\3\2\2\2\u0155\u0161\7\16\2\2\u0156\u0162\5&\24"+
		"\2\u0157\u015d\7\5\2\2\u0158\u0159\5&\24\2\u0159\u015a\5\u00d6l\2\u015a"+
		"\u015c\3\2\2\2\u015b\u0158\3\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b\3\2"+
		"\2\2\u015d\u015e\3\2\2\2\u015e\u0160\3\2\2\2\u015f\u015d\3\2\2\2\u0160"+
		"\u0162\7\6\2\2\u0161\u0156\3\2\2\2\u0161\u0157\3\2\2\2\u0162%\3\2\2\2"+
		"\u0163\u016b\5\24\13\2\u0164\u0167\5|?\2\u0165\u0166\7\t\2\2\u0166\u0168"+
		"\5\26\f\2\u0167\u0165\3\2\2\2\u0167\u0168\3\2\2\2\u0168\u016c\3\2\2\2"+
		"\u0169\u016a\7\t\2\2\u016a\u016c\5\26\f\2\u016b\u0164\3\2\2\2\u016b\u0169"+
		"\3\2\2\2\u016c\'\3\2\2\2\u016d\u016e\7\17\2\2\u016e\u016f\5*\26\2\u016f"+
		"\u0170\7\20\2\2\u0170)\3\2\2\2\u0171\u0172\5,\27\2\u0172\u0173\5\u00d6"+
		"l\2\u0173\u0175\3\2\2\2\u0174\u0171\3\2\2\2\u0175\u0178\3\2\2\2\u0176"+
		"\u0174\3\2\2\2\u0176\u0177\3\2\2\2\u0177+\3\2\2\2\u0178\u0176\3\2\2\2"+
		"\u0179\u0189\5\16\b\2\u017a\u0189\5F$\2\u017b\u0189\5.\30\2\u017c\u0189"+
		"\5z>\2\u017d\u0189\5H%\2\u017e\u0189\5J&\2\u017f\u0189\5L\'\2\u0180\u0189"+
		"\5N(\2\u0181\u0189\5P)\2\u0182\u0189\5(\25\2\u0183\u0189\5T+\2\u0184\u0189"+
		"\5Z.\2\u0185\u0189\5l\67\2\u0186\u0189\5t;\2\u0187\u0189\5R*\2\u0188\u0179"+
		"\3\2\2\2\u0188\u017a\3\2\2\2\u0188\u017b\3\2\2\2\u0188\u017c\3\2\2\2\u0188"+
		"\u017d\3\2\2\2\u0188\u017e\3\2\2\2\u0188\u017f\3\2\2\2\u0188\u0180\3\2"+
		"\2\2\u0188\u0181\3\2\2\2\u0188\u0182\3\2\2\2\u0188\u0183\3\2\2\2\u0188"+
		"\u0184\3\2\2\2\u0188\u0185\3\2\2\2\u0188\u0186\3\2\2\2\u0188\u0187\3\2"+
		"\2\2\u0189-\3\2\2\2\u018a\u0191\5\62\32\2\u018b\u0191\5\60\31\2\u018c"+
		"\u0191\5\64\33\2\u018d\u0191\5\66\34\2\u018e\u0191\5> \2\u018f\u0191\5"+
		"D#\2\u0190\u018a\3\2\2\2\u0190\u018b\3\2\2\2\u0190\u018c\3\2\2\2\u0190"+
		"\u018d\3\2\2\2\u0190\u018e\3\2\2\2\u0190\u018f\3\2\2\2\u0191/\3\2\2\2"+
		"\u0192\u0193\5\u00d0i\2\u0193\61\3\2\2\2\u0194\u0195\5\u00d0i\2\u0195"+
		"\u0196\7\21\2\2\u0196\u0197\5\u00d0i\2\u0197\63\3\2\2\2\u0198\u0199\5"+
		"\u00d0i\2\u0199\u019a\t\3\2\2\u019a\65\3\2\2\2\u019b\u019c\58\35\2\u019c"+
		"\u019d\5<\37\2\u019d\u019e\5:\36\2\u019e\67\3\2\2\2\u019f\u01a0\5\26\f"+
		"\2\u01a09\3\2\2\2\u01a1\u01a2\5\26\f\2\u01a2;\3\2\2\2\u01a3\u01a5\t\4"+
		"\2\2\u01a4\u01a3\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6"+
		"\u01a7\7\t\2\2\u01a7=\3\2\2\2\u01a8\u01a9\5@!\2\u01a9\u01aa\7\37\2\2\u01aa"+
		"\u01ab\5B\"\2\u01ab?\3\2\2\2\u01ac\u01ad\5\24\13\2\u01adA\3\2\2\2\u01ae"+
		"\u01af\5\26\f\2\u01afC\3\2\2\2\u01b0\u01b1\7\f\2\2\u01b1E\3\2\2\2\u01b2"+
		"\u01b3\7@\2\2\u01b3\u01b4\7 \2\2\u01b4\u01b5\5,\27\2\u01b5G\3\2\2\2\u01b6"+
		"\u01b8\7!\2\2\u01b7\u01b9\5\26\f\2\u01b8\u01b7\3\2\2\2\u01b8\u01b9\3\2"+
		"\2\2\u01b9I\3\2\2\2\u01ba\u01bc\7\"\2\2\u01bb\u01bd\7@\2\2\u01bc\u01bb"+
		"\3\2\2\2\u01bc\u01bd\3\2\2\2\u01bdK\3\2\2\2\u01be\u01c0\7#\2\2\u01bf\u01c1"+
		"\7@\2\2\u01c0\u01bf\3\2\2\2\u01c0\u01c1\3\2\2\2\u01c1M\3\2\2\2\u01c2\u01c3"+
		"\7$\2\2\u01c3\u01c4\7@\2\2\u01c4O\3\2\2\2\u01c5\u01c6\7%\2\2\u01c6Q\3"+
		"\2\2\2\u01c7\u01c8\7&\2\2\u01c8\u01c9\5\u00d0i\2\u01c9S\3\2\2\2\u01ca"+
		"\u01cc\5V,\2\u01cb\u01cd\5X-\2\u01cc\u01cb\3\2\2\2\u01cc\u01cd\3\2\2\2"+
		"\u01cdU\3\2\2\2\u01ce\u01d2\7\'\2\2\u01cf\u01d0\5.\30\2\u01d0\u01d1\7"+
		"\f\2\2\u01d1\u01d3\3\2\2\2\u01d2\u01cf\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3"+
		"\u01d4\3\2\2\2\u01d4\u01d5\5\u00d0i\2\u01d5\u01d6\5(\25\2\u01d6W\3\2\2"+
		"\2\u01d7\u01da\7(\2\2\u01d8\u01db\5T+\2\u01d9\u01db\5(\25\2\u01da\u01d8"+
		"\3\2\2\2\u01da\u01d9\3\2\2\2\u01dbY\3\2\2\2\u01dc\u01df\5\\/\2\u01dd\u01df"+
		"\5b\62\2\u01de\u01dc\3\2\2\2\u01de\u01dd\3\2\2\2\u01df[\3\2\2\2\u01e0"+
		"\u01e4\7)\2\2\u01e1\u01e2\5.\30\2\u01e2\u01e3\7\f\2\2\u01e3\u01e5\3\2"+
		"\2\2\u01e4\u01e1\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5\u01e7\3\2\2\2\u01e6"+
		"\u01e8\5\u00d0i\2\u01e7\u01e6\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01e9"+
		"\3\2\2\2\u01e9\u01ed\7\17\2\2\u01ea\u01ec\5^\60\2\u01eb\u01ea\3\2\2\2"+
		"\u01ec\u01ef\3\2\2\2\u01ed\u01eb\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee\u01f0"+
		"\3\2\2\2\u01ef\u01ed\3\2\2\2\u01f0\u01f1\7\20\2\2\u01f1]\3\2\2\2\u01f2"+
		"\u01f3\5`\61\2\u01f3\u01f4\7 \2\2\u01f4\u01f5\5*\26\2\u01f5_\3\2\2\2\u01f6"+
		"\u01f7\7*\2\2\u01f7\u01fa\5\26\f\2\u01f8\u01fa\7+\2\2\u01f9\u01f6\3\2"+
		"\2\2\u01f9\u01f8\3\2\2\2\u01faa\3\2\2\2\u01fb\u01ff\7)\2\2\u01fc\u01fd"+
		"\5.\30\2\u01fd\u01fe\7\f\2\2\u01fe\u0200\3\2\2\2\u01ff\u01fc\3\2\2\2\u01ff"+
		"\u0200\3\2\2\2\u0200\u0201\3\2\2\2\u0201\u0202\5d\63\2\u0202\u0206\7\17"+
		"\2\2\u0203\u0205\5f\64\2\u0204\u0203\3\2\2\2\u0205\u0208\3\2\2\2\u0206"+
		"\u0204\3\2\2\2\u0206\u0207\3\2\2\2\u0207\u0209\3\2\2\2\u0208\u0206\3\2"+
		"\2\2\u0209\u020a\7\20\2\2\u020ac\3\2\2\2\u020b\u020c\7@\2\2\u020c\u020e"+
		"\7\37\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020e\u020f\3\2\2\2"+
		"\u020f\u0210\5\u00c0a\2\u0210\u0211\7\7\2\2\u0211\u0212\7\5\2\2\u0212"+
		"\u0213\7\13\2\2\u0213\u0214\7\6\2\2\u0214e\3\2\2\2\u0215\u0216\5h\65\2"+
		"\u0216\u0217\7 \2\2\u0217\u0218\5*\26\2\u0218g\3\2\2\2\u0219\u021a\7*"+
		"\2\2\u021a\u021d\5j\66\2\u021b\u021d\7+\2\2\u021c\u0219\3\2\2\2\u021c"+
		"\u021b\3\2\2\2\u021di\3\2\2\2\u021e\u0223\5|?\2\u021f\u0220\7\n\2\2\u0220"+
		"\u0222\5|?\2\u0221\u021f\3\2\2\2\u0222\u0225\3\2\2\2\u0223\u0221\3\2\2"+
		"\2\u0223\u0224\3\2\2\2\u0224k\3\2\2\2\u0225\u0223\3\2\2\2\u0226\u0227"+
		"\7,\2\2\u0227\u022b\7\17\2\2\u0228\u022a\5n8\2\u0229\u0228\3\2\2\2\u022a"+
		"\u022d\3\2\2\2\u022b\u0229\3\2\2\2\u022b\u022c\3\2\2\2\u022c\u022e\3\2"+
		"\2\2\u022d\u022b\3\2\2\2\u022e\u022f\7\20\2\2\u022fm\3\2\2\2\u0230\u0231"+
		"\5p9\2\u0231\u0232\7 \2\2\u0232\u0233\5*\26\2\u0233o\3\2\2\2\u0234\u0237"+
		"\7*\2\2\u0235\u0238\5\62\32\2\u0236\u0238\5r:\2\u0237\u0235\3\2\2\2\u0237"+
		"\u0236\3\2\2\2\u0238\u023b\3\2\2\2\u0239\u023b\7+\2\2\u023a\u0234\3\2"+
		"\2\2\u023a\u0239\3\2\2\2\u023bq\3\2\2\2\u023c\u023d\5\26\f\2\u023d\u023e"+
		"\7\t\2\2\u023e\u0243\3\2\2\2\u023f\u0240\5\24\13\2\u0240\u0241\7\37\2"+
		"\2\u0241\u0243\3\2\2\2\u0242\u023c\3\2\2\2\u0242\u023f\3\2\2\2\u0242\u0243"+
		"\3\2\2\2\u0243\u0244\3\2\2\2\u0244\u0245\5\u00d0i\2\u0245s\3\2\2\2\u0246"+
		"\u024a\7-\2\2\u0247\u024b\5\u00d0i\2\u0248\u024b\5v<\2\u0249\u024b\5x"+
		"=\2\u024a\u0247\3\2\2\2\u024a\u0248\3\2\2\2\u024a\u0249\3\2\2\2\u024a"+
		"\u024b\3\2\2\2\u024b\u024c\3\2\2\2\u024c\u024d\5(\25\2\u024du\3\2\2\2"+
		"\u024e\u0250\5.\30\2\u024f\u024e\3\2\2\2\u024f\u0250\3\2\2\2\u0250\u0251"+
		"\3\2\2\2\u0251\u0253\7\f\2\2\u0252\u0254\5\u00d0i\2\u0253\u0252\3\2\2"+
		"\2\u0253\u0254\3\2\2\2\u0254\u0255\3\2\2\2\u0255\u0257\7\f\2\2\u0256\u0258"+
		"\5.\30\2\u0257\u0256\3\2\2\2\u0257\u0258\3\2\2\2\u0258w\3\2\2\2\u0259"+
		"\u025a\5\26\f\2\u025a\u025b\7\t\2\2\u025b\u0260\3\2\2\2\u025c\u025d\5"+
		"\24\13\2\u025d\u025e\7\37\2\2\u025e\u0260\3\2\2\2\u025f\u0259\3\2\2\2"+
		"\u025f\u025c\3\2\2\2\u025f\u0260\3\2\2\2\u0260\u0261\3\2\2\2\u0261\u0262"+
		"\7.\2\2\u0262\u0263\5\u00d0i\2\u0263y\3\2\2\2\u0264\u0265\7/\2\2\u0265"+
		"\u0266\5\u00d0i\2\u0266{\3\2\2\2\u0267\u026e\5~@\2\u0268\u026e\5\u0080"+
		"A\2\u0269\u026a\7\5\2\2\u026a\u026b\5|?\2\u026b\u026c\7\6\2\2\u026c\u026e"+
		"\3\2\2\2\u026d\u0267\3\2\2\2\u026d\u0268\3\2\2\2\u026d\u0269\3\2\2\2\u026e"+
		"}\3\2\2\2\u026f\u0272\7@\2\2\u0270\u0272\5\u00a8U\2\u0271\u026f\3\2\2"+
		"\2\u0271\u0270\3\2\2\2\u0272\177\3\2\2\2\u0273\u027c\5\u0082B\2\u0274"+
		"\u027c\5\u00b8]\2\u0275\u027c\5\u0088E\2\u0276\u027c\5\u0094K\2\u0277"+
		"\u027c\5\u008aF\2\u0278\u027c\5\u008cG\2\u0279\u027c\5\u008eH\2\u027a"+
		"\u027c\5\u0090I\2\u027b\u0273\3\2\2\2\u027b\u0274\3\2\2\2\u027b\u0275"+
		"\3\2\2\2\u027b\u0276\3\2\2\2\u027b\u0277\3\2\2\2\u027b\u0278\3\2\2\2\u027b"+
		"\u0279\3\2\2\2\u027b\u027a\3\2\2\2\u027c\u0081\3\2\2\2\u027d\u027e\7\60"+
		"\2\2\u027e\u027f\5\u0084C\2\u027f\u0280\7\61\2\2\u0280\u0281\5\u0086D"+
		"\2\u0281\u0083\3\2\2\2\u0282\u0283\5\u00d0i\2\u0283\u0085\3\2\2\2\u0284"+
		"\u0285\5|?\2\u0285\u0087\3\2\2\2\u0286\u0287\7\30\2\2\u0287\u0288\5|?"+
		"\2\u0288\u0089\3\2\2\2\u0289\u028a\7\62\2\2\u028a\u0290\7\17\2\2\u028b"+
		"\u028c\5\u0092J\2\u028c\u028d\5\u00d6l\2\u028d\u028f\3\2\2\2\u028e\u028b"+
		"\3\2\2\2\u028f\u0292\3\2\2\2\u0290\u028e\3\2\2\2\u0290\u0291\3\2\2\2\u0291"+
		"\u0293\3\2\2\2\u0292\u0290\3\2\2\2\u0293\u0294\7\20\2\2\u0294\u008b\3"+
		"\2\2\2\u0295\u0296\7\60\2\2\u0296\u0297\7\61\2\2\u0297\u0298\5\u0086D"+
		"\2\u0298\u008d\3\2\2\2\u0299\u029a\7\63\2\2\u029a\u029b\7\60\2\2\u029b"+
		"\u029c\5|?\2\u029c\u029d\7\61\2\2\u029d\u029e\5\u0086D\2\u029e\u008f\3"+
		"\2\2\2\u029f\u02a5\7\64\2\2\u02a0\u02a1\7\64\2\2\u02a1\u02a5\7\21\2\2"+
		"\u02a2\u02a3\7\21\2\2\u02a3\u02a5\7\64\2\2\u02a4\u029f\3\2\2\2\u02a4\u02a0"+
		"\3\2\2\2\u02a4\u02a2\3\2\2\2\u02a5\u02a6\3\2\2\2\u02a6\u02a7\5\u0086D"+
		"\2\u02a7\u0091\3\2\2\2\u02a8\u02a9\7@\2\2\u02a9\u02ac\5\u0096L\2\u02aa"+
		"\u02ac\5~@\2\u02ab\u02a8\3\2\2\2\u02ab\u02aa\3\2\2\2\u02ac\u0093\3\2\2"+
		"\2\u02ad\u02ae\7\r\2\2\u02ae\u02af\5\u0096L\2\u02af\u0095\3\2\2\2\u02b0"+
		"\u02b2\5\u009aN\2\u02b1\u02b3\5\u0098M\2\u02b2\u02b1\3\2\2\2\u02b2\u02b3"+
		"\3\2\2\2\u02b3\u0097\3\2\2\2\u02b4\u02b7\5\u009aN\2\u02b5\u02b7\5|?\2"+
		"\u02b6\u02b4\3\2\2\2\u02b6\u02b5\3\2\2\2\u02b7\u0099\3\2\2\2\u02b8\u02bd"+
		"\7\5\2\2\u02b9\u02bb\5\u009cO\2\u02ba\u02bc\7\n\2\2\u02bb\u02ba\3\2\2"+
		"\2\u02bb\u02bc\3\2\2\2\u02bc\u02be\3\2\2\2\u02bd\u02b9\3\2\2\2\u02bd\u02be"+
		"\3\2\2\2\u02be\u02bf\3\2\2\2\u02bf\u02c0\7\6\2\2\u02c0\u009b\3\2\2\2\u02c1"+
		"\u02c6\5\u009eP\2\u02c2\u02c3\7\n\2\2\u02c3\u02c5\5\u009eP\2\u02c4\u02c2"+
		"\3\2\2\2\u02c5\u02c8\3\2\2\2\u02c6\u02c4\3\2\2\2\u02c6\u02c7\3\2\2\2\u02c7"+
		"\u009d\3\2\2\2\u02c8\u02c6\3\2\2\2\u02c9\u02cb\5\24\13\2\u02ca\u02c9\3"+
		"\2\2\2\u02ca\u02cb\3\2\2\2\u02cb\u02cd\3\2\2\2\u02cc\u02ce\7\65\2\2\u02cd"+
		"\u02cc\3\2\2\2\u02cd\u02ce\3\2\2\2\u02ce\u02cf\3\2\2\2\u02cf\u02d0\5|"+
		"?\2\u02d0\u009f\3\2\2\2\u02d1\u02d9\5\u00a2R\2\u02d2\u02d9\5\u00a6T\2"+
		"\u02d3\u02d9\5\u00ccg\2\u02d4\u02d5\7\5\2\2\u02d5\u02d6\5\u00d0i\2\u02d6"+
		"\u02d7\7\6\2\2\u02d7\u02d9\3\2\2\2\u02d8\u02d1\3\2\2\2\u02d8\u02d2\3\2"+
		"\2\2\u02d8\u02d3\3\2\2\2\u02d8\u02d4\3\2\2\2\u02d9\u00a1\3\2\2\2\u02da"+
		"\u02de\5\u00a4S\2\u02db\u02de\5\u00aaV\2\u02dc\u02de\5\u00be`\2\u02dd"+
		"\u02da\3\2\2\2\u02dd\u02db\3\2\2\2\u02dd\u02dc\3\2\2\2\u02de\u00a3\3\2"+
		"\2\2\u02df\u02e0\t\5\2\2\u02e0\u00a5\3\2\2\2\u02e1\u02e4\7@\2\2\u02e2"+
		"\u02e4\5\u00a8U\2\u02e3\u02e1\3\2\2\2\u02e3\u02e2\3\2\2\2\u02e4\u00a7"+
		"\3\2\2\2\u02e5\u02e6\7@\2\2\u02e6\u02e7\7\7\2\2\u02e7\u02e8\7@\2\2\u02e8"+
		"\u00a9\3\2\2\2\u02e9\u02ea\5\u00acW\2\u02ea\u02eb\5\u00aeX\2\u02eb\u00ab"+
		"\3\2\2\2\u02ec\u02f6\5\u00b8]\2\u02ed\u02f6\5\u0082B\2\u02ee\u02ef\7\60"+
		"\2\2\u02ef\u02f0\7\65\2\2\u02f0\u02f1\7\61\2\2\u02f1\u02f6\5\u0086D\2"+
		"\u02f2\u02f6\5\u008cG\2\u02f3\u02f6\5\u008eH\2\u02f4\u02f6\5~@\2\u02f5"+
		"\u02ec\3\2\2\2\u02f5\u02ed\3\2\2\2\u02f5\u02ee\3\2\2\2\u02f5\u02f2\3\2"+
		"\2\2\u02f5\u02f3\3\2\2\2\u02f5\u02f4\3\2\2\2\u02f6\u00ad\3\2\2\2\u02f7"+
		"\u02fc\7\17\2\2\u02f8\u02fa\5\u00b0Y\2\u02f9\u02fb\7\n\2\2\u02fa\u02f9"+
		"\3\2\2\2\u02fa\u02fb\3\2\2\2\u02fb\u02fd\3\2\2\2\u02fc\u02f8\3\2\2\2\u02fc"+
		"\u02fd\3\2\2\2\u02fd\u02fe\3\2\2\2\u02fe\u02ff\7\20\2\2\u02ff\u00af\3"+
		"\2\2\2\u0300\u0305\5\u00b2Z\2\u0301\u0302\7\n\2\2\u0302\u0304\5\u00b2"+
		"Z\2\u0303\u0301\3\2\2\2\u0304\u0307\3\2\2\2\u0305\u0303\3\2\2\2\u0305"+
		"\u0306\3\2\2\2\u0306\u00b1\3\2\2\2\u0307\u0305\3\2\2\2\u0308\u0309\5\u00b4"+
		"[\2\u0309\u030a\7 \2\2\u030a\u030c\3\2\2\2\u030b\u0308\3\2\2\2\u030b\u030c"+
		"\3\2\2\2\u030c\u030d\3\2\2\2\u030d\u030e\5\u00b6\\\2\u030e\u00b3\3\2\2"+
		"\2\u030f\u0313\7@\2\2\u0310\u0313\5\u00d0i\2\u0311\u0313\5\u00aeX\2\u0312"+
		"\u030f\3\2\2\2\u0312\u0310\3\2\2\2\u0312\u0311\3\2\2\2\u0313\u00b5\3\2"+
		"\2\2\u0314\u0317\5\u00d0i\2\u0315\u0317\5\u00aeX\2\u0316\u0314\3\2\2\2"+
		"\u0316\u0315\3\2\2\2\u0317\u00b7\3\2\2\2\u0318\u0319\7\66\2\2\u0319\u031f"+
		"\7\17\2\2\u031a\u031b\5\u00ba^\2\u031b\u031c\5\u00d6l\2\u031c\u031e\3"+
		"\2\2\2\u031d\u031a\3\2\2\2\u031e\u0321\3\2\2\2\u031f\u031d\3\2\2\2\u031f"+
		"\u0320\3\2\2\2\u0320\u0322\3\2\2\2\u0321\u031f\3\2\2\2\u0322\u0323\7\20"+
		"\2\2\u0323\u00b9\3\2\2\2\u0324\u0325\5\24\13\2\u0325\u0326\5|?\2\u0326"+
		"\u0329\3\2\2\2\u0327\u0329\5\u00bc_\2\u0328\u0324\3\2\2\2\u0328\u0327"+
		"\3\2\2\2\u0329\u032b\3\2\2\2\u032a\u032c\7I\2\2\u032b\u032a\3\2\2\2\u032b"+
		"\u032c\3\2\2\2\u032c\u00bb\3\2\2\2\u032d\u032f\7\30\2\2\u032e\u032d\3"+
		"\2\2\2\u032e\u032f\3\2\2\2\u032f\u0330\3\2\2\2\u0330\u0331\5~@\2\u0331"+
		"\u00bd\3\2\2\2\u0332\u0333\7\r\2\2\u0333\u0334\5\36\20\2\u0334\u00bf\3"+
		"\2\2\2\u0335\u0336\ba\1\2\u0336\u0339\5\u00a0Q\2\u0337\u0339\5\u00d4k"+
		"\2\u0338\u0335\3\2\2\2\u0338\u0337\3\2\2\2\u0339\u0346\3\2\2\2\u033a\u033b"+
		"\f\7\2\2\u033b\u0345\5\u00c2b\2\u033c\u033d\f\6\2\2\u033d\u0345\5\u00c4"+
		"c\2\u033e\u033f\f\5\2\2\u033f\u0345\5\u00c6d\2\u0340\u0341\f\4\2\2\u0341"+
		"\u0345\5\u00c8e\2\u0342\u0343\f\3\2\2\u0343\u0345\5\u00caf\2\u0344\u033a"+
		"\3\2\2\2\u0344\u033c\3\2\2\2\u0344\u033e\3\2\2\2\u0344\u0340\3\2\2\2\u0344"+
		"\u0342\3\2\2\2\u0345\u0348\3\2\2\2\u0346\u0344\3\2\2\2\u0346\u0347\3\2"+
		"\2\2\u0347\u00c1\3\2\2\2\u0348\u0346\3\2\2\2\u0349\u034a\7\7\2\2\u034a"+
		"\u034b\7@\2\2\u034b\u00c3\3\2\2\2\u034c\u034d\7\60\2\2\u034d\u034e\5\u00d0"+
		"i\2\u034e\u034f\7\61\2\2\u034f\u00c5\3\2\2\2\u0350\u0360\7\60\2\2\u0351"+
		"\u0353\5\u00d0i\2\u0352\u0351\3\2\2\2\u0352\u0353\3\2\2\2\u0353\u0354"+
		"\3\2\2\2\u0354\u0356\7 \2\2\u0355\u0357\5\u00d0i\2\u0356\u0355\3\2\2\2"+
		"\u0356\u0357\3\2\2\2\u0357\u0361\3\2\2\2\u0358\u035a\5\u00d0i\2\u0359"+
		"\u0358\3\2\2\2\u0359\u035a\3\2\2\2\u035a\u035b\3\2\2\2\u035b\u035c\7 "+
		"\2\2\u035c\u035d\5\u00d0i\2\u035d\u035e\7 \2\2\u035e\u035f\5\u00d0i\2"+
		"\u035f\u0361\3\2\2\2\u0360\u0352\3\2\2\2\u0360\u0359\3\2\2\2\u0361\u0362"+
		"\3\2\2\2\u0362\u0363\7\61\2\2\u0363\u00c7\3\2\2\2\u0364\u0365\7\7\2\2"+
		"\u0365\u0366\7\5\2\2\u0366\u0367\5|?\2\u0367\u0368\7\6\2\2\u0368\u00c9"+
		"\3\2\2\2\u0369\u0378\7\5\2\2\u036a\u0371\5\26\f\2\u036b\u036e\5|?\2\u036c"+
		"\u036d\7\n\2\2\u036d\u036f\5\26\f\2\u036e\u036c\3\2\2\2\u036e\u036f\3"+
		"\2\2\2\u036f\u0371\3\2\2\2\u0370\u036a\3\2\2\2\u0370\u036b\3\2\2\2\u0371"+
		"\u0373\3\2\2\2\u0372\u0374\7\65\2\2\u0373\u0372\3\2\2\2\u0373\u0374\3"+
		"\2\2\2\u0374\u0376\3\2\2\2\u0375\u0377\7\n\2\2\u0376\u0375\3\2\2\2\u0376"+
		"\u0377\3\2\2\2\u0377\u0379\3\2\2\2\u0378\u0370\3\2\2\2\u0378\u0379\3\2"+
		"\2\2\u0379\u037a\3\2\2\2\u037a\u037b\7\6\2\2\u037b\u00cb\3\2\2\2\u037c"+
		"\u037d\5\u00ceh\2\u037d\u037e\7\7\2\2\u037e\u037f\7@\2\2\u037f\u00cd\3"+
		"\2\2\2\u0380\u038b\5~@\2\u0381\u0382\7\5\2\2\u0382\u0383\7\30\2\2\u0383"+
		"\u0384\5~@\2\u0384\u0385\7\6\2\2\u0385\u038b\3\2\2\2\u0386\u0387\7\5\2"+
		"\2\u0387\u0388\5\u00ceh\2\u0388\u0389\7\6\2\2\u0389\u038b\3\2\2\2\u038a"+
		"\u0380\3\2\2\2\u038a\u0381\3\2\2\2\u038a\u0386\3\2\2\2\u038b\u00cf\3\2"+
		"\2\2\u038c\u038d\bi\1\2\u038d\u038e\5\u00d2j\2\u038e\u0394\3\2\2\2\u038f"+
		"\u0390\f\3\2\2\u0390\u0391\t\6\2\2\u0391\u0393\5\u00d0i\4\u0392\u038f"+
		"\3\2\2\2\u0393\u0396\3\2\2\2\u0394\u0392\3\2\2\2\u0394\u0395\3\2\2\2\u0395"+
		"\u00d1\3\2\2\2\u0396\u0394\3\2\2\2\u0397\u039b\5\u00c0a\2\u0398\u0399"+
		"\t\7\2\2\u0399\u039b\5\u00d2j\2\u039a\u0397\3\2\2\2\u039a\u0398\3\2\2"+
		"\2\u039b\u00d3\3\2\2\2\u039c\u039d\5|?\2\u039d\u039e\7\5\2\2\u039e\u03a0"+
		"\5\u00d0i\2\u039f\u03a1\7\n\2\2\u03a0\u039f\3\2\2\2\u03a0\u03a1\3\2\2"+
		"\2\u03a1\u03a2\3\2\2\2\u03a2\u03a3\7\6\2\2\u03a3\u00d5\3\2\2\2\u03a4\u03a9"+
		"\7\f\2\2\u03a5\u03a9\7\2\2\3\u03a6\u03a9\6l\b\2\u03a7\u03a9\6l\t\2\u03a8"+
		"\u03a4\3\2\2\2\u03a8\u03a5\3\2\2\2\u03a8\u03a6\3\2\2\2\u03a8\u03a7\3\2"+
		"\2\2\u03a9\u00d7\3\2\2\2`\u00df\u00e7\u00f5\u00f9\u00fc\u0105\u010a\u0114"+
		"\u0118\u011c\u0120\u0127\u012f\u013a\u013e\u0147\u0151\u015d\u0161\u0167"+
		"\u016b\u0176\u0188\u0190\u01a4\u01b8\u01bc\u01c0\u01cc\u01d2\u01da\u01de"+
		"\u01e4\u01e7\u01ed\u01f9\u01ff\u0206\u020d\u021c\u0223\u022b\u0237\u023a"+
		"\u0242\u024a\u024f\u0253\u0257\u025f\u026d\u0271\u027b\u0290\u02a4\u02ab"+
		"\u02b2\u02b6\u02bb\u02bd\u02c6\u02ca\u02cd\u02d8\u02dd\u02e3\u02f5\u02fa"+
		"\u02fc\u0305\u030b\u0312\u0316\u031f\u0328\u032b\u032e\u0338\u0344\u0346"+
		"\u0352\u0356\u0359\u0360\u036e\u0370\u0373\u0376\u0378\u038a\u0394\u039a"+
		"\u03a0\u03a8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}