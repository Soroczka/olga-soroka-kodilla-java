package com.kodilla.patterns.strategy.social;

import com.kodilla.patterns.strategy.social.media.FacebookPublisher;
import com.kodilla.patterns.strategy.social.media.SnapchatPublisher;

public class ZGeneration extends User {
    public ZGeneration(String username) {
        super(username);
        this.socialPublisher = new SnapchatPublisher();
    }
}
