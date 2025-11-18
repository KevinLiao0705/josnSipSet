/*
sudo gpio readall



*/

#include <iostream>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <stdlib.h>
#include <ctime>
#include <time.h>
#include <sys/time.h>
#include <sys/socket.h> //socket
#include <arpa/inet.h>	//inet_addr
#include <fcntl.h>
#include <poll.h>
#include "uvm_usart.hpp"

extern "C"
{
#include <wiringSerial.h>
#include <wiringPi.h>
#include <wiringPiSPI.h>
#include <wiringPiI2C.h>
};
// Select One =================
#define sipphoneIo
//#define sipui2in1Io
//=============================
//#define roip
//=============================


#define TITLE "Name: Io In Raspberry pi\n"
#define VERSION "Version: 1.0\n"
#define LAST_EDIT_TIME "Last Edit Time: 2025,08,04\n"

//=============================
#ifdef sipphoneIo

#define MY_DEVICE_ID 0x1946
#define SOCKIO_SERVER_PORT 1234
#define roipPttPin 5
#define roipCorPin 4

#define cm4Gpio21 29
#define cm4Gpio20 28
#define cm4Gpio7 11
#define cm4Gpio8 10


#endif

#ifdef sipui2in1Io
#define MY_DEVICE_ID 0x1846
#define SOCKIO_SERVER_PORT 1232
#define usbSelPin 29

#endif

using namespace std;

// AMA2 TX:IO4, RX:IO5
// AMA3 TX:IO8, RX:IO9
// AMA4 TX:IO12, RX:IO13

/*
 +-----+-----+---------+------+---+---CM4----+---+------+---------+-----+-----+
 | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
 |     |     |    3.3v |      |   |  1 || 2  |   |      | 5v      |     |     |
 |   2 |   8 |   SDA.1 |   IN | 1 |  3 || 4  |   |      | 5v      |     |     |
 |   3 |   9 |   SCL.1 |   IN | 1 |  5 || 6  |   |      | 0v      |     |     |
 |   4 |   7 | GPIO. 7 | ALT4 | 1 |  7 || 8  | 1 | ALT0 | TxD     | 15  | 14  |
 |     |     |      0v |      |   |  9 || 10 | 1 | ALT0 | RxD     | 16  | 15  |
 |  17 |   0 | GPIO. 0 |   IN | 0 | 11 || 12 | 0 | IN   | GPIO. 1 | 1   | 18  |
 |  27 |   2 | GPIO. 2 |   IN | 0 | 13 || 14 |   |      | 0v      |     |     |
 |  22 |   3 | GPIO. 3 |   IN | 0 | 15 || 16 | 0 | IN   | GPIO. 4 | 4   | 23  |
 |     |     |    3.3v |      |   | 17 || 18 | 0 | IN   | GPIO. 5 | 5   | 24  |
 |  10 |  12 |    MOSI |   IN | 1 | 19 || 20 |   |      | 0v      |     |     |
 |   9 |  13 |    MISO | ALT4 | 1 | 21 || 22 | 0 | IN   | GPIO. 6 | 6   | 25  |
 |  11 |  14 |    SCLK |   IN | 1 | 23 || 24 | 1 | ALT4 | CE0     | 10  | 8   |
 |     |     |      0v |      |   | 25 || 26 | 1 | IN   | CE1     | 11  | 7   |
 |   0 |  30 |   SDA.0 | ALT4 | 1 | 27 || 28 | 1 | ALT4 | SCL.0   | 31  | 1   |
 |   5 |  21 | GPIO.21 | ALT4 | 1 | 29 || 30 |   |      | 0v      |     |     |
 |   6 |  22 | GPIO.22 |   IN | 1 | 31 || 32 | 1 | ALT4 | GPIO.26 | 26  | 12  |
 |  13 |  23 | GPIO.23 | ALT4 | 1 | 33 || 34 |   |      | 0v      |     |     |
 |  19 |  24 | GPIO.24 |   IN | 0 | 35 || 36 | 0 | IN   | GPIO.27 | 27  | 16  |
 |  26 |  25 | GPIO.25 |   IN | 0 | 37 || 38 | 0 | IN   | GPIO.28 | 28  | 20  |
 |     |     |      0v |      |   | 39 || 40 | 0 | IN   | GPIO.29 | 29  | 21  |
 +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
 | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 +-----+-----+---------+------+---+---CM4----+---+------+---------+-----+-----+
 */


typedef struct _myStream
{
	int inx;
	int spcChar_f;
	char name[32];
	unsigned char rdata[4096];
	int rdata_len;
	unsigned char rbuf[4096];
	int rbuf_len;
	//====================================
	unsigned char tdata[4096];
	int tdata_len = 0;
	unsigned char tbuf[4096];
	int tbuf_len = 0;
	int txStart_f = 0;
	int txwait_tim = 0;
	int txwait_tim_th = 0;
	int txNoData_cnt_th = 0;
	int txNoData_cnt = 0;
	//====================================
	int reced_pack_f = 0;
	int reced_clr_tim = 0;
	int recon_tim = 0;
	int err_cnt = 0;
	int connect_f = 0;
	int encType = 0;
	int decType = 0;
	int tx_nodata_f = 1;
	int waitNorx_f = 0;
	int rbuf_inx = 0;
	int chksum0, chksum1;
	int noRxCnt = 0;
	int noRxCnt_lim = 5;
	void (*fptr)(struct _myStream *);
	void (*testfp)(const char *);

} MYSTM;

