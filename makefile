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
	Role.java	\
	Room.java	\
	ScoringManager.java	\
	Stage.java	\
	SystemManager.java \
	Upgrade.java	\
	UserInterface.java \
	Set.java \
	Main.java	\

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class

