package ge.akikalia.rockpaperscissor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import java.util.*
import kotlin.random.Random.Default.nextInt
enum class Weapon(val value: Int) {
    ROCK(0),
    PAPER(1),
    SCISSORS(2);

    companion object {
        fun fromInt(value: Int) = Weapon.values().first { it.value == value }
    }
}

class MainActivity : AppCompatActivity() {
    val welcomeMessage: TextView = findViewById(R.id.startText)
    val imagePlayerOne: ImageView = findViewById(R.id.imageView)
    val imagePlayerTwo: ImageView = findViewById(R.id.imageView2)
    val rock: Button = findViewById(R.id.button)
    val paper: Button = findViewById(R.id.button2)
    val scissors: Button = findViewById(R.id.button3)
    var playerOneScore : TextView = findViewById(R.id.playerOneScore)
    var playerTwoScore :TextView = findViewById(R.id.playerTwoScore)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listener = View.OnClickListener{ view ->
            welcomeMessage.isVisible = false
            val playerOne = Weapon.fromInt((0..2).random())
            val playerTwo: Weapon
            if (view.getId() == rock.id)
                playerTwo = Weapon.ROCK
            else if (view.getId() == paper.id)
                playerTwo = Weapon.PAPER
            else
                playerTwo = Weapon.SCISSORS
            imagePlayerOne.setImageResource(getImageById(playerOne))
            imagePlayerTwo.setImageResource(getImageById(playerTwo))
            settleScore(playerOne, playerTwo)
            imagePlayerOne.isVisible = true
            imagePlayerTwo.isVisible = true

        }
        rock.setOnClickListener(listener)
        paper.setOnClickListener(listener)
        scissors.setOnClickListener(listener)
    }

    private fun getImageById(id: Weapon): Int{
        return when (id) {
            Weapon.ROCK -> R.drawable.rock
            Weapon.PAPER -> R.drawable.paper
            else -> R.drawable.scissors
        }
    }

    private fun settleScore(weaponP1: Weapon, weaponP2: Weapon){
        val winner = compareScore(weaponP1, weaponP2)
        if (winner == 1)
            setWinnerLoser(playerOneScore, playerTwoScore)
        else if (winner == 2)
            setWinnerLoser(playerOneScore, playerTwoScore)
        else{
            playerOneScore.setTextColor(ContextCompat.getColor(applicationContext, R.color.tie_score))
            playerTwoScore.setTextColor(ContextCompat.getColor(applicationContext, R.color.tie_score))
        }
    }

    private fun setWinnerLoser(winner: TextView, loser: TextView){
        val newScore = loser.text.toString().toInt() + 1
        winner.text = newScore.toString()
        winner.setTextColor(ContextCompat.getColor(applicationContext, R.color.winner_score))
        loser.setTextColor(ContextCompat.getColor(applicationContext, R.color.loser_score))
    }

    private fun compareScore(weaponP1: Weapon, weaponP2: Weapon): Int{
        if (weaponP2 == weaponP1){
            return 0
        }else if (weaponP2 == Weapon.ROCK && weaponP1 == Weapon.PAPER){
            return 1
        }else if (weaponP2 == Weapon.PAPER && weaponP1 == Weapon.SCISSORS){
            return 1
        }else if (weaponP2 == Weapon.SCISSORS && weaponP1 == Weapon.ROCK){
            return 1
        }else{
            return 2
        }
    }
}