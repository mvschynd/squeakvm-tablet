/*
 *	Manually composed plugin to interact with Android JNI.
 */

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define EXPORT(returnType) returnType

/* Do not include the entire sq.h file but just those parts needed. */
/*  The virtual machine proxy definition */
#include "sqVirtualMachine.h"
/* Configuration options */
#include "sqConfig.h"
/* Platform specific definitions */
#include "sqPlatformSpecific.h"

#define true 1
#define false 0
#define null 0  /* using 'null' because nil is predefined in Think C */

#include "sqMemoryAccess.h"

#pragma export on

EXPORT(sqInt) inAndroid(void);
EXPORT(sqInt) androidTTS(void);

#pragma export off

extern struct VirtualMachine* interpreterProxy;
static const char *moduleName = "AndroidPlugin 09 March 2011 (i)";

/*      Note: This is coded so that plugins can be run from Squeak. */

static VirtualMachine * getInterpreter(void) {
	        return interpreterProxy;
}


/*      Note: This is hardcoded so it can be run from Squeak.
 *      The module name is used for validating a module *after*
 *      it is loaded to check if it does really contain the module
 *      we're thinking it contains. This is important! */

EXPORT(const char*) getModuleName(void) {
	        return moduleName;
}

EXPORT(sqInt) initialiseModule(void) {
	return 1;
}

EXPORT(sqInt) inAndroid(void) {
        interpreterProxy->pop(1);
	interpreterProxy->pushBool(true);
}

EXPORT(sqInt) androidTTS(void) {
	sqInt toSpeak;
	char * toSpeakIndex;
	toSpeak = interpreterProxy->stackValue(0);
        if (!(interpreterProxy->isBytes(toSpeak))) {
                return interpreterProxy->primitiveFail();
        }
	toSpeakIndex = interpreterProxy->firstIndexableField(toSpeak);
	interpreterProxy->pop(2);
	interpreterProxy->pushInteger(strlen(toSpeakIndex));
}

EXPORT(sqInt) setInterpreter(struct VirtualMachine* anInterpreter) {
	    sqInt ok;

            interpreterProxy = anInterpreter;
            ok = interpreterProxy->majorVersion() == VM_PROXY_MAJOR;
            if (ok == 0) {
                    return 0;
            }
            ok = interpreterProxy->minorVersion() >= VM_PROXY_MINOR;
            return ok;
}


void* AndroidPlugin_exports[][3] = {
       	{"AndroidPlugin", "inAndroid", (void*)inAndroid},
       	{"AndroidPlugin", "androidTTS", (void*)androidTTS},
       	{"AndroidPlugin", "initialiseModule", (void*)initialiseModule},
	{"AndroidPlugin", "getModuleName", (void*)getModuleName},
        {"AndroidPlugin", "setInterpreter", (void*)setInterpreter},
	{NULL, NULL, NULL}
};

