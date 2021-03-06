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
package net.ripe.rpki.validator3.storage.stores;

import net.ripe.rpki.commons.crypto.cms.manifest.ManifestCms;
import net.ripe.rpki.validator3.api.util.InstantWithoutNanos;
import net.ripe.rpki.validator3.storage.Tx;
import net.ripe.rpki.validator3.storage.data.Key;
import net.ripe.rpki.validator3.storage.data.RpkiObject;

import java.util.*;
import java.util.stream.Stream;

public interface RpkiObjects extends GenericStore<RpkiObject> {
    Optional<RpkiObject> get(Tx.Read tx, Key key);

    void put(Tx.Write tx, RpkiObject rpkiObject);

    void put(Tx.Write tx, RpkiObject rpkiObject, String location);

    void delete(Tx.Write tx, RpkiObject o);

    void markReachable(Tx.Write tx, Key pk, InstantWithoutNanos i);

    void addLocation(Tx.Write tx, Key pk, String location);

    SortedSet<String> getLocations(Tx.Read tx, Key pk);

    void deleteLocation(Tx.Write tx, Key key, String uri);

    Optional<RpkiObject> findBySha256(Tx.Read tx, byte[] sha256);

    Optional<RpkiObject> findLatestMftByAKI(Tx.Read tx, byte[] authorityKeyIdentifier);

    long deleteUnreachableObjects(InstantWithoutNanos unreachableSince);

    Map<String, RpkiObject> findObjectsInManifest(Tx.Read tx, ManifestCms manifestCms);

    Stream<byte[]> streamObjects(Tx.Read tx, RpkiObject.Type type);

    Set<Key> getPkByType(Tx.Read tx, RpkiObject.Type type);

    void markReachable(Tx.Write tx, List<Key> rpkiObjectsKeys);
}
