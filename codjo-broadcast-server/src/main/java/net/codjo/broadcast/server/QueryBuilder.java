/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package net.codjo.broadcast.server;
import net.codjo.broadcast.common.columns.FileColumnGenerator;
/**
 * Interface pour la construction de la requ�te de selection des cours de la section.
 *
 * @author $Author: gonnot $
 * @version $Revision: 1.1.1.1 $
 */
interface QueryBuilder {
    /**
     * Construit la requ�te de selection pour les cours de la section.
     *
     * @param columns Les g�n�rateurs de colonnes
     *
     * @return La requ�te de s�lection
     */
    public String buildQuery(FileColumnGenerator[] columns);
}
