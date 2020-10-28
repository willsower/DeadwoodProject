JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES	=	\
	board.java	\
	Card.java	\
	dayManager.java	\
	Deck.java	\
	OnTurn.java	\
	ParseXML.java	\
	Player.java	\
	role.java	\
	room.java	\
	ScoringManager.java	\
	stage.java	\
	TurnManager.java	\
	Upgrade.java	\
	Main.java	\

default: classes

classes: $(CLASSES:.java=.class)

clean: $(RM) *.class