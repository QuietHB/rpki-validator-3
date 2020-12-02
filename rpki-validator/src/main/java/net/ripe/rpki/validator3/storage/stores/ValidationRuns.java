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

import net.ripe.rpki.validator3.api.Paging;
import net.ripe.rpki.validator3.api.SearchTerm;
import net.ripe.rpki.validator3.api.Sorting;
import net.ripe.rpki.validator3.api.util.InstantWithoutNanos;
import net.ripe.rpki.validator3.storage.Tx;
import net.ripe.rpki.validator3.storage.data.Key;
import net.ripe.rpki.validator3.storage.data.RpkiObject;
import net.ripe.rpki.validator3.storage.data.RpkiRepository;
import net.ripe.rpki.validator3.storage.data.TrustAnchor;
import net.ripe.rpki.validator3.storage.data.validation.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface ValidationRuns {
    <T extends ValidationRun> T add(Tx.Write tx, T validationRun);

    <T extends ValidationRun> void update(Tx.Write tx, T validationRun);

    <T extends ValidationRun> Optional<T> get(Tx.Read tx, Class<T> type, long id);

    <T extends ValidationRun> List<T> findAll(Tx.Read tx);

    <T extends ValidationRun> List<T> findAll(Tx.Read tx, Class<T> type);

    <T extends ValidationRun> List<T> findLatestSuccessful(Tx.Read tx, Class<T> type);

    Optional<CertificateTreeValidationRun> findLatestSuccessfulCaTreeValidationRun(Tx.Read tx, TrustAnchor trustAnchor);

    Optional<TrustAnchorValidationRun> findLatestCompletedForTrustAnchor(Tx.Read tx, TrustAnchor trustAnchor);

    Optional<CertificateTreeValidationRun> findLatestCompletedCaTreeValidationRun(Tx.Read tx, TrustAnchor trustAnchor);

    int removeOldValidationRuns(Tx.Write tx, InstantWithoutNanos completedBefore);

    Stream<ValidationCheck> findValidationChecksForValidationRun(Tx.Read tx, long validationRunId, Paging paging, SearchTerm searchTerm, Sorting sorting);

    int countValidationChecksForValidationRun(Tx.Read tx, long validationRunId, SearchTerm searchTerm);

    void associate(Tx.Write writeTx, RpkiRepositoryValidationRun validationRun, RpkiObject o);

    void associate(Tx.Write writeTx, RpkiRepositoryValidationRun validationRun, RpkiRepository r);

    void associateRpkiObjectKey(Tx.Write tx, CertificateTreeValidationRun validationRun, Key rpkiObjectKey);

    Set<Key> findAssociatedPks(Tx.Read tx, CertificateTreeValidationRun validationRun);

    Stream<Pair<CertificateTreeValidationRun, RpkiObject>> findCurrentlyValidated(Tx.Read tx, RpkiObject.Type cer);

    void clear(Tx.Write tx);

    int getObjectCount(Tx.Read tx, ValidationRun validationRun);

    int removeOrphanValidationRunAssociations(Tx.Write tx);
}
