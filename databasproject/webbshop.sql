DROP DATABASE IF EXISTS ShoeShop;
DROP DATABASE IF EXISTS webbshop;
CREATE DATABASE webbshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE webbshop;

CREATE TABLE Kund (
    kund_id INT AUTO_INCREMENT,
    fornamn VARCHAR(50) NOT NULL,
    efternamn VARCHAR(50) NOT NULL,
    ort VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (kund_id),
    INDEX idx_username (username)
) ENGINE=InnoDB COMMENT='Kundtabell med inloggningsuppgifter';


CREATE TABLE Produkt (
    produkt_id INT AUTO_INCREMENT,
    storlek INT NOT NULL,
    pris DECIMAL(10,2) NOT NULL CHECK (pris >= 0),
    marke VARCHAR(50) NOT NULL,
    farg VARCHAR(30) NOT NULL,
    lagerantal INT NOT NULL DEFAULT 0 CHECK (lagerantal >= 0),
    PRIMARY KEY (produkt_id),
    INDEX idx_marke_farg_storlek (marke, farg, storlek)
) ENGINE=InnoDB COMMENT='Produkttabell - innehåller skor';


CREATE TABLE Kategori (
    kategori_id INT AUTO_INCREMENT,
    namn VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (kategori_id)
) ENGINE=InnoDB COMMENT='Produktkategorier (Sandaler, Sportskor, etc.)';