typedef struct _trxPack
{
	int lenLim = 2000;
	int deviceIdH = MY_DEVICE_ID >> 8;	// tx device id
	int deviceIdL = MY_DEVICE_ID & 255; // tx device id
	int amt = 5;
	int packId[5] = {0x10, 0x11, 0x12, 0x13, 0x14}; // 0x00:system use//10 to uart
	int txLen[5];
	unsigned char *txData[5];

} TrxPack;

TrxPack trxPack0;
MYSTM sockIoStm;
MYSTM uartStmA[4];

void firstInit(int argc, char *argv[]);
int setUart(int inx, const char *uartName, int boudRate);

void encSt(MYSTM *mstp);
void encData(MYSTM *mstp, unsigned char uch, int enc);
void encEnd(MYSTM *mstp);
void encMystm(MYSTM *mstp);
void decMystm(MYSTM *mstp);
int encPack(MYSTM *mstp, TrxPack *trxp);
void decPack(MYSTM *mstp);
//===
void sockIoReced(MYSTM *mystm);
void uart0Reced(MYSTM *mystm);
void uart1Reced(MYSTM *mystm);
void uart2Reced(MYSTM *mystm);
void uart3Reced(MYSTM *mystm);
void uartPrg(int inx);
void sockPrg(int &deviceHd, MYSTM &mystm, struct sockaddr_in server, TrxPack &trxp);
//===
void serialPutchars(const int fd, const unsigned char *s, int len);
void loadWordToBytes(unsigned char *chp, int &inxp, int word);
int readWord(unsigned char *bytep, int &inx);
int readByte(unsigned char *bytep, int &inx);
//===
void loadPiIoToTxPack();
void readSystemTime(void);
void writeSystemTime(void);
void readRtc(void);
void writeRtc(void);
//=====================================
struct sockaddr_in sockIoServer;
int sockIoHd = -1;
int uartHdA[] = {-1, -1, -1, -1};
int iicHd;
//=====================================
int sec, minite, hour;
int year, month, date, day;
int debug_cnt = 0;
char ui_ipaddr[20] = "127.0.0.1";
/*
bit0 =system ok, bit1:uart0 ok, bit2:uart1 ok, bit3:uart2 ok, bit4:uart3 ok, bit5:uart4 ok, bit6:uart5 ok,
bit8 =roipCor
*/
unsigned short ioStatus0 = 0;
unsigned short ioStatus1 = 0;
unsigned short ioInFlag0 = 0;
unsigned short ioInFlag1 = 0;
//======================================
int noSockPackTime = 0;
int noSockPack_f = 0;
int uartOkF[] = {0, 0, 0, 0};
int piIoFlag=0;
unsigned short ioStatus0Pre=0;

void firstInit(int argc, char *argv[])
{
	for (int i = 0; i < trxPack0.amt; i++)
	{
		trxPack0.txData[i] = new unsigned char[4096];
		trxPack0.txLen[i] = 0;
	}

	//==========================================================================================================================
	printf("#######################################################################\n");
	printf(TITLE);
	printf(VERSION);
	printf(LAST_EDIT_TIME);
	//==========================================================================================================================
	printf("=======================================================================\n");
	if (wiringPiSetup() == -1)
	{
		fprintf(stdout, "Unable to start wiringPi: %s\n", strerror(errno));
		exit(1);
	}
	printf("wiringPiSetup OK\n");
	ioStatus0 |= 1;
	printf("=======================================================================\n");
	if (argc < 2)
	{
		printf("Using Default IP : %s \n", ui_ipaddr);
	}
	else
	{
		sprintf(ui_ipaddr, "%s", argv[1]);
		printf("Using Default IP : %s \n", ui_ipaddr);
	}
	//==========================================================================================================================
	sockIoServer.sin_addr.s_addr = inet_addr(ui_ipaddr);
	sockIoServer.sin_family = AF_INET;
	sockIoServer.sin_port = htons(SOCKIO_SERVER_PORT);

	sockIoStm.fptr = sockIoReced;
	uartStmA[0].fptr = uart0Reced;
	uartStmA[1].fptr = uart1Reced;
	uartStmA[2].fptr = uart2Reced;
	uartStmA[3].fptr = uart3Reced;
	strcpy(sockIoStm.name, "sockIo");
	strcpy(uartStmA[0].name, "uart0");
	strcpy(uartStmA[1].name, "uart1");
	strcpy(uartStmA[2].name, "uart2");
	strcpy(uartStmA[3].name, "uart3");
}
int setUart(int inx, const char *uartName, int boudRate)
{
	if ((uartHdA[inx] = serialOpen(uartName, boudRate)) < 0)
	{
		fprintf(stderr, "Unable to open %s serial device\n", uartName);
		uartOkF[inx] = 0;
		ioStatus0 &= (1 << (inx + 1)) ^ 0xffff;
		return 0;
	}
	else
	{
		printf("%s serialOpen OK\n", uartName);
		uartOkF[inx] = 1;
		ioStatus0 |= 1 << (inx + 1);
		return 1;
	}
}

