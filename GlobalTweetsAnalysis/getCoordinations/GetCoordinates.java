package getCoordinations;

import gatherTweets.ImportToDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.TimeZone;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GetCoordinates {

	private static String timeZoneID = "GMT-5"; //Australia/Melbourne GMT-5
	private static String City = "Boston"; //Melbourne Boston
	
	private static File Tweets = new File("/mnt/" + City + "_nodup.txt");
	private static File coordinates_total_positive = new File("./Coordinate_" + City + "_total_positive.txt");
	private static File coordinates_total_negative = new File("./Coordinate_" + City + "_total_negative.txt");
	private static File coordinates_total_neutral = new File("./Coordinate_" + City + "_total_neutral.txt");
	
	private static File[] coordinates_hour_positive = new File[24]; 
	private static File[] coordinates_hour_negative = new File[24];
	private static File[] coordinates_hour_neutral = new File[24];
	
	private static File[] coordinates_day_positive = new File[7]; 
	private static File[] coordinates_day_negative = new File[7];
	private static File[] coordinates_day_neutral = new File[7];
	
	private static File[] coordinates_date_positive = new File[30]; 
	private static File[] coordinates_date_negative = new File[30];
	private static File[] coordinates_date_neutral = new File[30];
	
	private static FileReader fr_tweets;
	private static FileWriter fw_coordinates_total_positive, fw_coordinates_total_negative, fw_coordinates_total_neutral;
	
	private static FileWriter[] fw_coordinates_hour_positive = new FileWriter[24];
	private static FileWriter[] fw_coordinates_hour_negative = new FileWriter[24];
	private static FileWriter[] fw_coordinates_hour_neutral = new FileWriter[24];
	
	private static FileWriter[] fw_coordinates_day_positive = new FileWriter[7];
	private static FileWriter[] fw_coordinates_day_negative = new FileWriter[7];
	private static FileWriter[] fw_coordinates_day_neutral = new FileWriter[7];
	
	private static FileWriter[] fw_coordinates_date_positive = new FileWriter[30];
	private static FileWriter[] fw_coordinates_date_negative = new FileWriter[30];
	private static FileWriter[] fw_coordinates_date_neutral = new FileWriter[30];
	
	private static BufferedReader br_tweets;
	private static BufferedWriter bw_coordinates_total_positive, bw_coordinates_total_negative, bw_coordinates_total_neutral;
	
	private static BufferedWriter[] bw_coordinates_hour_positive = new BufferedWriter[24];
	private static BufferedWriter[] bw_coordinates_hour_negative = new BufferedWriter[24];
	private static BufferedWriter[] bw_coordinates_hour_neutral = new BufferedWriter[24];
	
	private static BufferedWriter[] bw_coordinates_day_positive = new BufferedWriter[7];
	private static BufferedWriter[] bw_coordinates_day_negative = new BufferedWriter[7];
	private static BufferedWriter[] bw_coordinates_day_neutral = new BufferedWriter[7];
	
	private static BufferedWriter[] bw_coordinates_date_positive = new BufferedWriter[30];
	private static BufferedWriter[] bw_coordinates_date_negative = new BufferedWriter[30];
	private static BufferedWriter[] bw_coordinates_date_neutral = new BufferedWriter[30];
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
	
	private static String keywords[] = {"abandon","abandoned","abandons","abducted","abduction","abductions","abhor","abhorred","abhorrent","abhors","abilities","ability","aboard","absentee","absentees","absolve","absolved","absolves","absolving","absorbed","abuse","abused","abuses","abusive","accept","accepted","accepting","accepts","accident","accidental","accidentally","accidents","accomplish","accomplished","accomplishes","accusation","accusations","accuse","accused","accuses","accusing","ache","achievable","aching","acquit","acquits","acquitted","acquitting","acrimonious","active","adequate","admire","admired","admires","admiring","admit","admits","admitted","admonish","admonished","adopt","adopts","adorable","adore","adored","adores","advanced","advantage","advantages","adventure","adventures","adventurous","affected","affection","affectionate","afflicted","affronted","afraid","aggravate","aggravated","aggravates","aggravating","aggression","aggressions","aggressive","aghast","agog","agonise","agonised","agonises","agonising","agonize","agonized","agonizes","agonizing","agree","agreeable","agreed","agreement","agrees","alarm","alarmed","alarmist","alarmists","alas","alert","alienation","alive","allergic","allow","alone","amaze","amazed","amazes","amazing","ambitious","ambivalent","amuse","amused","amusement","amusements","anger","angers","angry","anguish","anguished","animosity","annoy","annoyance","annoyed","annoying","annoys","antagonistic","anti","anticipation","anxiety","anxious","apathetic","apathy","apeshit","apocalyptic","apologise","apologised","apologises","apologising","apologize","apologized","apologizes","apologizing","apology","appalled","appalling","appease","appeased","appeases","appeasing","applaud","applauded","applauding","applauds","applause","appreciate","appreciated","appreciates","appreciating","appreciation","apprehensive","approval","approved","approves","ardent","arrest","arrested","arrests","arrogant","ashame","ashamed","ass","assassination","assassinations","asset","assets","assfucking","asshole","astonished","astound","astounded","astounding","astoundingly","astounds","attack","attacked","attacking","attacks","attract","attracted","attracting","attraction","attractions","attracts","audacious","authority","avert","averted","averts","avid","avoid","avoided","avoids","await","awaited","awaits","award","awarded","awards","awesome","awful","awkward","axe","axed","backed","backing","backs","bad","badass","badly","bailout","bamboozle","bamboozled","bamboozles","ban","banish","bankrupt","bankster","banned","bargain","barrier","bastard","bastards","battle","battles","beaten","beatific","beating","beauties","beautiful","beautifully","beautify","belittle","belittled","beloved","benefit","benefits","benefitted","benefitting","bereave","bereaved","bereaves","bereaving","best","betray","betrayal","betrayed","betraying","betrays","better","bias","biased","big","bitch","bitches","bitter","bitterly","bizarre","blah","blame","blamed","blames","blaming","bless","blesses","blessing","blind","bliss","blissful","blithe","block","blockbuster","blocked","blocking","blocks","bloody","blurry","boastful","bold","boldly","bomb","boost","boosted","boosting","boosts","bore","bored","boring","bother","bothered","bothers","bothersome","boycott","boycotted","boycotting","boycotts","brainwashing","brave","breakthrough","breathtaking","bribe","bright","brightest","brightness","brilliant","brisk","broke","broken","brooding","bullied","bullshit","bully","bullying","bummer","buoyant","burden","burdened","burdening","burdens","calm","calmed","calming","calms","can't stand","cancel","cancelled","cancelling","cancels","cancer","capable","captivated","care","carefree","careful","carefully","careless","cares","cashing in","casualty","catastrophe","catastrophic","cautious","celebrate","celebrated","celebrates","celebrating","censor","censored","censors","certain","chagrin","chagrined","challenge","chance","chances","chaos","chaotic","charged","charges","charm","charming","charmless","chastise","chastised","chastises","chastising","cheat","cheated","cheater","cheaters","cheats","cheer","cheered","cheerful","cheering","cheerless","cheers","cheery","cherish","cherished","cherishes","cherishing","chic","childish","chilling","choke","choked","chokes","choking","clarifies","clarity","clash","classy","clean","cleaner","clear","cleared","clearly","clears","clever","clouded","clueless","cock","cocksucker","cocksuckers","cocky","coerced","collapse","collapsed","collapses","collapsing","collide","collides","colliding","collision","collisions","colluding","combat","combats","comedy","comfort","comfortable","comforting","comforts","commend","commended","commit","commitment","commits","committed","committing","compassionate","compelled","competent","competitive","complacent","complain","complained","complains","comprehensive","conciliate","conciliated","conciliates","conciliating","condemn","condemnation","condemned","condemns","confidence","confident","conflict","conflicting","conflictive","conflicts","confuse","confused","confusing","congrats","congratulate","congratulation","congratulations","consent","consents","consolable","conspiracy","constrained","contagion","contagions","contagious","contempt","contemptuous","contemptuously","contend","contender","contending","contentious","contestable","controversial","controversially","convince","convinced","convinces","convivial","cool","cool stuff","cornered","corpse","costly","courage","courageous","courteous","courtesy","cover-up","coward","cowardly","coziness","cramp","crap","crash","crazier","craziest","crazy","creative","crestfallen","cried","cries","crime","criminal","criminals","crisis","critic","criticism","criticize","criticized","criticizes","criticizing","critics","cruel","cruelty","crush","crushed","crushes","crushing","cry","crying","cunt","curious","curse","cut","cute","cuts","cutting","cynic","cynical","cynicism","damage","damages","damn","damned","damnit","danger","daredevil","daring","darkest","darkness","dauntless","dead","deadlock","deafening","dear","dearly","death","debonair","debt","deceit","deceitful","deceive","deceived","deceives","deceiving","deception","decisive","dedicated","defeated","defect","defects","defender","defenders","defenseless","defer","deferring","defiant","deficit","degrade","degraded","degrades","dehumanize","dehumanized","dehumanizes","dehumanizing","deject","dejected","dejecting","dejects","delay","delayed","delight","delighted","delighting","delights","demand","demanded","demanding","demands","demonstration","demoralized","denied","denier","deniers","denies","denounce","denounces","deny","denying","depressed","depressing","derail","derailed","derails","deride","derided","derides","deriding","derision","desirable","desire","desired","desirous","despair","despairing","despairs","desperate","desperately","despondent","destroy","destroyed","destroying","destroys","destruction","destructive","detached","detain","detained","detention","determined","devastate","devastated","devastating","devoted","diamond","dick","dickhead","die","died","difficult","diffident","dilemma","dipshit","dire","direful","dirt","dirtier","dirtiest","dirty","disabling","disadvantage","disadvantaged","disappear","disappeared","disappears","disappoint","disappointed","disappointing","disappointment","disappointments","disappoints","disaster","disasters","disastrous","disbelieve","discard","discarded","discarding","discards","disconsolate","disconsolation","discontented","discord","discounted","discouraged","discredited","disdain","disgrace","disgraced","disguise","disguised","disguises","disguising","disgust","disgusted","disgusting","disheartened","dishonest","disillusioned","disinclined","disjointed","dislike","dismal","dismayed","disorder","disorganized","disoriented","disparage","disparaged","disparages","disparaging","displeased","dispute","disputed","disputes","disputing","disqualified","disquiet","disregard","disregarded","disregarding","disregards","disrespect","disrespected","disruption","disruptions","disruptive","dissatisfied","distort","distorted","distorting","distorts","distract","distracted","distraction","distracts","distress","distressed","distresses","distressing","distrust","distrustful","disturb","disturbed","disturbing","disturbs","dithering","dizzy","dodging","dodgy","dolorous","dont like","doom","doomed","doubt","doubted","doubtful","doubting","doubts","douche","douchebag","downcast","downhearted","downside","drag","dragged","drags","drained","dread","dreaded","dreadful","dreading","dream","dreams","dreary","droopy","drop","drown","drowned","drowns","drunk","dubious","dud","dull","dumb","dumbass","dump","dumped","dumps","dupe","duped","dysfunction","eager","earnest","ease","easy","ecstatic","eerie","eery","effective","effectively","elated","elation","elegant","elegantly","embarrass","embarrassed","embarrasses","embarrassing","embarrassment","embittered","embrace","emergency","empathetic","emptiness","empty","enchanted","encourage","encouraged","encouragement","encourages","endorse","endorsed","endorsement","endorses","enemies","enemy","energetic","engage","engages","engrossed","enjoy","enjoying","enjoys","enlighten","enlightened","enlightening","enlightens","ennui","enrage","enraged","enrages","enraging","enrapture","enslave","enslaved","enslaves","ensure","ensuring","enterprising","entertaining","enthral","enthusiastic","entitled","entrusted","envies","envious","envy","envying","erroneous","error","errors","escape","escapes","escaping","esteemed","ethical","euphoria","euphoric","eviction","evil","exaggerate","exaggerated","exaggerates","exaggerating","exasperated","excellence","excellent","excite","excited","excitement","exciting","exclude","excluded","exclusion","exclusive","excuse","exempt","exhausted","exhilarated","exhilarates","exhilarating","exonerate","exonerated","exonerates","exonerating","expand","expands","expel","expelled","expelling","expels","exploit","exploited","exploiting","exploits","exploration","explorations","expose","exposed","exposes","exposing","extend","extends","exuberant","exultant","exultantly","fabulous","fad","fag","faggot","faggots","fail","failed","failing","fails","failure","failures","fainthearted","fair","faith","faithful","fake","fakes","faking","fallen","falling","falsified","falsify","fame","fan","fantastic","farce","fascinate","fascinated","fascinates","fascinating","fascist","fascists","fatalities","fatality","fatigue","fatigued","fatigues","fatiguing","favor","favored","favorite","favorited","favorites","favors","fear","fearful","fearing","fearless","fearsome","fed up","feeble","feeling","felonies","felony","fervent","fervid","festive","fiasco","fidgety","fight","fine","fire","fired","firing","fit","fitness","flagship","flees","flop","flops","flu","flustered","focused","fond","fondness","fool","foolish","fools","forced","foreclosure","foreclosures","forget","forgetful","forgive","forgiving","forgotten","fortunate","frantic","fraud","frauds","fraudster","fraudsters","fraudulence","fraudulent","free","freedom","frenzy","fresh","friendly","fright","frightened","frightening","frikin","frisky","frowning","frustrate","frustrated","frustrates","frustrating","frustration","ftw","fuck","fucked","fucker","fuckers","fuckface","fuckhead","fucking","fucktard","fud","fuked","fuking","fulfill","fulfilled","fulfills","fuming","fun","funeral","funerals","funky","funnier","funny","furious","futile","gag","gagged","gain","gained","gaining","gains","gallant","gallantly","gallantry","generous","genial","ghost","giddy","gift","glad","glamorous","glamourous","glee","gleeful","gloom","gloomy","glorious","glory","glum","god","goddamn","godsend","good","goodness","grace","gracious","grand","grant","granted","granting","grants","grateful","gratification","grave","gray","great","greater","greatest","greed","greedy","green wash","green washing","greenwash","greenwasher","greenwashers","greenwashing","greet","greeted","greeting","greetings","greets","grey","grief","grieved","gross","growing","growth","guarantee","guilt","guilty","gullibility","gullible","gun","ha","hacked","haha","hahaha","hahahah","hail","hailed","hapless","haplessness","happiness","happy","hard","hardier","hardship","hardy","harm","harmed","harmful","harming","harms","harried","harsh","harsher","harshest","hate","hated","haters","hates","hating","haunt","haunted","haunting","haunts","havoc","healthy","heartbreaking","heartbroken","heartfelt","heaven","heavenly","heavyhearted","hell","help","helpful","helping","helpless","helps","hero","heroes","heroic","hesitant","hesitate","hid","hide","hides","hiding","highlight","hilarious","hindrance","hoax","homesick","honest","honor","honored","honoring","honour","honoured","honouring","hooligan","hooliganism","hooligans","hope","hopeful","hopefully","hopeless","hopelessness","hopes","hoping","horrendous","horrible","horrific","horrified","hostile","huckster","hug","huge","hugs","humerous","humiliated","humiliation","humor","humorous","humour","humourous","hunger","hurrah","hurt","hurting","hurts","hypocritical","hysteria","hysterical","hysterics","idiot","idiotic","ignorance","ignorant","ignore","ignored","ignores","ill","illegal","illiteracy","illness","illnesses","imbecile","immobilized","immortal","immune","impatient","imperfect","importance","important","impose","imposed","imposes","imposing","impotent","impress","impressed","impresses","impressive","imprisoned","improve","improved","improvement","improves","improving","inability","inaction","inadequate","incapable","incapacitated","incensed","incompetence","incompetent","inconsiderate","inconvenience","inconvenient","increase","increased","indecisive","indestructible","indifference","indifferent","indignant","indignation","indoctrinate","indoctrinated","indoctrinates","indoctrinating","ineffective","ineffectively","infatuated","infatuation","infected","inferior","inflamed","influential","infringement","infuriate","infuriated","infuriates","infuriating","inhibit","injured","injury","injustice","innovate","innovates","innovation","innovative","inquisition","inquisitive","insane","insanity","insecure","insensitive","insensitivity","insignificant","insipid","inspiration","inspirational","inspire","inspired","inspires","inspiring","insult","insulted","insulting","insults","intact","integrity","intelligent","intense","interest","interested","interesting","interests","interrogated","interrupt","interrupted","interrupting","interruption","interrupts","intimidate","intimidated","intimidates","intimidating","intimidation","intricate","intrigues","invincible","invite","inviting","invulnerable","irate","ironic","irony","irrational","irresistible","irresolute","irresponsible","irreversible","irritate","irritated","irritating","isolated","itchy","jackass","jackasses","jailed","jaunty","jealous","jeopardy","jerk","jesus","jewel","jewels","jocular","join","joke","jokes","jolly","jovial","joy","joyful","joyfully","joyless","joyous","jubilant","jumpy","justice","justifiably","justified","keen","kill","killed","killing","kills","kind","kinder","kiss","kudos","lack","lackadaisical","lag","lagged","lagging","lags","lame","landmark","laugh","laughed","laughing","laughs","laughting","launched","lawl","lawsuit","lawsuits","lazy","leak","leaked","leave","legal","legally","lenient","lethargic","lethargy","liar","liars","libelous","lied","lifesaver","lighthearted","like","liked","likes","limitation","limited","limits","litigation","litigious","lively","livid","lmao","lmfao","loathe","loathed","loathes","loathing","lobby","lobbying","lol","lonely","lonesome","longing","loom","loomed","looming","looms","loose","looses","loser","losing","loss","lost","lovable","love","loved","lovelies","lovely","loving","lowest","loyal","loyalty","luck","luckily","lucky","lugubrious","lunatic","lunatics","lurk","lurking","lurks","mad","maddening","made-up","madly","madness","mandatory","manipulated","manipulating","manipulation","marvel","marvelous","marvels","masterpiece","masterpieces","matter","matters","mature","meaningful","meaningless","medal","mediocrity","meditative","melancholy","menace","menaced","mercy","merry","mess","messed","messing up","methodical","mindless","miracle","mirth","mirthful","mirthfully","misbehave","misbehaved","misbehaves","misbehaving","mischief","mischiefs","miserable","misery","misgiving","misinformation","misinformed","misinterpreted","misleading","misread","misreporting","misrepresentation","miss","missed","missing","mistake","mistaken","mistakes","mistaking","misunderstand","misunderstanding","misunderstands","misunderstood","moan","moaned","moaning","moans","mock","mocked","mocking","mocks","mongering","monopolize","monopolized","monopolizes","monopolizing","moody","mope","moping","moron","motherfucker","motherfucking","motivate","motivated","motivating","motivation","mourn","mourned","mournful","mourning","mourns","mumpish","murder","murderer","murdering","murderous","murders","myth","n00b","naive","nasty","natural","naive","needy","negative","negativity","neglect","neglected","neglecting","neglects","nerves","nervous","nervously","nice","nifty","niggas","nigger","noble","noisy","nonsense","noob","nosey","notorious","novel","numb","nuts","obliterate","obliterated","obnoxious","obscene","obsessed","obsolete","obstacle","obstacles","obstinate","odd","offend","offended","offender","offending","offends","offline","oks","ominous","once-in-a-lifetime","opportunities","opportunity","oppressed","oppressive","optimism","optimistic","optionless","outcry","outmaneuvered","outrage","outraged","outreach","outstanding","overjoyed","overload","overlooked","overreact","overreacted","overreaction","overreacts","oversell","overselling","oversells","oversimplification","oversimplified","oversimplifies","oversimplify","overstatement","overstatements","overweight","oxymoron","pain","pained","panic","panicked","panics","paradise","paradox","pardon","pardoned","pardoning","pardons","parley","passionate","passive","passively","pathetic","pay","peace","peaceful","peacefully","penalty","pensive","perfect","perfected","perfectly","perfects","peril","perjury","perpetrator","perpetrators","perplexed","persecute","persecuted","persecutes","persecuting","perturbed","pesky","pessimism","pessimistic","petrified","phobic","picturesque","pileup","pique","piqued","piss","pissed","pissing","piteous","pitied","pity","playful","pleasant","please","pleased","pleasure","poised","poison","poisoned","poisons","pollute","polluted","polluter","polluters","pollutes","poor","poorer","poorest","popular","positive","positively","possessive","postpone","postponed","postpones","postponing","poverty","powerful","powerless","praise","praised","praises","praising","pray","praying","prays","prblm","prblms","prepared","pressure","pressured","pretend","pretending","pretends","pretty","prevent","prevented","preventing","prevents","prick","prison","prisoner","prisoners","privileged","proactive","problem","problems","profiteer","progress","prominent","promise","promised","promises","promote","promoted","promotes","promoting","propaganda","prosecute","prosecuted","prosecutes","prosecution","prospect","prospects","prosperous","protect","protected","protects","protest","protesters","protesting","protests","proud","proudly","provoke","provoked","provokes","provoking","pseudoscience","punish","punished","punishes","punitive","pushy","puzzled","quaking","questionable","questioned","questioning","racism","racist","racists","rage","rageful","rainy","rant","ranter","ranters","rants","rape","rapist","rapture","raptured","raptures","rapturous","rash","ratified","reach","reached","reaches","reaching","reassure","reassured","reassures","reassuring","rebellion","recession","reckless","recommend","recommended","recommends","redeemed","refuse","refused","refusing","regret","regretful","regrets","regretted","regretting","reject","rejected","rejecting","rejects","rejoice","rejoiced","rejoices","rejoicing","relaxed","relentless","reliant","relieve","relieved","relieves","relieving","relishing","remarkable","remorse","repulse","repulsed","rescue","rescued","rescues","resentful","resign","resigned","resigning","resigns","resolute","resolve","resolved","resolves","resolving","respected","responsible","responsive","restful","restless","restore","restored","restores","restoring","restrict","restricted","restricting","restriction","restricts","retained","retard","retarded","retreat","revenge","revengeful","revered","revive","revives","reward","rewarded","rewarding","rewards","rich","ridiculous","rig","rigged","right direction","rigorous","rigorously","riot","riots","risk","risks","rob","robber","robed","robing","robs","robust","rofl","roflcopter","roflmao","romance","rotfl","rotflmfao","rotflol","ruin","ruined","ruining","ruins","sabotage","sad","sadden","saddened","sadly","safe","safely","safety","salient","sappy","sarcastic","satisfied","save","saved","scam","scams","scandal","scandalous","scandals","scapegoat","scapegoats","scare","scared","scary","sceptical","scold","scoop","scorn","scornful","scream","screamed","screaming","screams","screwed","screwed up","scumbag","secure","secured","secures","sedition","seditious","seduced","self-confident","self-deluded","selfish","selfishness","sentence","sentenced","sentences","sentencing","serene","severe","sexy","shaky","shame","shamed","shameful","share","shared","shares","shattered","shit","shithead","shitty","shock","shocked","shocking","shocks","shoot","short-sighted","short-sightedness","shortage","shortages","shrew","shy","sick","sigh","significance","significant","silencing","silly","sincere","sincerely","sincerest","sincerity","sinful","singleminded","skeptic","skeptical","skepticism","skeptics","slam","slash","slashed","slashes","slashing","slavery","sleeplessness","slick","slicker","slickest","sluggish","slut","smart","smarter","smartest","smear","smile","smiled","smiles","smiling","smog","sneaky","snub","snubbed","snubbing","snubs","sobering","solemn","solid","solidarity","solution","solutions","solve","solved","solves","solving","somber","some kind","son-of-a-bitch","soothe","soothed","soothing","sophisticated","sore","sorrow","sorrowful","sorry","spam","spammer","spammers","spamming","spark","sparkle","sparkles","sparkling","speculative","spirit","spirited","spiritless","spiteful","splendid","sprightly","squelched","stab","stabbed","stable","stabs","stall","stalled","stalling","stamina","stampede","startled","starve","starved","starves","starving","steadfast","steal","steals","stereotype","stereotyped","stifled","stimulate","stimulated","stimulates","stimulating","stingy","stolen","stop","stopped","stopping","stops","stout","straight","strange","strangely","strangled","strength","strengthen","strengthened","strengthening","strengthens","stressed","stressor","stressors","stricken","strike","strikers","strikes","strong","stronger","strongest","struck","struggle","struggled","struggles","struggling","stubborn","stuck","stunned","stunning","stupid","stupidly","suave","substantial","substantially","subversive","success","successful","suck","sucks","suffer","suffering","suffers","suicidal","suicide","suing","sulking","sulky","sullen","sunshine","super","superb","superior","support","supported","supporter","supporters","supporting","supportive","supports","survived","surviving","survivor","suspect","suspected","suspecting","suspects","suspend","suspended","suspicious","swear","swearing","swears","sweet","swift","swiftly","swindle","swindles","swindling","sympathetic","sympathy","tard","tears","tender","tense","tension","terrible","terribly","terrific","terrified","terror","terrorize","terrorized","terrorizes","thank","thankful","thanks","thorny","thoughtful","thoughtless","threat","threaten","threatened","threatening","threatens","threats","thrilled","thwart","thwarted","thwarting","thwarts","timid","timorous","tired","tits","tolerant","toothless","top","tops","torn","torture","tortured","tortures","torturing","totalitarian","totalitarianism","tout","touted","touting","touts","tragedy","tragic","tranquil","trap","trapped","trauma","traumatic","travesty","treason","treasonous","treasure","treasures","trembling","tremulous","tricked","trickery","triumph","triumphant","trouble","troubled","troubles","TRUE","trust","trusted","tumor","twat","ugly","unacceptable","unappreciated","unapproved","unaware","unbelievable","unbelieving","unbiased","uncertain","unclear","uncomfortable","unconcerned","unconfirmed","unconvinced","uncredited","undecided","underestimate","underestimated","underestimates","underestimating","undermine","undermined","undermines","undermining","undeserving","undesirable","uneasy","unemployment","unequal","unequaled","unethical","unfair","unfocused","unfulfilled","unhappy","unhealthy","unified","unimpressed","unintelligent","united","unjust","unlovable","unloved","unmatched","unmotivated","unprofessional","unresearched","unsatisfied","unsecured","unsettled","unsophisticated","unstable","unstoppable","unsupported","unsure","untarnished","unwanted","unworthy","upset","upsets","upsetting","uptight","urgent","useful","usefulness","useless","uselessness","vague","validate","validated","validates","validating","verdict","verdicts","vested","vexation","vexing","vibrant","vicious","victim","victimize","victimized","victimizes","victimizing","victims","vigilant","vile","vindicate","vindicated","vindicates","vindicating","violate","violated","violates","violating","violence","violent","virtuous","virulent","vision","visionary","visioning","visions","vitality","vitamin","vitriolic","vivacious","vociferous","vulnerability","vulnerable","walkout","walkouts","wanker","want","war","warfare","warm","warmth","warn","warned","warning","warnings","warns","waste","wasted","wasting","wavering","weak","weakness","wealth","wealthy","weary","weep","weeping","weird","welcome","welcomed","welcomes","whimsical","whitewash","whore","wicked","widowed","willingness","win","winner","winning","wins","winwin","wish","wishes","wishing","withdrawal","woebegone","woeful","won","wonderful","woo","woohoo","wooo","woow","worn","worried","worry","worrying","worse","worsen","worsened","worsening","worsens","worshiped","worst","worth","worthless","worthy","wow","wowow","wowww","wrathful","wreck","wrong","wronged","wtf","yeah","yearning","yeees","yes","youthful","yucky","yummy","zealot","zealots","zealous",":-)",":)",":o)",":]",":3",":c)",":>","=]","8)","=)",":}",":^)",":ã£)",":-D",":D","8-D","8D","x-D","xD","X-D","XD","=-D","=D","=-3","=3","B^D",":-))",">:[",":-(",":(",":-c",":c",":-<",":ã£C",":<",":-[",":[",":{",";(",":-||",":@",">:(",":'-(",":'(",":'-)",":')","D:<","D:","D8","D;","D=","DX","v.v","D-':",">:O",":-O",":O",":-o",":o","8-0","O_O","o-o","O_o","o_O","o_o","O-O",":*",":^*","( '}{' )",";-)",";)","*-)","*)",";-]",";]",";D",";^)",":-,",">:P",":-P",":P","X-P","x-p","xp","XP",":-p",":p","=p",":-Ãž",":Ãž",":Ã¾",":-Ã¾",":-b",":b","d:",">:\\",">:/",":-/",":-.",":/",":\\","=/","=\\",":L","=L",":S",">.<",":|",":-|",":$",":-X",":X",":-#",":#","O:-)","0:-3","0:03","0:-)","0:)","0;^)",">:)",">;)",">:-)","}:-)","}:)","3:-)","3:)","o/\\o","^5",">_>^ ^<_<","|;-)","|-O",":-&",":&","#-)","%-)","%)",":-###..",":###..","<:-|","à² _à² ","<*)))-{","><(((*>","><>","\\o/","*\\0/*","@}-;-'---","@>-->--","<3","</3"};
	private static int weights[] = {-2,-2,-2,-2,-2,-2,-3,-3,-3,-3,2,2,1,-1,-1,2,2,2,2,1,-3,-3,-3,-3,1,1,1,1,-2,-2,-2,-2,2,2,2,-2,-2,-2,-2,-2,-2,-2,1,-2,2,2,2,2,-3,1,1,3,3,3,3,-1,-1,-1,-2,-2,1,1,3,3,3,3,1,2,2,2,2,2,-1,3,3,-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,-3,-3,-3,-3,-3,-3,-3,-3,1,2,1,1,1,-2,-2,-2,-2,-1,-1,-2,1,-2,1,-2,2,2,2,4,2,-1,3,3,3,3,-3,-3,-3,-3,-3,-2,-2,-2,-2,-2,-2,-2,-1,1,-2,-2,-3,-3,-3,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-2,-2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,-2,2,2,2,1,-2,-3,-2,-2,-2,-2,-4,-3,-3,2,2,-4,-4,2,3,3,3,3,3,-1,-1,-1,-1,1,1,2,2,2,1,3,1,-1,-1,-1,2,-1,-1,-1,-1,-1,-1,3,3,3,4,-3,-2,-1,-1,1,2,1,-3,-3,-3,-2,-2,-2,-2,-2,-1,-3,-3,-2,2,-2,-5,-5,-1,-1,-2,3,-1,3,3,3,3,-2,-2,3,2,2,2,2,-2,-2,-2,-2,3,-3,-3,-3,-3,-3,2,-1,-2,1,-5,-5,-2,-2,-2,-2,-2,-2,-2,-2,2,2,3,-1,3,3,2,-1,3,-1,-1,-1,-3,-2,-2,2,2,-1,1,1,1,1,-2,-2,-3,-2,-2,-2,-2,-2,-2,-2,-2,-3,2,3,5,-3,1,2,1,4,2,-1,-1,-2,-2,-4,-2,-2,-2,2,-2,-2,-2,-2,2,2,2,2,-3,-1,-1,-1,-1,-1,1,3,2,1,2,2,-2,2,-2,-2,-3,-4,-1,3,3,3,3,-2,-2,-2,1,-2,-2,-1,2,2,-2,-2,-3,-2,3,3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,2,2,2,2,-2,2,3,2,2,2,2,2,-2,-1,-2,-2,-2,-2,2,2,-2,3,2,2,1,1,1,1,2,-1,-2,-5,-5,-5,-2,-2,-2,-2,-2,-2,-1,-1,-1,-2,-2,-3,-1,-1,1,2,2,2,2,2,2,1,2,1,1,1,2,1,2,2,-2,-2,-2,-2,2,2,2,2,2,-2,-2,-2,-2,2,2,-2,-2,-2,-2,-2,-2,-2,2,2,2,2,2,2,2,-3,-2,-2,-2,-1,-2,-2,-2,-1,-1,-1,-2,-2,-2,-2,1,1,1,2,1,3,-2,-1,-2,2,2,2,2,-3,-2,-2,2,-1,-3,-2,-2,-2,-2,2,-2,-2,-2,-3,-3,-3,-3,-2,-2,-2,-2,-2,-2,-2,-3,-3,-1,-2,-1,-1,-1,-2,-5,1,-1,-1,2,-1,-1,-2,-2,-2,-3,-3,-4,-4,-4,-2,2,2,-2,-1,2,-3,-2,-1,2,3,-2,2,-2,-3,-3,-3,-3,-3,-3,-3,1,2,-2,-3,-3,2,2,-2,-1,-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-1,3,3,3,3,-1,-1,-1,-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,1,2,2,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-1,-2,-2,-2,2,-2,-2,-2,3,1,-4,-4,-3,-3,-1,-2,-1,-3,-3,-3,-2,-2,-2,-2,-1,-2,-2,-1,-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-3,-2,-1,-1,-1,-1,-2,-2,-2,-2,-1,-2,-2,-2,-2,-2,-1,-1,-1,-1,-3,-3,-3,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-3,-3,-2,-2,-2,-2,-2,-1,-2,-2,-3,-2,-2,-2,-2,-1,-1,-1,-1,-1,-3,-3,-2,-2,-2,-1,-1,-1,-2,-2,-2,-3,-2,1,1,-2,-2,-1,-2,-2,-2,-2,-2,-2,-2,-3,-3,-1,-2,-1,-2,-2,-2,2,2,2,1,4,-2,-2,2,2,3,3,2,2,-2,-2,-2,-2,-2,-2,1,-2,2,-1,-1,2,2,2,2,2,2,2,2,2,-2,-2,2,1,1,1,2,2,2,2,2,2,2,-2,-2,-2,-2,-2,3,-2,-2,-2,1,1,1,2,3,3,1,2,-1,-2,-1,-1,-2,-2,-2,-1,-1,-1,2,2,3,4,-1,-3,-2,-2,-2,-2,2,3,3,3,3,3,3,-1,-2,-1,2,-1,-1,-2,3,3,3,2,2,2,2,1,1,-2,-2,-2,-2,-2,-2,-2,-2,1,1,-1,-1,-1,-1,1,1,4,3,3,4,-2,-3,-3,-3,-2,-2,-2,-2,-2,-2,-2,2,1,3,-3,-3,-3,-2,-1,-3,-3,1,3,4,-1,3,3,3,3,-2,-2,-3,-3,-2,-2,-2,-2,2,2,2,2,2,2,-2,-2,-2,2,-2,-3,-2,1,-3,-3,2,2,2,-3,-2,-1,2,-2,-2,-2,1,1,2,-1,-2,-2,-2,-2,2,2,2,-2,-2,-2,-1,-2,-2,-1,-2,1,1,-1,2,-1,-4,-4,-4,-4,-4,-4,1,2,-3,1,2,-2,-2,-3,-2,2,-1,-2,-2,-2,-2,-2,3,-4,-4,-4,-4,-4,-4,-4,-4,-3,-4,-4,2,2,2,-2,4,-1,-1,2,4,4,-3,2,-2,-2,2,2,2,2,3,3,3,2,3,-1,-2,2,3,3,3,3,3,-1,-2,2,2,-2,1,-3,4,3,3,1,3,3,1,1,1,1,3,2,-2,-1,3,3,3,-3,-2,-3,-3,-3,-3,-3,-3,1,1,1,2,1,-1,-2,-2,-2,1,2,1,-3,-3,-2,-2,-1,2,-1,3,3,3,2,2,-2,-2,3,3,-1,2,-2,2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-3,-3,-3,-3,-3,-1,-2,1,-1,-2,2,-3,-3,3,2,4,-2,-4,2,2,2,-2,2,2,2,3,-2,-2,-1,-1,-1,-1,2,2,-2,-2,-2,2,2,2,2,2,2,2,-2,-2,-2,2,2,2,-2,-2,2,2,-3,-3,-3,-3,-2,-2,2,1,2,3,-3,-3,2,2,2,2,-2,5,-2,-2,-2,-2,-3,-3,-3,-3,-3,-2,-2,-1,-2,-1,-2,-3,-2,-2,-2,-3,-1,2,1,-2,-2,2,2,-1,-1,-1,-1,-2,3,3,3,3,-2,2,2,2,2,2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,1,1,-2,2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,2,-2,-2,-2,2,-2,-2,-2,-2,-2,-1,-2,-2,-2,1,1,1,2,-2,2,-2,-2,-2,-2,-2,-2,-2,2,2,2,2,2,3,-2,-2,-2,-2,2,2,2,1,1,2,2,1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,1,2,1,1,2,-3,-1,-1,-1,2,-2,2,-1,-3,-3,-3,-1,-2,-4,-4,-2,2,-2,-2,-3,1,1,1,2,1,2,2,2,2,3,3,3,-2,3,3,-1,2,2,2,1,-3,-3,-3,-3,2,2,2,3,-2,-2,-1,-2,-2,-2,-2,2,1,1,1,1,1,1,3,-2,-2,-1,-1,-1,-1,1,1,1,-2,-2,-3,-3,-2,-2,4,1,2,2,2,-1,-1,-1,-1,-2,2,-2,4,4,-3,-3,-3,-3,-2,-2,3,-2,-2,-1,-1,-1,-1,-1,-3,-3,-3,-3,-3,-3,3,3,3,3,3,2,-1,3,3,3,3,3,-2,-3,-3,-1,-1,-1,-3,-3,-1,-3,-3,-1,-1,-1,-1,3,3,3,4,4,1,1,2,2,-2,3,-3,1,-2,-2,-2,2,3,-2,-2,-2,2,-2,4,3,3,3,-2,-2,-2,-2,-1,-1,-3,-2,-2,-2,-2,-2,-3,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-1,-1,-3,-5,-5,1,2,2,1,-2,-2,-2,-2,-2,-2,-2,-2,-3,-3,-2,-1,-2,-2,-3,1,-2,-2,-2,-2,-2,-2,-2,-2,-1,-2,-2,3,2,-5,-5,-1,-3,2,-1,-2,-2,-2,-2,-3,-2,2,-1,-3,-2,-2,-3,-2,2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,2,3,3,2,2,-2,-2,2,2,-2,-2,-2,-3,-3,2,5,4,-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-1,-2,-2,-3,-3,-3,3,-1,2,2,2,2,-1,2,-1,-1,-2,-1,2,2,2,-2,-1,3,2,3,2,-2,-3,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,-1,-2,-2,-4,-4,-3,-2,-1,-2,2,3,1,3,3,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,3,2,2,-2,-1,-1,-1,-1,-1,2,-2,3,3,3,3,1,1,1,-2,-2,1,-1,-2,-1,-1,-1,1,-1,-1,-1,-1,-5,-2,-2,-2,2,2,-2,-2,-2,2,2,1,1,1,1,1,1,1,-2,-1,-2,-1,-1,1,1,3,1,1,1,-2,-2,-2,-2,2,2,-1,-1,-1,-1,-3,-2,-2,-2,-2,-1,-2,-2,-2,-1,-1,-3,-3,-3,-2,-2,-1,-3,-3,-3,-3,-4,-4,2,2,2,4,-2,2,1,1,1,1,1,1,1,2,-2,-2,-2,2,2,2,2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-1,-1,-1,4,4,4,4,2,-1,2,1,2,1,2,2,2,-2,-1,-2,2,2,2,-2,-1,-1,-1,-1,2,2,2,2,2,2,2,2,2,-2,1,1,1,1,-2,-2,-2,-2,-2,-1,-2,-2,-1,-2,-2,2,2,2,2,2,2,2,2,-3,-1,-1,3,3,3,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,4,4,4,2,4,4,4,-2,-2,-2,-2,-2,-2,-2,-2,-2,1,1,1,1,-1,-2,2,2,2,-2,-2,-3,-3,-3,-2,-2,-2,-2,-2,-2,-2,3,-2,-2,-2,-2,-2,-2,-2,-3,-4,2,2,2,-2,-2,-1,2,-2,-3,-3,-2,-2,-2,-2,2,-2,3,-2,-2,-2,-2,1,1,1,-2,-4,-4,-3,-2,-2,-2,-2,-1,-2,-2,-2,-2,-4,-1,-2,-2,1,1,-1,-1,2,2,2,2,-3,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-3,-2,2,2,2,-2,-5,1,2,2,-2,2,2,2,2,-2,-1,-2,-2,-2,-2,1,-1,2,2,1,1,1,1,1,1,-2,0,-5,3,3,3,2,-1,-2,-2,-1,-2,-3,-3,-2,1,3,3,3,-2,1,2,-2,-2,3,2,-1,-2,-2,2,-2,-2,-2,-2,2,-2,-2,-2,-2,-2,-2,2,-2,-2,-2,-2,-1,1,1,1,2,-2,-2,-1,-1,-1,-1,2,1,-1,-1,-2,2,2,2,2,2,-2,-2,-2,-2,-1,-2,-1,2,2,2,-1,-2,-2,-2,-2,-2,-2,-2,4,-2,-2,2,1,1,-2,2,3,-3,-3,-2,-2,-2,-2,-2,-2,-2,-2,-2,2,3,5,2,2,2,1,1,1,2,2,2,2,2,-1,-1,-1,-1,-1,-1,-2,-2,-2,-2,2,2,2,-3,-3,-3,2,2,-2,-2,2,-2,-1,-3,-3,4,-3,-3,-3,-3,-3,2,2,2,-2,2,-2,-2,-2,-2,-2,-2,-2,5,-2,-2,-2,-2,-2,-2,-2,-2,2,-2,2,2,-2,-4,-4,-4,-4,-2,-2,-2,-2,-2,-2,-2,-2,2,-1,-2,-3,-3,-2,-3,-3,2,2,-2,-2,-2,-2,4,4,-2,-2,-2,2,1,2,-2,-5,-3,-2,-2,-2,-2,-1,-1,2,-1,-1,-2,-2,-1,-1,-1,-1,-1,-1,-1,-1,-2,-2,-2,-2,-2,-2,-2,-2,-1,2,-2,-2,-2,-2,-2,-2,1,-2,-2,1,-2,-2,-2,1,-2,-2,-2,-2,-2,-1,-2,-2,2,-2,-1,2,-2,-2,-2,-2,-2,-2,-1,2,2,-2,-2,-2,1,1,1,1,-1,-1,1,-2,-2,3,-2,-3,-3,-3,-3,-3,-3,3,-3,2,2,2,2,-2,-2,-2,-2,-3,-3,2,-2,1,3,1,1,3,1,-3,3,-1,-2,-2,-2,-2,-3,1,-2,-2,1,2,-2,-2,-3,-3,-2,-1,-2,-2,-1,-2,-2,3,2,-2,-2,-2,-2,2,2,2,1,-3,-4,-2,-1,2,4,4,4,4,3,1,1,1,-3,-2,-3,3,4,3,3,4,4,-1,-3,-3,-3,-3,-3,-3,-3,-3,3,-3,2,-2,2,4,4,4,-3,-2,-2,-2,-4,1,1,2,1,2,-2,3,-2,-2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,4,4,5,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-2,-5,-5,-5,-5,-5,5,5,-5,-5,-5,-5,-5,-5,-5,-5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,5,5,5,-2,-2,-2,-2,-2,-2,-2,-2,-2,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-1,-1,-4,-2,-2,-2,-2,4,4,4,4,4,4,-2,-2,-2,-4,-4,-4,-4,5,5,5,4,4,2,2,5,-4,-4,-4,-4,-2,-4,1,1,1,5,5,5,5,-4,-4};
	private static String denials[] = {"dont","doesnt","wont","aint","not","nobody","nothing","none","neither","never","seldom","hardly","barely","rare"};  
    	
	public static int sentimentAnalysis(String targetString)
	{
		int score = 0, total_score = 0;
		String substrings[] = targetString.split(",|;|\\.|!|\\?");
        for(int i=0;i<substrings.length;i++)
		{
			score = 0;
			for(int j=0;j<keywords.length;j++)
			{
				if(substrings[i].indexOf(keywords[j])>-1)
				{
					score += weights[j];
				}
			}
	
			for(int j=0;j<denials.length;j++)
			{
				if(substrings[i].indexOf(denials[j])>-1)
				{
					score *= -1;
				}	
			}
			total_score += score;
		}
		
		return total_score;
	}
	
	public static void main(String[] args) 
	{
		ArrayList<Double> latitude_total_positive = new ArrayList<Double>();
		ArrayList<Double> latitude_total_negative = new ArrayList<Double>();
		ArrayList<Double> latitude_total_neutral = new ArrayList<Double>();
		
		ArrayList<Double> longitude_total_positive = new ArrayList<Double>();
		ArrayList<Double> longitude_total_negative = new ArrayList<Double>();
		ArrayList<Double> longitude_total_neutral = new ArrayList<Double>();
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		
		try
		{
			fr_tweets = new FileReader(Tweets.getAbsoluteFile());
			br_tweets = new BufferedReader(fr_tweets);
			
			fw_coordinates_total_positive = new FileWriter(coordinates_total_positive.getAbsoluteFile());
			bw_coordinates_total_positive = new BufferedWriter(fw_coordinates_total_positive);
			
			fw_coordinates_total_negative = new FileWriter(coordinates_total_negative.getAbsoluteFile());
			bw_coordinates_total_negative = new BufferedWriter(fw_coordinates_total_negative);
			
			fw_coordinates_total_neutral = new FileWriter(coordinates_total_neutral.getAbsoluteFile());
			bw_coordinates_total_neutral = new BufferedWriter(fw_coordinates_total_neutral);
			
			for(int i=0;i<24;i++)
			{
				coordinates_hour_positive[i] = new File("./by hour/Coordinate_" + City + "_hour_" + i +"_positive.txt");
				coordinates_hour_negative[i] = new File("./by hour/Coordinate_" + City + "_hour_" + i +"_negative.txt");
				coordinates_hour_neutral[i] = new File("./by hour/Coordinate_" + City + "_hour_" + i +"_neutral.txt");
				
				fw_coordinates_hour_positive[i] = new FileWriter(coordinates_hour_positive[i].getAbsoluteFile());
				fw_coordinates_hour_negative[i] = new FileWriter(coordinates_hour_negative[i].getAbsoluteFile());
				fw_coordinates_hour_neutral[i] = new FileWriter(coordinates_hour_neutral[i].getAbsoluteFile());
				
				bw_coordinates_hour_positive[i] = new BufferedWriter(fw_coordinates_hour_positive[i]);
				bw_coordinates_hour_negative[i] = new BufferedWriter(fw_coordinates_hour_negative[i]);
				bw_coordinates_hour_neutral[i] = new BufferedWriter(fw_coordinates_hour_neutral[i]);		
			}
			
			for(int i=0;i<7;i++)
			{
				coordinates_day_positive[i] = new File("./by day/Coordinate_" + City + "_day_" + (i+1) +"_positive.txt");
				coordinates_day_negative[i] = new File("./by day/Coordinate_" + City + "_day_" + (i+1) +"_negative.txt");
				coordinates_day_neutral[i] = new File("./by day/Coordinate_" + City + "_day_" + (i+1) +"_neutral.txt");
				
				fw_coordinates_day_positive[i] = new FileWriter(coordinates_day_positive[i].getAbsoluteFile());
				fw_coordinates_day_negative[i] = new FileWriter(coordinates_day_negative[i].getAbsoluteFile());
				fw_coordinates_day_neutral[i] = new FileWriter(coordinates_day_neutral[i].getAbsoluteFile());
				
				bw_coordinates_day_positive[i] = new BufferedWriter(fw_coordinates_day_positive[i]);
				bw_coordinates_day_negative[i] = new BufferedWriter(fw_coordinates_day_negative[i]);
				bw_coordinates_day_neutral[i] = new BufferedWriter(fw_coordinates_day_neutral[i]);		
			}
			
			for(int i=0;i<30;i++)
			{
				coordinates_date_positive[i] = new File("./by date/Coordinate_" + City + "_date_" + (i+1) +"_positive.txt");
				coordinates_date_negative[i] = new File("./by date/Coordinate_" + City + "_date_" + (i+1) +"_negative.txt");
				coordinates_date_neutral[i] = new File("./by date/Coordinate_" + City + "_date_" + (i+1) +"_neutral.txt");
				
				fw_coordinates_date_positive[i] = new FileWriter(coordinates_date_positive[i].getAbsoluteFile());
				fw_coordinates_date_negative[i] = new FileWriter(coordinates_date_negative[i].getAbsoluteFile());
				fw_coordinates_date_neutral[i] = new FileWriter(coordinates_date_neutral[i].getAbsoluteFile());
				
				bw_coordinates_date_positive[i] = new BufferedWriter(fw_coordinates_date_positive[i]);
				bw_coordinates_date_negative[i] = new BufferedWriter(fw_coordinates_date_negative[i]);
				bw_coordinates_date_neutral[i] = new BufferedWriter(fw_coordinates_date_neutral[i]);		
			}
			
			String sCurrentLine;
			int score = 0;
			
			while((sCurrentLine = br_tweets.readLine()) != null)
			{
				if(!ImportToDB.isJSONValid(sCurrentLine))
				{
					continue;
				}
				
				try
				{
					JsonElement status = (new JsonParser()).parse(sCurrentLine);
					for(Entry<String, JsonElement> entry: status.getAsJsonObject().entrySet())
					{
						if(entry.getKey().equals("text"))
						{
							score = sentimentAnalysis(entry.getValue().toString());
						}
						
						if(entry.getKey().equals("createdAt"))
						{
							date = formatter.parse(entry.getValue().toString().replace("\"", ""));
							cal.setTimeZone(TimeZone.getTimeZone("UTC"));
							cal.setTime(date);
							cal.setTimeZone(TimeZone.getTimeZone(timeZoneID));
						}
						
						if(entry.getKey().equals("geoLocation"))
						{							
							for(Entry<String, JsonElement> geoEntry: entry.getValue().getAsJsonObject().entrySet())
							{
								if(geoEntry.getKey().equals("latitude"))
								{
									switch(score)
									{
										case -1:  	latitude_total_negative.add(Double.parseDouble(geoEntry.getValue().toString()));
												  	bw_coordinates_hour_negative[cal.get(Calendar.HOUR_OF_DAY)].append(geoEntry.getValue().toString() + "\t");
												  	bw_coordinates_day_negative[cal.get(Calendar.DAY_OF_WEEK)-1].append(geoEntry.getValue().toString() + "\t");
												  	bw_coordinates_date_negative[cal.get(Calendar.DAY_OF_MONTH)-1].append(geoEntry.getValue().toString() + "\t");
												  	break;
										case  0:  	latitude_total_neutral.add(Double.parseDouble(geoEntry.getValue().toString()));
												  	bw_coordinates_hour_neutral[cal.get(Calendar.HOUR_OF_DAY)].append(geoEntry.getValue().toString() + "\t");
												  	bw_coordinates_day_neutral[cal.get(Calendar.DAY_OF_WEEK)-1].append(geoEntry.getValue().toString() + "\t");
												  	bw_coordinates_date_neutral[cal.get(Calendar.DAY_OF_MONTH)-1].append(geoEntry.getValue().toString() + "\t");
												  	break;
										case  1:  	latitude_total_positive.add(Double.parseDouble(geoEntry.getValue().toString()));
													bw_coordinates_hour_positive[cal.get(Calendar.HOUR_OF_DAY)].append(geoEntry.getValue().toString() + "\t");
													bw_coordinates_day_positive[cal.get(Calendar.DAY_OF_WEEK)-1].append(geoEntry.getValue().toString() + "\t");
													bw_coordinates_date_positive[cal.get(Calendar.DAY_OF_MONTH)-1].append(geoEntry.getValue().toString() + "\t");
										  	      	break;
									}	
								}
								
								if(geoEntry.getKey().equals("longitude"))
								{
									switch(score)
									{
										case -1:  	longitude_total_negative.add(Double.parseDouble(geoEntry.getValue().toString()));
													bw_coordinates_hour_negative[cal.get(Calendar.HOUR_OF_DAY)].append(geoEntry.getValue().toString() + "\n");
													bw_coordinates_day_negative[cal.get(Calendar.DAY_OF_WEEK)-1].append(geoEntry.getValue().toString() + "\n");
													bw_coordinates_date_negative[cal.get(Calendar.DAY_OF_MONTH)-1].append(geoEntry.getValue().toString() + "\n");
													break;
										case  0:  	longitude_total_neutral.add(Double.parseDouble(geoEntry.getValue().toString()));
													bw_coordinates_hour_neutral[cal.get(Calendar.HOUR_OF_DAY)].append(geoEntry.getValue().toString() + "\n");
													bw_coordinates_day_neutral[cal.get(Calendar.DAY_OF_WEEK)-1].append(geoEntry.getValue().toString() + "\n");
													bw_coordinates_date_neutral[cal.get(Calendar.DAY_OF_MONTH)-1].append(geoEntry.getValue().toString() + "\n");
										  		  	break;
										case  1:  	longitude_total_positive.add(Double.parseDouble(geoEntry.getValue().toString()));
													bw_coordinates_hour_positive[cal.get(Calendar.HOUR_OF_DAY)].append(geoEntry.getValue().toString() + "\n");
													bw_coordinates_day_positive[cal.get(Calendar.DAY_OF_WEEK)-1].append(geoEntry.getValue().toString() + "\n");
													bw_coordinates_date_positive[cal.get(Calendar.DAY_OF_MONTH)-1].append(geoEntry.getValue().toString() + "\n");
										  	      	break;
									}	
								}
							}
						}
					}		
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			br_tweets.close();
			
			for(int i=0;i<latitude_total_positive.size();i++)
			{
				bw_coordinates_total_positive.append(latitude_total_positive.get(i).toString() + "\t" + longitude_total_positive.get(i).toString() + "\n");
			}
			bw_coordinates_total_positive.close();
			
			for(int i=0;i<latitude_total_negative.size();i++)
			{
				bw_coordinates_total_negative.append(latitude_total_negative.get(i).toString() + "\t" + longitude_total_negative.get(i).toString() + "\n");
			}
			bw_coordinates_total_negative.close();
			
			for(int i=0;i<latitude_total_neutral.size();i++)
			{
				bw_coordinates_total_neutral.append(latitude_total_neutral.get(i).toString() + "\t" + longitude_total_neutral.get(i).toString() + "\n");
			}
			bw_coordinates_total_neutral.close();
			
			for(int i=0;i<24;i++)
			{
				bw_coordinates_hour_negative[i].close();
				bw_coordinates_hour_positive[i].close();
				bw_coordinates_hour_neutral[i].close();
			}
			
			for(int i=0;i<7;i++)
			{
				bw_coordinates_day_negative[i].close();
				bw_coordinates_day_positive[i].close();
				bw_coordinates_day_neutral[i].close();
			}
			
			for(int i=0;i<30;i++)
			{
				bw_coordinates_date_negative[i].close();
				bw_coordinates_date_positive[i].close();
				bw_coordinates_date_neutral[i].close();
			}
		}
		catch(IOException ex)
		{
			
		}
	}
}
