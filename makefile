JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES	=	\
	Board.java	\
	Card.java	\
	Deck.java	\
	OnTurn.java	\
	ParseXML.java	\
	Player.java	\
	ScoringManager.java	\
	SystemManager.java \
	Upgrade.java	\
	UserInterface.java \
	Set.java \
	Deadwood.java	\

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class