#ifdef sipphoneIo

int main(int argc, char *argv[])
{
	firstInit(argc, argv);
	setUart(0, "/dev/ttyAMA4", 115200);
	setUart(1, "/dev/ttyAMA2", 115200);
	//==========================================================================================================================
	#ifdef roip
	//pinMode(cm4Gpio21, OUTPUT);
	//pinMode(cm4Gpio20, OUTPUT);
	//pinMode(cm4Gpio7, OUTPUT);
	//pinMode(cm4Gpio8, OUTPUT);
	pinMode(roipPttPin, OUTPUT);
	digitalWrite(roipPttPin, 1);
	pinMode(roipCorPin, INPUT);
	#endif
	//==========================================================================================================================
	printf("=======================================================================\n");
	printf("Running...........\n");
	sockIoStm.encType = 1;
	sockIoStm.decType = 1;
	//====================================
	uartStmA[0].encType = 1;
	uartStmA[0].decType = 1;
	uartStmA[0].waitNorx_f = 0; // waitNoRx To Get rx pack
	uartStmA[0].tx_nodata_f = 1;
	uartStmA[0].txwait_tim_th = 0;
	//====================================
	uartStmA[1].encType = 1;
	uartStmA[1].decType = 1;
	uartStmA[1].waitNorx_f = 0; // waitNoRx To Get rx pack
	uartStmA[1].tx_nodata_f = 1;
	uartStmA[1].txwait_tim_th = 0;
	//====================================
	int ptt=0;
	for (;;)
	{
		usleep(10000);

		//==========================================================================================================================
		// uartPrg(uartHdA[0], uartStmA[0]);
		// uartPrg(uartHdA[1], uartStmA[1]);
		uartPrg(0);
		uartPrg(1);
		#ifdef roip
		int roipCorPinSta = digitalRead(roipCorPin);
		if(roipCorPinSta)
			ioStatus0|=0x0100;
		else
			ioStatus0&=0xfeff;
		#endif
		if(ioStatus0Pre!=ioStatus0){
			ioStatus0Pre=ioStatus0;
			printf("ioStatus0=%x\n", ioStatus0);
		}
		
		//==========================================================================================================================
		sockPrg(sockIoHd, sockIoStm, sockIoServer, trxPack0);
		noSockPackTime++;
		if (noSockPackTime > 10)
			noSockPack_f = 0;
		debug_cnt++;
	}
}

#endif

#ifdef sipui2in1Io

int main(int argc, char *argv[])
{
	int ff;
	firstInit(argc, argv);
	pinMode(usbSelPin, OUTPUT);
	digitalWrite(usbSelPin, 1); // 1:for inner pi, 0:for out pc
	printf("usbSelPin, 0\n");
	usleep(2000000);
	usleep(2000000);
	digitalWrite(usbSelPin, 1); // 1:for inner pi, 0:for out pc
	printf("usbSelPin, 1\n");
	usleep(2000000);
	usleep(2000000);
	//=================================
	setUart(0, "/dev/ttyAMA2", 115200);	   // sip ui uart socks0u1
	setUart(1, "/dev/ttyAMA4", 115200);	   // key Pad uart socks0u2
	ff = setUart(2, "/dev/ttyUSB0", 9600); // switch uart socks0u3
	if (ff == 0)
		setUart(2, "/dev/ttyACM0", 9600); // switch uart socks0u3
	setUart(3, "/dev/ttyAMA3", 115200);	  // switch led board
	//==========================================================================================================================
	//==========================================================================================================================
	printf("=======================================================================\n");
	printf("Running...........\n");
	sockIoStm.encType = 1;
	sockIoStm.decType = 1;
	//====================================
	uartStmA[0].encType = 1;
	uartStmA[0].decType = 1;
	uartStmA[0].waitNorx_f = 0; // waitNoRx To Get rx pack
	uartStmA[0].tx_nodata_f = 1;
	uartStmA[0].txwait_tim_th = 0;
	//====================================
	uartStmA[1].encType = 1;
	uartStmA[1].decType = 1;
	uartStmA[1].waitNorx_f = 0; // waitNoRx To Get rx pack
	uartStmA[1].tx_nodata_f = 1;
	uartStmA[1].txwait_tim_th = 0;
	//====================================
	uartStmA[2].encType = 0;
	uartStmA[2].decType = 0;
	uartStmA[2].waitNorx_f = 1; // waitNoRx To Get rx pack
	uartStmA[2].tx_nodata_f = 0;
	uartStmA[2].txwait_tim_th = 0;
	//====================================
	uartStmA[3].encType = 1;
	uartStmA[3].decType = 1;
	uartStmA[3].waitNorx_f = 0; // waitNoRx To Get rx pack
	uartStmA[3].tx_nodata_f = 1;
	uartStmA[3].txwait_tim_th = 0;

	for (;;)
	{
		usleep(10000);
		//==========================================================================================================================
		uartPrg(0);
		uartPrg(1);
		uartPrg(2);
		uartPrg(3);
		//==========================================================================================================================
		sockPrg(sockIoHd, sockIoStm, sockIoServer, trxPack0);
		noSockPackTime++;
		if (noSockPackTime > 10)
			noSockPack_f = 0;
		debug_cnt++;
	}
}