CREATE TABLE ProduktKategori (
    produkt_id INT NOT NULL,
    kategori_id INT NOT NULL,
    PRIMARY KEY (produkt_id, kategori_id),
    
   
    FOREIGN KEY (produkt_id) REFERENCES Produkt(produkt_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    
 
    FOREIGN KEY (kategori_id) REFERENCES Kategori(kategori_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='Many-to-Many relation mellan Produkt och Kategori';


CREATE TABLE Bestallning (
    bestallning_id INT AUTO_INCREMENT,
    kund_id INT NOT NULL,
    datum DATE NOT NULL,
    status ENUM('ACTIVE', 'BETALD') NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (bestallning_id),
    
  
    FOREIGN KEY (kund_id) REFERENCES Kund(kund_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    
    INDEX idx_kund_status (kund_id, status)
) ENGINE=InnoDB COMMENT='Kundbeställningar';


CREATE TABLE Bestallningsrad (
    bestallning_id INT NOT NULL,
    produkt_id INT NOT NULL,
    antal INT NOT NULL DEFAULT 1 CHECK (antal > 0),
    pris_vid_kop DECIMAL(10,2) NOT NULL CHECK (pris_vid_kop >= 0),
    PRIMARY KEY (bestallning_id, produkt_id),
    
   
    FOREIGN KEY (bestallning_id) REFERENCES Bestallning(bestallning_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    
    
    FOREIGN KEY (produkt_id) REFERENCES Produkt(produkt_id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='Beställningsrader - produkter i varje beställning';


CREATE TABLE OutOfStock (
    produkt_id INT NOT NULL,
    out_of_stock_time DATETIME NOT NULL,
    PRIMARY KEY (produkt_id, out_of_stock_time),
    
    
    FOREIGN KEY (produkt_id) REFERENCES Produkt(produkt_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='Logg över produkter som tagit slut i lager';


INSERT INTO Kategori (namn) VALUES
('Sandaler'),
('Sportskor'),
('Löparskor'),
('Damskor'),
('Herrskor'),
('Barnskor'),
('Veganskor');


INSERT INTO Produkt (storlek, pris, marke, farg, lagerantal) VALUES
(38, 899.00, 'Ecco', 'Black', 15),    
(42, 1299.00, 'Nike', 'White', 20),
(40, 999.00, 'Adidas', 'Blue', 12),
(35, 599.00, 'Puma', 'Red', 18),
(41, 1199.00, 'Reebok', 'Green', 8),
(38, 950.00, 'Ecco', 'Brown', 10),
(43, 1399.00, 'Nike', 'Black', 14),
(38, 1099.00, 'Adidas', 'Pink', 16),
(40, 899.00, 'New Balance', 'Grey', 11),
(42, 1499.00, 'Ecco', 'Black', 9);


INSERT INTO ProduktKategori (produkt_id, kategori_id) VALUES
(1,1),(1,4),  
(2,2),(2,3),(2,5),  
(3,2),(3,5),  
(4,6),(4,2),  
(5,7),(5,2),  
(6,1),(6,5),  
(7,3),(7,5),  
(8,4),(8,2),  
(9,2),(9,5),  
(10,5),(10,7);  


INSERT INTO Kund (fornamn, efternamn, ort, username, password) VALUES
('Hisham', 'Saleh', 'Stockholm', 'Hisham1', '1234'),
('Sara', 'Svensson', 'Egypet', 'sara5', '4321'),
('Anna', 'Eriksson', 'Malmö', 'Anna10', '2233'),
('Hana', 'Omar', 'Göteborg', 'Hana55', '5544'),
('Layla', 'Karlsson', 'Oslo', 'Lal12', '1111'),
('Maria', 'Nilsson', 'Uppsala', 'Maria00', '0101');




INSERT INTO Bestallning (kund_id, datum, status) VALUES
(1, '2024-01-15', 'BETALD'),  
(2, '2024-01-20', 'BETALD'), 
(3, '2024-02-10', 'BETALD'),  
(4, '2024-02-25', 'BETALD'),  
(5, '2024-03-05', 'BETALD'),  
(1, '2024-03-12', 'BETALD'),  
(6, '2024-04-08', 'BETALD');  


INSERT INTO Bestallningsrad (bestallning_id, produkt_id, antal, pris_vid_kop) VALUES

(1,1,2,899.00),(1,2,1,1299.00),

(2,1,1,899.00),(2,8,1,1099.00),

(3,4,2,599.00),

(4,6,1,950.00),

(5,7,2,1399.00),(5,5,1,1199.00),(5,3,1,999.00),

(6,10,1,1499.00),(6,9,1,899.00),

(7,4,1,599.00);


DROP USER IF EXISTS 'dbUserPPtest'@'localhost';
CREATE USER 'dbUserPPtest'@'localhost' IDENTIFIED BY 'secretPassword';
GRANT ALL PRIVILEGES ON webbshop.* TO 'dbUserPPtest'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;



DROP PROCEDURE IF EXISTS AddToCart;

DELIMITER //

CREATE PROCEDURE AddToCart(
    IN p_kund_id INT,
    IN p_bestallning_id INT,
    IN p_produkt_id INT
)
procedure_block: BEGIN
    DECLARE v_bestallning_id INT;
    DECLARE v_stock INT;
    DECLARE v_pris DECIMAL(10,2);
    DECLARE v_existing_antal INT DEFAULT NULL;
    
   
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'ERROR' AS Status, 
               'Ett fel uppstod vid beställningen' AS Message;
    END;
    
    
    START TRANSACTION;
    
  
    IF NOT EXISTS (SELECT 1 FROM Produkt WHERE produkt_id = p_produkt_id) THEN
        ROLLBACK;
        SELECT 'ERROR' AS Status, 'Produkten finns inte' AS Message;
        LEAVE procedure_block;
    END IF;
    
    
    SELECT lagerantal, pris INTO v_stock, v_pris
    FROM Produkt WHERE produkt_id = p_produkt_id;
    
    
    IF v_stock IS NULL OR v_stock <= 0 THEN
        ROLLBACK;
        SELECT 'ERROR' AS Status, 'Produkten är slut i lager' AS Message;
        LEAVE procedure_block;
    END IF;
    
    
    IF p_bestallning_id IS NULL THEN
        
        SELECT bestallning_id INTO v_bestallning_id
        FROM Bestallning
        WHERE kund_id = p_kund_id AND status = 'ACTIVE'
        LIMIT 1;
        
        
        IF v_bestallning_id IS NULL THEN
            INSERT INTO Bestallning (kund_id, datum, status)
            VALUES (p_kund_id, CURDATE(), 'ACTIVE');
            SET v_bestallning_id = LAST_INSERT_ID();
        END IF;
    ELSE
        SET v_bestallning_id = p_bestallning_id;
        
       
        IF NOT EXISTS (
            SELECT 1 FROM Bestallning 
            WHERE bestallning_id = v_bestallning_id 
            AND kund_id = p_kund_id AND status = 'ACTIVE'
        ) THEN
            ROLLBACK;
            SELECT 'ERROR' AS Status, 
                   'Beställningen finns inte eller är inte aktiv' AS Message;
            LEAVE procedure_block;
        END IF;
    END IF;
    
    
    SELECT antal INTO v_existing_antal
    FROM Bestallningsrad
    WHERE bestallning_id = v_bestallning_id AND produkt_id = p_produkt_id;
    
    
    IF v_existing_antal IS NOT NULL THEN
        
        UPDATE Bestallningsrad SET antal = antal + 1
        WHERE bestallning_id = v_bestallning_id AND produkt_id = p_produkt_id;
    ELSE
        
        INSERT INTO Bestallningsrad (bestallning_id, produkt_id, antal, pris_vid_kop)
        VALUES (v_bestallning_id, p_produkt_id, 1, v_pris);
    END IF;
    
    
    UPDATE Produkt SET lagerantal = lagerantal - 1
    WHERE produkt_id = p_produkt_id;
    
    
    COMMIT;
    
    
    SELECT 'SUCCESS' AS Status, 
           'Produkten har lagts till i beställningen' AS Message,
           v_bestallning_id AS BestallningID;
END //

DELIMITER ;



DROP TRIGGER IF EXISTS check_stock_after_update;

DELIMITER //

CREATE TRIGGER check_stock_after_update
AFTER UPDATE ON Produkt
FOR EACH ROW
BEGIN
    
    IF NEW.lagerantal = 0 AND OLD.lagerantal > 0 THEN
        INSERT INTO OutOfStock (produkt_id, out_of_stock_time)
        VALUES (NEW.produkt_id, NOW());
    END IF;
END //

DELIMITER ;


SELECT ' FRÅGA 1 ' AS '';
SELECT DISTINCT k.fornamn AS Förnamn, k.efternamn AS Efternamn
FROM Kund k
JOIN Bestallning b ON k.kund_id = b.kund_id
JOIN Bestallningsrad br ON b.bestallning_id = br.bestallning_id
JOIN Produkt p ON br.produkt_id = p.produkt_id
JOIN ProduktKategori pk ON p.produkt_id = pk.produkt_id
JOIN Kategori kat ON pk.kategori_id = kat.kategori_id
WHERE p.marke = 'Ecco' AND p.farg = 'Black' AND p.storlek = 38 AND kat.namn = 'Sandaler';


SELECT ' FRÅGA 2 ' AS '';
SELECT kat.namn AS Kategori, COUNT(pk.produkt_id) AS Antal_Produkter
FROM Kategori kat
LEFT JOIN ProduktKategori pk ON kat.kategori_id = pk.kategori_id
GROUP BY kat.kategori_id, kat.namn
ORDER BY Antal_Produkter DESC;


SELECT 'FRÅGA 3 ' AS '';
SELECT k.fornamn AS Förnamn, k.efternamn AS Efternamn, 
       COALESCE(SUM(br.antal * br.pris_vid_kop), 0) AS Total_Summa
FROM Kund k
LEFT JOIN Bestallning b ON k.kund_id = b.kund_id
LEFT JOIN Bestallningsrad br ON b.bestallning_id = br.bestallning_id
GROUP BY k.kund_id, k.fornamn, k.efternamn
ORDER BY Total_Summa DESC;


SELECT ' FRÅGA 4 ' AS '';
SELECT k.ort AS Ort, SUM(br.antal * br.pris_vid_kop) AS Total_Värde
FROM Kund k
JOIN Bestallning b ON k.kund_id = b.kund_id
JOIN Bestallningsrad br ON b.bestallning_id = br.bestallning_id
GROUP BY k.ort
HAVING Total_Värde > 1000
ORDER BY Total_Värde DESC;


SELECT ' FRÅGA 5 ' AS '';
SELECT p.marke AS Märke, p.farg AS Färg, p.storlek AS Storlek, 
       SUM(br.antal) AS Antal_Sålda
FROM Produkt p
JOIN Bestallningsrad br ON p.produkt_id = br.produkt_id
GROUP BY p.produkt_id, p.marke, p.farg, p.storlek
ORDER BY Antal_Sålda DESC
LIMIT 5;


SELECT ' FRÅGA 6 ' AS '';
SELECT DATE_FORMAT(b.datum, '%Y-%m') AS Månad,
       DATE_FORMAT(b.datum, '%M %Y') AS Månad_Namn,
       SUM(br.antal * br.pris_vid_kop) AS Total_Försäljning
FROM Bestallning b
JOIN Bestallningsrad br ON b.bestallning_id = br.bestallning_id
GROUP BY DATE_FORMAT(b.datum, '%Y-%m'), DATE_FORMAT(b.datum, '%M %Y')
ORDER BY Total_Försäljning DESC
LIMIT 1;



SELECT '=' AS '';
SELECT '✓ ALLT ÄR KLART!' AS Status;
SELECT '=' AS '';

SELECT 'TABELLER SKAPADE:' AS Info;
SHOW TABLES;

SELECT '=' AS '';
SELECT 'STATISTIK:' AS Info;
SELECT 
    (SELECT COUNT(*) FROM Kund) AS Antal_Kunder,
    (SELECT COUNT(*) FROM Produkt) AS Antal_Produkter,
    (SELECT COUNT(*) FROM Kategori) AS Antal_Kategorier,
    (SELECT COUNT(*) FROM Bestallning) AS Antal_Bestallningar,
    (SELECT COUNT(*) FROM Bestallningsrad) AS Antal_Bestallningsrader;

SELECT '=' AS '';
SELECT '✓ DATABASE KOMPLETT!' AS '';
SELECT 'Nästa steg: Kompilera och kör Java-programmet' AS '';
SELECT '=' AS '';