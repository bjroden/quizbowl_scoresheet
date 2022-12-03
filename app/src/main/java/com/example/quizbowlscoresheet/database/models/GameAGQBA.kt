package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class GameAGQBA(
    @Embedded
    val game: Game,

    @Relation(
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val tossups: List<Tossup>,

    @Relation(
        entity = BonusCategoryInfo::class,
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val bonusCategories: List<BonusCategory>,

    @Relation(
        entity= LightningCategoryInfo::class,
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val lightningCategories: List<LightningCategory>,

    @Relation(
        parentColumn = "team1Id",
        entityColumn = "id"
    )
    val team1: Team?,

    @Relation(
        parentColumn = "team2Id",
        entityColumn = "id"
    )
    val team2: Team?
) {
    val round1Tossups by lazy { tossups.filter { it.roundNumber == 1 } }
    val round2Tossups by lazy { tossups.filter { it.roundNumber == 2 } }
    val round4Tossups by lazy { tossups.filter { it.roundNumber == 4 } }

    val team1LightningRound by lazy { lightningCategories.find { it.categoryInfo.team == TeamAnswered.TEAM1 } }
    val team2LightningRound by lazy { lightningCategories.find { it.categoryInfo.team == TeamAnswered.TEAM2 } }

    val team1Round1Score by lazy { round1Score(TeamAnswered.TEAM1) }
    val team1Round2Score by lazy { round2Score(TeamAnswered.TEAM1) }
    val team1Round4Score by lazy { round4Score(TeamAnswered.TEAM1) }

    val team2Round1Score by lazy { round1Score(TeamAnswered.TEAM2) }
    val team2Round2Score by lazy { round2Score(TeamAnswered.TEAM2) }
    val team2Round4Score by lazy { round4Score(TeamAnswered.TEAM2) }

    fun roundNTossups(n: Int) = tossups.filter { it.roundNumber == n }

    fun round1Score(team: TeamAnswered) = 10 * round1Tossups.count { it.team == team }
    fun round2Score(team: TeamAnswered): Int {
        val tossupsScore = 10 * round2Tossups.count { it.team == team }
        val bonusScore = 5 * bonusCategories.filter { it.categoryInfo.team == team }
            .sumOf { it.bonusQuestions.count { q -> q.answered } }
        return tossupsScore + bonusScore
    }
    fun round4Score(team: TeamAnswered) = 10 * round4Tossups.count { it.team == team }
}