#endif

void uartPrg(int inx)
{
	string sbuf;
	int i, j, k;
	int index = 0;

	if (uartOkF[inx] == 0)
		return;

	int deviceHd = uartHdA[inx];
	MYSTM &mystm = uartStmA[inx];

	if (mystm.reced_clr_tim < 100)
	{
		mystm.reced_clr_tim++;
		if (mystm.reced_clr_tim == 100)
		{
			mystm.reced_pack_f = 0;
		}
	}
	mystm.rbuf_len = serialDataAvail(deviceHd);
	if (mystm.rbuf_len >= sizeof(mystm.rbuf))
	{
		serialFlush(deviceHd);
		mystm.rbuf_len = 0;
	}
	if (mystm.waitNorx_f == 0)
	{
		if (mystm.rbuf_len)
		{
			for (i = 0; i < mystm.rbuf_len; i++)
				mystm.rbuf[i] = serialGetchar(deviceHd);
			/*	
			if(strcmp(mystm.name, "uart0") == 0){
			   	printf("uart0 received ,byte = %d", mystm.rbuf_len);
			   	printf("   %x %x\n", mystm.rbuf[0],mystm.rbuf[1]);

			}
			*/		

			decMystm(&mystm);
		}
	}
	else
	{
		if (mystm.rbuf_len)
		{
			for (i = 0; i < mystm.rbuf_len; i++)
			{
				if (mystm.rbuf_inx > 4000)
					mystm.rbuf_inx = 4000;
				mystm.rbuf[mystm.rbuf_inx++] = serialGetchar(deviceHd);
			}
		}
		else
		{
			if (mystm.rbuf_inx != 0)
			{
				mystm.rbuf_len = mystm.rbuf_inx;
				decMystm(&mystm);
			}
			mystm.rbuf_inx = 0;
		}
	}
	//==========================================================
	if (++mystm.txwait_tim >= mystm.txwait_tim_th)
	{
		mystm.txwait_tim = 0;
		if (mystm.tbuf_len)
		{
			encMystm(&mystm);
			serialPutchars(deviceHd, mystm.tdata, mystm.tdata_len);
			//if (strcmp(mystm.name, "uart2") == 0)
			//	printf("%s %x %d\n", mystm.name,mystm.tdata[0], mystm.tdata_len);
			mystm.tbuf_len = 0;
		}
		else
		{
			mystm.txNoData_cnt++;
			if (mystm.txNoData_cnt > mystm.txNoData_cnt_th)
			{
				mystm.txNoData_cnt = 0;
				if (mystm.tx_nodata_f)
				{
					encSt(&mystm);
					encData(&mystm, MY_DEVICE_ID & 255, 1);
					encData(&mystm, (MY_DEVICE_ID >> 8) & 255, 1);
					encData(&mystm, 255, 1);  // SERIAL ID LOW
					encData(&mystm, 255, 1);  // SERIAL ID HIGH
					encData(&mystm, 0x00, 1); // groupId low byte
					encData(&mystm, 0xab, 1); // groupId high byte
					encData(&mystm, 0x02, 1); // sub pack bytes len low byte
					encData(&mystm, 0x00, 1); // sub pack bytes len height byte
					encData(&mystm, 0x0e, 1); // scmd 0e:no data
					encData(&mystm, 0x00, 1); // mcmd system use
					encEnd(&mystm);
					serialPutchars(deviceHd, mystm.tdata, mystm.tdata_len);

					

				}
			}
		}
	}
}

