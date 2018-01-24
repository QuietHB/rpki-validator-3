/**
 * The BSD License
 *
 * Copyright (c) 2010-2018 RIPE NCC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the RIPE NCC nor the names of its contributors may be
 *     used to endorse or promote products derived from this software without
 *     specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.ripe.rpki.rtr.domain.pdus;

import io.netty.buffer.ByteBuf;
import lombok.Value;

import java.nio.charset.StandardCharsets;

/**
 * @see <a href="https://tools.ietf.org/html/rfc8210#section-5.6">RFC8210 section 5.6 - IPv4 Prefix</a>
 */
@Value(staticConstructor = "of")
public class ErrorPdu implements Pdu {
    public static final int PDU_TYPE = 10;

    ProtocolVersion protocolVersion;
    ErrorCode errorCode;
    byte[] causingPdu;
    String errorText;

    short headerShort() {
        return (short) errorCode.code;
    }

    public int length() {
        return 8 + 4 + causingPdu.length + 4 + errorTextBytes().length;
    }

    private byte[] errorTextBytes() {
        return errorText.getBytes(StandardCharsets.UTF_8);
    }

    public void write(ByteBuf out) {
        final byte[] errorTextBytes = errorTextBytes();
        out
            .writeByte(protocolVersion.getValue())
            .writeByte(PDU_TYPE)
            .writeShort(0)
            .writeInt(length())
            .writeBytes(causingPdu)
            .writeInt(errorTextBytes.length)
            .writeBytes(errorTextBytes);
    }
}
