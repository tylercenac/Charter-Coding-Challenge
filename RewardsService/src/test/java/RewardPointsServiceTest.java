import org.example.repository.RewardPointsRepository;
import org.example.service.RewardPointsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class RewardPointsServiceTest {

    @InjectMocks
    private RewardPointsService rewardPointsService;

    @MockBean
    private RewardPointsRepository rewardPointsRepository;

    @Test
    public void calculateRewardPointsTest(){

        int purchaseAmount = 120;
        int expectedRewardPointsEarned = 90;

        int actualRewardPointsEarned = rewardPointsService.calculateRewardPoints(purchaseAmount);

        assertEquals(expectedRewardPointsEarned, actualRewardPointsEarned);

    }

}