void sockPrg(int &sockHd, MYSTM &mystm, struct sockaddr_in sockServer, TrxPack &trxp)
{
	int index;
	string sbuf;
	if (mystm.reced_clr_tim < 100)
	{
		mystm.reced_clr_tim++;
		if (mystm.reced_clr_tim == 100)
		{
			mystm.reced_pack_f = 0;
		}
	}

	if (!mystm.connect_f)
	{
		if (mystm.recon_tim++ > 100)
		{
			mystm.recon_tim = 0;
			printf("%s Try To Connect To IP %s:%d\n", mystm.name, inet_ntoa(sockServer.sin_addr), SOCKIO_SERVER_PORT);
			if (sockHd == -1)
				sockHd = socket(AF_INET, SOCK_STREAM, 0);
			if (sockHd == -1)
			{
				printf("Could not create socket\n");
			}
			else
			{
				if (connect(sockHd, (struct sockaddr *)&sockServer, sizeof(sockServer)) < 0)
				// if (connect_with_timeout(sockHd, (struct sockaddr *)&sockServer, sizeof(sockServer), 1000) < 0)

				{
					printf("%s Connect To IP: %s Fail %d\n", mystm.name, inet_ntoa(sockServer.sin_addr), ++mystm.err_cnt);
				}
				else
				{
					printf("%s Connect To IP: %s Success\n", mystm.name, inet_ntoa(sockServer.sin_addr));
					mystm.connect_f = 1;
				}
			}
		}
	}
	//====================================================================================
	else
	{
		mystm.rbuf_len = recv(sockHd, mystm.rbuf, sizeof(mystm.rbuf), MSG_DONTWAIT);
		if (mystm.rbuf_len == 0) // read error
		{
			printf("%s rdata_len = %d\n", mystm.name, mystm.rbuf_len);
			mystm.connect_f = 0;
			close(sockHd);
			sockHd = -1;
		}
		else
		{
			if (mystm.rbuf_len > 0)
			{
				decMystm(&mystm);
			}
			else
			{
				/* no data */
			}

			if (++mystm.txwait_tim >= 0)
			{
				mystm.txwait_tim = 0;
				loadPiIoToTxPack();
				encPack(&mystm, &trxp);
				// printf("mystm.tdata_len %d\n", mystm.tdata_len);
				if (send(sockHd, mystm.tdata, mystm.tdata_len, 0) < 0)
				{
					mystm.connect_f = 0;
					sockHd = -1;
					printf("%s connection problem : Send failed\n", mystm.name);
				}
			}
		}
	}
}

void loadPiIoToTxPack()
{
	int inx = 0;
	unsigned char *bytes = trxPack0.txData[0];
	loadWordToBytes(bytes, inx, MY_DEVICE_ID);
	loadWordToBytes(bytes, inx, 0xffff);
	loadWordToBytes(bytes, inx, 0xab00);
	loadWordToBytes(bytes, inx, 10);
	loadWordToBytes(bytes, inx, 0x1000);
	loadWordToBytes(bytes, inx, ioStatus0);
	loadWordToBytes(bytes, inx, ioStatus1);
	loadWordToBytes(bytes, inx, ioInFlag0);
	loadWordToBytes(bytes, inx, ioInFlag1);
	trxPack0.txLen[0] = inx;
}

//=============================================================================================================================================
void sockIoReced(MYSTM *mystm)
{
	mystm->txwait_tim = 255;
	decPack(mystm);
}
void uart0Reced(MYSTM *mystm)
{
	int i;
	// printf("uart0 received, len:%d\n", mystm->rdata_len);
	if (mystm->rdata_len < 4000)
	{
		for (i = 0; i < mystm->rdata_len; i++)
		{
			trxPack0.txData[1][i] = mystm->rdata[i];
		}
		trxPack0.txLen[1] = mystm->rdata_len;
	}
}
void uart1Reced(MYSTM *mystm)
{
	int i;
	// printf("uart1 received, len:%d\n", mystm->rdata_len);
	if (mystm->rdata_len < 4000)
	{
		for (i = 0; i < mystm->rdata_len; i++)
		{
			trxPack0.txData[2][i] = mystm->rdata[i];
		}
		trxPack0.txLen[2] = mystm->rdata_len;
	}
}
void uart2Reced(MYSTM *mystm)
{
	int i;
	//printf("uart2 received, len:%d\n", mystm->rdata_len);
	if (mystm->rdata_len < 4000)
	{
		for (i = 0; i < mystm->rdata_len; i++)
		{
			trxPack0.txData[3][i] = mystm->rdata[i];
		}
		trxPack0.txLen[3] = mystm->rdata_len;
	}
}

void uart3Reced(MYSTM *mystm)
{
	int i;
	// printf("uart3 received, len:%d\n", mystm->rdata_len);
	if (mystm->rdata_len < 4000)
	{
		for (i = 0; i < mystm->rdata_len; i++)
		{
			trxPack0.txData[4][i] = mystm->rdata[i];
		}
		trxPack0.txLen[4] = mystm->rdata_len;
	}
}

//=============================================================================================================================================
void encMystm(MYSTM *mstp)
{
	int i, j;
	int len;
	int chksum0, chksum1;
	mstp->tdata_len = 0;
	if (mstp->encType == 0)
	{
		for (i = 0; i < mstp->tbuf_len; i++)
		{
			mstp->tdata[mstp->tdata_len++] = mstp->tbuf[i];
		}
	}
	else
	{
		encSt(mstp);
		for (i = 0; i < mstp->tbuf_len; i++)
		{
			encData(mstp, mstp->tbuf[i], 1);
		}
		encEnd(mstp);
	}
}

