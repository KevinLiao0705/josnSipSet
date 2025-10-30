CXX = g++
TARGET = main
SRC = main.cpp uvm_usart.cpp uvm_timer.cpp 
LIBS =  -lm -lwiringPi -lpthread
INC_PATH = -I./include 
OPTIONS = -g  
$(TARGET) : $(SRC)
	$(CXX) $(OPTIONS)  -o $(TARGET) $(INC_PATH) $(LIB_PATH)  $(SRC)  $(LIBS)
clean : 
	rm $(TARGET) *.o
install :
	cp -a main /home/pi