void decMystm(MYSTM *mstp)
{
	int i, j, k;
	int len;
	int chksum0, chksum1;

	if (mstp->decType == 0)
	{
		for (i = 0; i < mstp->rbuf_len; i++)
			mstp->rdata[i] = mstp->rbuf[i];
		mstp->rdata_len = mstp->rbuf_len;
		mstp->fptr(mstp);
	}
	else
	{

		/*
			if (strcmp(mstp->name, "uart0") == 0)
			{
				printf("uart0 %x", mstp->rbuf_len);
				for(k=0;k<mstp->rbuf_len;k++){
					printf(" %x", mstp->rbuf[k]);
				}
				printf("\n");

			}
		*/	


		for (i = 0; i < mstp->rbuf_len; i++)
		{
			if (mstp->rbuf[i] == 0xEA)
			{
				mstp->inx = 0;
				mstp->spcChar_f = 0;
				continue;
			}
			if (mstp->rbuf[i] == 0xEC)
			{
				mstp->spcChar_f = 1;
				continue;
			}
			if (mstp->rbuf[i] != 0xEB)
			{
				if (mstp->inx < sizeof(mstp->rdata))
				{
					if (mstp->spcChar_f)
						mstp->rdata[mstp->inx] = mstp->rbuf[i] ^ 0xAB;
					else
						mstp->rdata[mstp->inx] = mstp->rbuf[i];
					mstp->spcChar_f = 0;
					mstp->inx++;
				}
				continue;
			}

			mstp->spcChar_f = 0;
			len = mstp->inx - 2;

			//==================================
			chksum0 = 0xab;
			chksum1 = 0;
			for (j = 0; j < len; j++)
			{
				chksum0 ^= mstp->rdata[j];
				chksum1 += mstp->rdata[j];
			}
			
			/*
			if (strcmp(mstp->name, "uart0") == 0)
			{
				printf("uart0 received ,chksum = %x,%x \n", mstp->rdata[j], mstp->rdata[j + 1]);
			}
			*/	
			

			if ((chksum0 ^ mstp->rdata[j]) & 0xff)
				continue;
			j++;
			if ((chksum1 ^ mstp->rdata[j]) & 0xff)
				continue;

			mstp->rdata_len = len;
			mstp->fptr(mstp);
		}
	}
}

void encSt(MYSTM *mstp)
{
	mstp->chksum0 = 0xab;
	mstp->chksum1 = 0;
	mstp->tdata_len = 0;
	encData(mstp, 0xea, 0);
}

void encData(MYSTM *mstp, unsigned char uch, int enc)
{
	if (enc)
	{
		mstp->chksum0 ^= uch;
		mstp->chksum1 += uch;
		if (uch == 0xEA || uch == 0xEB || uch == 0xEC)
		{
			mstp->tdata[mstp->tdata_len++] = 0xEC;
			mstp->tdata[mstp->tdata_len++] = uch ^ 0xAB;
			return;
		}
		mstp->tdata[mstp->tdata_len++] = uch;
		return;
	}
	mstp->tdata[mstp->tdata_len++] = uch;
}

void encEnd(MYSTM *mstp)
{
	int sum0 = mstp->chksum0;
	int sum1 = mstp->chksum1;
	encData(mstp, sum0 & 255, 1);
	encData(mstp, sum1 & 255, 1);
	encData(mstp, 0xEB, 0);
}

//========================
int encPack(MYSTM *mstp, TrxPack *trxp)
{
	int i, j;
	int allLen;
	mstp->tdata_len = 0;
	int chks0, chks1;
	allLen = 0;
	for (i = 0; i < trxp->amt; i++)
	{
		allLen += trxp->txLen[i];
	}
	if (allLen > trxp->lenLim)
	{
		encSt(mstp);
		encData(mstp, trxp->deviceIdL, 1);
		encData(mstp, trxp->deviceIdH, 1);
		encData(mstp, 0xff, 1); // serialId low
		encData(mstp, 0xff, 1); // serialId high
		encData(mstp, 0x00, 1); // groupId
		encData(mstp, 0x00, 1); // flags
		encData(mstp, 0x02, 1); // sub pack bytes len low byte
		encData(mstp, 0x00, 1); // sub pack bytes len height byte
		encData(mstp, 0x0f, 1); // scmd 0f:pack length over
		encData(mstp, 0x00, 1); // mcmd system use
		encEnd(mstp);
		for (i = 0; i < trxp->amt; i++)
			trxp->txLen[i] = 0;
		return -1;
	}
	if (allLen == 0) // no data
	{
		encSt(mstp);
		encData(mstp, trxp->deviceIdL, 1);
		encData(mstp, trxp->deviceIdH, 1);
		encData(mstp, 0xff, 1); // serialId low
		encData(mstp, 0xff, 1); // serialId high
		encData(mstp, 0x00, 1); // flags
		encData(mstp, 0x00, 1); // groupId
		encData(mstp, 0x02, 1); // sub pack bytes len low byte
		encData(mstp, 0x00, 1); // sub pack bytes len height byte
		encData(mstp, 0x0e, 1); // scmd 0e:no data
		encData(mstp, 0x00, 1); // mcmd system use
		encEnd(mstp);
		for (i = 0; i < trxp->amt; i++)
			trxp->txLen[i] = 0;
		return -1;
	}

	encSt(mstp);
	encData(mstp, trxp->deviceIdL, 1);
	encData(mstp, trxp->deviceIdH, 1);
	encData(mstp, 0xff, 1); // serialId low
	encData(mstp, 0xff, 1); // serialId high
	encData(mstp, 0xff, 1);
	encData(mstp, 0xa9, 1); // pack
	allLen += trxp->amt * 4;
	encData(mstp, allLen & 255, 1);
	encData(mstp, allLen >> 8, 1); // pack
	// printf("packTx, len:%d %d %d %d\n", trxp->txLen[0],trxp->txLen[1],trxp->txLen[2],trxp->txLen[3]);
	for (i = 0; i < trxp->amt; i++)
	{
		encData(mstp, trxp->packId[i], 1);
		encData(mstp, 0xa9, 1); // groupId
		encData(mstp, trxp->txLen[i] & 255, 1);
		encData(mstp, trxp->txLen[i] >> 8, 1);
		for (j = 0; j < trxp->txLen[i]; j++)
		{
			encData(mstp, trxp->txData[i][j], 1);
		}
	}
	encEnd(mstp);
	// printf("txlen= %d\n", mstp->tdata_len);

	for (i = 0; i < trxp->amt; i++)
		trxp->txLen[i] = 0;
	return 0;
}

void decPack(MYSTM *mstp)
{
	int len;
	int i;
	int groupId;
	int cmd, para0, para1, para2, para3;
	unsigned char *rdataPtr = mstp->rdata;
	int dataInx = 0;
	int dataLen = mstp->rdata_len;
	int deviceId = readWord(rdataPtr, dataInx);
	int serialId = readWord(rdataPtr, dataInx);
	int packId = readWord(rdataPtr, dataInx);
	// printf("packIds %x %x %x\n", deviceId, serialId, packId);
	if (deviceId != MY_DEVICE_ID)
		return;
	if (packId != 0xa9ff)
		return;
	int allLen = readWord(rdataPtr, dataInx);
	if (allLen >= 4000)
	{
		printf("allLen %d\n", allLen);
		return;
	}
	if (noSockPack_f == 0)
		printf("socket package received, len:%d\n", allLen);
	noSockPack_f = 1;
	noSockPackTime = 0;
	int nextInx = dataInx;
	while ((dataInx + 4) <= dataLen)
	{
		if (dataInx < nextInx)
			dataInx = nextInx;
		if (dataInx >= 4000)
		{
			return;
		}
		packId = readWord(rdataPtr, dataInx);
		len = readWord(rdataPtr, dataInx);
		if (len > 2000)
			return;
		if (packId == 0xa900) // piIo
		{
			nextInx = dataInx + len;
			// printf("packIds %x %d\n", packId,len);
			deviceId = readWord(rdataPtr, dataInx);
			serialId = readWord(rdataPtr, dataInx);
			groupId = readWord(rdataPtr, dataInx);
			if (deviceId != MY_DEVICE_ID)
				continue;
			if (groupId != 0xab00)
				continue;
			len = readWord(rdataPtr, dataInx);
			cmd = readWord(rdataPtr, dataInx);
			para0 = readWord(rdataPtr, dataInx);
			para1 = readWord(rdataPtr, dataInx);
			para2 = readWord(rdataPtr, dataInx);
			para3 = readWord(rdataPtr, dataInx);
#ifdef sipui2in1Io
			if(para0!=piIoFlag)
				printf("sockPiIoRx:%d %x %x %x %x %x\n", len, cmd, para0, para1, para2, para3);
			piIoFlag=para0;
			if(piIoFlag&1)
				digitalWrite(usbSelPin, 0); // 1:for inner pi, 0:for out pc
			else
				digitalWrite(usbSelPin, 1); // 1:for inner pi, 0:for out pc

#endif
#ifdef sipphoneIo
			if(para0!=piIoFlag)
				printf("sockPiIoRx:%d %x %x %x %x %x\n", len, cmd, para0, para1, para2, para3);
			piIoFlag=para0;
			#ifdef roip
			
			if(piIoFlag&1)
				digitalWrite(roipPttPin, 1); // 1:for inner pi, 0:for out pc
			else
				digitalWrite(roipPttPin, 0); // 1:for inner pi, 0:for out pc
				
			#endif	

#endif



			continue;
		}
		if (packId == 0xa901) // uart0
		{
			nextInx = dataInx + len;
			// printf("packIds %x %d\n", packId,len);
			for (i = 0; i < len; i++)
				uartStmA[0].tbuf[i] = readByte(rdataPtr, dataInx);
			uartStmA[0].tbuf_len = len;
			continue;
		}
		if (packId == 0xa902) // uart1
		{
			nextInx = dataInx + len;
			// printf(" %x %d", packId,len);
			for (i = 0; i < len; i++)
				uartStmA[1].tbuf[i] = readByte(rdataPtr, dataInx);
			uartStmA[1].tbuf_len = len;
			continue;
		}
		if (packId == 0xa903) // uart2
		{
			nextInx = dataInx + len;
			if (len)
			{
				//printf(" %x %d\n", packId, len);
				for (i = 0; i < len; i++)
					uartStmA[2].tbuf[i] = readByte(rdataPtr, dataInx);
				uartStmA[2].tbuf_len = len;
			}
			continue;
		}
		if (packId == 0xa904) // uart3
		{
			nextInx = dataInx + len;
			// printf(" %x %d", packId,len);
			for (i = 0; i < len; i++)
				uartStmA[3].tbuf[i] = readByte(rdataPtr, dataInx);
			uartStmA[3].tbuf_len = len;
			continue;
		}

		break;
	}
	return;
}

//=============================================================================================================================================

void loadWordToBytes(unsigned char *chp, int &inxp, int word)
{
	chp[inxp++] = word & 255;
	chp[inxp++] = word >> 8;
}

int readWord(unsigned char *bytep, int &inx)
{
	int ibuf = bytep[inx++];
	ibuf += bytep[inx++] * 256;
	return ibuf;
}

int readByte(unsigned char *bytep, int &inx)
{
	int ibuf = bytep[inx++];
	return ibuf;
}

void serialPutchars(const int fd, const unsigned char *s, int len)
{
	write(fd, s, len);
}

void readSystemTime(void)
{
	//============================
	timeval curTime;
	tm *my_date_time;
	gettimeofday(&curTime, NULL);
	my_date_time = localtime(&curTime.tv_sec);
	//=============================
	// char my_date_time_string[22];
	// strftime(my_date_time_string, sizeof(my_date_time_string), "%Y-%m-%d %H:%M:%S", my_date_time);
	// printf("=====  %s =========\n", my_date_time_string);
	year = my_date_time->tm_year + 1900;
	month = my_date_time->tm_mon + 1;
	date = my_date_time->tm_mday;
	hour = my_date_time->tm_hour;
	minite = my_date_time->tm_min;
	sec = my_date_time->tm_sec;
	printf("\n System Time= %d-%d-%d %d:%d:%d\n", year, month, date, hour, minite, sec);
}

void writeSystemTime(void)
{
	string SetDataTimeString = "sudo date --set '";
	SetDataTimeString += to_string(year);
	SetDataTimeString += "-";
	SetDataTimeString += to_string(month);
	SetDataTimeString += "-";
	SetDataTimeString += to_string(date);
	SetDataTimeString += " ";
	SetDataTimeString += to_string(hour);
	SetDataTimeString += ":";
	SetDataTimeString += to_string(minite);
	SetDataTimeString += ":";
	SetDataTimeString += to_string(sec);
	SetDataTimeString += "'";
	cout << SetDataTimeString << endl;
	system((const char *)SetDataTimeString.c_str());
}

void readRtc(void)
{
	int idata;
	int ibuf;
	static int preSec;
	if (iicHd < 0)
		return;
	idata = wiringPiI2CReadReg8(iicHd, 0);
	sec = (idata >> 4) * 10 + (idata & 0x0f);
	//==============================
	idata = wiringPiI2CRead(iicHd);
	minite = (idata >> 4) * 10 + (idata & 0x0f);
	//==============================
	idata = wiringPiI2CRead(iicHd);
	if ((idata >> 6) == 0)
	{ // 0:24,1:12
		hour = (idata >> 4) * 10 + (idata & 0x0f);
	}
	else
	{
		ibuf = idata & 0x1f;
		hour = (ibuf >> 4) * 10 + (ibuf & 0x0f);
		if (hour == 12)
			hour = 0;
		if (((idata >> 5) & 1) == 1)
			hour += 12;
	}
	idata = wiringPiI2CRead(iicHd);
	day = (idata >> 4) * 10 + (idata & 0x0f);

	//==============================
	idata = wiringPiI2CRead(iicHd);
	date = (idata >> 4) * 10 + (idata & 0x0f);
	//==============================
	idata = wiringPiI2CRead(iicHd);
	month = ((idata >> 4) & 1) * 10 + (idata & 0x0f);
	ibuf = idata >> 7; // center 0:20,1:21
	//==============================
	idata = wiringPiI2CRead(iicHd);
	year = (idata >> 4) * 10 + (idata & 0x0f);
	if (ibuf == 1)
	{
		year += 2100;
	}
	else
	{
		year += 2000;
	}
	if (preSec != sec)
	{
		preSec = sec;
		printf("%d-%d-%d %d:%d:%d\n", year, month, date, hour, minite, sec);
	}
}

void writeRtc(void)
{
	int ibuf;
	printf("Write Time %d-%d-%d %d:%d:%d to RTC\n", year, month, date, hour, minite, sec);
	wiringPiI2CWriteReg8(iicHd, 0, ((sec / 10) << 4) + (sec % 10));
	wiringPiI2CWriteReg8(iicHd, 1, ((minite / 10) << 4) + (minite % 10));
	wiringPiI2CWriteReg8(iicHd, 2, ((hour / 10) << 4) + (hour % 10));
	wiringPiI2CWriteReg8(iicHd, 4, ((date / 10) << 4) + (date % 10));
	wiringPiI2CWriteReg8(iicHd, 5, ((month / 10) << 4) + (month % 10));
	ibuf = year - 2000;
	wiringPiI2CWriteReg8(iicHd, 6, ((ibuf / 10) << 4) + (ibuf % 10));
}
